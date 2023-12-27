package com.example.hogwartsPoints.scheduller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExtractScheduler {
    @Scheduled(cron = "0 08 16 * * *")
    public void scheduledTask() {
        log.info("scheduledTask() - 'tarefa agendada executada com secusso'");
    }
}
