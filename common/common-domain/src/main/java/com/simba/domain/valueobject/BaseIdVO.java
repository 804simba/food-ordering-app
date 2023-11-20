package com.simba.domain.valueobject;

import java.util.Objects;

public abstract class BaseIdVO<T> {
    private final T value;

    protected BaseIdVO(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseIdVO<?> baseIdVO = (BaseIdVO<?>) o;
        return Objects.equals(value, baseIdVO.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
