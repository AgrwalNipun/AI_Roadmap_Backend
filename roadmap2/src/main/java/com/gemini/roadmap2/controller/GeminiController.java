package com.gemini.roadmap2.controller;

import com.gemini.roadmap2.service.GeminiService;
import com.gemini.roadmap2.service.RoadmapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeminiController {
    @Autowired
    private GeminiService geminiService;
    @Autowired
    private RoadmapService roadmapService;


    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/generate")
    public String generate(@RequestParam String prompt) {
        
        
        final String keywords = geminiService.generateKeyword(prompt);


        if(roadmapService.titleExists(keywords)){

            return "Keyword already exists in the database.";

        }


        return geminiService.generateText(keywords);
    }
}
//keyword=aacdggiimmmnnoprry
//keyword=aaaacdggghiiiiiilmmmmmnnnoooopprrrstttyz