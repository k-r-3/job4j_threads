package ru.job4j.pool;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ForkJoinSearchTest {

    @Test
    public void whenBinarySearch() {
        Integer[] array = Arrays.stream(
                IntStream.range(1, 1000).toArray())
                .boxed()
                .toArray(Integer[]::new);
        int expected = ForkJoinSearch.search(array, 356, (Comparator.comparingInt(o -> (int) o)));
        assertThat(355, is(expected));
    }

    @Test
    public void whenLinearSearch() {
        Integer[] array = Arrays.stream(
                IntStream.range(1, 11).toArray())
                .boxed()
                .toArray(Integer[]::new);
        ForkJoinSearch<Integer> search = new ForkJoinSearch<>(array, 3,
                0, array.length - 1, Integer::compare);
        ForkJoinPool pool = new ForkJoinPool();
        int expected = pool.invoke(search);
        int active = pool.getActiveThreadCount();
        assertThat(active <= 1, is(true));
        assertThat(2, is(expected));
    }

    @Test
    public void whenNotSortBinarySearch() {
        List<Integer> list = Arrays.asList(Arrays.stream(
                IntStream.range(1, 110).toArray())
                .boxed()
                .toArray(Integer[]::new));
        Collections.shuffle(list);
        Object[] array = list.toArray();
        int expected = ForkJoinSearch.search(array, 5, Comparator.comparingInt(o -> (int) o));
        assertThat(-1, is(expected));
    }

    @Test
    public void whenNotSortLinearSearch() {
        List<Integer> list = Arrays.asList(Arrays.stream(
                IntStream.range(1, 11).toArray())
                .boxed()
                .toArray(Integer[]::new));
        Collections.shuffle(list);
        Object[] array = list.toArray();
        int expected = ForkJoinSearch.search(array, 5, Comparator.comparingInt(o -> (int) o));
        assertThat(expected != -1, is(true));
    }

}