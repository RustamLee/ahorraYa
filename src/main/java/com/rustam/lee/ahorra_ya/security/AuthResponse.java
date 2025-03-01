package com.rustam.lee.ahorra_ya.security;

import java.util.UUID;

public class AuthResponse {

    private String token;
    private String email;
    private UUID userId;

    public AuthResponse(String token, String email, UUID userId) {
        this.token = token;
        this.email = email;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }


    // Сеттеры
    public void setToken(String token) {
        this.token = token;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
