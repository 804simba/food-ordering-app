package com.simba.domain.valueobject;

import java.util.UUID;

public class OrderIdVO extends BaseId<UUID>{

    public OrderIdVO(UUID value) {
        super(value);
    }
}
