package com.fpm2025.domain.transaction;

import com.fpm2025.domain.common.BaseEvent;
import com.fpm2025.domain.common.Money;
import com.fpm2025.domain.transaction.TransactionSource;
import com.fpm2025.domain.enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransactionCreatedEvent extends BaseEvent {
    
    private Long transactionId;
    
    private Long walletId;
    
    private Money amount;
    
    private TransactionType type;
    
    private String description;
    
    private Long categoryId;
    
    private LocalDateTime transactionDate;
    
    private TransactionSource source;
}