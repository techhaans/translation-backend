package com.domain.exception.custom;

public class AuthenticationFailedException extends RuntimeException{
    public AuthenticationFailedException (String message){
        super(message);
    }
}
