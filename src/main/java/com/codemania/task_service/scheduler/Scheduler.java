package com.codemania.task_service.scheduler;

import com.codemania.task_service.model.DeliveryStatus;
import com.codemania.task_service.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Scheduler {

    private final KafkaService kafkaService;

    @Scheduled(fixedDelayString = "${scheduled.interval.send-pending}")
    public void sendMessageByStatusPending() {
        log.debug("Start scheduled PENDING");
        kafkaService.sendByDeliveryStatus(DeliveryStatus.PENDING);
    }

}
