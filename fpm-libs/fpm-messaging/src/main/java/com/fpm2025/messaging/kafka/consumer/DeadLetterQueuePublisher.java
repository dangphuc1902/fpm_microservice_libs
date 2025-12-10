package com.fpm2025.messaging.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Publisher for sending failed messages to Dead Letter Queue
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeadLetterQueuePublisher {

    private static final String DLQ_SUFFIX = ".dlq";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Send failed message to DLQ
     * @param originalTopic Original topic
     * @param key Message key
     * @param message Failed message
     * @param error Error details
     */
    public void sendToDeadLetterQueue(String originalTopic, String key, Object message, Exception error) {
        String dlqTopic = originalTopic + DLQ_SUFFIX;
        
        Map<String, Object> dlqMessage = new HashMap<>();
        dlqMessage.put("originalTopic", originalTopic);
        dlqMessage.put("originalMessage", message);
        dlqMessage.put("errorMessage", error.getMessage());
        dlqMessage.put("errorClass", error.getClass().getName());
        dlqMessage.put("failedAt", LocalDateTime.now());
        
        try {
            kafkaTemplate.send(dlqTopic, key, dlqMessage);
            log.info("Sent message to DLQ: topic={}, key={}", dlqTopic, key);
        } catch (Exception e) {
            log.error("Failed to send message to DLQ: topic={}, error={}", dlqTopic, e.getMessage(), e);
        }
    }

    /**
     * Send with additional metadata
     * @param originalTopic Original topic
     * @param key Message key
     * @param message Failed message
     * @param error Error details
     * @param metadata Additional metadata
     */
    public void sendToDeadLetterQueue(
            String originalTopic, 
            String key, 
            Object message, 
            Exception error,
            Map<String, Object> metadata) {
        
        String dlqTopic = originalTopic + DLQ_SUFFIX;
        
        Map<String, Object> dlqMessage = new HashMap<>();
        dlqMessage.put("originalTopic", originalTopic);
        dlqMessage.put("originalMessage", message);
        dlqMessage.put("errorMessage", error.getMessage());
        dlqMessage.put("errorClass", error.getClass().getName());
        dlqMessage.put("failedAt", LocalDateTime.now());
        dlqMessage.put("metadata", metadata);
        
        try {
            kafkaTemplate.send(dlqTopic, key, dlqMessage);
            log.info("Sent message with metadata to DLQ: topic={}, key={}", dlqTopic, key);
        } catch (Exception e) {
            log.error("Failed to send message to DLQ: topic={}, error={}", dlqTopic, e.getMessage(), e);
        }
    }
}