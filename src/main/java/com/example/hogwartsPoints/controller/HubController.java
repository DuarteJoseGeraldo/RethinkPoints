package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.register.RegisterHubDTO;
import com.example.hogwartsPoints.dto.update.UpdateHubDTO;
import com.example.hogwartsPoints.entity.HubEntity;
import com.example.hogwartsPoints.service.HubService;
import static com.example.hogwartsPoints.utils.AppUtils.getRequestToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Controller
@RequestMapping(value = "/hubs")
@RequiredArgsConstructor
public class HubController {
    private final HubService hubService;

    @PostMapping(value = "/register")
    public ResponseEntity<HubEntity> registerhub(@RequestBody RegisterHubDTO hubData, HttpServletRequest request) throws Exception {
        return ResponseEntity.created(URI.create("/hubs/register")).body(hubService.registerHub(getRequestToken(request),hubData));
    }

    @GetMapping(value = "/info")
    public ResponseEntity<HubEntity> gethubData(@RequestParam String hubName, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(hubService.getHubDataByName(getRequestToken(request),hubName));
    }

    @PatchMapping(  value = "/update")
    public ResponseEntity<HubEntity> updatehubData(@RequestParam Long hubId, @RequestBody UpdateHubDTO hubNewData, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(hubService.updateHubData(getRequestToken(request), hubId,hubNewData));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<MessagesDTO> deletehub(@RequestParam Long hubId, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(hubService.deleteHub(getRequestToken(request),hubId));
    }
}