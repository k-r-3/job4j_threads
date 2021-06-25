package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private ExecutorService pool;

    public EmailNotification() {
        this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void emailTo(User user) {
        String name = user.getUserName();
        String email = user.getEmail();
        pool.submit(() -> {
            String subject = String.format("Notification %s to email %s", user.getUserName(), user.getEmail());
            String body = String.format("Add a new event to %s", user.getUserName());
            send(subject, body, user.getEmail());
        });
    }


    public void send(String subject, String body, String email) {
        System.out.printf("%s %s %s", subject, body, email);
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
