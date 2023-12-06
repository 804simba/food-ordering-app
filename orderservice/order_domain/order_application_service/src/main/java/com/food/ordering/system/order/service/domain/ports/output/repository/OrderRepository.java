package com.food.ordering.system.order.service.domain.ports.output.repository;

import com.simba.order.service.domain.entity.Order;
import com.simba.order.service.domain.valueobject.TrackingIdVO;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findByTrackingId(TrackingIdVO trackingId);
}
