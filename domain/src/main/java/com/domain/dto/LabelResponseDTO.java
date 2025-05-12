package com.domain.dto;

import java.util.List;
import java.util.UUID;

public class LabelResponseDTO {
    private UUID customerCuid;
    private String defaultLanguageCode;
    private List<LabelTranslationResponseDTO> languages;

    public UUID getCuid() {
        return customerCuid;
    }

    public void setCuid(UUID cuid) {
        this.customerCuid = customerCuid;
    }

    public String getDefaultLanguageCode() {
        return defaultLanguageCode;
    }

    public void setDefaultLanguageCode(String defaultLanguageCode) {
        this.defaultLanguageCode = defaultLanguageCode;
    }

    public List<LabelTranslationResponseDTO> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LabelTranslationResponseDTO> languages) {
        this.languages = languages;
    }

    public UUID getCustomerCuid() {
        return customerCuid;
    }

    public void setCustomerCuid(UUID customerCuid) {
        this.customerCuid = customerCuid;
    }
}
