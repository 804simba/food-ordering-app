package com.simba.domain.valueobject;

import java.util.UUID;

public class RestaurantIdVO extends BaseIdVO<UUID> {
    public RestaurantIdVO(UUID value) {
        super(value);
    }
}
