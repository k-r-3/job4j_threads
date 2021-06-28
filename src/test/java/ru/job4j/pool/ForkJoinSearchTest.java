package ru.job4j.pool;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ForkJoinSearchTest {

    @Test
    public void whenIntegerPresent() {
        Integer[] array = Arrays.stream(
                IntStream.range(1, 1000).toArray())
                .boxed()
                .toArray(Integer[]::new);
        int expected = ForkJoinSearch.search(array, 356);
        assertThat(355, is(expected));
    }

    @Test
    public void whenIntegerNotPresent() {
        Integer[] array = Arrays.stream(
                IntStream.range(1, 1000).toArray())
                .boxed()
                .toArray(Integer[]::new);
        int expected = ForkJoinSearch.search(array, 0);
        assertThat(-1, is(expected));
    }

    @Test
    public void whenStringPresent() {
        String[] array = ("The fork/join framework is an implementation"
                + " of the ExecutorService interface that helps you take"
                + " advantage of multiple processors.").split(" ");
        int expected = ForkJoinSearch.search(array, "fork/join");
        assertThat(1, is(expected));
    }

    @Test
    public void whenStringNotPresent() {
        String[] array = ("The fork/join framework is an implementation"
                + " of the ExecutorService interface that helps you take"
                + " advantage of multiple processors.").split(" ");
        int expected = ForkJoinSearch.search(array, "ForkJoinPool");
        assertThat(-1, is(expected));
    }
}