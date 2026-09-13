package com.talenthub.resume_service.exception;

public class InvalidResumeDataException extends RuntimeException {
    public InvalidResumeDataException(String message) {
        super(message);
    }
}
