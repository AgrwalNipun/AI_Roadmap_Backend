    package com.gemini.roadmap2.controller;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

import com.gemini.roadmap2.DTOs.RoadmapResponseDto;
import com.gemini.roadmap2.models.User;
import com.gemini.roadmap2.models.Roadmap.Roadmap;
import com.gemini.roadmap2.service.RoadmapService;
import com.gemini.roadmap2.service.UserService;
import com.gemini.roadmap2.service.UserSubstepProgressService;

    @RestController
    @CrossOrigin

    public class RoadmapController {

        @Autowired
        RoadmapService service;
        @Autowired 
        UserService userService;
        @Autowired
        UserSubstepProgressService progressService;

        @GetMapping("/get")
        ResponseEntity<?> getRoadmapById(@RequestParam int id){

            User user = userService.getLoggedInUser();

           RoadmapResponseDto res =  progressService.getRoadmapForUser((long)id, (long)user.getId());
            

            return new ResponseEntity<RoadmapResponseDto>(res,  HttpStatus.OK);

        }
        

        
        
    }