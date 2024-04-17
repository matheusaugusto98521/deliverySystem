package com.example.deliverySystem.access.dto;

import com.example.deliverySystem.access.authentication.Roles;

public record AdminDTO(String name, String cnpj, String phone, String image) {
}
