package ru.job4j.synch;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void runQueue() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 4; i++) {
                        queue.offer(i);
                        assertThat(1, is(queue.size()));
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 4; i++) {
                        queue.poll();
                        assertThat(0, is(queue.size()));
                    }
                }
        );
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
    }
}
