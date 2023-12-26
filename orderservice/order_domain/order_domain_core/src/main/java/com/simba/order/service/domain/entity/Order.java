package com.simba.order.service.domain.entity;

import com.simba.domain.entity.AggregateRoot;
import com.simba.domain.valueobject.*;
import com.simba.order.service.domain.exception.OrderDomainException;
import com.simba.order.service.domain.valueobject.OrderItemIdVO;
import com.simba.order.service.domain.valueobject.StreetAddressVO;
import com.simba.order.service.domain.valueobject.TrackingIdVO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Order extends AggregateRoot<OrderIdVO> {
    private final CustomerIdVO customerId;
    private final RestaurantIdVO restaurantId;
    private final StreetAddressVO deliveryAddress;
    private final MoneyVO price;
    private final List<OrderItem> orderItems;

    private TrackingIdVO trackingId;
    private OrderStatusVO orderStatus;
    private List<String> failureMessages;

    public void initializeOrder() {
        setId(new OrderIdVO(UUID.randomUUID()));
        trackingId = new TrackingIdVO(UUID.randomUUID());
        orderStatus = OrderStatusVO.PENDING;
        initializeOrderItems();
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateItemsPrice() {
        MoneyVO orderItemsTotal = orderItems.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(MoneyVO.ZERO, MoneyVO::add);

        if (!price.equals(orderItemsTotal)) {
            throw new OrderDomainException("Total price: " + price.getAmount() + " is not equal to Order items total: " + orderItemsTotal.getAmount() + "!");
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid()) {
            throw new OrderDomainException("Order item price: is not valid for product");
        }
    }

    private void validateTotalPrice() {
        if (price == null || !price.isGreaterThanZero()) {
            throw new OrderDomainException("Total price must be greater than zero.");
        }
    }

    public void pay() {
        if (orderStatus != OrderStatusVO.PENDING) {
            throw new OrderDomainException("Order is not in correct state for payment operation.");
        }
        orderStatus = OrderStatusVO.PAID;
    }

    public void approve() {
        if (orderStatus != OrderStatusVO.PAID) {
            throw new OrderDomainException("Order is not in correct state for approval operation.");
        }
        orderStatus = OrderStatusVO.APPROVED;
    }

    public void initCancel(List<String> failureMessages) {
        if (orderStatus != OrderStatusVO.PAID) {
            throw new OrderDomainException("Order is not in correct state for initializing cancellation operation.");
        }
        orderStatus = OrderStatusVO.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null) {
            this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());
        }
        if (this.failureMessages == null) {
            this.failureMessages = failureMessages;
        }
    }

    public void cancel(List<String> failureMessages) {
        if (!(orderStatus == OrderStatusVO.CANCELLED || orderStatus == OrderStatusVO.PENDING)) {
            throw new OrderDomainException("Order is not in correct state for cancel operation.");
        }
        orderStatus = OrderStatusVO.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void validateInitialOrder() {
        if (orderStatus != null || getId() != null) {
            throw new OrderDomainException("Order is not in the correct state for initialization!");
        }
    }

    private void initializeOrderItems() {
        long itemId = 1;
        for (OrderItem orderItem : orderItems) {
            orderItem.initializeOrderItem(super.getId(), new OrderItemIdVO(itemId++));
        }
    }

    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        orderItems = builder.orderItems;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }

    public CustomerIdVO getCustomerId() {
        return customerId;
    }

    public RestaurantIdVO getRestaurantId() {
        return restaurantId;
    }

    public StreetAddressVO getDeliveryAddress() {
        return deliveryAddress;
    }

    public MoneyVO getPrice() {
        return price;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public TrackingIdVO getTrackingId() {
        return trackingId;
    }

    public OrderStatusVO getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static final class Builder {
        private OrderIdVO orderId;
        private CustomerIdVO customerId;
        private RestaurantIdVO restaurantId;
        private StreetAddressVO deliveryAddress;
        private MoneyVO price;
        private List<OrderItem> orderItems;
        private TrackingIdVO trackingId;
        private OrderStatusVO orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder orderId(OrderIdVO val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerIdVO val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantIdVO val) {
            restaurantId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddressVO val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(MoneyVO val) {
            price = val;
            return this;
        }

        public Builder orderItems(List<OrderItem> val) {
            orderItems = val;
            return this;
        }

        public Builder trackingId(TrackingIdVO val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatusVO val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
