package com.fpm2025.domain.dto.response;

import com.fpm2025.domain.enums.WalletPermissionLevel;
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
public class WalletPermissionResponse implements Serializable {
    private Long id;
    private Long walletId;
    private Long userId;
    private String userEmail;
    private String userName;
    private WalletPermissionLevel permissionLevel;
    private LocalDateTime createdAt;
}
