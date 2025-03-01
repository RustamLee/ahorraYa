package com.rustam.lee.ahorra_ya.presentation.controllers;

import com.rustam.lee.ahorra_ya.infrastructure.repositories.UserRepository;
import com.rustam.lee.ahorra_ya.security.AuthRequest;
import com.rustam.lee.ahorra_ya.security.AuthResponse;
import com.rustam.lee.ahorra_ya.security.CustomUserDetails;
import com.rustam.lee.ahorra_ya.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;


    public AuthController(JwtTokenService jwtTokenService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
        UserDetails user = userDetailsService.loadUserByUsername(authRequest.getEmail());
        if (user instanceof CustomUserDetails) {
            CustomUserDetails customUser = (CustomUserDetails) user;
            String token = jwtTokenService.generateToken(user.getUsername());
            AuthResponse authResponse = new AuthResponse(token, user.getUsername(), customUser.getUserId());
            return ResponseEntity.ok(authResponse);
        } else {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
