package com.fpm_2025.fpm_microservice_libs.base.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Generic CRUD service interface
 */
public interface CrudService<T, ID> {

    T create(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    T update(ID id, T entity);

    void delete(ID id);

    boolean exists(ID id);

    long count();
}