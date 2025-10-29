package com.gemini.roadmap2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemini.roadmap2.repository.RoadmapRepo;

@Service
public class RoadmapService   {
    

    @Autowired
    RoadmapRepo repo;

    boolean keywordExists(String keyword){
        boolean res =repo.existsByKeyword(keyword);
        return res;
    }


}
