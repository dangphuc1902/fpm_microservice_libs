package com.fpm2025.messaging.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * Custom JSON deserializer for Kafka messages
 */
@Slf4j
public class JsonDeserializer<T> implements Deserializer<T> {

    private final ObjectMapper objectMapper;
    private Class<T> targetType;
    private JavaType targetJavaType;

    public JsonDeserializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.objectMapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
    }

    public JsonDeserializer(Class<T> targetType) {
        this();
        this.targetType = targetType;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Try to get target type from config
        if (targetType == null) {
            Object typeConfig = configs.get("value.deserializer.type");
            if (typeConfig instanceof Class) {
                this.targetType = (Class<T>) typeConfig;
            } else if (typeConfig instanceof String) {
                try {
                    this.targetType = (Class<T>) Class.forName((String) typeConfig);
                } catch (ClassNotFoundException e) {
                    log.error("Cannot find class: {}", typeConfig, e);
                }
            }
        }
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) {
            log.warn("Null data received for deserialization on topic: {}", topic);
            return null;
        }

        try {
            T result;
            if (targetType != null) {
                result = objectMapper.readValue(data, targetType);
            } else if (targetJavaType != null) {
                result = objectMapper.readValue(data, targetJavaType);
            } else {
                // Fallback to Map if no type specified
                result = (T) objectMapper.readValue(data, Map.class);
            }
            
            log.debug("Deserialized {} bytes from topic {} to type {}", 
                    data.length, topic, result.getClass().getSimpleName());
            return result;
        } catch (Exception e) {
            log.error("Error deserializing message from topic {}: {}", topic, e.getMessage(), e);
            throw new SerializationException("Error deserializing JSON message", e);
        }
    }

    @Override
    public void close() {
        // Nothing to close
    }

    /**
     * Set target type for deserialization
     * @param targetType Class to deserialize to
     */
    public void setTargetType(Class<T> targetType) {
        this.targetType = targetType;
    }

    /**
     * Set target JavaType for complex types (generics, collections, etc.)
     * @param targetJavaType JavaType to deserialize to
     */
    public void setTargetJavaType(JavaType targetJavaType) {
        this.targetJavaType = targetJavaType;
    }
}