package com.example.hogwartsPoints.controller;

import com.example.hogwartsPoints.service.HotsiteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.example.hogwartsPoints.utils.AppUtils.getRequestToken;

import javax.servlet.http.HttpServletRequest;
@Controller
@Slf4j
@RequestMapping(value = "/hotsite")
@RequiredArgsConstructor
public class HotsiteController {
    private final HotsiteService hotsiteService;
    @GetMapping(value = "/click", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> getHotsiteToken(@RequestBody MultiValueMap<String, String> requestBody, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(hotsiteService.getHotsiteToken(getRequestToken(request),requestBody));
    }
}