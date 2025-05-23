package com.domain.service;

public interface EmailService {
    void sendResetPasswordEmail(String to, String token);

}
