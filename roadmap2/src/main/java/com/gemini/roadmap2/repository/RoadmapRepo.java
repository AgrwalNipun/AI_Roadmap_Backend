package com.gemini.roadmap2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gemini.roadmap2.models.Roadmap;




@Repository
public interface RoadmapRepo extends JpaRepository<Roadmap,Integer>{

    boolean existsByKeyword(String keyword);

    Roadmap findByKeyword(String keyword);

}
