package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.register.RegisterHouseDTO;
import com.example.hogwartsPoints.dto.update.UpdateHouseDTO;
import com.example.hogwartsPoints.entity.HouseEntity;
import com.example.hogwartsPoints.service.HouseService;
import static com.example.hogwartsPoints.utils.AppUtils.getRequestToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.nio.file.AccessDeniedException;

@Controller
@RequestMapping(value = "/houses")
@RequiredArgsConstructor
public class HouseController {
    private final HouseService houseService;

    @PostMapping(value = "/register")
    public ResponseEntity<HouseEntity> registerHouse(@RequestBody RegisterHouseDTO houseData, HttpServletRequest request) throws Exception {
        return ResponseEntity.created(URI.create("/houses/register")).body(houseService.registerHouse(getRequestToken(request),houseData));
    }

    @GetMapping(value = "/info")
    public ResponseEntity<HouseEntity> getHouseData(@RequestParam String houseName, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(houseService.getHouseDataByName(getRequestToken(request),houseName));
    }

    @PatchMapping(  value = "/update")
    public ResponseEntity<HouseEntity> updateHouseData(@RequestParam Long houseId, @RequestBody UpdateHouseDTO houseNewData, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(houseService.updateHouseData(getRequestToken(request), houseId,houseNewData));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<MessagesDTO> deleteHouse(@RequestParam Long houseId, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(houseService.deleteHouse(getRequestToken(request),houseId));
    }
}