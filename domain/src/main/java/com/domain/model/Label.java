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

    // Reference to Customer by cid
//    @ManyToOne
//    @JoinColumn(name = "cid", referencedColumnName = "cid")
//    private Customer customer;

    // Reference to Customer by cuid
    @ManyToOne
    @JoinColumn(name = "cuid", referencedColumnName = "cuid")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="userid" , referencedColumnName = "id")
    private UserTable user;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Label() {}

    public Label(Integer labelId, String labelName, String labelKey, Customer customerByCuid, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.labelId = labelId;
        this.labelName = labelName;
        this.labelKey = labelKey;
       // this.customer = customerByCid;
        this.customer = customerByCuid;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    // Getters and setters

    public Integer getLabelId() {
        return labelId;
    }

//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

//    public Customer getCustomerByCid() {
//        return customer;
//    }
//
//    public void setCustomerByCid(Customer customerByCid) {
//        this.customer = customerByCid;
//    }

    public Customer getCustomerByCuid() {
        return customer;
    }

    public void setCustomerByCuid(Customer customerByCuid) {
        this.customer = customerByCuid;
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
        return "Label{" +
                "labelId=" + labelId +
                ", labelName='" + labelName + '\'' +
                ", labelKey='" + labelKey + '\'' +
                //", customerByCid=" + customer +
                ", customerByCuid=" + customer +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
