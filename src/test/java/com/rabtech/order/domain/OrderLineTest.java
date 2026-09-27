package com.rabtech.order.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderLineTest {

    @Test
    void shouldRejectZeroQuantity() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new OrderLine(
                        "P001",
                        0,
                        new BigDecimal("100.00")
                )
        );
    }

    @Test
    void shouldRejectNegativeQuantity() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new OrderLine(
                        "P001",
                        -1,
                        new BigDecimal("100.00")
                )
        );
    }
}