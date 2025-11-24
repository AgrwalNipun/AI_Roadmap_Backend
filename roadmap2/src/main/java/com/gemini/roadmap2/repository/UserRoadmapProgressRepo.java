package com.gemini.roadmap2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.roadmap2.models.Progress.UserRoadmapProgress;

public interface UserRoadmapProgressRepo extends JpaRepository<UserRoadmapProgress, Long> {

    UserRoadmapProgress findByUserIdAndRoadmapId(Long userId, Long roadmapId);

    List<UserRoadmapProgress> findByUserId(Long userId);

}


