package com.rabtech.order.application;

import com.rabtech.order.domain.Order;
import com.rabtech.order.domain.repository.OrderRepository;

import java.util.UUID;

public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order createOrder() {
        Order order = new Order();
        repository.save(order);
        return order;
    }

    public Order getOrder(UUID id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Order not found"));
    }
}
