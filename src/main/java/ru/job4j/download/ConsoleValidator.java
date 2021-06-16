package ru.job4j.download;

public class ConsoleValidator implements Validator {

    @Override
    public boolean check(String[] in) {
        String adres = in[0];
        int speed = Integer.parseInt(in[1]);
        if (adres.startsWith("https://")) {
            if (speed > 1) {
                return true;
            }
        }
        return false;
    }
}
