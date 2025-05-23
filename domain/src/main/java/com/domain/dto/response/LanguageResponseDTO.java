package com.domain.dto.response;

import com.domain.model.Language;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LanguageResponseDTO {

    @NotNull(message = "Id cannot be null")
    private Integer id;

    @NotNull(message = "Language code cannot be null")
    @Size(min = 2, max = 10, message = "Language code must be between 2 and 10 characters")
    private String languageCode;

    @NotNull(message = "Language name cannot be null")
    @Size(min = 1, max = 100, message = "Language name must be between 1 and 100 characters")
    private String languageName;

    /**
     * Convert a Language entity to LanguageResponseDTO.
     * @param language Language entity
     * @return LanguageResponseDTO instance
     */
    public static LanguageResponseDTO fromEntity(Language language) {
        if (language == null) return null;
        return new LanguageResponseDTO(
                language.getId(),
                language.getLanguageKey(),
                language.getLanguageName()
        );
    }
}

