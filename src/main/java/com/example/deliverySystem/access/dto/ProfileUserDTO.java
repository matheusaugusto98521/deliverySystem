package com.example.deliverySystem.access.dto;

import java.util.Date;

public record ProfileUserDTO(String name, String document, Date birthDate, String email, String phone) {
}
