package com.fpm2025.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankNotificationRequest implements Serializable {
    private String bankName;
    private String account;
    private BigDecimal amount;
    private String type; // INCOME | EXPENSE
    private String note;
    private String transactionRef;
}
