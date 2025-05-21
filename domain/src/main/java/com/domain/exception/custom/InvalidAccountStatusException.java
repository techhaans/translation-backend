package com.domain.exception.custom;

public class InvalidAccountStatusException extends RuntimeException{
    public InvalidAccountStatusException(String message) {
        super(message);
    }

    public InvalidAccountStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
