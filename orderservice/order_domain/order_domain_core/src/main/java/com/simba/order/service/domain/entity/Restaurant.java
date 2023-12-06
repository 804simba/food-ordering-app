package com.simba.order.service.domain.entity;

import com.simba.domain.entity.AggregateRoot;
import com.simba.domain.valueobject.RestaurantIdVO;

import java.util.List;

public class Restaurant extends AggregateRoot<RestaurantIdVO> {
    private final List<Product> products;
    private final boolean active;

    private Restaurant(Builder builder) {
        super.setId(builder.restaurantId);
        products = builder.products;
        active = builder.active;
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private RestaurantIdVO restaurantId;
        private List<Product> products;
        private boolean active;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(RestaurantIdVO val) {
            restaurantId = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}
