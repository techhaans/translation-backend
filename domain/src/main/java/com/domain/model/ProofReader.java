package com.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "proof_reader")
public class ProofReader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "puid", nullable = false, unique = true, updatable = false)
    private UUID puid = UUID.randomUUID();

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "supported_languages", columnDefinition = "TEXT")
    private String supportedLanguages;

    @Column(name = "years_of_experience")
    private int yearsOfExperience;

    @Column(length = 100)
    private String availability;

    @Column(name = "resume_path", length = 255)
    private String resumePath;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}