package com.gemini.roadmap2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gemini.roadmap2.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {
 
    Optional<User> findByEmail(String email);
    
}
