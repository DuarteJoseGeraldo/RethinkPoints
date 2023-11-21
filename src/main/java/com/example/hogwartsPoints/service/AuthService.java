package com.example.hogwartsPoints.service;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.hogwartsPoints.dto.LoginDTO;
import com.example.hogwartsPoints.dto.TokenDataDTO;
import com.example.hogwartsPoints.entity.UserEntity;
import com.example.hogwartsPoints.respository.UserRepository;
import com.example.hogwartsPoints.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    JwtUtil jwtUtil;

    public String getUserToken(LoginDTO loginData) {
        UserEntity userEntityData = userRepo.findByCpf(loginData.getCpf()).orElseThrow(() -> new EntityNotFoundException("User not Found"));
        if (!BCrypt.verifyer().verify(loginData.getPassword().toCharArray(), userEntityData.getPassword()).verified)
            throw new IllegalArgumentException("Wrong Password");

        userEntityData.setLastValidToken(jwtUtil.generateToken(TokenDataDTO.builder()
                .id(userEntityData.getId())
                .name(userEntityData.getName())
                .userType(userEntityData.getUserType())
                .build()));

        userEntityData.setLastLogin(LocalDateTime.now());

        userRepo.save(userEntityData);

        return userEntityData.getLastValidToken();
    }

}