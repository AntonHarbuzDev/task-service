package com.codemania.task_service.config;

import com.codemania.task_service.model.DeliveryStatus;
import com.codemania.task_service.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
@Slf4j
@RequiredArgsConstructor
public class SchedulingConfig {

    private final KafkaService kafkaService;

    @Scheduled(cron = "${scheduled.interval.send-pending}")
    public void sendMessageByStatusPending() {
        log.debug("Start scheduled PENDING");
        kafkaService.sendByDeliveryStatus(DeliveryStatus.PENDING);
    }

}
