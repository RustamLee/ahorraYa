package com.rustam.lee.ahorra_ya.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private UUID userId;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, UUID userId) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
