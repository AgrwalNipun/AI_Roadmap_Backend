package com.gemini.roadmap2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemini.roadmap2.DTOs.RoadmapResponseDto;
import com.gemini.roadmap2.DTOs.StepDto;
import com.gemini.roadmap2.DTOs.SubstepDto;
import com.gemini.roadmap2.models.User;
import com.gemini.roadmap2.models.UserSubstepProgress;
import com.gemini.roadmap2.models.Roadmap.Roadmap;
import com.gemini.roadmap2.models.Roadmap.RoadmapStep;
import com.gemini.roadmap2.models.Roadmap.RoadmapSubstep;
import com.gemini.roadmap2.repository.RoadmapRepo;
import com.gemini.roadmap2.repository.UserSubstepProgressRepo;

@Service
public class UserSubstepProgressService {

    @Autowired
    private UserSubstepProgressRepo progressRepo;


    @Autowired
    private RoadmapRepo roadmapRepo;

    public void initializeRoadmapProgress(User user, Roadmap roadmap) {

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
    System.out.println(list+"////////////////////////////////");
}

public RoadmapResponseDto getRoadmapForUser(Long roadmapId, Long userId) {

    Roadmap roadmap = roadmapRepo.findById(roadmapId.intValue())
            .orElseThrow(() -> new RuntimeException("Not found"));

    // 1) Fetch all progress for this user
    List<UserSubstepProgress> progressList = progressRepo.findByUserId(userId);


    for(UserSubstepProgress progress : progressList){
        System.out.println(progress.getId()+progress.getUser().getEmail());
    }

    // List<Long> progressIds = progressList.stream().map()
    List<Long> progressIds = progressList.stream()
        .map(p -> p.getRoadmapSubstep().getId().longValue())
        .toList();
;


    if(!progressIds.contains(roadmap.getSteps().get(0).getSubsteps().get(0).getId().longValue())){
        throw new RuntimeException("You cannot access this roadmap");
    }

    // 2) Convert to map → O(1) lookup
    Map<Object, Boolean> progressMap = progressList.stream()
            .collect(Collectors.toMap(
                    p -> p.getRoadmapSubstep().getId(),
                    UserSubstepProgress::isCompleted
            ));

    // 3) Build final Dto
    RoadmapResponseDto Dto = new RoadmapResponseDto();
    Dto.setId((long)roadmap.getId());
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



}
