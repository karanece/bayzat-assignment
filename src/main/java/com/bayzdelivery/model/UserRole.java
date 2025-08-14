package com.bayzdelivery.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    CUSTOMER("customer"),
    DELIVERY_AGENT("delivery_agent");

    private final String role;

    UserRole(final String role) {
        this.role = role;
    }

    @JsonValue
    public String getRole() {
        return role;
    }
}
