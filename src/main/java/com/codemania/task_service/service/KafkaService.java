package com.codemania.task_service.service;

import com.codemania.task_service.model.DeliveryStatus;
import com.codemania.task_service.model.OutboxMessage;
import com.codemania.task_service.model.dto.OutboxMessageUpdateDeliveryStatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {

    @Value("${spring.application.name}")
    private String appName;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OutboxMessageService outboxMessageService;

    @Transactional
    public void sendByDeliveryStatus(DeliveryStatus deliveryStatus) {
        List<OutboxMessage> outboxMessages = outboxMessageService.getByDeliveryStatus(deliveryStatus);
        outboxMessages.forEach(this::sendMessage);
    }

    private void sendMessage(OutboxMessage outboxMessage) {
        log.debug("Sending OutboxMessage to kafka - {}", outboxMessage);
        String payload = outboxMessage.getPayload();
        var result = kafkaTemplate.send(appName, payload);

        result.whenComplete((sendResult, throwable) -> {
            OutboxMessageUpdateDeliveryStatusDto outboxMessageUpdateDeliveryStatusDto = new OutboxMessageUpdateDeliveryStatusDto();
            outboxMessageUpdateDeliveryStatusDto.setId(outboxMessage.getId());
            if (throwable == null) {
                log.debug("Payload - {} send to kafka success", payload);
                outboxMessageUpdateDeliveryStatusDto.setDeliveryStatus(DeliveryStatus.PUBLISHED);
            } else {
                log.error("Failed send payload - {} to kafka: {}", payload, throwable.getMessage());
                outboxMessageUpdateDeliveryStatusDto.setDeliveryStatus(DeliveryStatus.FAILED);
            }
            outboxMessageService.updateDeliveryStatus(outboxMessageUpdateDeliveryStatusDto);
        });
    }
}
