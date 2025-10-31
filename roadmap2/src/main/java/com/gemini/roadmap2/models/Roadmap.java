package com.gemini.roadmap2.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Roadmap {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;





  //title : "Java roadmap 30days"
  String title;

  //keyword:"Java roadmap 30days "  but it ll be sorted
  String keyword;



  @OneToMany(mappedBy = "roadmap",cascade = CascadeType.ALL,orphanRemoval = true)
  List<RoadmapStep> steps ;


  public Roadmap() {}


  public Roadmap(String title,List<RoadmapStep> steps){
    this.title=title;
    this.steps=steps;
  }

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


  @Override
  public String toString() {
    return "Roadmap [id=" + id + ", title=" + title + ", keyword=" + keyword + ", steps=" + steps + "]";
  }
  
  

    
}
