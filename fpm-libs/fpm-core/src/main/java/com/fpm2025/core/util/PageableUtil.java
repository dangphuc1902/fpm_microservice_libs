package com.fpm2025.core.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtil {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final int MAX_SIZE = 100;

    public static Pageable createPageable(Integer page, Integer size) {
        int validPage = page != null && page >= 0 ? page : DEFAULT_PAGE;
        int validSize = size != null && size > 0 ? Math.min(size, MAX_SIZE) : DEFAULT_SIZE;
        return PageRequest.of(validPage, validSize);
    }

    public static Pageable createPageable(Integer page, Integer size, Sort sort) {
        int validPage = page != null && page >= 0 ? page : DEFAULT_PAGE;
        int validSize = size != null && size > 0 ? Math.min(size, MAX_SIZE) : DEFAULT_SIZE;
        return PageRequest.of(validPage, validSize, sort);
    }

    public static Pageable createPageable(Integer page, Integer size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction != null ? direction : "ASC"),
                           sortBy != null ? sortBy : "id");
        return createPageable(page, size, sort);
    }

    public static int getValidPage(Integer page) {
        return page != null && page >= 0 ? page : DEFAULT_PAGE;
    }

    public static int getValidSize(Integer size) {
        return size != null && size > 0 ? Math.min(size, MAX_SIZE) : DEFAULT_SIZE;
    }
}
