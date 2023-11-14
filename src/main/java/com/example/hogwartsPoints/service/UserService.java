package com.example.hogwartsPoints.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.hogwartsPoints.DTOs.RegisterUserDTO;
import com.example.hogwartsPoints.entity.House;
import com.example.hogwartsPoints.entity.User;
import com.example.hogwartsPoints.exceptions.InvalidCpfException;
import com.example.hogwartsPoints.respository.HouseRepository;
import com.example.hogwartsPoints.respository.UserRepository;
import com.example.hogwartsPoints.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    HouseRepository houseRepo;

    ;

    public User getUserDataById(Long id) throws Exception {
        Optional<User> userData = userRepo.findById(id);
        if (userData.isPresent()) return userData.get();
        throw new EntityNotFoundException("user not found");
    }

    public User registerUser(RegisterUserDTO userData) throws Exception {
        User newUser;

        AppUtils.validateCpf(userData.getCpf());
        if (userRepo.findByCpf(userData.getCpf()).isPresent())
            throw new EntityExistsException("User Already registered");

        String password = BCrypt.withDefaults().hashToString(12, userData.getPassword().toCharArray());

        House userHouse = houseRepo.findByNameContainingIgnoreCase(userData.getHouse()).orElseThrow(() -> new EntityNotFoundException("House not found"));

        newUser = userRepo.save(User.builder().name(userData.getName()).cpf(userData.getCpf()).password(password).house(userHouse).points(0.0F).lastLogin(LocalDateTime.now()).build());
        return userRepo.findById(newUser.getId()).orElseThrow(() -> new Exception("Erro ao cadastrar"));
    }

}
