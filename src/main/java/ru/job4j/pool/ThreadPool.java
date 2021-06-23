package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;
    private int capacity;

    public ThreadPool(int capacity) {
        this.capacity = capacity;
        tasks = new SimpleBlockingQueue<>(capacity);
    }

    public void work(Runnable job) {
        try {
            if (tasks.size() < capacity) {
                tasks.offer(job);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).interrupt();
        }
    }

    private void initThreads() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        for (int j = 0; j < tasks.size(); j++) {
                            try {
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ));
        }
    }

    public void execute() {
        initThreads();
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