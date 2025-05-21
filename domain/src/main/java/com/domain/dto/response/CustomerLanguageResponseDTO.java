package com.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLanguageResponseDTO {

    private String languageCode;

    private String languageName;

    @JsonProperty("isDefault")
    private boolean isDefault;
}
