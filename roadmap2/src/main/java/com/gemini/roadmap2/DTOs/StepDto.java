package com.gemini.roadmap2.DTOs;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepDto {
    private long id;
    private String aim;
    private String description;

    private List<SubstepDto> substeps;
}
