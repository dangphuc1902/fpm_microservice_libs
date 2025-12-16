package com.fpm_2025.fpm_microservice_libs.base.repository;

import com.fpm_2025.fpm_microservice_libs.base.entity.SoftDeleteEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Base repository for entities with soft delete
 */
@NoRepositoryBean
public interface SoftDeleteRepository<T extends SoftDeleteEntity> extends BaseRepository<T> {

    /**
     * Find all non-deleted entities
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = false")
    List<T> findAllActive();

    /**
     * Find by ID (only if not deleted)
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.deleted = false")
    Optional<T> findByIdActive(@Param("id") Long id);

    /**
     * Soft delete by ID
     */
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleted = true, e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = :id")
    void softDeleteById(@Param("id") Long id);

    /**
     * Restore deleted entity by ID
     */
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleted = false, e.deletedAt = null WHERE e.id = :id")
    void restoreById(@Param("id") Long id);
}