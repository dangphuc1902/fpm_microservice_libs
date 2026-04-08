package com.fpm2025.domain.dto.response;

import com.fpm2025.domain.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse implements Serializable {
    private Long id;
    private String name;
    private Long parentId;
    private Long userId;
    private String iconPath;
    private String color;
    private CategoryType type;
    private Integer depth;
    private Integer sortOrder;
    private List<CategoryResponse> children;
}
