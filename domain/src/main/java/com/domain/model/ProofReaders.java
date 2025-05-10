package com.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "proof_readers")
public class ProofReaders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "puid", unique = true, nullable = false, updatable = false)
    private UUID puid;

    @Column(name = "proof_reader_name")
    private String proofReaderName;

    @Column(name = "country")
    private String country;

    @Column(name = "supported_languages")
    private String supportedLanguages; // comma-separated values

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserTable user;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getPuid() {
        return puid;
    }

    public void setPuid(UUID puid) {
        this.puid = puid;
    }

    public String getProofReaderName() {
        return proofReaderName;
    }

    public void setProofReaderName(String proofReaderName) {
        this.proofReaderName = proofReaderName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSupportedLanguages() {
        return supportedLanguages;
    }

    public void setSupportedLanguages(String supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserTable getUser() {
        return user;
    }

    public void setUser(UserTable user) {
        this.user = user;
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
        return "ProofReaders{" +
                "id=" + id +
                ", puid=" + puid +
                ", proofReaderName='" + proofReaderName + '\'' +
                ", country='" + country + '\'' +
                ", supportedLanguages='" + supportedLanguages + '\'' +
                ", status='" + status + '\'' +
                ", role='" + role + '\'' +
                ", user=" + user +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}