package com.gemini.roadmap2.DTOs;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserProgressDto {
        long userId;
        List<RoadmapProgress> roadmaps;
}
