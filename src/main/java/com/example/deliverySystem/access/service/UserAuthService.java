package com.example.deliverySystem.access.service;

import com.example.deliverySystem.access.authentication.Roles;
import com.example.deliverySystem.access.authentication.UserAuth;
import com.example.deliverySystem.access.dto.AdminDTO;
import com.example.deliverySystem.access.dto.RegisterDTO;
import com.example.deliverySystem.access.profile.AdminProfile;
import com.example.deliverySystem.access.profile.UserProfile;
import com.example.deliverySystem.access.repository.IUserAuthRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    private IUserAuthRepository repository;

    public UserAuthService(IUserAuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByLogin(username);
    }

    public String encoderPassword(RegisterDTO data){
        return new BCryptPasswordEncoder().encode(data.password());
    }

    public void createAdminUser(RegisterDTO data, AdminProfile profile){
        String encodedPassword = encoderPassword(data);
        UserAuth newAdminUser = new UserAuth(data.login(), encodedPassword, Roles.ADMIN);
        newAdminUser.setAdminProfile(profile);
        this.repository.save(newAdminUser);
    }

    public void createUser(RegisterDTO data, UserProfile user){
        String encodedPassword = encoderPassword(data);
        UserAuth newUser = new UserAuth(data.login(), encodedPassword, Roles.ADMIN);
        newUser.setUserProfile(user);
        this.repository.save(newUser);
    }

}
