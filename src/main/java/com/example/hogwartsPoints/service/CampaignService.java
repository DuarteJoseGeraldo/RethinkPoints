package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.DTOs.RegisterCampaignDTO;
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


    public CampaignEntity registerCampaign(RegisterCampaignDTO campaingData) {
        if (campaignRepo.findByIdCampaignIgnoreCase(campaingData.getIdCampaign()).isPresent())
            throw new EntityExistsException("Campaign already exists");
        if (campaingData.getStartAt().isBefore(LocalDateTime.now()))
            throw new DateTimeException("The start date of the campaign must be greater than the current date");
        if (campaingData.getStartAt().isAfter(campaingData.getEndAt()))
            throw new DateTimeException("The campaign start date must be less than the end date");
        if (Objects.nonNull(campaingData.getOurParity()) && campaingData.getOurParity() < 0)
            throw new IllegalArgumentException("Our Parity needs to be greater than 0");
        if (Objects.nonNull(campaingData.getPartnerParity()) && campaingData.getPartnerParity() < 0)
            throw new IllegalArgumentException("Partner Parity needs to be greater than 0");

        if (Objects.isNull(campaingData.getOurParity()) && Objects.nonNull(campaingData.getPartnerParity())
                || Objects.isNull(campaingData.getPartnerParity()) && Objects.nonNull(campaingData.getOurParity()))
            throw new IllegalArgumentException("You can't register only one score parity");

        return campaignRepo.save(CampaignEntity.builder()
                .idCampaign(campaingData.getIdCampaign())
                .description(campaingData.getDescription())
                .ourParity(campaingData.getOurParity())
                .partnerParity(campaingData.getPartnerParity())
                .startAt(campaingData.getStartAt())
                .endAt(campaingData.getEndAt())
                .build());
    }

    public CampaignEntity getCampaignDataByIdCampaign(String idCampaign){
        return campaignRepo.findByIdCampaignIgnoreCase(idCampaign).orElseThrow(() -> new EntityNotFoundException("Campaign Not Found"));
    }
}
