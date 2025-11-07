package com.gemini.roadmap2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gemini.roadmap2.DTOs.LoginUserDto;
import com.gemini.roadmap2.DTOs.RegisterUserDto;
import com.gemini.roadmap2.models.User;
import com.gemini.roadmap2.repository.UserRepo;

@Service
public class AuthenticationService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authManager;

    public User signup(RegisterUserDto input){

        User user = new User();
        user.setFullName(input.getfullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepo.save(user);

    }

    public User authenticate(LoginUserDto input){
        authManager.authenticate(

        new UsernamePasswordAuthenticationToken(
            input.getEmail(),
            input.getPassword()

        ));

        return userRepo.findByEmail(input.getEmail()).orElseThrow();

    }

}
