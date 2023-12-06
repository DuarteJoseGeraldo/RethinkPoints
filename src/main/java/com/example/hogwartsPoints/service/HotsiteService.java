package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.entity.*;
import com.example.hogwartsPoints.respository.CampaignRepository;
import com.example.hogwartsPoints.respository.HotsiteRepository;
import com.example.hogwartsPoints.respository.PartnerRepository;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotsiteService {
    private final HotsiteRepository hotsiteRepo;
    private final JwtUtil jwtUtil;
    private final PartnerRepository partnerRepo;
    private final CampaignRepository campaignRepo;

    public String getHotsiteToken(String accessToken, MultiValueMap<String, String> requestBody) throws Exception {
        UserEntity user = jwtUtil.userTokenValidator(accessToken);
        HotsiteEntity hotsiteData = validateGetHotsiteTokenData(requestBody);
        hotsiteData.setCpf(user.getCpf());
        hotsiteData.setToken(jwtUtil.generateHotsiteToken());
        hotsiteRepo.save(hotsiteData);
        return hotsiteData.getToken();
    }

    private HotsiteEntity validateGetHotsiteTokenData(MultiValueMap<String, String> requestBody) {
        CampaignEntity campaign = campaignRepo.findByIdCampaignIgnoreCase(requestBody.getFirst("id_campaign")).orElseThrow(() -> new EntityNotFoundException("Campaign not found"));
        PartnerEntity partner = partnerRepo.findByCode(requestBody.getFirst("partner_code")).orElseThrow(() -> new EntityNotFoundException("Campaign not found"));
        return HotsiteEntity.builder().clickDate(LocalDateTime.now()).idCampaign(campaign.getIdCampaign()).partnerCode(partner.getCode()).build();
    }
}
