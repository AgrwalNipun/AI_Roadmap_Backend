package com.gemini.roadmap2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gemini.roadmap2.DTOs.LoginResponse;
import com.gemini.roadmap2.DTOs.LoginUserDto;
import com.gemini.roadmap2.DTOs.RegisterUserDto;
import com.gemini.roadmap2.DTOs.RegisterUserResponseDto;
import com.gemini.roadmap2.models.User;
import com.gemini.roadmap2.service.AuthenticationService;
import com.gemini.roadmap2.service.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided details.")
    @PostMapping("/signup")
    public ResponseEntity<RegisterUserResponseDto> register(@RequestBody RegisterUserDto registerUserDto) {

        RegisterUserResponseDto registerUserResponseDto = authenticationService.signup(registerUserDto);

        // new RegisterUserResponseDto();

        return ResponseEntity.ok(registerUserResponseDto);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken)
                .setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<RegisterUserResponseDto> getCurrentUser(
            @Parameter(hidden = true) HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        String token = authHeader.substring(7);

        // Extract email from JWT
        String email = jwtService.extractUsername(token);

        // Load the user
        User user = authenticationService.getUserByEmail(email);

        RegisterUserResponseDto dto = new RegisterUserResponseDto();

        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setId(user.getId());

        return ResponseEntity.ok(dto);
    }




}
