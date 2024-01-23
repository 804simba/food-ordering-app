package com.food.ordering.system.order.service.dataaccess.order.mapper;

import com.food.ordering.system.order.service.dataaccess.order.entity.OrderAddressEntity;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderItemEntity;
import com.simba.domain.valueobject.*;
import com.simba.order.service.domain.entity.Order;
import com.simba.order.service.domain.entity.OrderItem;
import com.simba.order.service.domain.entity.Product;
import com.simba.order.service.domain.valueobject.OrderItemIdVO;
import com.simba.order.service.domain.valueobject.StreetAddressVO;
import com.simba.order.service.domain.valueobject.TrackingIdVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDataAccessMapper {
    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                .price(order.getPrice().getAmount())
                .items(orderItemsToOrderItemEntities(order.getOrderItems()))
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages() != null ?
                        String.join(Order.FAILURE_MESSAGE_DELIMITER, order.getFailureMessages()) : "").build();

        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));

        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.Builder.builder()
                .orderId(new OrderIdVO(orderEntity.getId()))
                .customerId(new CustomerIdVO(orderEntity.getCustomerId()))
                .restaurantId(new RestaurantIdVO(orderEntity.getRestaurantId()))
                .deliveryAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
                .price(new MoneyVO(orderEntity.getPrice()))
                .orderItems(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                .trackingId(new TrackingIdVO(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .failureMessages(orderEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages()
                                .split(Order.FAILURE_MESSAGE_DELIMITER)))).build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return items.stream().map(orderItemEntity -> OrderItem.Builder.builder()
                .orderItemId(new OrderItemIdVO(orderItemEntity.getId()))
                .product(new Product(new ProductIdVO(orderItemEntity.getProductId())))
                .price(new MoneyVO(orderItemEntity.getPrice()))
                .quantity(orderItemEntity.getQuantity())
                .subTotal(new MoneyVO(orderItemEntity.getSubTotal())).build())
                .collect(Collectors.toList());
    }

    private StreetAddressVO addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddressVO(address.getId(),
                address.getStreet(), address.getPostalCode(), address.getCity());
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> orderItems) {
        return orderItems.stream().map(orderItem -> OrderItemEntity.builder()
                .id(orderItem.getId().getValue())
                .productId(orderItem.getProduct().getId().getValue())
                .price(orderItem.getPrice().getAmount())
                .quantity(orderItem.getQuantity())
                .subTotal(orderItem.getSubTotal().getAmount())
                .build()).collect(Collectors.toList());
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddressVO deliveryAddress) {
        return OrderAddressEntity.builder()
                .id(deliveryAddress.getId())
                .street(deliveryAddress.getStreet())
                .postalCode(deliveryAddress.getPostalCode())
                .city(deliveryAddress.getCity()).build();
    }
}
