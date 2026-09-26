package com.rabtech.order.domain.event;

import java.time.Instant;

public record PaymentRecorded(
        String orderId,
        Instant occurredAt
) implements DomainEvent {
}
