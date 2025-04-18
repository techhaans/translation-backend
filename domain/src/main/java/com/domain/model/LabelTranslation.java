package com.domain.model;

import com.domain.dto.LabelTranslationResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "label_translation")
public class LabelTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_translation_id")
    private Integer labelTranslationId;

    @Column(name = "label_name")
    private String label;

    @Column(name = "language_code", nullable = false)
    private String languageCode;  

    @Column(name = "translations", nullable = false)
    private String translations;


    @Column(name = "label_translated", nullable = false, columnDefinition = "TEXT")
    private String labelTranslated;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "approved_by", referencedColumnName = "id")
    private ProofReaders approvedBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    // Constructors, getters and setters...

    public LabelTranslation() {
    }

    public LabelTranslation(Integer labelTranslationId, String label, String languageCode,
                            String translations,
                            String labelTranslated, String status, ProofReaders approvedBy,
                            LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.labelTranslationId = labelTranslationId;
        this.label = label;
        this.translations = translations;
        this.languageCode = languageCode;
        this.labelTranslated = labelTranslated;
        this.status = status;
        this.approvedBy = approvedBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Integer getLabelTranslationId() {
        return labelTranslationId;
    }

    public void setLabelTranslationId(Integer labelTranslationId) {
        this.labelTranslationId = labelTranslationId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLabelTranslated() {
        return labelTranslated;
    }

    public void setLabelTranslated(String labelTranslated) {
        this.labelTranslated = labelTranslated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProofReaders getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(ProofReaders approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getTranslations() {
        return translations;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
    }

    @Override
    public String toString() {
        return "LabelTranslation{" +
                "labelTranslationId=" + labelTranslationId +
                ", labelName='" + label + '\'' +
                ", translations='" + translations + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", labelTranslated='" + labelTranslated + '\'' +
                ", status='" + status + '\'' +
                ", approvedBy=" + approvedBy +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}