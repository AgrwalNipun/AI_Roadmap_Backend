package com.gemini.roadmap2.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "roadmap_substep")
public class RoadmapSubstep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String aim;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id")
    @JsonBackReference
    private RoadmapStep step;

    public RoadmapSubstep() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoadmapStep getStep() {   // 🔥 FIXED: was private earlier
        return step;
    }

    public void setStep(RoadmapStep step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "RoadmapSubstep [id=" + id + ", aim=" + aim + ", description=" + description + "]";
    }
}
