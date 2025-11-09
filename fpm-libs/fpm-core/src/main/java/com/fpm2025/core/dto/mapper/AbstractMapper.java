package com.fpm2025.core.dto.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMapper<Entity, DTO> implements BaseMapper<Entity, DTO> {

    @Override
    public List<DTO> toDTOList(List<Entity> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Entity> toEntityList(List<DTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return Collections.emptyList();
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
