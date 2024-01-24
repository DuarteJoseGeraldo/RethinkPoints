package com.example.RethinkPoints.service;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.RethinkPoints.dto.TokenDataDTO;
import com.example.RethinkPoints.dto.enums.LoginType;
import com.example.RethinkPoints.dto.enums.Status;
import com.example.RethinkPoints.entity.PartnerEntity;
import com.example.RethinkPoints.entity.UserEntity;
import com.example.RethinkPoints.respository.PartnerRepository;
import com.example.RethinkPoints.respository.UserRepository;
import com.example.RethinkPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepo;
    private final PartnerRepository partnerRepo;
    private final JwtUtil jwtUtil;

    public String getUserToken(MultiValueMap<String, String> loginData) throws Exception {
        UserEntity userData = validateUserData(loginData);
        userData.setLastLogin(LocalDateTime.now());
        userRepo.save(userData);
        return jwtUtil.generateUserToken(TokenDataDTO.builder().loginType(LoginType.COMMON_AUTH).userIdentifier(userData.getCpf()).build());
    }

    public String getPartnerToken(MultiValueMap<String, String> loginData) throws Exception {
        PartnerEntity partnerData = validatePartnerData(loginData);
        return jwtUtil.generatePartnerToken(TokenDataDTO.builder().userIdentifier(partnerData.getCode()).loginType(LoginType.CLIENT_CREDENTIALS).build());
    }
    private UserEntity validateUserData(MultiValueMap<String, String> loginData) throws Exception {
        if (!Objects.equals(loginData.getFirst("grant_type"), LoginType.COMMON_AUTH.getValue()))
            throw new AccessDeniedException("Invalid grant type");
        UserEntity userEntityData = userRepo.findByCpf(loginData.getFirst("cpf")).orElseThrow(() -> new EntityNotFoundException("User not Found"));
        if (!userEntityData.getStatus().equals(Status.ACTIVE)) throw new AccessDeniedException("User disabled");
        if (!BCrypt.verifyer().verify(Objects.requireNonNull(loginData.getFirst("password")).toCharArray(), userEntityData.getPassword()).verified)
            throw new IllegalArgumentException("Wrong Password");
        return userEntityData;
    }

    private PartnerEntity validatePartnerData(MultiValueMap<String, String> loginData) throws Exception {
        if (!Objects.equals(loginData.getFirst("grant_type"), LoginType.CLIENT_CREDENTIALS.getValue()))
            throw new AccessDeniedException("Invalid grant type");
        PartnerEntity partnerData = partnerRepo.findByClientId(loginData.getFirst("client_id")).orElseThrow(() -> new EntityNotFoundException("Partner not found"));
        if (!partnerData.getStatus().equals(Status.ACTIVE)) throw new AccessDeniedException("Partner disabled");
        if(!Objects.equals(loginData.getFirst("client_secret"), partnerData.getClientSecret())) throw new AccessDeniedException("Wrong client secret");
        return partnerData;
    }

}