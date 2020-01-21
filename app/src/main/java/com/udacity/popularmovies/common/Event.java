package com.udacity.popularmovies.common;

import java.util.concurrent.atomic.AtomicBoolean;

public class Event<T> {
    private AtomicBoolean hasBeenHandled = new AtomicBoolean(false);
    private T content;

    public Event(T content) {
        this.content = content;
    }

    public T getIfNotHandled() {
        if (hasBeenHandled.getAndSet(true))
            return null;
        else
            return content;
    }

    public T peek() {
        return content;
    }
}
