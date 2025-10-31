package com.gemini.roadmap2.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

import java.util.Timer;

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
    System.out.println("Gem ini client initialized successfully");

}




    @Autowired
    RoadmapService roadmapService;




    ///////////Generates Keyword for the prompth


   public String generateKeyword(String prompt) {
    // Timer timer = new Timer();
        long startTime = System.currentTimeMillis();

        // System.out.println(prompt+"?????????????");
    
           String jsonPrompt = 
        "You are a keyword generator. " +
        "Extract 2 to 3 short lowercase keywords " +
        "that summarize the topic of this query. Include time constrains if it has that" +
        "Return ONLY the keywords, no explanation, punctuation, or extra text.\n\n" +
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
                
                GenerateContentConfig.builder().temperature(val).build()
        )
        ;

        long endTime = System.currentTimeMillis();

        System.out.println(endTime-startTime+"ms????Keyword Generation Time???????????");

        return response.text();
    }



    ///Generates Complete Roadmap 
    public String generateText(String prompt) {
String jsonPrompt = prompt +
    " Do not send anything else. Respond ONLY in JSON format like this. Make it have a tree-like structure if necessary:\n" +
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

        long startTime = System.currentTimeMillis();

        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                jsonPrompt,
                null
        );


        long endTime = System.currentTimeMillis();

        System.out.println(endTime-startTime+"ms????Complete Roadmap Generation TIme???????????");


        String textRes = response.text().replaceAll("```json", "");
         textRes = textRes.replaceAll("```", "");


         roadmapService.save(textRes);
         System.out.println("///////////Success");




        return textRes;
    }


   


}
