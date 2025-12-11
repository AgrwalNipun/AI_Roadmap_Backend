package com.gemini.roadmap2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemini.roadmap2.DTOs.RoadmapProgress;
import com.gemini.roadmap2.DTOs.RoadmapResponseDto;
import com.gemini.roadmap2.DTOs.StepDto;
import com.gemini.roadmap2.DTOs.SubstepDto;
import com.gemini.roadmap2.DTOs.UpdateProgressDto;
import com.gemini.roadmap2.DTOs.UserProgressDto;
import com.gemini.roadmap2.models.User;
import com.gemini.roadmap2.models.Progress.UserRoadmapProgress;
import com.gemini.roadmap2.models.Progress.UserSubstepProgress;
import com.gemini.roadmap2.models.Roadmap.Roadmap;
import com.gemini.roadmap2.models.Roadmap.RoadmapStep;
import com.gemini.roadmap2.models.Roadmap.RoadmapSubstep;
import com.gemini.roadmap2.repository.RoadmapRepo;
import com.gemini.roadmap2.repository.UserRoadmapProgressRepo;
import com.gemini.roadmap2.repository.UserSubstepProgressRepo;

@Service
public class UserSubstepProgressService {

    @Autowired
    private UserSubstepProgressRepo progressRepo;

    @Autowired
    private RoadmapRepo roadmapRepo;

    @Autowired
    private UserRoadmapProgressRepo roadmapProgressRepo;


    boolean existsByUserIdAndRoadmapId(long userId, long roadmapId){
        return roadmapProgressRepo.existsByUserIdAndRoadmapId(userId,roadmapId);
    }

    public void initializeRoadmapProgress(User user, Roadmap roadmap) {

        boolean exists = existsByUserIdAndRoadmapId((long)user.getId(),(long)roadmap.getId());

        if(exists){
            // throw new Error("Roadmap already generated for the user ");
            return;
        }

        List<UserSubstepProgress> list = new ArrayList<>();

        for (RoadmapStep step : roadmap.getSteps()) {
            for (RoadmapSubstep sub : step.getSubsteps()) {

                UserSubstepProgress p = new UserSubstepProgress();
                p.setUser(user);
                p.setRoadmapSubstep(sub);
                p.setCompleted(false);

                list.add(p);
            }
        }

        // batch save = faster than saving inside loop
        progressRepo.saveAll(list);
        // System.out.println(list + "////////////////////////////////");

        UserRoadmapProgress rp = new UserRoadmapProgress();

        rp.setRoadmap(roadmap);
        rp.setUser(user);
        rp.setSubstepsCompleted(0);
        rp.setTotalSubsteps(list.size());

        roadmapProgressRepo.save(rp);

        System.out.println(rp.toString() + "SAVED");

    }

    public RoadmapResponseDto getRoadmapForUser(Long roadmapId, Long userId) {

        Roadmap roadmap = roadmapRepo.findById(roadmapId.intValue())
                .orElseThrow(() -> new RuntimeException("Not found"));

        
        List<Integer> substepIds = roadmap.getSteps().stream()
                .flatMap(step -> step.getSubsteps().stream())
                .map(RoadmapSubstep::getId)
                .collect(Collectors.toList());

        List<UserSubstepProgress> progressList = progressRepo.findByUserIdAndRoadmapSubstepIdIn(userId, substepIds);

        if (progressList.isEmpty()) {
            throw new RuntimeException("You cannot access this roadmap");
        }

        Map<Object, Boolean> progressMap = progressList.stream()
                .collect(Collectors.toMap(
                        p -> p.getRoadmapSubstep().getId(),
                        UserSubstepProgress::isCompleted,
                        (a,b)->a||b
                    ));

        // 3) Build final Dto
        RoadmapResponseDto Dto = new RoadmapResponseDto();
        Dto.setId((long) roadmap.getId());
        Dto.setTitle(roadmap.getTitle());

        List<StepDto> stepDtos = new ArrayList<>();

        for (RoadmapStep step : roadmap.getSteps()) {

            StepDto stepDto = new StepDto();
            stepDto.setId(step.getId());
            stepDto.setAim(step.getAim());
            stepDto.setDescription(step.getDescription());

            List<SubstepDto> substepDtos = new ArrayList<>();

            for (RoadmapSubstep sub : step.getSubsteps()) {

                SubstepDto sd = new SubstepDto();   
                sd.setId(sub.getId());
                sd.setAim(sub.getAim());
                sd.setDescription(sub.getDescription());

                // 👇 Check completion using map (no nested loops)
                sd.setCompleted(progressMap.getOrDefault(sub.getId(), false));

                substepDtos.add(sd);
            }

            stepDto.setSubsteps(substepDtos);
            stepDtos.add(stepDto);
        }

        Dto.setSteps(stepDtos);
        return Dto;
    }

    public UpdateProgressDto updateSubstepProgress(UpdateProgressDto dto, User user) {

        long substepId = dto.getId();
        long userId = user.getId();

        // System.out.println(substepId);
        UserSubstepProgress progress = progressRepo.findByUserIdAndRoadmapSubstepId(userId, substepId);
        // System.out.println(progress.getId()+"//"+progress.getUser()+"//"+progress.isCompleted());

        // System.out.println("Fetched progress row:");
        // System.out.println("progress_id = " + progress.getId());
        // System.out.println("substep_id = " + progress.getRoadmapSubstep().getId());
        // System.out.println("completed_before = " + progress.isCompleted());

        if (dto.isCompleted() != progress.isCompleted()) {

            int roadmapId = progress.getRoadmapSubstep().getStep().getRoadmap().getId();

            UserRoadmapProgress roadmapProgress = roadmapProgressRepo.findByUserIdAndRoadmapId(userId,
                    (long) roadmapId);

            if (dto.isCompleted() && !progress.isCompleted()) {
                // increment count
                roadmapProgress.setSubstepsCompleted(
                        roadmapProgress.getSubstepsCompleted() + 1);
            } else if (!dto.isCompleted() && progress.isCompleted()) {
                // decrement count
                roadmapProgress.setSubstepsCompleted(
                        roadmapProgress.getSubstepsCompleted() - 1);
            }

            roadmapProgressRepo.save(roadmapProgress);   
        }

        progress.setCompleted(dto.isCompleted());

        UserSubstepProgress progress2 = progressRepo.save(progress);

        UpdateProgressDto resDto = new UpdateProgressDto();

        resDto.setId(progress2.getRoadmapSubstep().getId());
        resDto.setCompleted(progress2.isCompleted());

        return resDto;

    }

    public UserProgressDto getAllProgressByUserId(Long userId){
        List<UserRoadmapProgress> progresses= roadmapProgressRepo.findByUserId(userId);

        UserProgressDto dto = new UserProgressDto();
        
        if(progresses.size()>=1)
        dto.setUserId(progresses.get(0).getUser().getId());
        
        
        List<RoadmapProgress> roadmaps =  new ArrayList<>();
        
        for(UserRoadmapProgress progress : progresses){
            RoadmapProgress temp = new RoadmapProgress();
            temp.setRoadmapTitle(progress.getRoadmap().getTitle());
            temp.setSubstepsCompleted(progress.getSubstepsCompleted());
            temp.setTotalSubsteps(progress.getTotalSubsteps()); 
            temp.setId(progress.getRoadmap().getId());
            roadmaps.add(temp);
        }
        dto.setRoadmaps(roadmaps);

        return dto;

        
    }

}
