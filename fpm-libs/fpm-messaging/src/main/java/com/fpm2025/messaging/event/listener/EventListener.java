package com.fpm2025.messaging.event.listener;

import com.fpm2025.messaging.event.model.DomainEvent;

public interface EventListener<T extends DomainEvent> {

    void handle(T event);

    Class<T> getEventType();

    default int getOrder() {
        return 0;
    }
}
