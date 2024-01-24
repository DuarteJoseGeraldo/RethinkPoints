package com.example.RethinkPoints.api;

import com.example.RethinkPoints.dto.MessagesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "RethinkPoints", url = "https://rethinkpoins.onrender.com")
public interface RethinkPointsClient {
    @GetMapping(path = "/check/")
    MessagesDTO checkServer();
}