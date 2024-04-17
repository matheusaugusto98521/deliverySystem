package com.example.deliverySystem.access.repository;

import com.example.deliverySystem.access.authentication.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserAuthRepository extends JpaRepository<UserAuth, String> {
    UserDetails findByLogin(String login);
}
