package com.rabtech.order.application;

import com.rabtech.order.domain.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public class InMemoryNotificationAdapter implements NotificationPort {

    private final List<DomainEvent> events = new ArrayList<>();

    @Override
    public void publish(DomainEvent event) {
        events.add(event);
    }

    public List<DomainEvent> getEvents() {
        return List.copyOf(events);
    }
}