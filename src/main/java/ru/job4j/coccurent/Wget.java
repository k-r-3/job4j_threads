package ru.job4j.coccurent;

public class Wget {

    public static void main(String[] args) {
        Thread loadThread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading");
                        int index = 0;
                        while (index < 101) {
                            System.out.print("\rLoading : " + index  + "%");
                            index++;
                            Thread.sleep(100);
                        }
                        System.out.println("\n\rLoading complete");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        loadThread.start();
    }
}
