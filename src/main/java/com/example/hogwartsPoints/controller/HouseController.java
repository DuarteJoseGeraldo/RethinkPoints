package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.DTOs.RegisterHouseDTO;
import com.example.hogwartsPoints.service.HouseService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/houses")
public class HouseController {

    @Autowired
    HouseService houseService;

    @PostMapping()
    public ResponseEntity<?> registerHouse(@RequestBody RegisterHouseDTO houseData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(houseService.registerHouse(houseData));
    }

    @GetMapping("/data")
    public ResponseEntity<?> getHouseData(@RequestHeader String houseName) {
        return ResponseEntity.status(HttpStatus.OK).body(houseService.getHouseDataByName(houseName));
    }
}
