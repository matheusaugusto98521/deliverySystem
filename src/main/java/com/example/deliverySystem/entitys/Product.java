package com.example.deliverySystem.entitys;

import com.example.deliverySystem.DTO.ProductDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;
    private BigDecimal price;

    private String image;

    @ManyToOne
    private Category category;

    @ManyToOne
    private OrderItems orderItem;

    public Product(ProductDTO productDTO){
        this.description = productDTO.description();
        this.price = productDTO.price();
    }

}
