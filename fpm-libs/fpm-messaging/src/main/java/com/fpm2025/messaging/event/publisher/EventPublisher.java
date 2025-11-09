package com.fpm2025.messaging.event.publisher;

import com.fpm2025.messaging.event.model.DomainEvent;

import java.util.List;

public interface EventPublisher {

    void publish(DomainEvent event, String exchange, String routingKey);

    void publishBatch(List<DomainEvent> events, String exchange, String routingKey);

    void publishAsync(DomainEvent event, String exchange, String routingKey);
}
