package com.gemini.roadmap2.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Roadmap {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  String id;

  String title;
  List<RoadmapStep> steps ;

  public Roadmap(String title,List<RoadmapStep> steps){
    this.title=title;
    this.steps=steps;
  }
  
  

    
}
