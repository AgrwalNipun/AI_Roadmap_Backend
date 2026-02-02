package com.gemini.roadmap2.DTOs;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoadmapProgress{

        // long id;
        // Roadmap roadmap;
        String roadmapTitle;
        long id;
        int substepsCompleted;
        int totalSubsteps;
}
