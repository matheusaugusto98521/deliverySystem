package com.example.deliverySystem.entitys;


import com.example.deliverySystem.DTO.CategoryDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_category")
@Data
@NoArgsConstructor
public class Category {
    @Id //mostra pro spring q essa variável representa a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) //declara que será um auto_increment
    @Column(nullable = false, unique = true) //configurações da coluna id
    private Long id;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "category") //relacionamento entre as entidades categoria e produto
    //no ccaso seria muitos produtos pra uma categoria
    private List<Product> product;

    public Category(CategoryDTO categoryDTO){
        this.description = categoryDTO.description();
    }

}
