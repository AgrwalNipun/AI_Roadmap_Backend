package com.gemini.roadmap2.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "steps")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoadmapStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String aim;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    @JsonBackReference // back side to avoid recursion
    private Roadmap roadmap;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // parent for substeps
    private List<RoadmapSubstep> substeps = new ArrayList<>();

    public RoadmapStep() {}

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

    public Roadmap getRoadmap() {
        return roadmap;
    }

    public void setRoadmap(Roadmap roadmap) {
        this.roadmap = roadmap;
    }

    public List<RoadmapSubstep> getSubsteps() {
        return substeps;
    }

    public void setSubsteps(List<RoadmapSubstep> substeps) {
        this.substeps = substeps;
    }

    // helper methods
    public void addSubstep(RoadmapSubstep substep) {
        substeps.add(substep);
        substep.setStep(this);
    }

    public void removeSubstep(RoadmapSubstep substep) {
        substeps.remove(substep);
        substep.setStep(null);
    }

    @Override
    public String toString() {
        return "RoadmapStep [id=" + id + ", aim=" + aim + ", description=" + description + "]";
    }
}
