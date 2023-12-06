package com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment;

import com.simba.domain.event.publisher.DomainEventPublisher;
import com.simba.order.service.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublishser extends DomainEventPublisher<OrderCancelledEvent> {
}
