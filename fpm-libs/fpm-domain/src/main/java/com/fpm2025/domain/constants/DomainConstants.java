package com.fpm2025.domain.constants;

import java.math.BigDecimal;

public class DomainConstants {

    public static class Wallet {
        public static final String NAME_PATTERN = "^[a-zA-Z0-9\\s]{1,100}$";
        public static final BigDecimal MIN_BALANCE = BigDecimal.ZERO;
        public static final BigDecimal MAX_BALANCE = new BigDecimal("999999999.99");
        public static final int NAME_MAX_LENGTH = 100;
        public static final int DESCRIPTION_MAX_LENGTH = 255;
    }

    public static class Transaction {
        public static final BigDecimal MIN_AMOUNT = new BigDecimal("0.01");
        public static final BigDecimal MAX_AMOUNT = new BigDecimal("999999999.99");
        public static final String NOTE_PATTERN = "^[\\s\\S]{0,500}$";
        public static final int NOTE_MAX_LENGTH = 500;
    }

    public static class Category {
        public static final int MAX_DEPTH = 3;
        public static final int NAME_MAX_LENGTH = 50;
        public static final int DESCRIPTION_MAX_LENGTH = 255;
    }

    public static class User {
        public static final int USERNAME_MIN_LENGTH = 3;
        public static final int USERNAME_MAX_LENGTH = 50;
        public static final int PASSWORD_MIN_LENGTH = 8;
        public static final int PASSWORD_MAX_LENGTH = 100;
        public static final int FULLNAME_MAX_LENGTH = 100;
    }

    public static class Cache {
        public static final String KEY_WALLET = "wallet:%d";
        public static final String KEY_USER_WALLETS = "user:wallets:%d";
        public static final String KEY_CATEGORIES = "categories:%s";
        public static final String KEY_CATEGORY = "category:%d";
        public static final String KEY_TRANSACTION = "transaction:%d";
        public static final String KEY_USER_TRANSACTIONS = "user:transactions:%d";

        public static final long TTL_SHORT = 300L;
        public static final long TTL_MEDIUM = 1800L;
        public static final long TTL_LONG = 3600L;
    }

    public static class Event {
        public static final String EXCHANGE_WALLET = "wallet.exchange";
        public static final String EXCHANGE_TRANSACTION = "transaction.exchange";
        public static final String EXCHANGE_CATEGORY = "category.exchange";

        public static final String ROUTING_KEY_WALLET_CREATED = "wallet.created";
        public static final String ROUTING_KEY_WALLET_UPDATED = "wallet.updated";
        public static final String ROUTING_KEY_WALLET_DELETED = "wallet.deleted";

        public static final String ROUTING_KEY_TRANSACTION_CREATED = "transaction.created";
        public static final String ROUTING_KEY_TRANSACTION_UPDATED = "transaction.updated";
        public static final String ROUTING_KEY_TRANSACTION_DELETED = "transaction.deleted";
    }

    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }
}
