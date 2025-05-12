package com.domain.dto;

import java.util.UUID;

public class CustomerRegistrationResponse {
    private UUID cuid;
    private int userId;
    private String name;
    private String role;

    // Constructors
    public CustomerRegistrationResponse(UUID cuid, int userId, String name, String role) {
        this.cuid = cuid;
        this.userId = userId;
        this.name = name;
        this.role = role;
    }

    // Getters and Setters
    public UUID getCuid() {
        return cuid;
    }

    public void setCuid(UUID cuid) {
        this.cuid = cuid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
