package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.dto.RegisterCampaignDTO;
import com.example.hogwartsPoints.entity.CampaignEntity;
import com.example.hogwartsPoints.respository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class CampaignService {
    @Autowired
    CampaignRepository campaignRepo;


    public CampaignEntity registerCampaign(RegisterCampaignDTO campaignData) {
        if (campaignRepo.findByIdCampaignIgnoreCase(campaignData.getIdCampaign()).isPresent())
            throw new EntityExistsException("Campaign already exists");
        if (campaignData.getStartAt().isBefore(LocalDateTime.now()))
            throw new DateTimeException("The start date of the campaign must be greater than the current time");
        if (campaignData.getStartAt().isAfter(campaignData.getEndAt()))
            throw new DateTimeException("The campaign start date must be less than the end date");
        if (Objects.nonNull(campaignData.getOurParity()) && campaignData.getOurParity() < 0)
            throw new IllegalArgumentException("Our Parity needs to be greater than 0");
        if (Objects.nonNull(campaignData.getPartnerParity()) && campaignData.getPartnerParity() < 0)
            throw new IllegalArgumentException("Partner Parity needs to be greater than 0");

        if (Objects.isNull(campaignData.getOurParity()) && Objects.nonNull(campaignData.getPartnerParity())
                || Objects.isNull(campaignData.getPartnerParity()) && Objects.nonNull(campaignData.getOurParity()))
            throw new IllegalArgumentException("You can't register only one score parity");

        return campaignRepo.save(CampaignEntity.builder()
                .idCampaign(campaignData.getIdCampaign())
                .description(campaignData.getDescription())
                .ourParity(campaignData.getOurParity())
                .partnerParity(campaignData.getPartnerParity())
                .startAt(campaignData.getStartAt())
                .endAt(campaignData.getEndAt())
                .build());
    }

    public CampaignEntity getCampaignDataByIdCampaign(String idCampaign){
        return campaignRepo.findByIdCampaignIgnoreCase(idCampaign).orElseThrow(() -> new EntityNotFoundException("Campaign Not Found"));
    }
}
