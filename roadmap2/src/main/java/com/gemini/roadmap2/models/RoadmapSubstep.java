package com.gemini.roadmap2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RoadmapSubstep{
    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    String Id;


    String aim;
    String desc;


    @ManyToOne
    @JoinColumn(name = "step_id")
    private RoadmapStep step;

    public RoadmapSubstep(){}

    public RoadmapSubstep(String aim,String desc){
        this.aim = aim;
        this.desc = desc;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public RoadmapStep getStep() {
        return step;
    }

    public void setStep(RoadmapStep step) {
        this.step = step;
    }


}
