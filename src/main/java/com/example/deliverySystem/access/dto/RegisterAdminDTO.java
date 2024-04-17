package com.example.deliverySystem.access.dto;

import javax.validation.Valid;

public record RegisterAdminDTO(@Valid RegisterDTO registerData, @Valid AdminDTO adminData ) {
}
