package ru.job4j.pool;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadPoolTest {
    private static AtomicInteger count = new AtomicInteger(0);

    @Before
    public void initCount() {
        count.set(0);
    }

    private static class Task implements Runnable {

        @Override
        public void run() {
            count.incrementAndGet();
        }
    }

    @Test
    public void whenAddInPool() {
        ThreadPool pool = new ThreadPool(3);
        pool.work(new Task());
        pool.work(new Task());
        pool.work(new Task());
        pool.execute();
        pool.shutdown();
        assertThat(count.get(), is(3));
    }

    @Test
    public void whenCapacityLessThenResultLess() {
        ThreadPool pool = new ThreadPool(3);
        pool.work(new Task());
        pool.work(new Task());
        pool.work(new Task());
        pool.work(new Task());
        pool.execute();
        pool.shutdown();
        assertThat(count.get(), is(3));
    }

}