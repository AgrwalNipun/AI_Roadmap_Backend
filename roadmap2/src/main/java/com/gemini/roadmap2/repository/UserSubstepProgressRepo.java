package com.gemini.roadmap2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gemini.roadmap2.models.UserSubstepProgress;





@Repository
public interface UserSubstepProgressRepo extends JpaRepository<UserSubstepProgress, Long> {
    

    List<UserSubstepProgress> findByUserId(Long userId);


}
