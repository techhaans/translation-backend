package com.domain.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordDTO {
    private String token;
    private String newPassword;
    private String confirmPassword;
}
