package com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval;

import com.simba.domain.event.DomainEvent;
import com.simba.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEvent<OrderPaidEvent> {
}
