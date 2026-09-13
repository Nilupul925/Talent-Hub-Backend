package com.talenthub.resume_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talenthub.resume_service.dto.ResumeRequest;
import com.talenthub.resume_service.dto.ResumeResponse;
import com.talenthub.resume_service.service.ResumeService;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping()
    public ResponseEntity<ResumeResponse> getResume(@RequestHeader("X-User") String email) {
        return ResponseEntity.ok(resumeService.get(email));
    }

    @PutMapping()
    public ResponseEntity<ResumeResponse> saveResume(
            @RequestHeader("X-User") String email,
            @Valid @RequestBody ResumeRequest request) {
        return ResponseEntity.ok(resumeService.save(email, request));
    }

    @DeleteMapping("/delete")
    public String deleteResume(@RequestParam("email") String email) {
        return resumeService.delete(email);
    }

}