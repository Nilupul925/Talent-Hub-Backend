package com.talenthub.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthub.auth_service.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsById(Integer id);
    boolean existsByEmail(String email);
}
