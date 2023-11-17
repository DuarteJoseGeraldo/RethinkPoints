package com.example.hogwartsPoints.service;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.hogwartsPoints.DTOs.LoginDTO;
import com.example.hogwartsPoints.DTOs.TokenDataDTO;
import com.example.hogwartsPoints.entity.User;
import com.example.hogwartsPoints.respository.UserRepository;
import com.example.hogwartsPoints.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    JwtUtil jwtUtil;


    public String getUserToken(LoginDTO loginData) {
        User userData = userRepo.findByCpf(loginData.getCpf()).orElseThrow(() -> new EntityNotFoundException("User not Found"));
        if (!BCrypt.verifyer().verify(loginData.getPassword().toCharArray(), userData.getPassword()).verified)
            throw new IllegalArgumentException("Wrong Password");

        userData.setLastValidToken(jwtUtil.generateToken(TokenDataDTO.builder()
                .id(userData.getId())
                .name(userData.getName())
                .house(userData.getHouse().getName())
                .build()));

        userRepo.save(userData);

        return userData.getLastValidToken();
    }
}