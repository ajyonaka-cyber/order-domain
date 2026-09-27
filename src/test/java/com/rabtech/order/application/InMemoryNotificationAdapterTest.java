package com.rabtech.order.application;

import com.rabtech.order.domain.event.OrderConfirmed;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryNotificationAdapterTest {

    @Test
    void shouldStorePublishedEvent() {
        InMemoryNotificationAdapter adapter =
                new InMemoryNotificationAdapter();

        OrderConfirmed event =
                new OrderConfirmed(
                        "order-1",
                        Instant.parse("2026-09-26T10:00:00Z")
                );

        adapter.publish(event);

        assertEquals(
                1,
                adapter.getEvents().size()
        );

        assertEquals(
                event,
                adapter.getEvents().get(0)
        );
    }
}