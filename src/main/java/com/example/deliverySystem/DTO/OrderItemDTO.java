package com.example.deliverySystem.DTO;

import com.example.deliverySystem.entitys.Order;
import com.example.deliverySystem.entitys.Product;

import java.math.BigDecimal;
import java.util.List;

public record OrderItemDTO(Order order, List<Product> product, int quantity, BigDecimal valueItem) {
}
