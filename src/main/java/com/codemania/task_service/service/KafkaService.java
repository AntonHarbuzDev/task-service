package com.codemania.task_service.service;

import com.codemania.task_service.model.dto.TaskKafka;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {

    @Value("${spring.application.name}")
    private String appName;

    private final KafkaTemplate<String, TaskKafka> taskKafkaKafkaTemplate;

    public void sendMessage(TaskKafka taskKafka) {
        log.debug("Sending task kafka - {}", taskKafka);

        var result = taskKafkaKafkaTemplate.send(appName, taskKafka);

        result.whenComplete((stringTaskKafkaSendResult, throwable) -> {
           if (throwable == null) {
                log.debug("Task title - {} send to kafka success", taskKafka.getTitle());
           } else {
               log.error("Failed send task title - {} to kafka: {}",taskKafka.getTitle(), throwable.getMessage());
           }
        });
    }
}
