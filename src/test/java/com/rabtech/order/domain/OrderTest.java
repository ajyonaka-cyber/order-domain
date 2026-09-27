package com.rabtech.order.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void newOrderShouldBeDraft() {
        Order order = new Order();
        assertEquals(OrderStatus.DRAFT, order.getStatus());
    }

    @Test
    void shouldNotConfirmEmptyOrder() {
        Order order = new Order();

        assertThrows(
                IllegalStateException.class,
                order::confirm
        );
    }

    @Test
    void shouldConfirmOrderWithLine() {
        Order order = new Order();

        order.addLine(
                new OrderLine("P001", 1, new BigDecimal("100.00"))
        );

        order.confirm();

        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    @Test
    void shouldPayConfirmedOrder() {
        Order order = new Order();

        order.addLine(
                new OrderLine("P001", 1, new BigDecimal("100.00"))
        );

        order.confirm();
        order.pay();

        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    void shouldNotPayDraftOrder() {
        Order order = new Order();

        assertThrows(
                IllegalStateException.class,
                order::pay
        );
    }

    @Test
    void shouldCalculateOrderTotal() {
        Order order = new Order();

        order.addLine(
                new OrderLine("P001", 2, new BigDecimal("100.00"))
        );

        order.addLine(
                new OrderLine("P002", 1, new BigDecimal("50.00"))
        );

        assertEquals(
                new BigDecimal("250.00"),
                order.getTotal()
        );
    }

    @Test
    void shouldPublishOrderConfirmedEvent() {
        Order order = new Order();

        order.addLine(
                new OrderLine("P001", 1, new BigDecimal("100.00"))
        );

        order.confirm();

        assertEquals(1, order.getEvents().size());
        assertTrue(
                order.getEvents().get(0)
                        instanceof com.rabtech.order.domain.event.OrderConfirmed
        );
    }

    @Test
    void shouldPublishOrderCancelledEvent() {
        Order order = new Order();

        order.cancel();

        assertEquals(1, order.getEvents().size());
        assertTrue(
                order.getEvents().get(0)
                        instanceof com.rabtech.order.domain.event.OrderCancelled
        );
    }

    @Test
    void shouldPublishPaymentRecordedEvent() {
        Order order = new Order();

        order.addLine(
                new OrderLine("P001", 1, new BigDecimal("100.00"))
        );

        order.confirm();
        order.pay();

        assertEquals(2, order.getEvents().size());
        assertTrue(
                order.getEvents().get(1)
                        instanceof com.rabtech.order.domain.event.PaymentRecorded
        );
    }

    @Test
    void shouldPublishEventsInOrder() {
        Order order = new Order();

        order.addLine(
                new OrderLine("P001", 1, new BigDecimal("100.00"))
        );

        order.confirm();
        order.pay();
        order.cancel();

        assertEquals(3, order.getEvents().size());

        assertTrue(
                order.getEvents().get(0)
                        instanceof com.rabtech.order.domain.event.OrderConfirmed
        );

        assertTrue(
                order.getEvents().get(1)
                        instanceof com.rabtech.order.domain.event.PaymentRecorded
        );

        assertTrue(
                order.getEvents().get(2)
                        instanceof com.rabtech.order.domain.event.OrderCancelled
        );
    }

    @Test
    void shouldNotPayCancelledOrder() {
        Order order = new Order();

        order.addLine(
                new OrderLine("P001", 1, new BigDecimal("100.00"))
        );

        order.cancel();

        assertThrows(
                IllegalStateException.class,
                order::pay
        );
    }

    @Test
    void shouldNotConfirmPaidOrder() {
        Order order = new Order();

        order.addLine(
                new OrderLine("P001", 1, new BigDecimal("100.00"))
        );

        order.confirm();
        order.pay();

        assertThrows(
                IllegalStateException.class,
                order::confirm
        );
    }
}