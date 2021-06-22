package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public CASCount(int initial) {
        count.set(initial);
    }

    public void increment() {
        int expected;
        do {
            expected = count.get();
        } while (!count.compareAndSet(count.get(), expected + 1));
    }

    public int get() {
        return count.get();
    }
}
