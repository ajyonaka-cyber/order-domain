package com.rabtech.order.domain;

import com.rabtech.order.domain.repository.InMemoryOrderRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryOrderRepositoryTest {

    @Test
    void shouldSaveAndFindOrder() {
        InMemoryOrderRepository repository =
                new InMemoryOrderRepository();

        Order order = new Order();

        repository.save(order);

        Optional<Order> found =
                repository.findById(order.getId());

        assertTrue(found.isPresent());
        assertEquals(order, found.get());
    }
}
