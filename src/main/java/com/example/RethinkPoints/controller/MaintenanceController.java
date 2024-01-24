package com.example.RethinkPoints.controller;

import com.example.RethinkPoints.dto.MessagesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/check")
public class MaintenanceController {
    @GetMapping(value = "/")
    public ResponseEntity<MessagesDTO> checkServer() {
        return ResponseEntity.ok(MessagesDTO.builder().message("Server Ok").build());
    }
}
