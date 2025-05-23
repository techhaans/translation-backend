package com.dc.api.controller;

import com.dc.facade.fd.ContactMessageFacade;
import com.domain.dto.ContactMessageDTO;
import com.domain.dto.response.ApiResponse;
import com.domain.dto.response.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin
@Tag(name = "Contact Us", description = "APIs for submitting contact messages from the Contact Us form")
public class ContactMessageController {

    private final ContactMessageFacade contactMessageFacade;

    public ContactMessageController(ContactMessageFacade contactMessageFacade) {
        this.contactMessageFacade = contactMessageFacade;
    }

    @PostMapping
    @Operation(
            summary = "Submit contact message",
            description = "Allows users to submit a message via the Contact Us form. The message is stored or emailed to the site admin."
    )
    public ResponseEntity<ApiResponse<String>> submitMessage(@RequestBody ContactMessageDTO messageDTO) {
        contactMessageFacade.submitMessage(messageDTO);
        return ResponseEntity.ok(ApiResponseBuilder.success(null, "Message submitted successfully!"));
    }
}
