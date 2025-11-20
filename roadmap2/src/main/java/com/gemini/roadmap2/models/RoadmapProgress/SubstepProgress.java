package com.gemini.roadmap2.models.RoadmapProgress;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gemini.roadmap2.models.User;
import com.gemini.roadmap2.models.Roadmap.RoadmapSubstep;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "substep_progress")
public class SubstepProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private RoadmapSubstep substep;

    private boolean completed = false;
}
