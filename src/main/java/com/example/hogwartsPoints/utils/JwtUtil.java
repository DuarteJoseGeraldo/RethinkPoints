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
                .claim("userType", tokenDataDTO.getUserType())
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public TokenDataDTO extractTokenData(String token) {
        Claims claims = extractClaims(token);

        TokenDataDTO userData = TokenDataDTO.builder()
                .id(claims.get("id", Long.class))
                .name(claims.get("name", String.class))
                .userType(claims.get("userType", String.class))
                .build();

        return userData;
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token).getBody();
    }

    public boolean tokenValidator (String token){
        Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token).getBody();
        return true;
    }

    public boolean adminValidator(String token){
        return "admin".equalsIgnoreCase(Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token).getBody().get("userType", String.class));
    }
}
