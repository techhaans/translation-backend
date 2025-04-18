package com.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "labels")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_id")
    private Integer labelId;

    @Column(name = "label_name", nullable = false)
    private String labelName;
    @Column(name = "label_key", nullable = false)
    private String labelKey;
    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    private Customer customer;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Label() {}

    public Label(Integer labelId, String labelName, String labelKey, Customer customer, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.labelId = labelId;
        this.labelName = labelName;
        this.labelKey = labelKey;
        this.customer = customer;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Integer getLabelId() { return labelId; }
    public void setLabelId(Integer labelId) { this.labelId = labelId; }
    public String getLabelName() { return labelName; }
    public void setLabelName(String labelName) { this.labelName = labelName; }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public LocalDateTime getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(LocalDateTime updatedDate) { this.updatedDate = updatedDate; }

    @Override
    public String toString() {
        return "Label{" +
                "labelId=" + labelId +
                ", labelName='" + labelName + '\'' +
                ", labelKey='" + labelKey + '\'' +
                ", customer=" + customer +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}