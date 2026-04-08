package com.fpm2025.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum WalletPermissionLevel {
    READ_ONLY("READ_ONLY"),
    READ_WRITE("READ_WRITE"),
    FULL_ACCESS("FULL_ACCESS"),
    OWNER("OWNER");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static WalletPermissionLevel fromValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid WalletPermissionLevel: " + value));
    }

    @Override
    public String toString() {
        return value;
    }
}
