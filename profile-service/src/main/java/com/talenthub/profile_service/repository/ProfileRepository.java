package com.talenthub.profile_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthub.profile_service.entity.Profile;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByEmail(String email);
    boolean existsByEmail(String email);
}
