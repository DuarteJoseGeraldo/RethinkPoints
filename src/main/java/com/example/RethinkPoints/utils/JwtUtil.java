package com.example.RethinkPoints.utils;

import com.example.RethinkPoints.dto.TokenDataDTO;
import com.example.RethinkPoints.dto.enums.LoginType;
import com.example.RethinkPoints.dto.enums.UserType;
import com.example.RethinkPoints.entity.AccessTokenEntity;
import com.example.RethinkPoints.entity.PartnerEntity;
import com.example.RethinkPoints.entity.UserEntity;
import com.example.RethinkPoints.respository.AccessTokenRepository;
import com.example.RethinkPoints.respository.PartnerRepository;
import com.example.RethinkPoints.respository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
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
    private final UserRepository userRepo;
    private final PartnerRepository partnerRepo;

    public String generateUserToken(TokenDataDTO tokenDataDTO) {
        return accessTokenGenerator(tokenDataDTO, userExpirationMs, userSecret);
    }

    public String generatePartnerToken(TokenDataDTO tokenDataDTO) {
        return accessTokenGenerator(tokenDataDTO, partnerExpirationMs, partnerSecret);
    }

    public UserEntity userTokenValidator(String token) throws Exception {
        return userRepo.findByCpf(accessTokenValidator(token, userSecret).getUserIdentifier()).orElseThrow(() -> new EntityNotFoundException("Token Owner not Found"));
    }

    public PartnerEntity partnerTokenValidator(String token) throws Exception {
        return partnerRepo.findByCode(accessTokenValidator(token, partnerSecret).getUserIdentifier()).orElseThrow(() -> new EntityNotFoundException("Token Owner not Found"));
    }

    public void disableTokenByUserIdentifier(String identifier) {
        AccessTokenEntity token = accessTokenRepo.findByUserIdentifier(identifier).orElseThrow(()-> new EntityNotFoundException("Hotsite Not Found"));
        accessTokenRepo.delete(token);
    }

    public UserEntity adminValidator(String token) throws Exception {
        UserEntity userData = userTokenValidator(token);
        if (!userData.getUserType().equals(UserType.ADMIN))
            throw new AccessDeniedException("User type is not administrator");
        return userData;
    }

    private String accessTokenGenerator(TokenDataDTO tokenDataDTO, long expirationTime, String secret) {
        Date expiration = new Date(System.currentTimeMillis() + expirationTime);
        String token = Jwts.builder()
                .claim("userIdentifier", tokenDataDTO.getUserIdentifier())
                .claim("loginType", tokenDataDTO.getLoginType().toString())
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

    private AccessTokenEntity accessTokenValidator(String token, String secret) throws AccessDeniedException {
        if (token.startsWith("RethinkPointsJWTToken ")) {
            String rawToken = token.substring(22).trim();
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(rawToken).getBody();
            return accessTokenRepo.findByUserIdentifier(accessTokenDataExtractor(rawToken, secret).getUserIdentifier()).orElseThrow(() -> new AccessDeniedException("Access token not Registered"));
        }
        throw new UnsupportedJwtException("Invalid Token Prefix");
    }

    private TokenDataDTO accessTokenDataExtractor(String token, String secret) {
        Claims claims = extractClaims(token, secret);

        return TokenDataDTO.builder()
                .userIdentifier(claims.get("userIdentifier", String.class))
                .loginType(LoginType.fromValue(claims.get("loginType", String.class)))
                .build();
    }

    private Claims extractClaims(String token, String secret) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token).getBody();
    }
}