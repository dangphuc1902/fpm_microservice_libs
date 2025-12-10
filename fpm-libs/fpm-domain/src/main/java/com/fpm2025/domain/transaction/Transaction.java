package com.fpm2025.domain.transaction;

import com.fpm2025.domain.common.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fpm2025.domain.enums.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    
    private Long id;
    
    private Long walletId;
    
    private Long userId;
    
    private Money amount;
    
    private TransactionType type;
    
    private String description;
    
    private Long categoryId;
    
    private LocalDateTime transactionDate;
    
    private TransactionSource source;
    
    private String note;
    
    private String location;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}