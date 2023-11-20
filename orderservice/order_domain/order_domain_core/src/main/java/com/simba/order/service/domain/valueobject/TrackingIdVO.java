package com.simba.order.service.domain.valueobject;

import com.simba.domain.valueobject.BaseIdVO;

import java.util.UUID;

public class TrackingIdVO extends BaseIdVO<UUID> {
    public TrackingIdVO(UUID value) {
        super(value);
    }
}
