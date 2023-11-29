package com.example.hogwartsPoints.utils;

import com.example.hogwartsPoints.dto.TokenDataDTO;
import com.example.hogwartsPoints.dto.ourEnum.LoginType;
import com.example.hogwartsPoints.entity.AccessTokenEntity;
import com.example.hogwartsPoints.respository.AccessTokenRepository;
import com.example.hogwartsPoints.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    @Value("${jwt.userSecret}")
    private String userSecret;
    @Value("${jwt.userExpirationMs}")
    private long userExpirationMs;
    @Value("${jwt.partnerSecret}")
    private String partnerSecret;
    @Value("${jwt.partnerExpirationMs}")
    private long partnerExpirationMs;
    private final AccessTokenRepository accessTokenRepo;

    public String generateUserToken(TokenDataDTO tokenDataDTO) {
        return tokenGenerator(tokenDataDTO, userExpirationMs, userSecret);
    }

    public String generatePartnerToken(TokenDataDTO tokenDataDTO) {
        return tokenGenerator(tokenDataDTO, partnerExpirationMs, partnerSecret);
    }

    public TokenDataDTO userTokenValidator(String token) throws Exception {
        return dataExtractor(tokenValidator(token, userSecret), userSecret);
    }

    public TokenDataDTO partnerTokenValidator(String token) throws Exception {
        return dataExtractor(tokenValidator(token, partnerSecret), partnerSecret);
    }

    private String tokenGenerator(TokenDataDTO tokenDataDTO, long expirationTime, String secret) {
        Date expiration = new Date(System.currentTimeMillis() + expirationTime);
        String token = Jwts.builder()
                .claim("userIdentifier", tokenDataDTO.getUserIdentifier())
                .claim("loginType", tokenDataDTO.getLoginType())
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
        accessTokenRepo.save(AccessTokenEntity.builder()
                .userIdentifier(tokenDataDTO.getUserIdentifier())
                .token(token)
                .loginType(tokenDataDTO.getLoginType())
                .createdAt(LocalDateTime.now())
                .build());
        return token;
    }

    private String tokenValidator(String token, String secret) throws AccessDeniedException {
        if (token.startsWith("HogwartsAppJWTToken ")) {
            String rawToken = token.substring(20).trim();
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(rawToken).getBody();
            if (accessTokenRepo.findByUserIdentifier(dataExtractor(rawToken, secret).getUserIdentifier()).isEmpty())
                throw new AccessDeniedException("Token not Registered");
            return rawToken;
        }
        throw new UnsupportedJwtException("Invalid Token Prefix");
    }

    private TokenDataDTO dataExtractor(String token, String secret) {
        Claims claims = extractClaims(token, secret);
        return TokenDataDTO.builder()
                .userIdentifier(claims.get("userIdentifier", String.class))
                .loginType(claims.get("loginType", LoginType.class))
                .build();
    }

    private Claims extractClaims(String token, String secret) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token).getBody();
    }
}