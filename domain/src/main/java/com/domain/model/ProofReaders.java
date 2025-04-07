package com.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "proof_readers")
public class ProofReaders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "proof_reader_name")
    private String proofReaderName;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public ProofReaders() {
    }

    public ProofReaders(Integer id, String proofReaderName, String status, String role, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.proofReaderName = proofReaderName;
        this.status = status;
        this.role = role;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProofReaderName() {
        return proofReaderName;
    }

    public void setProofReaderName(String proofReaderName) {
        this.proofReaderName = proofReaderName;
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
                ", proofReaderName='" + proofReaderName + '\'' +
                ", status='" + status + '\'' +
                ", role='" + role + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}