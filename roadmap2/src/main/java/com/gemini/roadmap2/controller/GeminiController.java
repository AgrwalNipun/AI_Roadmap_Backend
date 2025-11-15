package com.gemini.roadmap2.controller;

import com.gemini.roadmap2.service.GeminiService;
import com.gemini.roadmap2.service.RoadmapService;

import java.util.Arrays;

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
    @Autowired
    private RoadmapService roadmapService;


    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/generate")
    public ResponseEntity<?> generate(@RequestParam String prompt) {
        
        System.out.println("Called the api"+prompt);
        final String keyword = geminiService.generateKeyword(prompt);

        char[] keywords = keyword.toCharArray();

    Arrays.sort(keywords);
    String sortedKeyword = new String(keywords);
    sortedKeyword=sortedKeyword.trim();
 

        if(roadmapService.existsByKeyword(sortedKeyword)){
            System.out.println("Roadmap exists for keyword: " + sortedKeyword);
            return ResponseEntity.ok(roadmapService.getRoadmapByKeyword(sortedKeyword));

        }


        return ResponseEntity.ok(geminiService.generateText(keyword));
    }
}
//keyword=aacdggiimmmnnoprry
//keyword=aaaacdggghiiiiiilmmmmmnnnoooopprrrstttyz