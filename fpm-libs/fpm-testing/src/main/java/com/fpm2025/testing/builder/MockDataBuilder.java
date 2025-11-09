package com.fpm2025.testing.builder;

import com.fpm2025.testing.util.TestDataFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class MockDataBuilder<T, B extends MockDataBuilder<T, B>> {

    protected Long id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @SuppressWarnings("unchecked")
    protected B self() {
        return (B) this;
    }

    public B withId(Long id) {
        this.id = id;
        return self();
    }

    public B withRandomId() {
        this.id = TestDataFactory.randomLong();
        return self();
    }

    public B withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return self();
    }

    public B withUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return self();
    }

    public B withTimestamps() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        return self();
    }

    public abstract T build();

    public T buildAndCustomize(Consumer<T> customizer) {
        T obj = build();
        customizer.accept(obj);
        return obj;
    }

    public List<T> buildList(int count) {
        List<T> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(build());
        }
        return list;
    }

    public List<T> buildList(int count, Consumer<B> customizer) {
        List<T> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            customizer.accept(self());
            list.add(build());
        }
        return list;
    }

    protected String randomString(int length) {
        return TestDataFactory.randomString(length);
    }

    protected String randomEmail() {
        return TestDataFactory.randomEmail();
    }

    protected Long randomLong() {
        return TestDataFactory.randomLong();
    }

    protected Integer randomInt() {
        return TestDataFactory.randomInt();
    }

    protected BigDecimal randomAmount() {
        return TestDataFactory.randomAmount();
    }

    protected LocalDateTime randomDateTime() {
        return TestDataFactory.randomDateTime();
    }

    protected boolean randomBoolean() {
        return TestDataFactory.randomBoolean();
    }

    protected <E extends Enum<?>> E randomEnum(Class<E> enumClass) {
        return TestDataFactory.randomEnum(enumClass);
    }
}
