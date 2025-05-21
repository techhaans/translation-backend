package com.domain.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LabelTranslationResponseDTO {

    @NotBlank(message = "Language code cannot be blank")
    private String languageCode;

    @NotNull(message = "Translations map cannot be null")
    @NotEmpty(message = "Translations map cannot be empty")
    private Map<String, String> translations;
}
