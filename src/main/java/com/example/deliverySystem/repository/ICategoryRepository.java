package com.example.deliverySystem.repository;

import com.example.deliverySystem.entitys.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Category findById(long id);
}
