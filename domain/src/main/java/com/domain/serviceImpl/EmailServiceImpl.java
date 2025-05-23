package com.domain.serviceImpl;

import com.domain.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendResetPasswordEmail(String to, String token) {
        String subject = "Password Reset Request";
        String resetLink = "http://localhost:3000/reset-password?token=" + token;
        String text = "Click the link to reset your password:\n" + resetLink;

        sendEmail(to, subject, text);
    }

    @Override
    public void sendCustomerRegistrationEmail(String to, String fullName) {
        String subject = "Welcome to Our Platform!";
        String text = "Dear " + fullName + ",\n\nThank you for registering as a customer with us.\n\nBest regards,\nTeam";
        sendEmail(to, subject, text);
    }

    @Override
    public void sendProofReaderRegistrationEmail(String to, String fullName) {
        String subject = "Welcome Proofreader!";
        String text = "Dear " + fullName + ",\n\nThank you for joining as a proofreader. We're glad to have you.\n\nRegards,\nTeam";
        sendEmail(to, subject, text);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}

