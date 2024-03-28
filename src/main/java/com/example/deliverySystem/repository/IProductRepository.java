package com.example.deliverySystem.repository;

import com.example.deliverySystem.entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {

    Product findById(long id);
    Product findByDescription(String name);
}
