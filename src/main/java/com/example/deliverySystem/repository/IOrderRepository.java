package com.example.deliverySystem.repository;

import com.example.deliverySystem.entitys.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    Order findById(long id);
}
