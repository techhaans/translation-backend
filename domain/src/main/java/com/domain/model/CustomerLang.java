package com.domain.model;
import jakarta.persistence.*;

@Entity
@Table(name = "customerlang")
public class CustomerLang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "id")
    private Language language;

    @Column(name = "is_default")
    private Boolean isDefault;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomerId() {
        return customer;
    }

    public void setCustomerId(Customer customerId) {
        this.customer = customerId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "CustomerLang{" +
                "id=" + id +
                ", customer=" + customer +
                ", language=" + language +
                ", isDefault=" + isDefault +
                '}';
    }

    public CustomerLang(Integer id, Customer customer, Language language, Boolean isDefault) {
        this.id = id;
        this.customer = customer;
        this.language = language;
        this.isDefault = isDefault;
    }

    public CustomerLang(Integer id) {
        this.id = id;
    }

    public CustomerLang(Customer customer) {
        this.customer = customer;
    }

    public CustomerLang(Language language) {
        this.language = language;
    }

    public CustomerLang(Boolean isDefault) {
        this.isDefault = isDefault;
    }
     public  CustomerLang(){

     }
}
