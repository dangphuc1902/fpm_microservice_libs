package com.fpm2025.domain.dto.response;

import com.fpm2025.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse implements Serializable {
    private Long id;
    private Long walletId;
    private String walletName;
    private Long categoryId;
    private String categoryName;
    private TransactionType type;
    private BigDecimal amount;
    private String currency;
    private String note;
    private LocalDateTime transactionDate;
    private LocalDateTime createdAt;
    private List<String> attachments;
}
