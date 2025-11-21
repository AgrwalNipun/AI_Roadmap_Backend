package com.gemini.roadmap2.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubstepDto {
    private long id;
    private String aim;
    private String description;
    private boolean completed;   
}
