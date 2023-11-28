package com.example.hogwartsPoints.service;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.hogwartsPoints.dto.LoginDTO;
import com.example.hogwartsPoints.dto.TokenDataDTO;
import com.example.hogwartsPoints.entity.UserEntity;
import com.example.hogwartsPoints.respository.UserRepository;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    public String getUserToken(LoginDTO loginData) throws Exception {
        UserEntity userEntityData = validateUserData(loginData);

        userEntityData.setLastValidToken(jwtUtil.generateToken(TokenDataDTO.builder()
                .id(userEntityData.getId())
                .name(userEntityData.getName())
                .userType(userEntityData.getUserType())
                .build()));
        userEntityData.setLastLogin(LocalDateTime.now());
        userRepo.save(userEntityData);

        return userEntityData.getLastValidToken();
    }

    private UserEntity validateUserData(LoginDTO loginData) throws Exception {
        UserEntity userEntityData = userRepo.findByCpf(loginData.getCpf()).orElseThrow(() -> new EntityNotFoundException("User not Found"));
        if (!userEntityData.isActive()) throw new AccessDeniedException("Disabled user");
        if (!BCrypt.verifyer().verify(loginData.getPassword().toCharArray(), userEntityData.getPassword()).verified)
            throw new IllegalArgumentException("Wrong Password");
        return userEntityData;
    }

}