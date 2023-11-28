package com.example.hogwartsPoints.utils;

import com.example.hogwartsPoints.dto.TokenDataDTO;
import com.example.hogwartsPoints.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expirationMs}")
    private long expirationMs;
    private final UserService userService;

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
        return TokenDataDTO.builder()
                .id(claims.get("id", Long.class))
                .name(claims.get("name", String.class))
                .userType(claims.get("userType", String.class))
                .build();
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token).getBody();
    }

    public TokenDataDTO tokenValidator (String token) throws Exception {
        if (token.startsWith("HogwartsAppJWTToken ")) {
            String rawToken = token.substring(20).trim();
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(rawToken).getBody();
            TokenDataDTO tokenData = extractTokenData(rawToken);
//            if(!userService.getUserDataById(tokenData.getId()).getLastValidToken().equals(rawToken)) throw new UnsupportedJwtException("Expired Token");
            return tokenData;
        }
        throw new UnsupportedJwtException("Invalid Token Prefix");
    }

    public void adminValidator(String type) throws Exception {
        if (!type.equals("admin")) throw new AccessDeniedException("user does not have authorization");
    }
}