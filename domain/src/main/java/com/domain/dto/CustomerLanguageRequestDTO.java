package com.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLanguageRequestDTO {

    @NotNull(message = "Customer UID cannot be null")
    private UUID customerUid;

    @NotEmpty(message = "Languages list cannot be empty")
    @Valid
    private List<LanguageEntry> languages;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LanguageEntry {

        @NotEmpty(message = "Language code cannot be empty")
        private String languageCode;

        @NotNull(message = "isDefault flag must be provided")
        private Boolean isDefault;
    }
}
