package com.example.deliverySystem.access.profile;

import com.example.deliverySystem.access.authentication.UserAuth;
import com.example.deliverySystem.access.dto.ProfileUserDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tb_profiles")
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAuth userAuth;

    public UserProfile(ProfileUserDTO data){
        this.name = data.name();
        this.document = data.document();
        this.birthDate = data.birthDate();
        this.email = data.email();
        this.phone = data.phone();
    }
}
