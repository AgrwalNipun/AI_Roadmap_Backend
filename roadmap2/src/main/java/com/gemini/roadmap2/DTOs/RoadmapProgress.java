package com.gemini.roadmap2.DTOs;

import com.gemini.roadmap2.models.Roadmap.Roadmap;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoadmapProgress{

        // long id;
        Roadmap roadmap;
        int substepsCompleted;
        int totalSubsteps;
}
