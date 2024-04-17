package com.example.deliverySystem.access.profile;

import com.example.deliverySystem.access.authentication.UserAuth;
import com.example.deliverySystem.access.dto.AdminDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_enterprises")
@Data
public class AdminProfile implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String cnpj;
    private String phone;

    @JoinColumn(name = "path_image_profile")
    private String image;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAuth userAuth;

    public AdminProfile(AdminDTO data){
        this.name = data.name();
        this.cnpj = data.cnpj();
        this.phone = data.phone();
        this.image = data.image();
    }
}
