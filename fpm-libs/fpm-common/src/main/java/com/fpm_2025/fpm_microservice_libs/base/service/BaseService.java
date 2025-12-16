package com.fpm_2025.fpm_microservice_libs.base.service;

import com.fpm_2025.fpm_microservice_libs.base.entity.BaseEntity;
import com.fpm_2025.fpm_microservice_libs.base.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Base service with common CRUD operations
 */
@Slf4j
public abstract class BaseService<T extends BaseEntity, R extends BaseRepository<T>> {

    protected final R repository;

    protected BaseService(R repository) {
        this.repository = repository;
    }

    /**
     * Find entity by ID
     */
    public Optional<T> findById(Long id) {
        log.debug("Finding entity by id: {}", id);
        return repository.findById(id);
    }

    /**
     * Find all entities
     */
    public List<T> findAll() {
        log.debug("Finding all entities");
        return repository.findAll();
    }

    /**
     * Find all entities with pagination
     */
    public Page<T> findAll(Pageable pageable) {
        log.debug("Finding all entities with pagination: {}", pageable);
        return repository.findAll(pageable);
    }

    /**
     * Save entity
     */
    @Transactional
    public T save(T entity) {
        log.debug("Saving entity: {}", entity);
        return repository.save(entity);
    }

    /**
     * Update entity
     */
    @Transactional
    public T update(T entity) {
        log.debug("Updating entity: {}", entity);
        return repository.save(entity);
    }

    /**
     * Delete entity by ID
     */
    @Transactional
    public void deleteById(Long id) {
        log.debug("Deleting entity by id: {}", id);
        repository.deleteById(id);
    }

    /**
     * Check if entity exists by ID
     */
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    /**
     * Count all entities
     */
    public long count() {
        return repository.count();
    }
}