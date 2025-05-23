package com.domain.serviceImpl.contactus;

import com.domain.dto.ContactMessageDTO;
import com.domain.model.ContactMessage;
import com.domain.repo.ContactMessageRepository;
import com.domain.service.ContactMessageService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class ContactMessageServiceImpl implements ContactMessageService {

    private final JavaMailSender mailSender;
    private final ContactMessageRepository contactRepo;

    @Value("${contactus.email.to}")
    private String contactToEmail;

    public ContactMessageServiceImpl(JavaMailSender mailSender, ContactMessageRepository contactRepo) {
        this.mailSender = mailSender;
        this.contactRepo = contactRepo;
    }

    @Override
    public void sendEmail(ContactMessageDTO dto) {
        // Save message to DB
        ContactMessage messageEntity = new ContactMessage();
        messageEntity.setFullName(dto.getFullName());
        messageEntity.setEmail(dto.getEmail());
        messageEntity.setPhoneNumber(dto.getPhoneNumber());
        messageEntity.setDescription(dto.getDescription());
        messageEntity.setCreatedAt(LocalDateTime.now());
        contactRepo.save(messageEntity);

        // Send Email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(contactToEmail);
        message.setSubject("New Contact Us Submission");
        message.setText(
                        "Name: " + dto.getFullName() + "\n" +
                        "Email: " + dto.getEmail() + "\n" +
                        "Phone: " + dto.getPhoneNumber() + "\n\n" +
                        "Message:\n" + dto.getDescription()
        );
        mailSender.send(message);
    }
}
