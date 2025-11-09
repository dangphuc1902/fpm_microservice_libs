package com.fpm2025.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CurrencyCode {
    VND("VND", "₫"),
    USD("USD", "$"),
    EUR("EUR", "€"),
    GBP("GBP", "£"),
    JPY("JPY", "¥");

    private final String code;
    private final String symbol;

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static CurrencyCode fromCode(String code) {
        return Arrays.stream(values())
                .filter(c -> c.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid CurrencyCode: " + code));
    }

    @Override
    public String toString() {
        return code;
    }
}
