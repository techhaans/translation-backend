package com.domain.dto;

public class CustomerLanguageResponseDTO {
    private String languageCode;
    private String languageName;
    private boolean isDefault;

    // Constructors
    public CustomerLanguageResponseDTO() {
    }

    public CustomerLanguageResponseDTO(String languageCode, String languageName, boolean isDefault) {
        this.languageCode = languageCode;
        this.languageName = languageName;
        this.isDefault = isDefault;
    }

    // Getters and Setters
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}
