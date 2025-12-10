package com.fpm2025.security.grpc;

import io.grpc.Context;

public class GrpcSecurityContext {

    public static final Context.Key<Long> USER_ID_KEY = Context.key("userId");
    public static final Context.Key<String> EMAIL_KEY = Context.key("email");

    public static Long getCurrentUserId() {
        return USER_ID_KEY.get();
    }

    public static String getCurrentUserEmail() {
        return EMAIL_KEY.get();
    }

    private GrpcSecurityContext() {
        throw new UnsupportedOperationException("Utility class");
    }
}