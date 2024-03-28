package com.example.deliverySystem.repository;

import com.example.deliverySystem.entitys.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderItemsRepository extends JpaRepository<OrderItems, Long> {
    OrderItems findById(long id);
}
