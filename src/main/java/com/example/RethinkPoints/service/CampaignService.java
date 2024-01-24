package com.example.RethinkPoints.service;

import com.example.RethinkPoints.dto.MessagesDTO;
import com.example.RethinkPoints.dto.enums.Status;
import com.example.RethinkPoints.dto.register.RegisterCampaignDTO;
import com.example.RethinkPoints.dto.register.RegisterDefaultCampaignDTO;
import com.example.RethinkPoints.dto.update.UpdateCampaignDTO;
import com.example.RethinkPoints.entity.CampaignEntity;
import com.example.RethinkPoints.entity.PartnerEntity;
import com.example.RethinkPoints.respository.CampaignRepository;
import com.example.RethinkPoints.respository.PartnerRepository;
import com.example.RethinkPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.RethinkPoints.utils.AppUtils.copyNonNullProperties;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignService {
    private final CampaignRepository campaignRepo;
    private final PartnerRepository partnerRepo;
    private final JwtUtil jwtUtil;

    public CampaignEntity registerCampaign(String accessToken, RegisterCampaignDTO campaignData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        return campaignRepo.save(validateCampaignData(campaignData));
    }

    public CampaignEntity registerDefaultCampaign(String accessToken, RegisterDefaultCampaignDTO campaignData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        return campaignRepo.save(validateDefaultCampaignData(campaignData));
    }

    public CampaignEntity getCampaignDataByIdCampaign(String accessToken, String idCampaign) throws Exception {
        jwtUtil.userTokenValidator(accessToken);
        return campaignRepo.findByIdCampaign(idCampaign).orElseThrow(() -> new EntityNotFoundException("Campaign Not Found"));
    }

    public CampaignEntity updateCampaignData(String accessToken, Long campaignId, UpdateCampaignDTO campaignNewData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        CampaignEntity campaignData = campaignRepo.findById(campaignId).orElseThrow(() -> new EntityNotFoundException("Campaign Not Found"));
        copyNonNullProperties(campaignNewData, campaignData);
        log.info("updateCampaignData() - 'copyNonNullProperties': {}", campaignData);
        validateUpdateCampaignData(campaignData);
        return campaignRepo.save(campaignData);
    }

    public MessagesDTO deleteCampaign(String accessToken, Long id) throws Exception {
        jwtUtil.adminValidator(accessToken);
        campaignRepo.deleteById(id);
        return MessagesDTO.builder().message("Campaign deleted successfully").build();
    }

    public float calculatePoints(String idCampaign, float total) {
        CampaignEntity campaign = campaignRepo.findByIdCampaign(idCampaign).orElseThrow(() -> new EntityNotFoundException("Campaign Not Found"));
        return (total/campaign.getPartnerParity()) * campaign.getOurParity();
    }

    private CampaignEntity validateCampaignData(RegisterCampaignDTO campaignData) {
        if (campaignData.getStartAt().isBefore(LocalDateTime.now()))
            throw new DateTimeException("The start date of the campaign must be greater than the current time");
        if (campaignData.getStartAt().isAfter(campaignData.getEndAt()))
            throw new DateTimeException("The campaign start date must be less than the end date");
        if (campaignData.getOurParity() <= 0)
            throw new IllegalArgumentException("Our Parity needs to be greater than 0");
        if (campaignData.getPartnerParity() <= 0)
            throw new IllegalArgumentException("Partner Parity needs to be greater than 0");
        PartnerEntity partner = partnerRepo.findByCode(campaignData.getPartnerCode()).orElseThrow(() -> new EntityNotFoundException("Partner Not Found"));
        if(partner.getStatus().equals(Status.INACTIVE)) throw new IllegalArgumentException("Partner is not active");
        campaignData.setIdCampaign(campaignData.getIdCampaign().replace(" ", "").toUpperCase());
        return CampaignEntity.builder()
                .idCampaign(campaignData.getIdCampaign())
                .description(campaignData.getDescription())
                .ourParity(campaignData.getOurParity())
                .partnerParity(campaignData.getPartnerParity())
                .startAt(campaignData.getStartAt())
                .endAt(campaignData.getEndAt())
                .partner(partner)
                .build();
    }

    private CampaignEntity validateDefaultCampaignData(RegisterDefaultCampaignDTO campaignData) {
        if (campaignData.getOurParity() <= 0)
            throw new IllegalArgumentException("Our Parity needs to be greater than 0");
        if (campaignData.getPartnerParity() <= 0)
            throw new IllegalArgumentException("Partner Parity needs to be greater than 0");
        PartnerEntity partner = partnerRepo.findByCode(campaignData.getPartnerCode()).orElseThrow(() -> new EntityNotFoundException("Partner Not Found"));
        if(partner.getStatus().equals(Status.INACTIVE)) throw new IllegalArgumentException("Partner is not active");
        if(campaignRepo.findByIdCampaign("DEFAULT"+campaignData.getPartnerCode()).isPresent()) throw new EntityExistsException("Partner default campaign already registered");
        return CampaignEntity.builder()
                .idCampaign("DEFAULT"+campaignData.getPartnerCode())
                .description("Campanha padrao para "+partner.getName())
                .ourParity(campaignData.getOurParity())
                .partnerParity(campaignData.getPartnerParity())
                .startAt(LocalDateTime.now())
                .partner(partner)
                .build();
    }

    private void validateUpdateCampaignData(CampaignEntity campaignData) {
        if (!campaignData.getIdCampaign().contains("DEFAULT") && campaignData.getStartAt().isBefore(LocalDateTime.now()))
            throw new DateTimeException("The start date of the campaign must be greater than the current time");
        if (!campaignData.getIdCampaign().contains("DEFAULT") && campaignData.getStartAt().isAfter(campaignData.getEndAt()))
            throw new DateTimeException("The campaign start date must be less than the end date");
        if (Objects.isNull(campaignData.getOurParity()) || campaignData.getOurParity() <= 0)
            throw new IllegalArgumentException("Our Parity needs to be greater than 0");
        if (Objects.isNull(campaignData.getPartnerParity()) || campaignData.getPartnerParity() <= 0)
            throw new IllegalArgumentException("Partner Parity needs to be greater than 0");
    }
}