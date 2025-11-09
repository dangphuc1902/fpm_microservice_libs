package com.fpm2025.core.dto.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class MapperRegistry {

    private final Map<String, BaseMapper<?, ?>> mappers = new ConcurrentHashMap<>();

    public void register(String key, BaseMapper<?, ?> mapper) {
        log.debug("Registering mapper: {}", key);
        mappers.put(key, mapper);
    }

    @SuppressWarnings("unchecked")
    public <E, D> BaseMapper<E, D> getMapper(String key) {
        BaseMapper<?, ?> mapper = mappers.get(key);
        if (mapper == null) {
            throw new IllegalArgumentException("Mapper not found for key: " + key);
        }
        return (BaseMapper<E, D>) mapper;
    }

    @SuppressWarnings("unchecked")
    public <E, D> BaseMapper<E, D> getMapper(Class<E> entityClass, Class<D> dtoClass) {
        String key = entityClass.getSimpleName() + "-" + dtoClass.getSimpleName();
        return getMapper(key);
    }

    public boolean hasMapper(String key) {
        return mappers.containsKey(key);
    }

    public void clear() {
        mappers.clear();
    }

    public int size() {
        return mappers.size();
    }
}
