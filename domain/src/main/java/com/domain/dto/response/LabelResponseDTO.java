package com.domain.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class LabelResponseDTO {

    @NotNull(message = "Customer CUID cannot be null")
    private UUID customerCuid;

    @NotBlank(message = "Default language code cannot be blank")
    private String defaultLanguageCode;

    @NotNull(message = "Languages list cannot be null")
    @Size(min = 1, message = "At least one language must be provided")
    private List<LabelTranslationResponseDTO> languages;
}
