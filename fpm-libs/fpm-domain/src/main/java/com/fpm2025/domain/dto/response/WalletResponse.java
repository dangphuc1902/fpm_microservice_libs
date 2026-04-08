package com.fpm2025.domain.dto.response;

import com.fpm2025.domain.enums.WalletType;
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
public class WalletResponse implements Serializable {
    private Long id;
    private Long userId;
    private Long familyId;
    private String name;
    private WalletType type;
    private String currency;
    private BigDecimal balance;
    private String icon;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
