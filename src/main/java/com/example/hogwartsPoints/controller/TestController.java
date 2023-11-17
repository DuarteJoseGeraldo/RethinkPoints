package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.service.AuthService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    AuthService authService;

    @PostMapping("/usertoken")
    public ResponseEntity<?> testUserToken(@RequestBody @NonNull String token){
        return ResponseEntity.status(HttpStatus.OK).body(authService.testUserToken(token));
    }
    @PostMapping("/admintoken")
    public ResponseEntity<?> testAdminToken(@RequestBody @NonNull String token){
        return ResponseEntity.status(HttpStatus.OK).body(authService.testAdminToken(token));
    }
}
