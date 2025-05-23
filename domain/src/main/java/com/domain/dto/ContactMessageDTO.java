package com.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactMessageDTO {

    @NotBlank(message = "Full name is required.")
    @Size(max = 100, message = "Full name must be less than 100 characters.")
    private String fullName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Please provide a valid email address.")
    @Size(max = 100, message = "Email must be less than 100 characters.")
    private String email;

    @Size(max = 20, message = "Phone number must be less than 20 characters.")
    @Pattern(regexp = "^[0-9\\-\\+\\s]{7,20}$", message = "Invalid phone number format.")
    private String phoneNumber;

    @NotBlank(message = "Description is required.")
    @Size(max = 2000, message = "Description must be less than 2000 characters.")
    private String description;
}
