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

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Controller
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerPartner(@RequestBody RegisterPartnerDTO partnerData, HttpServletRequest request) throws Exception {
        if (Objects.isNull(request.getAttribute("accessToken")))
            throw new AccessDeniedException("Access Token required");
        return ResponseEntity.status(HttpStatus.CREATED).body(partnerService.registerPartner(request.getAttribute("accessToken").toString(), partnerData));
    }

    @DeleteMapping(value = "/disable")
    public ResponseEntity<?> disablePartner(@RequestParam String clientId, HttpServletRequest request) throws Exception {
        if (Objects.isNull(request.getAttribute("accessToken")))
            throw new AccessDeniedException("Access Token required");
        return ResponseEntity.status(HttpStatus.OK).body(partnerService.disablePartner(request.getAttribute("accessToken").toString(), clientId));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deletePartner(@RequestParam Long partnerId, HttpServletRequest request) throws Exception {
        if (Objects.isNull(request.getAttribute("accessToken")))
            throw new AccessDeniedException("Access Token required");
        return ResponseEntity.status(HttpStatus.OK).body(partnerService.deletePartner(request.getAttribute("accessToken").toString(), partnerId));
    }

    @PatchMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePartner(@RequestParam String clientId, @RequestBody UpdatePartnerDTO partnerData, HttpServletRequest request) throws Exception {
        if (Objects.isNull(request.getAttribute("accessToken")))
            throw new AccessDeniedException("Access Token required");
        return ResponseEntity.status(HttpStatus.OK).body(partnerService.updatePartner(request.getAttribute("accessToken").toString(), clientId, partnerData));
    }
}
