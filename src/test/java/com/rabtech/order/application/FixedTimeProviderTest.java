package com.rabtech.order.application;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FixedTimeProviderTest {

    @Test
    void shouldReturnFixedTime() {
        Instant fixedTime =
                Instant.parse("2026-09-26T10:00:00Z");

        FixedTimeProvider provider =
                new FixedTimeProvider(fixedTime);

        assertEquals(
                fixedTime,
                provider.now()
        );
    }
}