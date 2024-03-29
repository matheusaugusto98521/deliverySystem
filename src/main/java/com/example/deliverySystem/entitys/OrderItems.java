package com.example.deliverySystem.entitys;

import com.example.deliverySystem.DTO.OrderItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Order order;

    @ManyToOne
    private Product product;

    private int quantity;

    private BigDecimal valueItem;

    public void setValueItem(){
        if(product != null && !product.getPrice().equals(BigDecimal.ZERO)) {
            this.valueItem = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        }else {
            this.valueItem = BigDecimal.ZERO;
        }
    }

    public OrderItems(OrderItemDTO data){
        this.quantity = data.quantity();
        this.valueItem = data.valueItem();
    }


}
