package com.example.deliverySystem.access.profile;

import lombok.Data;
import lombok.Getter;

@Getter
public enum Roles {

    ADMIN("Admin"),
    USER("User");

    private String role;

    Roles(String role){this.role = role;}
}

