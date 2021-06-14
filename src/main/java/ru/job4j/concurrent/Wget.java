package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private static final long NANO_SEC = 1_000_000_000;
    private static final long MILLI_SEC = 1_000_000;
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bufferSize = dataBuffer.length;
            int bytesRead;
            double expectedTime = ((double) bufferSize / speed) * NANO_SEC;
            int pause = 0;
            long mark = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                if (bytesRead == bufferSize) {
                    long actualTime = System.nanoTime() - mark;
                    mark = System.nanoTime();
                    if (actualTime < expectedTime) {
                        pause = (int) ((expectedTime - actualTime) / MILLI_SEC);
                    }
                }
                Thread.sleep(pause);
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
