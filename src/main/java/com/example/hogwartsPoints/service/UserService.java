package com.example.hogwartsPoints.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.hogwartsPoints.dto.TokenDataDTO;
import com.example.hogwartsPoints.dto.update.ChangePasswordDTO;
import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.register.RegisterUserDTO;
import com.example.hogwartsPoints.dto.update.UpdateUserDTO;
import com.example.hogwartsPoints.dto.enums.Status;
import com.example.hogwartsPoints.dto.enums.UserType;
import com.example.hogwartsPoints.entity.HouseEntity;
import com.example.hogwartsPoints.entity.UserEntity;
import com.example.hogwartsPoints.exception.ChangePasswordException;
import com.example.hogwartsPoints.respository.AccessTokenRepository;
import com.example.hogwartsPoints.respository.HouseRepository;
import com.example.hogwartsPoints.respository.UserRepository;

import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.util.Objects;

import static com.example.hogwartsPoints.utils.AppUtils.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final HouseRepository houseRepo;
    private final JwtUtil jwtUtil;

    public UserEntity getUserDataById(String accessToken, Long userId) throws Exception {
        jwtUtil.adminValidator(accessToken);
        return userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
    }

    public UserEntity getUserInfo(String accessToken) throws Exception {
        return jwtUtil.userTokenValidator(accessToken);
    }

    public UserEntity registerUser(String accessToken, RegisterUserDTO userData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        userData.setCpf(validateCpf(userData.getCpf()));
        if (userRepo.findByCpf(userData.getCpf()).isPresent())
            throw new EntityExistsException("User Already registered");
        String password = BCrypt.withDefaults().hashToString(12, userData.getPassword().toCharArray());
        HouseEntity userHouseEntity = houseRepo.findByNameContainingIgnoreCase(userData.getHouse()).orElseThrow(() -> new EntityNotFoundException("House not found"));
        return userRepo.save(UserEntity.builder().name(userData.getName()).cpf(userData.getCpf()).password(password).houseEntity(userHouseEntity).userType(UserType.STANDARD).points(0.0F).status(Status.ACTIVE).build());
    }

    public UserEntity updateData(String accessToken, UpdateUserDTO userNewData) throws Exception {
        UserEntity userData = jwtUtil.userTokenValidator(accessToken);
        if (Objects.nonNull(userNewData.getCpf())) userNewData.setCpf(validateCpf(userNewData.getCpf()));
        copyNonNullProperties(userNewData, userData);
        return userRepo.save(userData);
    }

    public MessagesDTO changePassword(String accessToken, MultiValueMap<String, String> passwordData) throws Exception {
        UserEntity userData = jwtUtil.userTokenValidator(accessToken);
        changePasswordValidator(passwordData, userData);
        userRepo.save(userData);
        return MessagesDTO.builder().message("Password updated successfully").build();
    }

    public MessagesDTO disableUser(String accessToken, String password) throws Exception {
        UserEntity userData = jwtUtil.userTokenValidator(accessToken);
        if (!BCrypt.verifyer().verify(password.toCharArray(), userData.getPassword()).verified)
            throw new IllegalArgumentException("Wrong Password");
        userData.setStatus(Status.INACTIVE);
        userRepo.save(userData);
        jwtUtil.disableTokenByUserIdentifier(userData.getCpf());
        return MessagesDTO.builder().message("user disabled successfully").build();
    }

    public MessagesDTO deleteUser(String accessToken, Long userId) throws Exception {
        jwtUtil.adminValidator(accessToken);
        userRepo.deleteById(userId);
        return MessagesDTO.builder().message("User deleted successfully").build();
    }

    private void changePasswordValidator(MultiValueMap<String, String> passwordData, UserEntity userData) {
        if (!BCrypt.verifyer().verify(Objects.requireNonNull(passwordData.getFirst("current_password")).toCharArray(), userData.getPassword()).verified)
            throw new IllegalArgumentException("Wrong Current Password");
        if (!Objects.equals(passwordData.getFirst("new_password"), passwordData.getFirst("password_confirmation")))
            throw new ChangePasswordException("new password and confirmation of new password do not match");
        userData.setPassword(BCrypt.withDefaults().hashToString(12, Objects.requireNonNull(passwordData.getFirst("new_password")).toCharArray()));
    }
}
