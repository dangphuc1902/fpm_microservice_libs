package com.fpm2025.domain.category;
import com.fpm2025.domain.enums.*;
public record Category(
	    Long id,
	    String name,
	    String icon,
	    CategoryType type,          // EXPENSE / INCOME
	    Long parentId
	) {}