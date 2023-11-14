package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.DTOs.RegisterUserDTO;
import com.example.hogwartsPoints.service.UserService;
import com.sun.istack.FinalArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/data")
    public ResponseEntity<?> getUserDataById(@RequestParam Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDataById(id));
    }
    @PostMapping()
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO userData) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userData));
    }
}
