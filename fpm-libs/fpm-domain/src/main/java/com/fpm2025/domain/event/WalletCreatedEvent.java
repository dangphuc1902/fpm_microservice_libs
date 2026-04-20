package com.fpm2025.domain.event;

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
public class WalletCreatedEvent implements Serializable {
    private Long walletId;
    private Long userId;
    private String name;
    private String type;
    private String currency;
    private LocalDateTime createdAt;
}
