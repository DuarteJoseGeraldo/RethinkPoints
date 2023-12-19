package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.register.RegisterPartnerDTO;
import com.example.hogwartsPoints.dto.enums.Status;
import com.example.hogwartsPoints.dto.update.UpdatePartnerDTO;
import com.example.hogwartsPoints.entity.PartnerEntity;
import com.example.hogwartsPoints.respository.AccessTokenRepository;
import com.example.hogwartsPoints.respository.PartnerRepository;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.hogwartsPoints.utils.AppUtils.getRandomAlphanumeric;
import static com.example.hogwartsPoints.utils.AppUtils.getPartnerClientId;
import static com.example.hogwartsPoints.utils.AppUtils.copyNonNullProperties;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepo;
    private final AccessTokenRepository accessTokenRepo;
    private final JwtUtil jwtUtil;

    public PartnerEntity getPartnerInfo(String accessToken, String code) throws Exception {
        jwtUtil.adminValidator(accessToken);
        return partnerRepo.findByCode(code).orElseThrow(() -> new EntityNotFoundException("Partner not found"));
    }

    public PartnerEntity registerPartner(String accessToken, RegisterPartnerDTO partnerData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        partnerRegisterValidator(partnerData);
        return partnerRepo.save(PartnerEntity.builder()
                .code(partnerData.getCode().toUpperCase())
                .name(partnerData.getName())
                .status(Status.ACTIVE)
                .clientId(getPartnerClientId(partnerData.getCode()))
                .clientSecret(getRandomAlphanumeric())
                .createdAt(LocalDateTime.now()).build());
    }

    public PartnerEntity updatePartner(String accessToken, String clientId, UpdatePartnerDTO partnerData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        partnerUpdateValidator(partnerData);
        PartnerEntity partner = partnerRepo.findByClientId(clientId).orElseThrow(() -> new EntityNotFoundException("Partner Not Found"));
        copyNonNullProperties(partnerData, partner);
        partnerRepo.save(partner);
        return partner;
    }

    public MessagesDTO disablePartner(String accessToken, String clientId) throws Exception {
        jwtUtil.adminValidator(accessToken);
        PartnerEntity partner = partnerRepo.findByClientId(clientId).orElseThrow(() -> new EntityNotFoundException("Partner Not Found"));
        partner.setStatus(Status.INACTIVE);
        partnerRepo.save(partner);
        try {
            jwtUtil.disableTokenByUserIdentifier(partner.getCode());
        } catch (EntityNotFoundException ignored) {
            return MessagesDTO.builder().message("Partner successfully disabled ").build();
        }
        return MessagesDTO.builder().message("Partner successfully disabled ").build();
    }

    public MessagesDTO deletePartner(String accessToken, Long partnerId) throws Exception {
        jwtUtil.adminValidator(accessToken);
        PartnerEntity partner = partnerRepo.findById(partnerId).orElseThrow(() -> new EntityNotFoundException("Partner Not Found"));
        partnerRepo.deleteById(partnerId);
        try {
            jwtUtil.disableTokenByUserIdentifier(partner.getCode());
        } catch (EntityNotFoundException ignored) {
            return MessagesDTO.builder().message("Partner successfully deleted ").build();
        }
        return MessagesDTO.builder().message("Partner successfully deleted ").build();
    }

    private void partnerRegisterValidator(RegisterPartnerDTO partnerData) {
        partnerData.setCode(partnerData.getCode().toUpperCase());
        if (partnerRepo.findByCode(partnerData.getCode()).isPresent())
            throw new EntityExistsException("Partner code already registered");
        if (partnerData.getCode().length() != 3)
            throw new IllegalArgumentException("Partner code must be 3 characters long");
        partnerData.setCode(partnerData.getCode().toUpperCase());
    }

    private void partnerUpdateValidator(UpdatePartnerDTO partnerData) {
        if (Objects.nonNull(partnerData.getCode())) {
            partnerData.setCode(partnerData.getCode().toUpperCase());
            if (partnerRepo.findByCode(partnerData.getCode()).isPresent())
                throw new EntityExistsException("Partner code already registered");
            if (partnerData.getCode().length() != 3)
                throw new IllegalArgumentException("Partner code must be 3 characters long");
        }
    }
}