package com.domain.dto.response;

import com.domain.enums.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
    private String token;
    private String fullName;
    private String email;
    private Role role;
    private Long userId;
    private String message;
}

