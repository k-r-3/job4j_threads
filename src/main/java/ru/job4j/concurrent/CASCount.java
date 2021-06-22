package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();
    private int initial;

    public CASCount(int initial) {
        this.initial = initial;
    }

    public void increment() {
        int accumulator;
        int expected;
        count.set(initial);
        do {
            expected = count.get();
            accumulator = expected + 1;
        } while (!count.compareAndSet(expected, initial));
        initial = accumulator;
    }

    public int get() {
        int result;
        count.set(initial);
        do {
            initial = count.get();
            result = initial;
        } while (!count.compareAndSet(initial, result));
        return result;
    }
}
