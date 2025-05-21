package com.domain.exception.custom;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException (String message){
        super(message);
    }
}
