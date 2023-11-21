package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.RegisterHouseDTO;
import com.example.hogwartsPoints.service.HouseService;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/houses")
@RequiredArgsConstructor
public class HouseController {
    private final HouseService houseService;
    JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerHouse(@RequestBody RegisterHouseDTO houseData, HttpServletRequest request) throws Exception {
        jwtUtil.adminValidator((String) request.getAttribute("userType"));
        return ResponseEntity.status(HttpStatus.CREATED).body(houseService.registerHouse(houseData));
    }


    @GetMapping("/data")
    public ResponseEntity<?> getHouseData(@RequestHeader String houseName, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(houseService.getHouseDataByName(houseName));
    }
}