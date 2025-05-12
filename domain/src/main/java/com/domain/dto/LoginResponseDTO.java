package com.domain.dto;

import java.util.UUID;

public class LoginResponseDTO {
    private Long id;
    private String name;
    private String role;
    private UUID customerUId;

    public LoginResponseDTO(Long id, String name, String role, UUID customerUId) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.customerUId = customerUId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UUID getCustomerUId() {
        return customerUId;
    }

    public void setCustomerUId(UUID customerUId) {
        this.customerUId = customerUId;
    }
}
