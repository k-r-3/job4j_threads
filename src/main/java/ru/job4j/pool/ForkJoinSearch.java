package ru.job4j.pool;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T key;
    private final int leftBord;
    private final int rightBord;
    private final Comparator<T> comp;

    public ForkJoinSearch(T[] array, T key, int leftBord, int rightBord, Comparator<T> comp) {
        this.array = array;
        this.key = key;
        this.leftBord = leftBord;
        this.rightBord = rightBord;
        this.comp = comp;
    }

    @Override
    protected Integer compute() {
        if (leftBord > rightBord) {
            return -1;
        }
        if ((rightBord - leftBord) <= 10) {
            return linearSearch();
        } else {
            int mid = leftBord + (rightBord - leftBord) / 2;
            if (array[mid] == key) {
                return mid;
            }
            return binarySearch(mid);
        }
    }

    private int binarySearch(int mid) {
        ForkJoinSearch<T> forkSearch;
        if (Objects.compare(array[mid], key, comp) < 0) {
            forkSearch = new ForkJoinSearch<>(array, key,
                    mid + 1, rightBord, comp);
        } else {
            forkSearch = new ForkJoinSearch<>(array, key,
                    leftBord, mid - 1, comp);
        }
        forkSearch.fork();
        return forkSearch.join();
    }

    private int linearSearch() {
        for (int i = leftBord; i <= rightBord; i++) {
            if (array[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public static int search(Object[] array, Object key, Comparator<Object> comp) {
        ForkJoinSearch<Object> fork = new ForkJoinSearch<>(array, key,
                0, array.length - 1, comp);
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(fork);
    }
}
