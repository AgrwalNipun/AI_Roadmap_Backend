package com.gemini.roadmap2.models.RoadmapProgress;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gemini.roadmap2.models.User;
import com.gemini.roadmap2.models.Roadmap.Roadmap;

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
@Setter
@Getter
@Table(name = "roadmap_progress")
public class RoadmapProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;




    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference  
    private Roadmap roadmap;

    private boolean completed = false; 

    private int percentCompleted; // optional
}
