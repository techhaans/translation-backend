package com.tech.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;



@Entity
@Table(name = "customer_language")
public class CustomerLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_id")
    private Integer clId;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "lid", referencedColumnName = "lid")
    private Languages languages;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public CustomerLanguage() {
    }

    public CustomerLanguage(Integer clId, Customer customer, Languages languages, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.clId = clId;
        this.customer = customer;
        this.languages = languages;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Integer getClId() {
        return clId;
    }

    public void setClId(Integer clId) {
        this.clId = clId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Languages getLanguages() {
        return languages;
    }

    public void setLanguages(Languages languages) {
        this.languages = languages;
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
        return "CustomerLanguage{" +
                "clId=" + clId +
                ", customer=" + customer +
                ", languages=" + languages +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
