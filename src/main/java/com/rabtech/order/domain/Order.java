package com.rabtech.order.domain;

import com.rabtech.order.domain.event.DomainEvent;
import com.rabtech.order.domain.event.OrderCancelled;
import com.rabtech.order.domain.event.OrderConfirmed;
import com.rabtech.order.domain.event.PaymentRecorded;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID id;
    private OrderStatus status;
    private final List<OrderLine> lines = new ArrayList<>();
    private final List<DomainEvent> events = new ArrayList<>();

    public Order() {
        this.id = UUID.randomUUID();
        this.status = OrderStatus.DRAFT;
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void addLine(OrderLine line) {
        lines.add(line);
    }

    public BigDecimal getTotal() {
        return lines.stream()
                .map(OrderLine::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<DomainEvent> getEvents() {
        return List.copyOf(events);
    }

    public void confirm() {
        if (lines.isEmpty()) {
            throw new IllegalStateException("Cannot confirm an empty order");
        }

        if (status != OrderStatus.DRAFT) {
            throw new IllegalStateException("Only draft orders can be confirmed");
        }

        status = OrderStatus.CONFIRMED;

        events.add(
                new OrderConfirmed(
                        id.toString(),
                        Instant.now()
                )
        );
    }

    public void pay() {
        if (status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Only confirmed orders can be paid");
        }

        status = OrderStatus.PAID;

        events.add(
                new PaymentRecorded(
                        id.toString(),
                        Instant.now()
                )
        );
    }

    public void cancel() {
        status = OrderStatus.CANCELLED;

        events.add(
                new OrderCancelled(
                        id.toString(),
                        Instant.now()
                )
        );
    }
}










