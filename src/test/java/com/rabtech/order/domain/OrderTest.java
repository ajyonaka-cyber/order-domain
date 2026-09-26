
package com.rabtech.order.domain;
import com.rabtech.order.domain.event.OrderCancelled;
import com.rabtech.order.domain.event.OrderConfirmed;
import com.rabtech.order.domain.event.PaymentRecorded;
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
                new OrderLine(
                        "P001",
                        2,
                        new BigDecimal("100.00")
                )
        );

        order.confirm();

        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    @Test
    void shouldPayConfirmedOrder() {
        Order order = new Order();

        order.addLine(
                new OrderLine(
                        "P001",
                        1,
                        new BigDecimal("100.00")
                )
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
                new OrderLine(
                        "P001",
                        2,
                        new BigDecimal("100.00")
                )
        );

        order.addLine(
                new OrderLine(
                        "P002",
                        3,
                        new BigDecimal("50.00")
                )
        );

        assertEquals(
                new BigDecimal("350.00"),
                order.getTotal()
        );
    }@Test
    void shouldPublishOrderConfirmedEvent() {
        Order order = new Order();

        order.addLine(
                new OrderLine(
                        "P001",
                        1,
                        new BigDecimal("100.00")
                )
        );

        order.confirm();

        assertEquals(1, order.getEvents().size());
        assertInstanceOf(OrderConfirmed.class, order.getEvents().get(0));

        OrderConfirmed event =
                (OrderConfirmed) order.getEvents().get(0);

        assertEquals(order.getId().toString(), event.orderId());
        assertNotNull(event.occurredAt());
    }@Test
    void shouldPublishOrderCancelledEvent() {
        Order order = new Order();

        order.addLine(
                new OrderLine(
                        "P001",
                        1,
                        new BigDecimal("100.00")
                )
        );

        order.cancel();

        assertEquals(1, order.getEvents().size());
        assertInstanceOf(OrderCancelled.class, order.getEvents().get(0));

        OrderCancelled event =
                (OrderCancelled) order.getEvents().get(0);

        assertEquals(order.getId().toString(), event.orderId());
        assertNotNull(event.occurredAt());
    }
    @Test
    void shouldPublishPaymentRecordedEvent() {
        Order order = new Order();

        order.addLine(
                new OrderLine(
                        "P001",
                        1,
                        new BigDecimal("100.00")
                )
        );

        order.confirm();
        order.pay();

        assertEquals(2, order.getEvents().size());
        assertInstanceOf(PaymentRecorded.class, order.getEvents().get(1));

        PaymentRecorded event =
                (PaymentRecorded) order.getEvents().get(1);

        assertEquals(order.getId().toString(), event.orderId());
        assertNotNull(event.occurredAt());
    }
    @Test
    void shouldPublishEventsInOrder() {
        Order order = new Order();

        order.addLine(
                new OrderLine(
                        "P001",
                        1,
                        new BigDecimal("100.00")
                )
        );

        order.confirm();
        order.pay();

        assertInstanceOf(
                OrderConfirmed.class,
                order.getEvents().get(0)
        );

        assertInstanceOf(
                PaymentRecorded.class,
                order.getEvents().get(1)
        );
    }
    }

