package com.rabtech.order.domain.event;

import java.time.Instant;

public record OrderCancelled(
        String orderId,
        Instant occurredAt
) implements DomainEvent {
}
