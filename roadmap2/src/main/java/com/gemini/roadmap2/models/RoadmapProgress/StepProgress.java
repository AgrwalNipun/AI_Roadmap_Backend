package com.gemini.roadmap2.models.RoadmapProgress;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gemini.roadmap2.models.User;
import com.gemini.roadmap2.models.Roadmap.RoadmapStep;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
@Table(name = "step_progress")
public class StepProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-step-progress")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id")
    @JsonBackReference(value = "step-step-progress")
    private RoadmapStep step;

    private boolean completed = false;
}
