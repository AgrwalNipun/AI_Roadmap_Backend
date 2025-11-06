package com.gemini.roadmap2.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
    

    public SecurityFilterChain securityFilterChain(HttpServletRequest request) {
        SecurityFilterChain sfc = null;
        // request.authentic?ate(HttpServletResponse.,);

        return sfc;


    }
}
