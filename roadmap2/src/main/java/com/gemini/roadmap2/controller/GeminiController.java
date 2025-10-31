package com.gemini.roadmap2.controller;

import com.gemini.roadmap2.service.GeminiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeminiController {

    private  GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/generate")
    public String generate(@RequestParam String prompt) {
        final String keywords = geminiService.generateKeyword(prompt);

        return geminiService.generateText(keywords);
    }
}
