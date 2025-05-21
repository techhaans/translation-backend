package com.domain.exception.custom;

public class InvalidMembershipException extends RuntimeException{
    public InvalidMembershipException(String message){
        super(message);
    }
}
