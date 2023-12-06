package com.food.ordering.system.order.service.domain.mapper;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.simba.domain.valueobject.CustomerIdVO;
import com.simba.domain.valueobject.MoneyVO;
import com.simba.domain.valueobject.ProductIdVO;
import com.simba.domain.valueobject.RestaurantIdVO;
import com.simba.order.service.domain.entity.Order;
import com.simba.order.service.domain.entity.OrderItem;
import com.simba.order.service.domain.entity.Product;
import com.simba.order.service.domain.entity.Restaurant;
import com.simba.order.service.domain.valueobject.StreetAddressVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.Builder.builder()
                .id(new RestaurantIdVO(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(orderItem ->
                        new Product(new ProductIdVO(orderItem.getProductId())))
                        .collect(Collectors.toList())
                )
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.Builder.builder()
                .customerId(new CustomerIdVO(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantIdVO(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getOrderAddress()))
                .price(new MoneyVO(createOrderCommand.getPrice()))
                .orderItems(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(List<com.food.ordering.system.order.service.domain.dto.create.OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> OrderItem.Builder.builder()
                        .product(new Product(new ProductIdVO(orderItem.getProductId())))
                        .price(new MoneyVO(orderItem.getPrice()))
                        .quantity(orderItem.getQuantity())
                        .subTotal(new MoneyVO(orderItem.getSubTotal()))
                        .build()).collect(Collectors.toList());
    }

    private StreetAddressVO orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddressVO(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }
}
