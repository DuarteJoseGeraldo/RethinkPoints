package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.register.RegisterCampaignDTO;
import com.example.hogwartsPoints.dto.register.RegisterDefaultCampaignDTO;
import com.example.hogwartsPoints.dto.update.UpdateCampaignDTO;
import com.example.hogwartsPoints.entity.CampaignEntity;
import com.example.hogwartsPoints.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import static com.example.hogwartsPoints.utils.AppUtils.getRequestToken;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/campaigns")
public class CampaignController {
    private final CampaignService campaignService;
    @PostMapping(value = "/register")
    public ResponseEntity<CampaignEntity> registerCampaign(@RequestBody RegisterCampaignDTO campaignData,  HttpServletRequest request) throws Exception {
        return ResponseEntity.created(URI.create("/campaign/register")).body(campaignService.registerCampaign(getRequestToken(request),campaignData));
    }
    @PostMapping(value = "/registerdefault")
    public ResponseEntity<CampaignEntity> registerDefaultCampaign(@RequestBody RegisterDefaultCampaignDTO campaignData, HttpServletRequest request) throws Exception {
        return ResponseEntity.created(URI.create("/campaign/register")).body(campaignService.registerDefaultCampaign(getRequestToken(request),campaignData));
    }
    @GetMapping(value = "/info")
    public ResponseEntity<CampaignEntity> getCampaignData(@RequestHeader String idCampaign, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(campaignService.getCampaignDataByIdCampaign(getRequestToken(request),idCampaign));
    }
    @PatchMapping(value = "/update")
    public ResponseEntity<CampaignEntity> updateCampaignData(@RequestParam Long campaignId, @RequestBody UpdateCampaignDTO campaignNewData, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(campaignService.updateCampaignData(getRequestToken(request),campaignId, campaignNewData));
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<MessagesDTO> deleteCampaign(@RequestParam Long campaignId, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(campaignService.deleteCampaign(getRequestToken(request),campaignId));
    }
}