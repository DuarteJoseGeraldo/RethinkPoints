package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.LoginDTO;
import com.example.hogwartsPoints.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> getUserToken(@RequestBody LoginDTO loginData) throws Exception {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authService.getUserToken(loginData));
    }
}