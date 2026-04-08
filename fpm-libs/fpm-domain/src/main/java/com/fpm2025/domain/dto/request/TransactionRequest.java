package com.fpm2025.domain.dto.request;

import com.fpm2025.domain.enums.CategoryType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class TransactionRequest implements Serializable {
    @NotNull(message = "Wallet ID is required")
    private Long walletId;

    private Long categoryId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @Builder.Default
    private String currency = "VND";

    @NotNull(message = "Type is required")
    private CategoryType type;

    @NotNull(message = "Transaction date is required")
    private LocalDateTime transactionDate;

    private String description;
    private String note;
    private String location;

    @Builder.Default
    private Boolean isRecurring = false;
}
