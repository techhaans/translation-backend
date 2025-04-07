package com.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "label_translation")
public class LabelTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_translation_id")
    private Integer labelTranslationId;

    @Column(name = "language", nullable = false)
    private String language;

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

    public LabelTranslation() {
    }
    public LabelTranslation(Integer labelTranslationId, String language, String labelTranslated, String status, ProofReaders approvedBy, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.labelTranslationId = labelTranslationId;
        this.language = language;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    @Override
    public String toString() {
        return "LabelTranslation{" +
                "labelTranslationId=" + labelTranslationId +
                ", language='" + language + '\'' +
                ", labelTranslated='" + labelTranslated + '\'' +
                ", status='" + status + '\'' +
                ", approvedBy=" + approvedBy +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}