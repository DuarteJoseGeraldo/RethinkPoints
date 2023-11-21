package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.RegisterUserDTO;
import com.example.hogwartsPoints.service.UserService;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    JwtUtil jwtUtil;

    @GetMapping("/data") //acessar os dados de qualquer user
    public ResponseEntity<?> getUserDataById(@RequestHeader Long userId, HttpServletRequest request) throws Exception {
        jwtUtil.adminValidator((String) request.getAttribute("userType"));
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDataById(userId));
    }

    @GetMapping("/info") // dados do user logado
    public ResponseEntity<?> getUserDataById(HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDataById((Long)request.getAttribute("userId")));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO userData, HttpServletRequest request) throws Exception {
        jwtUtil.adminValidator((String) request.getAttribute("userType"));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userData));
    }
}
