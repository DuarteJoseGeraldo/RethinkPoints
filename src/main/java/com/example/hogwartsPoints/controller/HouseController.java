package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.register.RegisterHouseDTO;
import com.example.hogwartsPoints.dto.update.UpdateHouseDTO;
import com.example.hogwartsPoints.service.HouseService;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerHouse(@RequestBody RegisterHouseDTO houseData, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(houseService.registerHouse(houseData));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getHouseData(@RequestParam String houseName) {
        return ResponseEntity.status(HttpStatus.OK).body(houseService.getHouseDataByName(houseName));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateHouseData(@RequestParam Long houseId, @RequestBody UpdateHouseDTO houseNewData, HttpServletRequest request) throws Exception {
        houseNewData.setId(houseId);
        return ResponseEntity.status(HttpStatus.OK).body(houseService.updateHouseData(houseNewData));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteHouse(@RequestParam Long houseId) {
        return ResponseEntity.status(HttpStatus.OK).body(houseService.deleteHouse(houseId));
    }
}