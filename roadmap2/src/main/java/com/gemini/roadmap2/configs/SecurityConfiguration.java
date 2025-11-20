package com.gemini.roadmap2.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

@Autowired
AuthenticationProvider authenticationProvider;

@Autowired
JwtAuthenticatonFilter jwtAuthenticatonFilter;


@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

return http.csrf(csrf -> csrf
        .disable())
        .authorizeHttpRequests(requests -> requests
                .requestMatchers("/auth/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html")
                .permitAll()
                .anyRequest()
                .authenticated())
        .sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticatonFilter, UsernamePasswordAuthenticationFilter.class)
        .build();

}

}
