package com.example.deliverySystem.infra.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.deliverySystem.access.authentication.UserAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    public String generateToken(UserAuth user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("south-delivery-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        }catch(JWTCreationException e){
            throw new RuntimeException("Erro na geração de token ", e);
        }

    }

    public String validationToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("south-delivery-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch(JWTVerificationException e){
            return "";
        }
    }

    public Instant getExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
