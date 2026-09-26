package com.rabtech.order.domain.repository;

import com.rabtech.order.domain.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    void save(Order order);

    Optional<Order> findById(UUID id);
}

