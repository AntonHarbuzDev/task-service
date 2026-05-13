package com.codemania.task_service.service;

import com.codemania.task_service.exception.EntityNotFoundException;
import com.codemania.task_service.model.DeliveryStatus;
import com.codemania.task_service.model.OutboxMessage;
import com.codemania.task_service.model.dto.OutboxMessageCreateDto;
import com.codemania.task_service.model.dto.OutboxMessageUpdateDeliveryStatusDto;
import com.codemania.task_service.model.mapper.OutboxMessageMapper;
import com.codemania.task_service.repository.OutboxMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutboxMessageService {

    private final OutboxMessageRepository outboxMessageRepository;
    private final OutboxMessageMapper outboxMessageMapper;

    @Transactional
    public OutboxMessage create(OutboxMessageCreateDto dto) {
        OutboxMessage outboxMessage = outboxMessageMapper.toEntity(dto);
        outboxMessage.setDeliveryStatus(DeliveryStatus.PENDING);
        OutboxMessage outboxMessageCreated = outboxMessageRepository.save(outboxMessage);
        log.debug("Create OutboxMessage - {} success.", outboxMessageCreated);
        return outboxMessageCreated;
    }

    @Transactional(readOnly = true)
    public List<OutboxMessage> getByDeliveryStatus(DeliveryStatus deliveryStatus) {
        return outboxMessageRepository.findByDeliveryStatus(deliveryStatus);
    }

    @Transactional
    public void updateDeliveryStatus(OutboxMessageUpdateDeliveryStatusDto dto) {
        OutboxMessage outboxMessageLoaded = loadById(dto.getId());
        outboxMessageMapper.updateDeliveryStatusFromDto(dto, outboxMessageLoaded);
        OutboxMessage outboxMessageSaved = outboxMessageRepository.save(outboxMessageLoaded);
        log.debug("Update delivery status from OutboxMessage - {} success", outboxMessageSaved);
    }

    private OutboxMessage loadById(Long id) {
        return outboxMessageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(""));
    }
}
