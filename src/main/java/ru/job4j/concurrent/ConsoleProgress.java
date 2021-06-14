package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        try {
            char[] loading = {'\\', '|', '-', '/'};
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r loading... " + loading[i++]);
                if (i == 4) {
                    i = 0;
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(2100);
        progress.interrupt();
    }
}
