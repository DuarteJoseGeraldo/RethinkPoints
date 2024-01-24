package com.example.hogwartsPoints.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.register.RegisterUserDTO;
import com.example.hogwartsPoints.dto.update.UpdateUserDTO;
import com.example.hogwartsPoints.dto.enums.Status;
import com.example.hogwartsPoints.dto.enums.UserType;
import com.example.hogwartsPoints.entity.AddressEntity;
import com.example.hogwartsPoints.entity.HubEntity;
import com.example.hogwartsPoints.entity.UserEntity;
import com.example.hogwartsPoints.exception.ChangePasswordException;
import com.example.hogwartsPoints.respository.HubRepository;
import com.example.hogwartsPoints.respository.UserRepository;

import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.util.Objects;

import static com.example.hogwartsPoints.utils.AppUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepo;
    private final HubRepository hubRepo;
    private final JwtUtil jwtUtil;

    public UserEntity getUserDataById(String accessToken, Long userId) throws Exception {
        jwtUtil.adminValidator(accessToken);
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
        user.setPassword(null);
        return user;
    }

    public UserEntity getUserInfo(String accessToken) throws Exception {
        UserEntity user = jwtUtil.userTokenValidator(accessToken);
        user.setPassword(null);
        return user;
    }

    public UserEntity registerUser(RegisterUserDTO userData) throws Exception {
        userData.setCpf(validateCpf(userData.getCpf()));
        return userRepo.save(validateUserData(userData));
    }

    public UserEntity updateData(String accessToken, UpdateUserDTO updateData) throws Exception {
        UserEntity userData = jwtUtil.userTokenValidator(accessToken);
        if (Objects.nonNull(updateData.getAddress())){
            copyNonNullProperties(updateData.getAddress(), userData.getAddress());
        }
        copyNonNullProperties(updateData, userData);
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
        log.info("changePasswordValidator() - 'userData': {}", userData);
    }

    private UserEntity validateUserData(RegisterUserDTO userData) {
        HubEntity userHub = hubRepo.findByNameContainingIgnoreCase(userData.getHub()).orElseThrow(() -> new EntityNotFoundException("Hub not found"));
        if (userRepo.findByCpf(userData.getCpf()).isPresent())
            throw new EntityExistsException("CPF Already registered");
        userData.setPassword(BCrypt.withDefaults().hashToString(12, userData.getPassword().toCharArray()));
        AddressEntity address = AddressEntity.builder()
                .street(userData.getAddress().getStreet())
                .number(userData.getAddress().getNumber())
                .neighborhood(userData.getAddress().getNeighborhood())
                .complement(userData.getAddress().getComplement())
                .city(userData.getAddress().getCity())
                .state(userData.getAddress().getState())
                .zipCode(userData.getAddress().getZipCode())
                .build();
        return UserEntity.builder().name(userData.getName()).cpf(userData.getCpf()).password(userData.getPassword()).hub(userHub).address(address).userType(UserType.STANDARD).points(0.0F).status(Status.ACTIVE).build();
    }
}
