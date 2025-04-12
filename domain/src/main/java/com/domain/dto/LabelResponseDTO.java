package com.domain.dto;

import java.util.List;

public class LabelResponseDTO {
    private Integer customerId;
    private String defaultLanguageCode;
    private List<LabelTranslationResponseDTO> languages;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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
}
