package com.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "label_translation",
        indexes = {
                @Index(name = "idx_label_language", columnList = "label_id, language_code")
        }
)
public class LabelTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_translation_id")
    private Integer labelTranslationId;

    @ManyToOne
    @JoinColumn(name = "label_id", referencedColumnName = "label_id", nullable = false)
    private Label label;

    @Column(name = "language_code", nullable = false, length = 10)
    private String languageCode;

    @Column(name = "translations", nullable = false)
    private String translations;

    @Column(name = "label_translated", nullable = false, columnDefinition = "TEXT")
    private String labelTranslated;

    @Column(name = "status", length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "approved_by", referencedColumnName = "id")
    private ProofReader approvedBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = this.createdDate;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
