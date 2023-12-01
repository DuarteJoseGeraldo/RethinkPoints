package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.register.RegisterPartnerDTO;
import com.example.hogwartsPoints.dto.update.UpdatePartnerDTO;
import com.example.hogwartsPoints.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.hogwartsPoints.utils.AppUtils.getRequestToken;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerPartner(@RequestBody RegisterPartnerDTO partnerData, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(partnerService.registerPartner(getRequestToken(request), partnerData));
    }

    @DeleteMapping(value = "/disable")
    public ResponseEntity<?> disablePartner(@RequestParam String clientId, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(partnerService.disablePartner(getRequestToken(request), clientId));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deletePartner(@RequestParam Long partnerId, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(partnerService.deletePartner(getRequestToken(request), partnerId));
    }

    @PatchMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePartner(@RequestParam String clientId, @RequestBody UpdatePartnerDTO partnerData, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(partnerService.updatePartner(getRequestToken(request), clientId, partnerData));
    }
}
