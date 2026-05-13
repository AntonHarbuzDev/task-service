package com.codemania.task_service.model.mapper;

import com.codemania.task_service.model.OutboxMessage;
import com.codemania.task_service.model.dto.OutboxMessageCreateDto;
import com.codemania.task_service.model.dto.OutboxMessageUpdateDeliveryStatusDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OutboxMessageMapper {

    OutboxMessage toEntity(OutboxMessageCreateDto dto);

    void updateDeliveryStatusFromDto(OutboxMessageUpdateDeliveryStatusDto dto, @MappingTarget OutboxMessage outboxMessage);
}
