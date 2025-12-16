package com.fpm_2025.fpm_microservice_libs.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Utility class for Pageable operations
 */
public final class PageableUtil {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 100;

    private PageableUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Create Pageable with validation
     */
    public static Pageable createPageable(int page, int size) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > MAX_PAGE_SIZE) {
            size = DEFAULT_PAGE_SIZE;
        }
        return PageRequest.of(page, size);
    }

    /**
     * Create Pageable with sorting
     */
    public static Pageable createPageable(int page, int size, Sort sort) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > MAX_PAGE_SIZE) {
            size = DEFAULT_PAGE_SIZE;
        }
        return PageRequest.of(page, size, sort);
    }

    /**
     * Create Pageable with sorting by field and direction
     */
    public static Pageable createPageable(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return createPageable(page, size, sort);
    }
}