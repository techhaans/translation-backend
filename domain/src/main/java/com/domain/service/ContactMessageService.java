package com.domain.service;

import com.domain.dto.ContactMessageDTO;

public interface ContactMessageService {
    void sendEmail(ContactMessageDTO dto);
}

