package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.DTOs.LoginDTO;
import com.example.hogwartsPoints.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> getUserToken(@RequestBody LoginDTO loginData){

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authService.getUserToken(loginData));
    }
}
