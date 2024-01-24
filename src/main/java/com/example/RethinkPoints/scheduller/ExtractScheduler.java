package com.example.RethinkPoints.scheduller;

import com.example.RethinkPoints.api.RethinkPointsClient;
import com.example.RethinkPoints.dto.MessagesDTO;
import com.example.RethinkPoints.service.PointsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExtractScheduler {
    private final PointsService pointsService;
    private final RethinkPointsClient rethinkPointsClient;

    //    @Scheduled(cron = "*/10 * * * * *")
    @Scheduled(cron = "0 0/30 * * * *")
    public void creditPoints() {
        log.info("creditPoints() - 'Schedule Started'");
        pointsService.creditPointsOfConfirmedOrders();
    }

    @Scheduled(cron = "*/60 * * * * *")
    public void checkServer(){
        MessagesDTO status = rethinkPointsClient.checkServer();
        log.info("checkServer() - 'Server Status: {}'", status.getMessage());
    }
}