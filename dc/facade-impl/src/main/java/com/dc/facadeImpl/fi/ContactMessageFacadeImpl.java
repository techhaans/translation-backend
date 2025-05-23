package com.dc.facadeImpl.fi;

import com.dc.facade.fd.ContactMessageFacade;
import com.domain.dto.ContactMessageDTO;
import com.domain.service.ContactMessageService;
import org.springframework.stereotype.Component;

@Component
public class ContactMessageFacadeImpl implements ContactMessageFacade {

    private final ContactMessageService contactMessageService;

    public ContactMessageFacadeImpl(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @Override
    public void submitMessage(ContactMessageDTO dto) {
        contactMessageService.sendEmail(dto);
    }
}

