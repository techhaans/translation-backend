package com.domain.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Register new proofreader")
public class RegisterProofReaderDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String fullName;

    @Pattern(regexp = "^[0-9+\\-\\s]*$", message = "Invalid phone number")
    private String phoneNumber;

    @NotEmpty(message = "At least one supported language must be selected")
    private List<@NotBlank String> supportedLanguages;

    @Min(0)
    private int yearsOfExperience;

    @NotBlank
    private String availability;

    @NotNull(message = "Resume file must be uploaded")
    private MultipartFile resume;

    @AssertTrue(message = "Terms must be accepted")
    private boolean termsAccepted;
}
