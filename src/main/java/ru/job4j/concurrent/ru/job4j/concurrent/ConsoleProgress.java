package ru.job4j.concurrent.ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        try {
            String loading = "Loading \\";
            String process = "process[.  ]";
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r load : " + process + "["+loading+"]");
                process = process.equals("process[.  ]") ? "process[.. ]" :
                        process.equals("process[.. ]") ? "process[...]" : "process[.  ]";
                loading = loading.equals("Loading \\") ? "Loading |" :
                        loading.equals("Loading |") ? "Loading /" :
                                loading.equals("Loading /") ? "Loading -" : "Loading \\";
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(2000);
        progress.interrupt();
    }
}
