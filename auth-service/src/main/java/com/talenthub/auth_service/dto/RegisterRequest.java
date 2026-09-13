package com.talenthub.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class RegisterRequest {

    @NotBlank(message = "firstName is required")
    @Size(min = 2, max = 50, message = "firstame must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "lastName is required")
    @Size(min = 2, max = 50, message = "lastame must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 3, message = "password must be at least 3 characters")
    private String password;

}
