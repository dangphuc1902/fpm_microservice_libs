package com.fpm2025.messaging.kafka.constants;

public final class KafkaTopics {
    
    // User events
    public static final String USER_CREATED = "user.created";
    public static final String USER_UPDATED = "user.updated";
    public static final String USER_DELETED = "user.deleted";
    
    // Wallet events
    public static final String WALLET_CREATED = "wallet.created";
    public static final String WALLET_UPDATED = "wallet.updated";
    public static final String BALANCE_CHANGED = "balance.changed";
    
    // Transaction events
    public static final String TRANSACTION_CREATED = "transaction.created";
    public static final String TRANSACTION_UPDATED = "transaction.updated";
    public static final String TRANSACTION_DELETED = "transaction.deleted";
    
    // Category events
    public static final String CATEGORY_ASSIGNED = "category.assigned";
    public static final String BUDGET_EXCEEDED = "budget.exceeded";
    
    // Notification events
    public static final String NOTIFICATION_PARSED = "notification.parsed";
    public static final String PUSH_NOTIFICATION = "push.notification";
    
    // OCR events
    public static final String OCR_COMPLETED = "ocr.completed";
    
    // Sharing events
    public static final String FAMILY_CREATED = "family.created";
    public static final String MEMBER_ADDED = "member.added";
    
    private KafkaTopics() {
        throw new UnsupportedOperationException("Utility class");
    }
}