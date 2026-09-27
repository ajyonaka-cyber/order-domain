package com.rabtech.order.application;

import com.rabtech.order.domain.Order;
import com.rabtech.order.domain.OrderLine;
import com.rabtech.order.domain.event.OrderConfirmed;
import com.rabtech.order.domain.repository.InMemoryOrderRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void shouldCreateAndSaveOrder() {
        InMemoryOrderRepository repository =
                new InMemoryOrderRepository();

        InMemoryNotificationAdapter notificationAdapter =
                new InMemoryNotificationAdapter();

        FixedTimeProvider timeProvider =
                new FixedTimeProvider(
                        Instant.parse("2026-09-26T10:00:00Z")
                );

        OrderService service =
                new OrderService(
                        repository,
                        notificationAdapter,
                        timeProvider
                );

        Order order = service.createOrder();

        assertNotNull(order);
        assertEquals(
                order,
                service.getOrder(order.getId())
        );
    }

    @Test
    void shouldThrowExceptionWhenOrderDoesNotExist() {
        InMemoryOrderRepository repository =
                new InMemoryOrderRepository();

        InMemoryNotificationAdapter notificationAdapter =
                new InMemoryNotificationAdapter();

        FixedTimeProvider timeProvider =
                new FixedTimeProvider(
                        Instant.parse("2026-09-26T10:00:00Z")
                );

        OrderService service =
                new OrderService(
                        repository,
                        notificationAdapter,
                        timeProvider
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getOrder(UUID.randomUUID())
        );
    }

    @Test
    void shouldPublishOrderEvents() {
        InMemoryOrderRepository repository =
                new InMemoryOrderRepository();

        InMemoryNotificationAdapter notificationAdapter =
                new InMemoryNotificationAdapter();

        FixedTimeProvider timeProvider =
                new FixedTimeProvider(
                        Instant.parse("2026-09-26T10:00:00Z")
                );

        OrderService service =
                new OrderService(
                        repository,
                        notificationAdapter,
                        timeProvider
                );

        Order order = service.createOrder();

        order.addLine(
                new OrderLine(
                        "P001",
                        1,
                        new BigDecimal("100.00")
                )
        );

        order.confirm();

        service.publishEvents(order);

        assertEquals(
                1,
                notificationAdapter.getEvents().size()
        );
    }

    @Test
    void shouldUseFixedTimeForOrderConfirmedEvent() {
        InMemoryOrderRepository repository =
                new InMemoryOrderRepository();

        InMemoryNotificationAdapter notificationAdapter =
                new InMemoryNotificationAdapter();

        Instant fixedTime =
                Instant.parse("2026-09-26T10:00:00Z");

        FixedTimeProvider timeProvider =
                new FixedTimeProvider(fixedTime);

        OrderService service =
                new OrderService(
                        repository,
                        notificationAdapter,
                        timeProvider
                );

        Order order = service.createOrder();

        order.addLine(
                new OrderLine(
                        "P001",
                        1,
                        new BigDecimal("100.00")
                )
        );

        order.confirm();

        OrderConfirmed event =
                (OrderConfirmed) order.getEvents().get(0);

        assertEquals(
                fixedTime,
                event.occurredAt()
        );
    }
}