package com.fpm2025.messaging.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public abstract class DomainEvent {

    private String eventId;
    private String eventType;
    private Long aggregateId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant timestamp;

    private Long userId;
    private String serviceName;

    protected DomainEvent(Long aggregateId, Long userId, String serviceName) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = this.getClass().getSimpleName();
        this.aggregateId = aggregateId;
        this.userId = userId;
        this.serviceName = serviceName;
        this.timestamp = Instant.now();
    }

    public abstract String getEventName();
}
