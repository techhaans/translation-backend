package com.domain.exception.custom;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException (String message){
        super(message);
    }
}
