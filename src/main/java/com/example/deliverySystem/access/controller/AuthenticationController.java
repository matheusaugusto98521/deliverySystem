package com.example.deliverySystem.access.controller;

import com.example.deliverySystem.access.authentication.Roles;
import com.example.deliverySystem.access.authentication.UserAuth;
import com.example.deliverySystem.access.dto.*;
import com.example.deliverySystem.access.profile.AdminProfile;
import com.example.deliverySystem.access.profile.UserProfile;
import com.example.deliverySystem.access.repository.IUserAuthRepository;
import com.example.deliverySystem.access.responseDTO.LoginResponseDTO;
import com.example.deliverySystem.access.service.UserAuthService;
import com.example.deliverySystem.infra.security.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserAuthRepository repository;

    @Autowired
    UserAuthService service;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        String token = this.tokenService.generateToken((UserAuth) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    @PostMapping("/register-adm")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid RegisterAdminDTO data){
        if(this.repository.findByLogin(data.registerData().login()) != null)return ResponseEntity.badRequest().build();

        if(data.registerData().role() == Roles.ADMIN){
            AdminProfile newAdmin = new AdminProfile(data.adminData());
            this.service.createAdminUser(data.registerData(), newAdmin);
        }else{
            return ResponseEntity.badRequest().body("Voce precisa ser administrador para se cadastrar nesse endpoint");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserDTO data){
        if(this.repository.findByLogin(data.registerData().login()) != null)return ResponseEntity.badRequest().build();

        if(data.registerData().role() == Roles.USER){
            UserProfile newUser = new UserProfile(data.userData());
            this.service.createUser(data.registerData(), newUser);
        }else{
            return ResponseEntity.badRequest().body("Voce precisa ser usuário comum para se cadastrar nesse endpoint");
        }

        return ResponseEntity.ok().build();
    }
}
