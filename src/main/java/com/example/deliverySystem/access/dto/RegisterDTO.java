package com.example.deliverySystem.access.dto;

import com.example.deliverySystem.access.authentication.Roles;

public record RegisterDTO(String login, String password, Roles role) {
}
