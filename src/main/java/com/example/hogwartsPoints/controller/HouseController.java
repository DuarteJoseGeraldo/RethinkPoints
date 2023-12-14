package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.register.RegisterHouseDTO;
import com.example.hogwartsPoints.dto.update.UpdateHouseDTO;
import com.example.hogwartsPoints.service.HouseService;
import static com.example.hogwartsPoints.utils.AppUtils.getRequestToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@Controller
@RequestMapping("/houses")
@RequiredArgsConstructor
public class HouseController {
    private final HouseService houseService;

    @PostMapping("/register")
    public ResponseEntity<?> registerHouse(@RequestBody RegisterHouseDTO houseData, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(houseService.registerHouse(getRequestToken(request),houseData));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getHouseData(@RequestParam String houseName, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(houseService.getHouseDataByName(getRequestToken(request),houseName));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateHouseData(@RequestParam Long houseId, @RequestBody UpdateHouseDTO houseNewData, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(houseService.updateHouseData(getRequestToken(request), houseId,houseNewData));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteHouse(@RequestParam Long houseId, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(houseService.deleteHouse(getRequestToken(request),houseId));
    }
}