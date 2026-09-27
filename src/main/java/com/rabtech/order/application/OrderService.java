package com.rabtech.order.application;

import com.rabtech.order.domain.Order;
import com.rabtech.order.domain.event.DomainEvent;
import com.rabtech.order.domain.repository.OrderRepository;

import java.time.Instant;
import java.util.UUID;

public class OrderService {

    private final OrderRepository repository;
    private final NotificationPort notificationPort;
    private final TimeProvider timeProvider;

    public OrderService(
            OrderRepository repository,
            NotificationPort notificationPort,
            TimeProvider timeProvider
    ) {
        this.repository = repository;
        this.notificationPort = notificationPort;
        this.timeProvider = timeProvider;
    }

    public Order createOrder() {
        Order order = new Order(timeProvider::now);
        repository.save(order);
        return order;
    }

    public Order getOrder(UUID id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Order not found"));
    }

    public void publishEvents(Order order) {
        for (DomainEvent event : order.getEvents()) {
            notificationPort.publish(event);
        }
    }
}