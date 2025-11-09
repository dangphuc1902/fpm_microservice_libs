package com.fpm2025.messaging.event.publisher;

import com.fpm2025.messaging.event.model.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitEventPublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(DomainEvent event, String exchange, String routingKey) {
        try {
            log.info("Publishing event: {} to exchange: {} with routing key: {}",
                    event.getEventType(), exchange, routingKey);

            rabbitTemplate.convertAndSend(exchange, routingKey, event);

            log.debug("Event published successfully: {}", event.getEventId());
        } catch (Exception e) {
            log.error("Failed to publish event: {}", event.getEventId(), e);
            throw new RuntimeException("Failed to publish event", e);
        }
    }

    @Override
    public void publishBatch(List<DomainEvent> events, String exchange, String routingKey) {
        log.info("Publishing batch of {} events to exchange: {}", events.size(), exchange);

        events.forEach(event -> {
            try {
                publish(event, exchange, routingKey);
            } catch (Exception e) {
                log.error("Failed to publish event in batch: {}", event.getEventId(), e);
            }
        });
    }

    @Override
    @Async
    public void publishAsync(DomainEvent event, String exchange, String routingKey) {
        publish(event, exchange, routingKey);
    }
}
