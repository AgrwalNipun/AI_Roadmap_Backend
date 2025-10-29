package com.gemini.roadmap2.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "steps")
public class RoadmapStep{
    
    @Id
    int id;


    String aim;
    String description;

    @ManyToOne
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;



    @OneToMany(mappedBy = "step",cascade = CascadeType.ALL,orphanRemoval = true)
    List<RoadmapSubstep> substeps;


    public RoadmapStep(){}

    public RoadmapStep(String aim,String description, List<RoadmapSubstep> substeps){
        this.aim = aim;
        this.description = description;
        this.substeps=substeps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String description) {
        this.description = description;
    }

    public List<RoadmapSubstep> getSubsteps() {
        return substeps;
    }

    public void setSubsteps(List<RoadmapSubstep> substeps) {
        this.substeps = substeps;
    }


}
