package com.gemini.roadmap2.controller;

import com.gemini.roadmap2.models.Roadmap.Roadmap;
import com.gemini.roadmap2.service.GeminiService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin

public class GeminiController {
    @Autowired
    private GeminiService geminiService;
    // @Autowired
    // private RoadmapProgressService progressService;


    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/generate")
    public ResponseEntity<Roadmap> generate(@RequestParam String prompt) {

        System.out.println("Called the api" + prompt);
        // final String keyword = geminiService.generateKeyword(prompt);
        Roadmap roadmap = geminiService.generateText(prompt);

        // progressService.assignUserToRoadmap(userService.getLoggedInUser(), roadmap);

        return ResponseEntity.ok(roadmap);
    }

}
// keyword=aacdggiimmmnnoprry
// keyword=aaaacdggghiiiiiilmmmmmnnnoooopprrrstttyz