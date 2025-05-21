package com.domain.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Register new customer")
public class RegisterCustomerDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank
    private String fullName;

    @Pattern(regexp = "^[0-9+\\-\\s]*$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank
    private String country;

    @NotNull
    private Long membershipId;

    private String companyName;

    private LocalDate planExpiryDate;

    private LocalDate registrationDate;

    @Pattern(regexp = "^(active|suspended)$", message = "Status must be 'active' or 'suspended'")
    private String accountStatus;
}
