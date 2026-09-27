package com.rabtech.order.application;

import java.time.Instant;

public class FixedTimeProvider implements TimeProvider {

    private final Instant time;

    public FixedTimeProvider(Instant time) {
        this.time = time;
    }

    @Override
    public Instant now() {
        return time;
    }
}