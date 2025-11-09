package com.fpm2025.core.dto.mapper;

import java.util.List;

public interface BaseMapper<Entity, DTO> {

    DTO toDTO(Entity entity);

    Entity toEntity(DTO dto);

    List<DTO> toDTOList(List<Entity> entities);

    List<Entity> toEntityList(List<DTO> dtos);
}
