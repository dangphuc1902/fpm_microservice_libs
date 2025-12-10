package com.fpm2025.domain.wallet;

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
public class Wallet {
    
    private Long id;
    
    private Long userId;
    
    private String name;
    
    private WalletType type;
    
    private Money balance;
    
    private String icon;
    
    private boolean active;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    public void deposit(Money amount) {
        if (!amount.getCurrency().equals(balance.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        this.balance = this.balance.add(amount);
    }
    
    public void withdraw(Money amount) {
        if (!amount.getCurrency().equals(balance.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        if (this.balance.getAmount().compareTo(amount.getAmount()) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }
        this.balance = this.balance.subtract(amount);
    }
}