package com.example.hogwartsPoints.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.RegisterUserDTO;
import com.example.hogwartsPoints.entity.HouseEntity;
import com.example.hogwartsPoints.entity.UserEntity;
import com.example.hogwartsPoints.respository.HouseRepository;
import com.example.hogwartsPoints.respository.UserRepository;
import com.example.hogwartsPoints.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    HouseRepository houseRepo;

    public UserEntity getUserDataById(Long userId) throws Exception {
        Optional<UserEntity> userData = userRepo.findById(userId);
        if (userData.isPresent()) return userData.get();
        throw new EntityNotFoundException("user not found");
    }

    public UserEntity registerUser(RegisterUserDTO userData) throws Exception {
        UserEntity newUserEntity;

        userData.setCpf(userData.getCpf().replaceAll("[.\\-]", ""));

        AppUtils.validateCpf(userData.getCpf());

        if (userRepo.findByCpf(userData.getCpf()).isPresent())
            throw new EntityExistsException("User Already registered");

        String password = BCrypt.withDefaults().hashToString(12, userData.getPassword().toCharArray());

        HouseEntity userHouseEntity = houseRepo.findByNameContainingIgnoreCase(userData.getHouse()).orElseThrow(() -> new EntityNotFoundException("House not found"));

        newUserEntity = userRepo.save(UserEntity.builder().name(userData.getName()).cpf(userData.getCpf()).password(password).houseEntity(userHouseEntity).userType("standard").points(0.0F).build());
        return userRepo.findById(newUserEntity.getId()).orElseThrow(() -> new Exception("Erro ao cadastrar"));
    }

    public MessagesDTO changePassword(long userId, String currentPassword, String newPassword){
        return MessagesDTO.builder().build();
    }
}
