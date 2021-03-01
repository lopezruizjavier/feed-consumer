package com.sparta.feedconsumer.util;

public abstract class AbstractTestBuilder<T> {

    protected T object;

    public AbstractTestBuilder() {
        create();
    }

    protected abstract void create();

    public T build() {
        return object;
    }
}
