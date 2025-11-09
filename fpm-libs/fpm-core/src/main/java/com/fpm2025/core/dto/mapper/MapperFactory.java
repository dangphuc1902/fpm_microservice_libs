package com.fpm2025.core.dto.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MapperFactory {

    private final MapperRegistry mapperRegistry;

    public <E, D> BaseMapper<E, D> getMapper(String key) {
        return mapperRegistry.getMapper(key);
    }

    public <E, D> BaseMapper<E, D> getMapper(Class<E> entityClass, Class<D> dtoClass) {
        return mapperRegistry.getMapper(entityClass, dtoClass);
    }

    public void registerMapper(String key, BaseMapper<?, ?> mapper) {
        mapperRegistry.register(key, mapper);
    }

    public <E, D> void registerMapper(Class<E> entityClass, Class<D> dtoClass, BaseMapper<E, D> mapper) {
        String key = entityClass.getSimpleName() + "-" + dtoClass.getSimpleName();
        mapperRegistry.register(key, mapper);
    }
}
