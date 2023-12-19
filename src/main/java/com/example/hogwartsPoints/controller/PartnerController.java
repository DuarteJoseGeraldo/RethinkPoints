package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.register.RegisterPartnerDTO;
import com.example.hogwartsPoints.dto.update.UpdatePartnerDTO;
import com.example.hogwartsPoints.entity.PartnerEntity;
import com.example.hogwartsPoints.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.hogwartsPoints.utils.AppUtils.getRequestToken;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Controller
@RequestMapping(value = "/partner")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;

    @GetMapping(value = "/info")
    public ResponseEntity<PartnerEntity> getPartnerInfo(@RequestParam String code, HttpServletRequest request) throws Exception{
        return ResponseEntity.ok(partnerService.getPartnerInfo(getRequestToken(request), code));
    }
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PartnerEntity> registerPartner(@RequestBody RegisterPartnerDTO partnerData, HttpServletRequest request) throws Exception {
        return ResponseEntity.created(URI.create("/partner/register")).body(partnerService.registerPartner(getRequestToken(request), partnerData));
    }

    @DeleteMapping(value = "/disable")
    public ResponseEntity<MessagesDTO> disablePartner(@RequestParam String clientId, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(partnerService.disablePartner(getRequestToken(request), clientId));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<MessagesDTO> deletePartner(@RequestParam Long partnerId, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(partnerService.deletePartner(getRequestToken(request), partnerId));
    }

    @PatchMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PartnerEntity> updatePartner(@RequestParam String clientId, @RequestBody UpdatePartnerDTO partnerData, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(partnerService.updatePartner(getRequestToken(request), clientId, partnerData));
    }
}
