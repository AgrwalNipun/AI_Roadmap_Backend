package com.gemini.roadmap2.service;

import com.gemini.roadmap2.models.Roadmap.Roadmap;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final Client client;

    public GeminiService(@Value("${google.ai.api.key}") String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("Google AI API key is missing!");
        }
        this.client = Client.builder().apiKey(apiKey).build();
        System.out.println("Gemini client initialized successfully");

    }

    @Autowired
    private RoadmapService roadmapService;




    /////////// Generates Keyword for the prompt

    public String generateKeyword(String prompt) {
        String jsonPrompt = "You are a keyword generator. " +
                "Extract 2 to 3 short lowercase keywords " +
                "that summarize the topic of this query. Include time constrains if it has that" +
                "Always generate the same keyword for the same title using deterministic logic." +
                "Return ONLY the keywords, no explanation, punctuation, or extra text.Do not send any break or /n \n\n"
                +
                "Query: " + prompt + "\n\n" +
                "Example:\n" +
                "Input: Write a roadmap for learning Spring Boot\n" +
                "Output: spring boot roadmap\n\n" +
                "Now output only the keywords:";

        //// used to set temperature the lesser the temps the consistent the reuslt
        float val = 0;

        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                jsonPrompt,

                GenerateContentConfig.builder().temperature(val).build());


        return response.text();
    }

    /// Generates Complete Roadmap from keywords
    @SuppressWarnings("unused")
    public Roadmap generateText(String prompt) {

        String keyword = generateKeyword(prompt);

        char[] keywords = keyword.toCharArray();

        Arrays.sort(keywords);
        String sortedKeyword = new String(keywords);
        sortedKeyword = sortedKeyword.trim();

        Roadmap roadmap = new Roadmap();
        if(roadmapService.existsByKeyword(sortedKeyword)){
            System.out.println("Roadmap exists for keyword: " + sortedKeyword);
             roadmap =  roadmapService.getRoadmapByKeyword(sortedKeyword);




        }


else{
        String jsonPrompt = keyword +
                " Do not send anything else. Respond ONLY in JSON format like this. Make it have a tree-like structure if necessary:\n"
                +
                "{\n" +
                "  \"steps\": [\n" +
                "    {\n" +
                "      \"aim\": \"\",\n" +
                "      \"description\": \"\",\n" +
                "      \"substeps\": [\n" +
                "        { \"aim\": \"\", \"description\": \"\" }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"aim\": \"\",\n" +
                "      \"description\": \"\",\n" +
                "      \"substeps\": []\n" +
                "    }\n" +
                "  ]\n" +
                "} ";


        
        GenerateContentConfig config = GenerateContentConfig.builder()
                .responseMimeType("application/json")
                // setResponseFormat("JSON")
                // .maxOutputTokens(4096)
                .temperature(0.2f)
                .build();

        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                jsonPrompt,
                config);



        String textRes = response.text().replaceAll("```json", "");
        textRes = textRes.replaceAll("```", "");

         roadmap = roadmapService.saveRoadmap(textRes, keyword, sortedKeyword);
}


            return roadmap; 
        // System.out.println("///////////Success");
    }

}
