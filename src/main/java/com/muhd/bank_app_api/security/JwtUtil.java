package com.muhd.bank_app_api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import com.muhd.bank_app_api.model.BankUser;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "thisisaverysecretkeythatshouldbeatleast32characters";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(BankUser user) {
    HashMap<String, Object> claims = new HashMap<>();
    claims.put("role", user.getRole());

    return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.getEmail()) // store email as the "username"
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
}

    // Extract username
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate token
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
