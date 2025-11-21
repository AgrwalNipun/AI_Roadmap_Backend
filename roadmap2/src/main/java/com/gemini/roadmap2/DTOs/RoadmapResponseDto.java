package com.gemini.roadmap2.DTOs;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoadmapResponseDto {
    private Long id;
    private String title;
    private List<StepDto> steps;    
}
