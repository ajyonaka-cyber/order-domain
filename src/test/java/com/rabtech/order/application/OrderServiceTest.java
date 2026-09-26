package com.rabtech.order.application;

import com.rabtech.order.domain.Order;
import com.rabtech.order.domain.repository.InMemoryOrderRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void shouldCreateAndSaveOrder() {
        InMemoryOrderRepository repository =
                new InMemoryOrderRepository();

        OrderService service =
                new OrderService(repository);

        Order order = service.createOrder();

        assertNotNull(order);
        assertEquals(order, service.getOrder(order.getId()));
    }

    @Test
    void shouldThrowExceptionWhenOrderDoesNotExist() {
        InMemoryOrderRepository repository =
                new InMemoryOrderRepository();

        OrderService service =
                new OrderService(repository);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getOrder(java.util.UUID.randomUUID())
        );
    }
}
