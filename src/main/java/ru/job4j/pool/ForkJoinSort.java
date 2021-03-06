package ru.job4j.pool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSort extends RecursiveTask<int[]> {
    private final int[] array;
    private final int from;
    private final int to;

    public ForkJoinSort(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    protected int[] compute() {
        if (from == to) {
            return new int[] {array[from]};
        }
        int mid = (from + to) / 2;
        ForkJoinSort left = new ForkJoinSort(array, from, mid);
        ForkJoinSort right = new ForkJoinSort(array, mid + 1, to);
        left.fork();
        right.fork();
        int[] leftArr = left.join();
        int[] rightArr = right.join();
        return MergeSort.merge(leftArr, rightArr);
    }

    public static int[] sort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ForkJoinSort(array, 0, array.length - 1));
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(ForkJoinSort.sort(new int[]{1, 13, 45, 2, 9, 7})));
    }
}
