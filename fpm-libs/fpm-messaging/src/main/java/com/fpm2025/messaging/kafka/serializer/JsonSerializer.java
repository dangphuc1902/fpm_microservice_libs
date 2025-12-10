package com.fpm2025.messaging.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Custom JSON serializer for Kafka messages
 */
@Slf4j
public class JsonSerializer<T> implements Serializer<T> {

    private final ObjectMapper objectMapper;

    public JsonSerializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration needed
    }

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null) {
            log.warn("Null data received for serialization on topic: {}", topic);
            return null;
        }

        try {
            byte[] result = objectMapper.writeValueAsBytes(data);
            log.debug("Serialized object of type {} for topic {}: {} bytes", 
                    data.getClass().getSimpleName(), topic, result.length);
            return result;
        } catch (Exception e) {
            log.error("Error serializing object for topic {}: {}", topic, e.getMessage(), e);
            throw new SerializationException("Error serializing JSON message", e);
        }
    }

    @Override
    public void close() {
        // Nothing to close
    }
}