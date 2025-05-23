package com.dc.facade.fd;

import com.domain.dto.ContactMessageDTO;

public interface ContactMessageFacade {
    void submitMessage(ContactMessageDTO dto);
}

