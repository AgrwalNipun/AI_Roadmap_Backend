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
    String id;


    String aim;
    String desc;

    @ManyToOne
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;



    @OneToMany(mappedBy = "step",cascade = CascadeType.ALL,orphanRemoval = true)
    List<RoadmapSubstep> substeps;


    public RoadmapStep(){}

    public RoadmapStep(String aim,String desc, List<RoadmapSubstep> substeps){
        this.aim = aim;
        this.desc = desc;
        this.substeps=substeps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<RoadmapSubstep> getSubsteps() {
        return substeps;
    }

    public void setSubsteps(List<RoadmapSubstep> substeps) {
        this.substeps = substeps;
    }


}
