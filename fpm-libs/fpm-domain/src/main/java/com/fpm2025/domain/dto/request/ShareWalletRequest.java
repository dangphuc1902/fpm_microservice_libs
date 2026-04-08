package com.fpm2025.domain.dto.request;

import com.fpm2025.domain.enums.WalletPermissionLevel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareWalletRequest implements Serializable {
    @NotNull(message = "User email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Permission level is required")
    private WalletPermissionLevel permissionLevel;
}
