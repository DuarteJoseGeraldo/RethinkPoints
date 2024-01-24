package com.example.RethinkPoints.controller;

import com.example.RethinkPoints.service.PointsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static com.example.RethinkPoints.utils.AppUtils.getRequestToken;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/points")
public class PointsController {
    private final PointsService pointsService;

    @PostMapping(value = "/redeem")
    public ResponseEntity<?> redeemProduct(@RequestHeader String productCode, HttpServletRequest request) throws Exception {
        return ResponseEntity.created(URI.create("/points/redeem")).body(pointsService.redeemProduct(getRequestToken(request), productCode));
    }
}
