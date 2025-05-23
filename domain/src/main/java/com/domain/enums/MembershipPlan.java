package com.domain.enums;

public enum MembershipPlan {
    TRIAL("$0/mth"),
    BASIC_MONTHLY("$9/mth"),
    BUSINESS_MONTHLY("$19/mth"),
    BASIC_YEARLY("$99/mth"),
    BUSINESS_YEARLY("$149/mth");

    private final String price;

    MembershipPlan(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }
}