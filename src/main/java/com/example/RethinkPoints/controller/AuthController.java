package com.example.RethinkPoints.controller;

import com.example.RethinkPoints.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> getUserToken(@RequestBody MultiValueMap<String, String> body) throws Exception {
        return ResponseEntity.accepted().body(authService.getUserToken(body));
    }
    @PostMapping(value = "/partner/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> getPartnerToken(@RequestBody MultiValueMap<String, String> body) throws Exception {
        return ResponseEntity.accepted().body(authService.getPartnerToken(body));
    }
}