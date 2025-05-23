package com.domain.exception.custom;

public class InvalidResetTokenException extends RuntimeException{
    public InvalidResetTokenException (String message){
        super(message);
    }
}
