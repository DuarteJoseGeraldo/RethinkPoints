package com.example.hogwartsPoints.utils;

import com.example.hogwartsPoints.DTOs.TokenDataDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    public String generateToken(TokenDataDTO tokenDataDTO) {
        Date expiration = new Date(System.currentTimeMillis() + expirationMs);

        return Jwts.builder()
                .claim("id", tokenDataDTO.getId())
                .claim("name", tokenDataDTO.getName())
                .claim("house", tokenDataDTO.getHouse())
                .setExpiration(expiration)
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512))
                .compact();
    }

    public TokenDataDTO extractTokenData(String token) {
        Claims claims = extractClaims(token);

        TokenDataDTO userData = TokenDataDTO.builder()
                .id(claims.get("id", Long.class))
                .name(claims.get("name", String.class))
                .house(claims.get("house", String.class))
                .build();

        return userData;
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
