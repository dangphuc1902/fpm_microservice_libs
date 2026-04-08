package com.fpm2025.domain.dto.request;

import com.fpm2025.domain.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTransactionRequest implements Serializable {
    private BigDecimal amount;
    private String currency;
    private CategoryType type;
    private Long categoryId;
    private LocalDateTime transactionDate;
    private String description;
    private String note;
    private String location;
    private Boolean isRecurring;
}
