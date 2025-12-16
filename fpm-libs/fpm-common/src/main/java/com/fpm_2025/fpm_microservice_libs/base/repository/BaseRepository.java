package com.fpm_2025.fpm_microservice_libs.base.repository;

import com.fpm_2025.fpm_microservice_libs.base.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository interface with common query methods
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> 
        extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    
    // Common custom query methods can be added here
}
