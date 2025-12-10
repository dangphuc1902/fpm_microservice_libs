package com.fpm2025.domain.transaction;

public enum TransactionSource {
    MANUAL("Nhập tay"),
    VOICE("Giọng nói"),
    NOTIFICATION("Thông báo"),
    OCR("Quét hóa đơn");
    
    private final String displayName;
    
    TransactionSource(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}