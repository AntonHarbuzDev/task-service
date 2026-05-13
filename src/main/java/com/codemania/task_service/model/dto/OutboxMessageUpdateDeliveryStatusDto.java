package com.codemania.task_service.model.dto;

import com.codemania.task_service.model.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutboxMessageUpdateDeliveryStatusDto {

    private Long id;
    private DeliveryStatus deliveryStatus;
}
