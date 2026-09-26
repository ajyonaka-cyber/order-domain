package com.rabtech.order.domain.event;

import java.time.Instant;

public interface DomainEvent {

    String orderId();

    Instant occurredAt();
}
