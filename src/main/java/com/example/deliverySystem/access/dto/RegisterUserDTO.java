package com.example.deliverySystem.access.dto;

import javax.validation.Valid;

public record RegisterUserDTO(@Valid ProfileUserDTO userData, @Valid RegisterDTO registerData) {
}
