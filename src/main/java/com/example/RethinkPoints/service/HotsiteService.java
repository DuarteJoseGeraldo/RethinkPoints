package com.example.RethinkPoints.service;

import com.example.RethinkPoints.entity.*;
import com.example.RethinkPoints.respository.CampaignRepository;
import com.example.RethinkPoints.respository.HotsiteRepository;
import com.example.RethinkPoints.respository.PartnerRepository;
import com.example.RethinkPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import static com.example.RethinkPoints.utils.AppUtils.getRandomAlphanumeric;

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
        hotsiteData.setToken(getRandomAlphanumeric());
        hotsiteRepo.save(hotsiteData);
        return hotsiteData.getToken();
    }

    public HotsiteEntity hotsiteTokenValidator (String token){
        return hotsiteRepo.findById(token).orElseThrow(() -> new EntityNotFoundException("Hotsite Token Not Found")) ;
    }

    public void orderConfirmation (String hotsiteToken){
        HotsiteEntity hotsite = hotsiteRepo.findById(hotsiteToken).orElseThrow(() -> new EntityNotFoundException("Hotsite Token not found - final"));
        hotsite.setOrderConfirmation(true);
        hotsiteRepo.save(hotsite);
    }

    private HotsiteEntity validateGetHotsiteTokenData(MultiValueMap<String, String> requestBody) {
        CampaignEntity campaign = campaignRepo.findByIdCampaign(requestBody.getFirst("id_campaign")).orElseThrow(() -> new EntityNotFoundException("Campaign not found"));
        PartnerEntity partner = partnerRepo.findByCode(requestBody.getFirst("partner_code")).orElseThrow(() -> new EntityNotFoundException("Partner not found"));
        if (!campaign.getPartner().getCode().equals(partner.getCode())) throw new IllegalArgumentException("Campaign does not belong to this partner");
        return HotsiteEntity.builder().clickDate(LocalDateTime.now()).idCampaign(campaign.getIdCampaign()).partnerCode(partner.getCode()).build();
    }
}
