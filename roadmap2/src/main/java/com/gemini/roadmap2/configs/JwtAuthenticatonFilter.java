package com.gemini.roadmap2.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
// import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.lang.NonNull;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.gemini.roadmap2.service.JwtService;

@Component
public class JwtAuthenticatonFilter extends OncePerRequestFilter {

    // @Autowired
    // private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String requestURI = request.getRequestURI();
        System.out.println("\n🔹 [JwtFilter] Incoming request: " + requestURI);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("⚠️ No JWT token found in header — skipping filter.");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            System.out.println("🟢 Extracted JWT: " + jwt.substring(0, Math.min(20, jwt.length())) + "...");

            final String userEmail = jwtService.extractUsername(jwt);
            System.out.println("👤 Extracted username: " + userEmail);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                boolean isValid = jwtService.isTokenValid(jwt, userDetails);
                System.out.println("✅ Token valid: " + isValid);

                if (isValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("🔐 Authentication set in SecurityContext for user: " + userEmail);
                } else {
                    System.out.println("❌ Token invalid for user: " + userEmail);
                }
            } else if (authentication != null) {
                System.out.println("ℹ️ Authentication already exists: " + authentication.getName());
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            // System.out.println("💥 Exception in JWT filter: " + exception.getMessage());
            // handlerExceptionResolver.resolveException(request, response, null, exception);
            throw exception;
        }
    }
}
