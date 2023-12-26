package com.food.ordering.system.order.service.domain.dto.create;

import com.simba.domain.valueobject.OrderStatusVO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderResponse {
    @NotNull
    private UUID orderTrackingId;
    @NotNull
    private OrderStatusVO orderStatus;
    @NotNull
    private String message;
}
