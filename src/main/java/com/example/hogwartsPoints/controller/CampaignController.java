package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.DTOs.RegisterCampaignDTO;
import com.example.hogwartsPoints.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/campaigns")
public class CampaignController {
    @Autowired
    CampaignService campaignService;

    @PostMapping()
    public ResponseEntity<?> registerCampaign(@RequestBody RegisterCampaignDTO campaignData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campaignService.registerCampaign(campaignData));
    }

    @GetMapping("/data")
    public ResponseEntity<?> getCampaignData(@RequestHeader String idCampaign) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.getCampaignDataByIdCampaign(idCampaign));
    }
}
