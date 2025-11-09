package com.fpm2025.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum WalletType {
    CASH("cash"),
    BANK("bank"),
    CREDIT_CARD("credit_card"),
    E_WALLET("e_wallet"),
    SAVINGS("savings"),
    INVESTMENT("investment");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static WalletType fromValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid WalletType: " + value));
    }

    @Override
    public String toString() {
        return value;
    }
}
