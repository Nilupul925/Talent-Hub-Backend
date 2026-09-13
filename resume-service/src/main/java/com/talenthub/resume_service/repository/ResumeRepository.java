package com.talenthub.resume_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talenthub.resume_service.entity.Resume;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    Optional<Resume> findByEmail(String email);
}
