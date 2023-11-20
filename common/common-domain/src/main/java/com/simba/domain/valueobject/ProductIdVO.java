package com.simba.domain.valueobject;

import java.util.UUID;

public class ProductIdVO extends BaseIdVO<UUID> {
    public ProductIdVO(UUID value) {
        super(value);
    }
}
