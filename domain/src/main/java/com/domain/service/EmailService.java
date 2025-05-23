package com.domain.service;

public interface EmailService {
    void sendResetPasswordEmail(String to, String token);

    void sendCustomerRegistrationEmail(String to, String fullName);

    void sendProofReaderRegistrationEmail(String to, String fullName);

}
