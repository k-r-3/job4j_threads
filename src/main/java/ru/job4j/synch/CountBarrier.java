package ru.job4j.synch;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count <= total) {
                try {
                    System.out.println("notify, count : " + count);
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("more than total");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier barrier = new CountBarrier(2);
        Thread first = new Thread(
                barrier::await
        );
        Thread second = new Thread(
                barrier::count
        );
        first.start();
        barrier.count();
        Thread.sleep(500);
        second.start();
        Thread.sleep(500);
        barrier.count();
        first.join();
        second.join();
    }
}