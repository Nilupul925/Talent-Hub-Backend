package com.talenthub.profile_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.talenthub.profile_service.entity.Profile;
import com.talenthub.profile_service.service.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }

    @GetMapping()
    public ResponseEntity<Profile> getProfile(@Valid @RequestHeader("X-User") String email) {
        return ResponseEntity.ok(profileService.get(email));
    }

    @PostMapping()
    public ResponseEntity<Profile> createProfile(@Valid @RequestHeader("X-User") String email, @RequestBody Profile request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.create(email, request));
    }
    @PutMapping()
    public ResponseEntity<Profile> updateProfile(@Valid @RequestHeader("X-User") String email, @RequestBody Profile request) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.update(email, request));
    }

}
