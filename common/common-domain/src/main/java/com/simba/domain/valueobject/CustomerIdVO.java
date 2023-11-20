package com.simba.domain.valueobject;

import java.util.UUID;

public class CustomerIdVO extends BaseIdVO<UUID> {
    public CustomerIdVO(UUID value) {
        super(value);
    }
}
