package com.domain.exception.custom;

public class TermsNotAcceptedException extends RuntimeException{
    public TermsNotAcceptedException (String message){
        super(message);
    }
}
