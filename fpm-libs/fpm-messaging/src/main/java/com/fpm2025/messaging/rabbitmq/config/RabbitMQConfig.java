package com.fpm2025.messaging.rabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpm2025.messaging.rabbitmq.producer.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@ConditionalOnProperty(name = "spring.rabbitmq.enabled", havingValue = "true", matchIfMissing = true)
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    // Define queues
    @Bean
    public Queue voiceTranscriptionQueue() {
        return new Queue(RabbitMQQueues.VOICE_TRANSCRIPTION, true);
    }

    @Bean
    public Queue ocrProcessQueue() {
        return new Queue(RabbitMQQueues.OCR_PROCESS, true);
    }

    @Bean
    public Queue aiCategorizationQueue() {
        return new Queue(RabbitMQQueues.AI_CATEGORIZATION, true);
    }

    @Bean
    public Queue reportGenerateQueue() {
        return new Queue(RabbitMQQueues.REPORT_GENERATE, true);
    }

    @Bean
    public Queue fcmSendQueue() {
        return new Queue(RabbitMQQueues.FCM_SEND, true);
    }
}