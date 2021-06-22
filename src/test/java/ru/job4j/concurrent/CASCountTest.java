package ru.job4j.concurrent;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenIncrement() throws InterruptedException {
        CASCount count = new CASCount(-1);
        Thread first = new Thread (
                () -> {
                    for (int i = 0; i < 3; i++) {
                        count.increment();
                    }
                }

        );
        Thread second = new Thread (
                () -> {
                    for (int i = 0; i < 3; i++) {
                        count.increment();
                    }
                }

        );
        first.start();
        second.start();
        first.join();
        second.join();
        count.increment();
        assertThat(count.get(), is(6));
    }

}