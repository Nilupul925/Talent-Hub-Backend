package com.talenthub.resume_service.exception;

public class ResumeNotFoundException extends RuntimeException {
    public ResumeNotFoundException(String email) {
        super("No Resume found for user email " + email);
    }
}
