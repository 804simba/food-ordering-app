package com.simba.order.service.domain.entity;

import com.simba.domain.entity.BaseEntity;
import com.simba.domain.valueobject.MoneyVO;
import com.simba.domain.valueobject.OrderIdVO;
import com.simba.order.service.domain.valueobject.OrderItemIdVO;


public class OrderItem extends BaseEntity<OrderItemIdVO> {
    private OrderIdVO orderId;
    private final Product product;
    private final int quantity;
    private final MoneyVO price;
    private final MoneyVO subTotal;

    void initializeOrderItem(OrderIdVO orderId, OrderItemIdVO orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);
    }

    boolean isPriceValid() {
        return price.isGreaterThanZero() && price.equals(product.getPrice()) &&
                price.multiply(quantity).equals(subTotal);
    }

    private OrderItem(Builder builder) {
        super.setId(builder.orderItemId);
        quantity = builder.quantity;
        price = builder.price;
        subTotal = builder.subTotal;
        product = builder.product;
    }


    public OrderIdVO getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public MoneyVO getPrice() {
        return price;
    }

    public MoneyVO getSubTotal() {
        return subTotal;
    }

    public static final class Builder {
        private OrderItemIdVO orderItemId;
        private Product product;
        private int quantity;
        private MoneyVO price;
        private MoneyVO subTotal;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder orderItemId(OrderItemIdVO val) {
            orderItemId = val;
            return this;
        }

        public Builder product(Product val) {
            product = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder price(MoneyVO val) {
            price = val;
            return this;
        }

        public Builder subTotal(MoneyVO val) {
            subTotal = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
