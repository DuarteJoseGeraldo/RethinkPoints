package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.dto.RegisterCampaignDTO;
import com.example.hogwartsPoints.entity.CampaignEntity;
import com.example.hogwartsPoints.respository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CampaignService {
    private final CampaignRepository campaignRepo;

    public CampaignEntity registerCampaign(RegisterCampaignDTO campaignData) {
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

    public CampaignEntity getCampaignDataByIdCampaign(String idCampaign) {
        return campaignRepo.findByIdCampaignIgnoreCase(idCampaign).orElseThrow(() -> new EntityNotFoundException("Campaign Not Found"));
    }

    private void validateCampaignData(RegisterCampaignDTO campaignData) {
        if (campaignData.getStartAt().isBefore(LocalDateTime.now()))
            throw new DateTimeException("The start date of the campaign must be greater than the current time");
        if (campaignData.getStartAt().isAfter(campaignData.getEndAt()))
            throw new DateTimeException("The campaign start date must be less than the end date");
        if (Objects.isNull(campaignData.getOurParity()) || campaignData.getOurParity() < 0)
            throw new IllegalArgumentException("Our Parity needs to be greater than 0");
        if (Objects.isNull(campaignData.getPartnerParity()) || campaignData.getPartnerParity() < 0)
            throw new IllegalArgumentException("Partner Parity needs to be greater than 0");
    }
}
