package com.talenthub.auth_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

import com.talenthub.auth_service.entity.Role;
import com.talenthub.auth_service.entity.User;

@Data 
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private LocalDateTime createdAt;

    public static UserResponse fromEntity(User user) {
        UserResponse dto = new UserResponse();

        dto.id = user.getId();
        dto.firstName = user.getFirstName();
        dto.lastName = user.getLastName();
        dto.email = user.getEmail();
        dto.role = user.getRole();
        dto.createdAt = user.getCreatedAt();
        
        return dto;
    }

}
