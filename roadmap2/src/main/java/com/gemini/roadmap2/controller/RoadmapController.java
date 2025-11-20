    package com.gemini.roadmap2.controller;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

import com.gemini.roadmap2.models.Roadmap.Roadmap;
import com.gemini.roadmap2.service.RoadmapService;

    @RestController
    @CrossOrigin

    public class RoadmapController {

        @Autowired
        RoadmapService service;

        @GetMapping("/get")
        ResponseEntity<?> getRoadmapById(@RequestParam int id){

            return new ResponseEntity<Roadmap>(service.getById(id),  HttpStatus.OK);

        }
        
        
    }