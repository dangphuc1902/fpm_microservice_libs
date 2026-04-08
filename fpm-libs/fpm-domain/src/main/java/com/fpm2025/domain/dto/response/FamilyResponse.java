package com.fpm2025.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private String role; // Current user's role in this family
}
