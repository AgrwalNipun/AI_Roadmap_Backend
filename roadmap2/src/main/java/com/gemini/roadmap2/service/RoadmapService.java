package com.gemini.roadmap2.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gemini.roadmap2.models.Roadmap;
import com.gemini.roadmap2.models.RoadmapStep;
import com.gemini.roadmap2.repository.RoadmapRepo;

@Service
public class RoadmapService {

    @Autowired
    RoadmapRepo repo;


    public Roadmap getById(int id){
        return repo.getReferenceById(id);
    }


   public boolean existsByKeyword(String sortedKeyword) {
    
    boolean exists = repo.existsByKeyword(sortedKeyword);

    System.out.println("Checking keyword: " + sortedKeyword + " => " + exists);
    return exists;
}

    

    public Roadmap getRoadmapByKeyword(String keyword){
        Roadmap roadmap = repo.findByKeyword(keyword);
        if(roadmap!=null){
            return roadmap;
        }
        return null;
    }


    Roadmap convertToRodmap(String roadmapString, String keywordString) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // keywordString.sort
            char[] keywords = keywordString.toCharArray();
            Arrays.sort(keywords);
            String sortedKeywords = new String(keywords);
            sortedKeywords=sortedKeywords.trim();
            Roadmap roadmap = mapper.readValue(roadmapString, Roadmap.class);
            roadmap.setKeyword(sortedKeywords);

            //simplified title setting
            roadmap.setTitle(keywordString);


            System.out.println(roadmap.getSteps().size()+"????????????? Steps Loaded???????????");
            
            //setting steps parent and substeps parent



            for(RoadmapStep step : roadmap.getSteps()){
                
                step.setRoadmap(roadmap);
                for(var substep : step.getSubsteps()){
                    substep.setStep(step);
                }
            }

            

            // System.out.println(roadmap.toString());
            return roadmap;

        } catch (Exception e) {
            System.out.println(e.toString());

            return new Roadmap();

        }

    }



    Roadmap saveRoadmap(String roadmapString, String keywords){
        System.out.println("Hereeeeee");

        Roadmap roadmap = convertToRodmap(roadmapString, keywords);

        Roadmap saved = repo.save(roadmap);
        System.out.println(saved.toString()+"????????????? Saved Roadmap???????????");
        saved.getSteps().forEach(s -> System.out.println("Saved step id = " + s.getId()));


        return saved;

    }

}
