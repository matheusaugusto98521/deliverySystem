package com.example.deliverySystem.DTO;

import com.example.deliverySystem.entitys.Order;
import com.example.deliverySystem.entitys.Product;

import java.math.BigDecimal;
import java.util.List;

public record OrderItemDTO(int quantity, BigDecimal valueItem) {
}
