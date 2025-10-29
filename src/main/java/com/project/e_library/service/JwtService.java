package com.project.e_library.service;

import com.project.e_library.model.LibUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${JWT_SECRET}")
    private String secret;

    public String generateToken(LibUser user) {
        Map<String, Object> claims = new LinkedHashMap<>();

        claims.put("userId", user.getUserId());
        claims.put("email", user.getEmail());
        claims.put("name", user.getName()+" "+user.getSurname());


        return Jwts.builder()
                .claims()
                .add(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)))
                .and()
                .signWith(getSecretKey())
                .compact();

    }
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
