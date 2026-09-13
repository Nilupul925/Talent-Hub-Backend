package com.talenthub.auth_service.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class AuthenticatedUser {
    private final Integer userId;
    private final String email;
    private final String role;

}
