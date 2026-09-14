package com.talenthub.profile_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Table(name = "profiles", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    @Email 
    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    private String phone;
    
    private String address;

    @Column(columnDefinition = "TEXT")
    private String about;

    private String linkedin;
    private String github;
    private String website;

    // Only the Supabase Storage public URL is stored - never binary image data.
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Profile(String email, String firstName, String lastName, String phone, String address, String about, String linkedin, String github, String website, String profileImageUrl){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.about = about;
        this.linkedin = linkedin;
        this.github = github;
        this.website = website;
        this.profileImageUrl = profileImageUrl;
    }

}
