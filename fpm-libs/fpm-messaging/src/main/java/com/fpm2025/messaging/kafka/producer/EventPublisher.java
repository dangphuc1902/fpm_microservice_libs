package com.fpm2025.messaging.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public <T> CompletableFuture<SendResult<String, Object>> publish(String topic, String key, T event) {
        log.info("Publishing event to topic: {}, key: {}", topic, key);
        
        return kafkaTemplate.send(topic, key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish event to topic: {}", topic, ex);
                    } else {
                        log.info("Event published successfully. Topic: {}, Partition: {}, Offset: {}",
                                topic,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }

    public <T> void publishAndForget(String topic, String key, T event) {
        publish(topic, key, event);
    }
}