package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == capacity) {
                    this.wait();
            }
            queue.offer(value);
            this.notify();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.size() == 0) {
                    this.wait();
            }
            T result = queue.poll();
            this.notify();
            return result;
        }
    }

    public synchronized int size() {
        return queue.size();
    }
}
