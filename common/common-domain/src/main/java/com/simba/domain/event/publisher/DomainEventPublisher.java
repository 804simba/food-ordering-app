package com.simba.domain.event.publisher;

import com.simba.domain.event.DomainEvent;

public interface DomainEventPublisher <T extends DomainEvent>{
    void publish(T domainEvent);
}
