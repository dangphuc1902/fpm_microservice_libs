package com.fpm2025.testing.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class TestDataFactory {

    private static final Random random = new Random();

    public static String randomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String randomEmail() {
        return randomString(10).toLowerCase() + "@test.com";
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public static Long randomLong() {
        return random.nextLong(1, 1000000);
    }

    public static Long randomLong(long min, long max) {
        return random.nextLong(min, max);
    }

    public static Integer randomInt() {
        return random.nextInt(1, 10000);
    }

    public static Integer randomInt(int min, int max) {
        return random.nextInt(min, max);
    }

    public static BigDecimal randomAmount() {
        return BigDecimal.valueOf(random.nextDouble(1.0, 10000.0))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal randomAmount(double min, double max) {
        return BigDecimal.valueOf(random.nextDouble(min, max))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static LocalDateTime randomDateTime() {
        return LocalDateTime.now()
                .minusDays(random.nextInt(365))
                .minusHours(random.nextInt(24))
                .minusMinutes(random.nextInt(60));
    }

    public static boolean randomBoolean() {
        return random.nextBoolean();
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        return values[random.nextInt(values.length)];
    }
}
