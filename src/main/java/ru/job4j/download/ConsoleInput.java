package ru.job4j.download;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleInput implements Input {
    private static final Scanner SC = new Scanner(System.in);

    @Override
    public String[] read() throws IOException {
        return SC.nextLine().split(" ");
    }
}
