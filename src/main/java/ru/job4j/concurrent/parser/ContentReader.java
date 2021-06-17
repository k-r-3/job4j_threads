package ru.job4j.concurrent.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

public class ContentReader {
    private final File file;

    public ContentReader(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> filter) {
        File copy = new File(String.valueOf(file));
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(copy))) {
            int data;
            while ((data = reader.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
