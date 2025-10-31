    package com.gemini.roadmap2.service;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.gemini.roadmap2.models.Roadmap;
    import com.gemini.roadmap2.repository.RoadmapRepo;

    @Service
    public class RoadmapService   {
        

        @Autowired
        RoadmapRepo repo;

        boolean keywordExists(String keyword){
            boolean res =repo.existsByKeyword(keyword);
            return res;
        }


        Roadmap save(String roadmapString){

            ObjectMapper mapper = new ObjectMapper();

           try{

           Roadmap roadmap = mapper.readValue(roadmapString, Roadmap.class);
           System.out.println(roadmap.toString());
            return roadmap;

           }catch(Exception e){
                System.out.println(e.toString());

                return new Roadmap();

           } 



        }


    }
