package com.domain.exception.custom;

public class ResumeUploadException extends RuntimeException {

    public ResumeUploadException(String message) {
        super(message);
    }

    public ResumeUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
