package com.fpm2025.testing.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class AssertionUtil {

    public static void assertBigDecimalEquals(BigDecimal expected, BigDecimal actual) {
        assertThat(actual)
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(expected);
    }

    public static void assertDateTimeClose(LocalDateTime expected, LocalDateTime actual, long seconds) {
        assertThat(actual)
                .isCloseTo(expected, within(seconds, ChronoUnit.SECONDS));
    }

    public static void assertDateTimeClose(LocalDateTime actual, long seconds) {
        assertDateTimeClose(LocalDateTime.now(), actual, seconds);
    }

    public static void assertPositive(BigDecimal value) {
        assertThat(value).isGreaterThan(BigDecimal.ZERO);
    }

    public static void assertNonNegative(BigDecimal value) {
        assertThat(value).isGreaterThanOrEqualTo(BigDecimal.ZERO);
    }

    public static void assertNotBlank(String value) {
        assertThat(value).isNotNull().isNotBlank();
    }

    public static void assertValidId(Long id) {
        assertThat(id).isNotNull().isPositive();
    }
}
