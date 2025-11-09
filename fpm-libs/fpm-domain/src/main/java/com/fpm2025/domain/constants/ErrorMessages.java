package com.fpm2025.domain.constants;

public class ErrorMessages {

    public static class Wallet {
        public static final String NOT_FOUND = "Wallet not found";
        public static final String NOT_FOUND_WITH_ID = "Wallet not found with id: %d";
        public static final String INSUFFICIENT_BALANCE = "Insufficient balance in wallet";
        public static final String INVALID_BALANCE = "Invalid wallet balance";
        public static final String UNAUTHORIZED_ACCESS = "You don't have permission to access this wallet";
        public static final String DUPLICATE_NAME = "Wallet with this name already exists";
    }

    public static class Transaction {
        public static final String NOT_FOUND = "Transaction not found";
        public static final String NOT_FOUND_WITH_ID = "Transaction not found with id: %d";
        public static final String INVALID_AMOUNT = "Transaction amount must be positive";
        public static final String UNAUTHORIZED_ACCESS = "You don't have permission to access this transaction";
        public static final String SAME_WALLET_TRANSFER = "Cannot transfer to the same wallet";
    }

    public static class Category {
        public static final String NOT_FOUND = "Category not found";
        public static final String NOT_FOUND_WITH_ID = "Category not found with id: %d";
        public static final String DUPLICATE_NAME = "Category with this name already exists";
        public static final String MAX_DEPTH_EXCEEDED = "Category depth exceeds maximum allowed level";
        public static final String CANNOT_DELETE_WITH_CHILDREN = "Cannot delete category with child categories";
        public static final String CANNOT_DELETE_WITH_TRANSACTIONS = "Cannot delete category with existing transactions";
    }

    public static class User {
        public static final String NOT_FOUND = "User not found";
        public static final String NOT_FOUND_WITH_ID = "User not found with id: %d";
        public static final String NOT_FOUND_WITH_EMAIL = "User not found with email: %s";
        public static final String DUPLICATE_EMAIL = "Email already exists";
        public static final String DUPLICATE_USERNAME = "Username already exists";
        public static final String INVALID_CREDENTIALS = "Invalid email or password";
        public static final String WEAK_PASSWORD = "Password does not meet security requirements";
    }

    public static class Validation {
        public static final String REQUIRED_FIELD = "%s is required";
        public static final String INVALID_FORMAT = "%s has invalid format";
        public static final String OUT_OF_RANGE = "%s must be between %s and %s";
        public static final String TOO_SHORT = "%s must be at least %d characters";
        public static final String TOO_LONG = "%s must not exceed %d characters";
    }

    private ErrorMessages() {
        throw new IllegalStateException("Utility class");
    }
}
