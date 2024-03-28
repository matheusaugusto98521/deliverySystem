package com.example.deliverySystem.entitys;

import com.example.deliverySystem.DTO.OrderItemDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tb_orderItems")
@Data
@NoArgsConstructor
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @OneToMany
    private List<Product> product;

    private int quantity;

    private BigDecimal valueItem;

    public OrderItems(OrderItemDTO data){
        this.order = data.order();
        this.product = data.product();
        this.quantity = data.quantity();
        this.valueItem = data.valueItem();
    }

}
