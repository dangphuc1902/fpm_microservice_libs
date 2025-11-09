package com.fpm2025.messaging.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMetadata {

    private String eventId;
    private String eventType;
    private String serviceName;
    private Instant timestamp;
    private Long userId;
    private String correlationId;
    private Map<String, String> headers;

    public static EventMetadata from(DomainEvent event) {
        return EventMetadata.builder()
                .eventId(event.getEventId())
                .eventType(event.getEventType())
                .serviceName(event.getServiceName())
                .timestamp(event.getTimestamp())
                .userId(event.getUserId())
                .build();
    }
}
