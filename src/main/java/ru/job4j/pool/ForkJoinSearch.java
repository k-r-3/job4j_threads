package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSearch<T> extends RecursiveTask<Integer> {
    private static final int INTERVAL = 10;
    private final T[] array;
    private final T key;
    private final int start;
    private final int end;

    public ForkJoinSearch(T[] array, T key, int start, int end) {
        this.array = array;
        this.key = key;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int first = -1;
        int second;
        if ((end - start) <= INTERVAL) {
            for (int i = start; i <= end; i++) {
                if (array[i].equals(key)) {
                    return i;
                }
            }
        } else {
            int mid = (start + end) / 2;
            ForkJoinSearch<T> leftSearch = new ForkJoinSearch<>(array, key,
                    start, mid);
            ForkJoinSearch<T> rightSearch = new ForkJoinSearch<>(array, key,
                    mid + 1, end);
            leftSearch.fork();
            rightSearch.fork();
            first = leftSearch.join();
            second = rightSearch.join();
            if (second != -1) {
                return second;
            }
        }
        return first;
    }

    public static int search(Object[] array, Object key) {
        ForkJoinSearch<Object> fork = new ForkJoinSearch<>(array, key,
                0, array.length - 1);
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(fork);
    }
}
