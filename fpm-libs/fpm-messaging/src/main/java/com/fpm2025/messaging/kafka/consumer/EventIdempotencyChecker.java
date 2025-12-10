package com.fpm2025.messaging.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Check for duplicate events using Redis
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventIdempotencyChecker {

    private static final String IDEMPOTENCY_KEY_PREFIX = "event:processed:";
    private static final Duration DEFAULT_TTL = Duration.ofHours(24);

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Check if event has been processed
     * @param eventId Event identifier
     * @return true if already processed, false otherwise
     */
    public boolean isProcessed(String eventId) {
        String key = IDEMPOTENCY_KEY_PREFIX + eventId;
        Boolean exists = redisTemplate.hasKey(key);
        
        if (Boolean.TRUE.equals(exists)) {
            log.warn("Duplicate event detected: {}", eventId);
            return true;
        }
        
        return false;
    }

    /**
     * Mark event as processed
     * @param eventId Event identifier
     */
    public void markAsProcessed(String eventId) {
        String key = IDEMPOTENCY_KEY_PREFIX + eventId;
        redisTemplate.opsForValue().set(key, "processed", DEFAULT_TTL);
        log.debug("Marked event as processed: {}", eventId);
    }

    /**
     * Mark event as processed with custom TTL
     * @param eventId Event identifier
     * @param ttl Time to live
     */
    public void markAsProcessed(String eventId, Duration ttl) {
        String key = IDEMPOTENCY_KEY_PREFIX + eventId;
        redisTemplate.opsForValue().set(key, "processed", ttl);
        log.debug("Marked event as processed with TTL {}: {}", ttl, eventId);
    }

    /**
     * Check and mark atomically (if not processed, mark as processed)
     * @param eventId Event identifier
     * @return true if event was not processed before, false if duplicate
     */
    public boolean checkAndMark(String eventId) {
        String key = IDEMPOTENCY_KEY_PREFIX + eventId;
        Boolean wasAbsent = redisTemplate.opsForValue().setIfAbsent(key, "processed", DEFAULT_TTL);
        
        if (Boolean.TRUE.equals(wasAbsent)) {
            log.debug("Event is new and marked as processed: {}", eventId);
            return true;
        } else {
            log.warn("Duplicate event detected and skipped: {}", eventId);
            return false;
        }
    }
}