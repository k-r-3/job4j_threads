package ru.job4j.download;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Predicate;

public class FileWalker extends SimpleFileVisitor<Path> {
    private Predicate<Path> pred;
    private boolean available;

    public FileWalker(Predicate<Path> pred) {
        this.pred = pred;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println(file.toString());
        if (pred.test(file)) {
            available = true;
        }
        return super.visitFile(file, attrs);
    }

    public boolean nameIsExist() {
        return available;
    }
}
