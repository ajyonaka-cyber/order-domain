package com.rabtech.order.domain.event;

import java.time.Instant;

public record OrderConfirmed(
        String orderId,
        Instant occurredAt
) implements DomainEvent {
}

