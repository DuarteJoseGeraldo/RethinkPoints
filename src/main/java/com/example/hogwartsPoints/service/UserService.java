package com.example.hogwartsPoints.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.hogwartsPoints.dto.ChangePasswordDTO;
import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.RegisterUserDTO;
import com.example.hogwartsPoints.dto.UpdateUserDTO;
import com.example.hogwartsPoints.dto.ourEnum.Status;
import com.example.hogwartsPoints.entity.HouseEntity;
import com.example.hogwartsPoints.entity.UserEntity;
import com.example.hogwartsPoints.exception.ChangePasswordException;
import com.example.hogwartsPoints.respository.HouseRepository;
import com.example.hogwartsPoints.respository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.util.Objects;

import static com.example.hogwartsPoints.utils.AppUtils.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final HouseRepository houseRepo;

    public UserEntity getUserDataById(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
    }

    public UserEntity registerUser(RegisterUserDTO userData) {
        userData.setCpf(validateCpf(userData.getCpf()));
        if (userRepo.findByCpf(userData.getCpf()).isPresent())
            throw new EntityExistsException("User Already registered");
        String password = BCrypt.withDefaults().hashToString(12, userData.getPassword().toCharArray());
        HouseEntity userHouseEntity = houseRepo.findByNameContainingIgnoreCase(userData.getHouse()).orElseThrow(() -> new EntityNotFoundException("House not found"));
        return userRepo.save(UserEntity.builder().name(userData.getName()).cpf(userData.getCpf()).password(password).houseEntity(userHouseEntity).userType("standard").points(0.0F).status(Status.ACTIVE).build());
    }

    public UserEntity updateData(UpdateUserDTO userNewData) {
        if (Objects.nonNull(userNewData.getCpf())) userNewData.setCpf(validateCpf(userNewData.getCpf()));

        UserEntity userData = userRepo.findById(userNewData.getId()).orElseThrow(() -> new EntityNotFoundException("User not Found"));
        copyNonNullProperties(userNewData, userData);
        return userRepo.save(userData);
    }

    public MessagesDTO changePassword(long userId, ChangePasswordDTO changePasswordData) {
        UserEntity userData = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not Found"));
        if (!BCrypt.verifyer().verify(changePasswordData.getCurrentPassword().toCharArray(), userData.getPassword()).verified)
            throw new IllegalArgumentException("Wrong Current Password");
        if (!changePasswordData.getNewPassword().equals(changePasswordData.getNewPasswordConfirm()))
            throw new ChangePasswordException("new password and confirmation of new password do not match");
        userData.setPassword(BCrypt.withDefaults().hashToString(12, changePasswordData.getNewPassword().toCharArray()));
        userRepo.save(userData);
        return MessagesDTO.builder().message("Password updated successfully").build();
    }

    public MessagesDTO disableUser(Long userId, String password) {
        UserEntity userData = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
        if (!BCrypt.verifyer().verify(password.toCharArray(), userData.getPassword()).verified)
            throw new IllegalArgumentException("Wrong Password");
        userData.setStatus(Status.INACTIVE);
        userRepo.save(userData);
        return MessagesDTO.builder().message("user disabled successfully").build();
    }
    public MessagesDTO deleteUser(Long userId){
        userRepo.deleteById(userId);
        return MessagesDTO.builder().message("User deleted successfully").build();
    }
}
