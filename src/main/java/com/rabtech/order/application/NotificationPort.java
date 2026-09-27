package com.rabtech.order.application;

import com.rabtech.order.domain.event.DomainEvent;

public interface NotificationPort {

    void publish(DomainEvent event);
}