package com.domain.dto;

import java.util.List;

public class LabelRequestDTO {
    private Integer customerId;
    private String defaultLanguageCode;
    private List<LabelTranslationRequestDTO> languages;

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

    public List<LabelTranslationRequestDTO> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LabelTranslationRequestDTO> languages) {
        this.languages = languages;
    }
}
