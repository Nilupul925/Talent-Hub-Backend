package com.talenthub.portfolio_service.security;

import lombok.Data;

@Data 
public class AuthenticatedUser {
    private final Integer userId;
    private final String email;
    private final String role;

    public AuthenticatedUser(Integer userId, String email, String role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }
}
