package com.example.deliverySystem.access.profile;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
@Data
@NoArgsConstructor
public class UserProfile implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String document;
    private Date birthDate;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;

}
