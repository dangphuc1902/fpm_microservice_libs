package com.fpm2025.messaging.event.listener;

import com.fpm2025.messaging.event.model.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class EventListenerRegistry {

    private final Map<String, List<EventListener<? extends DomainEvent>>> listeners = new ConcurrentHashMap<>();

    public <T extends DomainEvent> void register(String eventType, EventListener<T> listener) {
        log.info("Registering event listener for event type: {}", eventType);

        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(listener);

        listeners.get(eventType).sort(Comparator.comparingInt(EventListener::getOrder));
    }

    @SuppressWarnings("unchecked")
    public <T extends DomainEvent> void handle(T event) {
        String eventType = event.getEventType();
        log.debug("Handling event: {} with type: {}", event.getEventId(), eventType);

        List<EventListener<? extends DomainEvent>> eventListeners = listeners.get(eventType);

        if (eventListeners == null || eventListeners.isEmpty()) {
            log.warn("No listeners registered for event type: {}", eventType);
            return;
        }

        eventListeners.forEach(listener -> {
            try {
                log.debug("Executing listener: {} for event: {}", listener.getClass().getSimpleName(), eventType);
                ((EventListener<T>) listener).handle(event);
            } catch (Exception e) {
                log.error("Error executing listener for event: {}", eventType, e);
            }
        });
    }

    public boolean hasListeners(String eventType) {
        List<EventListener<? extends DomainEvent>> eventListeners = listeners.get(eventType);
        return eventListeners != null && !eventListeners.isEmpty();
    }

    public int getListenerCount(String eventType) {
        List<EventListener<? extends DomainEvent>> eventListeners = listeners.get(eventType);
        return eventListeners != null ? eventListeners.size() : 0;
    }

    public void clear() {
        listeners.clear();
    }
}
