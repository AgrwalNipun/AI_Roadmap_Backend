package com.gemini.roadmap2.models.Roadmap;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "roadmap")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Roadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String keyword;

    @OneToMany(mappedBy = "roadmap", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // parent side
    private List<RoadmapStep> steps = new ArrayList<>();

    public Roadmap() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<RoadmapStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RoadmapStep> steps) {
        this.steps = steps;
    }

    // helper for bidirectional relationship
    public void addStep(RoadmapStep step) {
        steps.add(step);
        step.setRoadmap(this);
    }

    public void removeStep(RoadmapStep step) {
        steps.remove(step);
        step.setRoadmap(null);
    }

    @Override
    public String toString() {
        return "Roadmap [id=" + id + ", title=" + title + ", keyword=" + keyword + "]";
    }
}
