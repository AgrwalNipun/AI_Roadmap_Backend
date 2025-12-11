package com.gemini.roadmap2.controller;

import com.gemini.roadmap2.models.User;
import com.gemini.roadmap2.models.Roadmap.Roadmap;
import com.gemini.roadmap2.service.GeminiService;
import com.gemini.roadmap2.service.UserService;
import com.gemini.roadmap2.service.UserSubstepProgressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeminiController {
    @Autowired
    private GeminiService geminiService;
    // @Autowired
    // private RoadmapProgressService progressService;



    @Autowired
    private UserSubstepProgressService userSubstepProgressService;

    @Autowired
    private UserService userService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/generate")
    public ResponseEntity<?> generate(@RequestParam String prompt) {

        System.out.println("Called the api" + prompt);
        // final String keyword = geminiService.generateKeyword(prompt);


        try{      Roadmap roadmap = geminiService.generateText(prompt);

        // progressService.assignUserToRoadmap(userService.getLoggedInUser(), roadmap);

            User user = userService.getLoggedInUser();

            userSubstepProgressService.initializeRoadmapProgress(user, roadmap);


        return ResponseEntity.ok(roadmap);
    }
    catch(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
        
    }
  
    }

}
// keyword=aacdggiimmmnnoprry
// keyword=aaaacdggghiiiiiilmmmmmnnnoooopprrrstttyz