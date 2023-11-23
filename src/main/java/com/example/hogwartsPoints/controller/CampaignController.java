package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.RegisterCampaignDTO;
import com.example.hogwartsPoints.dto.UpdateCampaignDTO;
import com.example.hogwartsPoints.service.CampaignService;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    private final CampaignService campaignService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerCampaign(@RequestBody RegisterCampaignDTO campaignData,  HttpServletRequest request) throws Exception {
        jwtUtil.adminValidator((String) request.getAttribute("userType"));
        return ResponseEntity.status(HttpStatus.CREATED).body(campaignService.registerCampaign(campaignData));
    }

    @GetMapping("/data")
    public ResponseEntity<?> getCampaignData(@RequestHeader String idCampaign) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.getCampaignDataByIdCampaign(idCampaign));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateCampaignData(@RequestParam String idCampaign, @RequestBody UpdateCampaignDTO campaignNewData, HttpServletRequest request) throws Exception {
        jwtUtil.adminValidator((String) request.getAttribute("userType"));
        campaignNewData.setIdCampaign(idCampaign);
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.updateCampaignData(campaignNewData));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCampaign(@RequestParam Long id, HttpServletRequest request) throws Exception {
        jwtUtil.adminValidator((String) request.getAttribute("userType"));
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.deleteCampaign(id));
    }
}