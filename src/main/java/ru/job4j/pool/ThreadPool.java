package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int capacity) {
        tasks = new SimpleBlockingQueue<>(capacity);
        initThreads();
    }

    public void work(Runnable job) {
        try {
                tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    private void initThreads() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                            while (!Thread.currentThread().isInterrupted()) {
                                try {
                                    tasks.poll().run();
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                        }
                    }
            ));
        }
    }

    public void execute() {
        try {
            for (Thread thread : threads) {
                thread.start();
                thread.join(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}