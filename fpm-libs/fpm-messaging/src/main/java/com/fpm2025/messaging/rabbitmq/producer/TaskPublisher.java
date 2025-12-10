package com.fpm2025.messaging.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskPublisher {

    private final RabbitTemplate rabbitTemplate;

    public <T> void sendTask(String queue, T task) {
        log.info("Sending task to queue: {}", queue);
        try {
            rabbitTemplate.convertAndSend(queue, task);
            log.info("Task sent successfully to queue: {}", queue);
        } catch (Exception e) {
            log.error("Failed to send task to queue: {}", queue, e);
            throw e;
        }
    }

    public <T> void sendTaskWithDelay(String queue, T task, int delayMillis) {
        log.info("Sending delayed task to queue: {}, delay: {}ms", queue, delayMillis);
        try {
            rabbitTemplate.convertAndSend(queue, task, message -> {
                message.getMessageProperties().setDelay(delayMillis);
                return message;
            });
            log.info("Delayed task sent successfully to queue: {}", queue);
        } catch (Exception e) {
            log.error("Failed to send delayed task to queue: {}", queue, e);
            throw e;
        }
    }
}