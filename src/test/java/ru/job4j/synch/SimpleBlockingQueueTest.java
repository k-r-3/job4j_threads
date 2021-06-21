package ru.job4j.synch;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void whenPutAndGet() throws InterruptedException {
        CopyOnWriteArrayList<Integer> products = new CopyOnWriteArrayList<>();
        SimpleBlockingQueue<Integer> productions = new SimpleBlockingQueue<>(4);
        Thread producer = new Thread(
                () -> {
                        try {
                            for (int i = 0; i < 4; i++) {
                                productions.offer(i);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    while ((productions.size() > 0) || !Thread.currentThread().isInterrupted()) {
                        try {
                            products.add(productions.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(products, is(List.of(0, 1, 2, 3)));
    }
}