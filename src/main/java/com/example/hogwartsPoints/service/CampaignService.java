package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.register.RegisterCampaignDTO;
import com.example.hogwartsPoints.dto.update.UpdateCampaignDTO;
import com.example.hogwartsPoints.entity.CampaignEntity;
import com.example.hogwartsPoints.respository.CampaignRepository;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static com.example.hogwartsPoints.utils.AppUtils.copyNonNullProperties;
import javax.persistence.EntityNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Objects;
@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignService {
    private final CampaignRepository campaignRepo;
    private final JwtUtil jwtUtil;

    public CampaignEntity registerCampaign(String accessToken, RegisterCampaignDTO campaignData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        validateCampaignData(campaignData);
        return campaignRepo.save(CampaignEntity.builder()
                .idCampaign(campaignData.getIdCampaign())
                .description(campaignData.getDescription())
                .ourParity(campaignData.getOurParity())
                .partnerParity(campaignData.getPartnerParity())
                .startAt(campaignData.getStartAt())
                .endAt(campaignData.getEndAt())
                .build());
    }

    public CampaignEntity getCampaignDataByIdCampaign(String accessToken,String idCampaign) throws Exception {
        jwtUtil.userTokenValidator(accessToken);
        return campaignRepo.findByIdCampaignIgnoreCase(idCampaign).orElseThrow(() -> new EntityNotFoundException("Campaign Not Found"));
    }

    public CampaignEntity updateCampaignData(String accessToken,Long campaignId , UpdateCampaignDTO campaignNewData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        CampaignEntity campaignData = campaignRepo.findById(campaignId).orElseThrow(() -> new EntityNotFoundException("Campaign Not Found"));
        copyNonNullProperties(campaignNewData, campaignData);
        validateCampaignData(campaignData);
        log.info("updateCampaignData() - 'Campaign atualizada': {}", campaignData);
        return campaignRepo.save(campaignData);
    }

    public MessagesDTO deleteCampaign(String accessToken,Long id) throws Exception {
        jwtUtil.adminValidator(accessToken);
        campaignRepo.deleteById(id);
        return MessagesDTO.builder().message("Campaign deleted successfully").build();
    }

    public float calculatePoints(String idCampaign, Double total){
        CampaignEntity campaign = campaignRepo.findByIdCampaignIgnoreCase(idCampaign).orElseThrow(() -> new EntityNotFoundException("Campaign Not Found"));
        return (float) ((campaign.getOurParity() * total) * campaign.getPartnerParity());
    }

    private void validateCampaignData(RegisterCampaignDTO campaignData) {
        if (campaignData.getStartAt().isBefore(LocalDateTime.now()))
            throw new DateTimeException("The start date of the campaign must be greater than the current time");
        if (campaignData.getStartAt().isAfter(campaignData.getEndAt()))
            throw new DateTimeException("The campaign start date must be less than the end date");
        if (Objects.isNull(campaignData.getOurParity()) || campaignData.getOurParity() <= 0)
            throw new IllegalArgumentException("Our Parity needs to be greater than 0");
        if (Objects.isNull(campaignData.getPartnerParity()) || campaignData.getPartnerParity() <= 0)
            throw new IllegalArgumentException("Partner Parity needs to be greater than 0");
        campaignData.setIdCampaign(campaignData.getIdCampaign().replace(" ", "").toUpperCase());
    }

    private void validateCampaignData(CampaignEntity campaignData) {
        if (campaignData.getStartAt().isBefore(LocalDateTime.now()))
            throw new DateTimeException("The start date of the campaign must be greater than the current time");
        if (campaignData.getStartAt().isAfter(campaignData.getEndAt()))
            throw new DateTimeException("The campaign start date must be less than the end date");
        if (Objects.isNull(campaignData.getOurParity()) || campaignData.getOurParity() <= 0)
            throw new IllegalArgumentException("Our Parity needs to be greater than 0");
        if (Objects.isNull(campaignData.getPartnerParity()) || campaignData.getPartnerParity() <= 0)
            throw new IllegalArgumentException("Partner Parity needs to be greater than 0");
    }
}
