package com.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import com.domain.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Login request")
public class LoginDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Schema(description = "User role", example = "CUSTOMER")
    private Role role;
}


