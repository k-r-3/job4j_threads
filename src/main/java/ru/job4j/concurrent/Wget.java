package ru.job4j.concurrent;

public class Wget {

    public static void main(String[] args) throws InterruptedException {
        Thread loadThread = new Thread(
                () -> {
                    System.out.println("Start loading");
                    int index = 0;
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.print("\rLoading : " + index + "%");
                            index++;
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            System.out.println(System.lineSeparator() + "Loading complete");
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        loadThread.start();
        Thread.sleep(11100);
        loadThread.interrupt();
        loadThread.join();
    }
}
