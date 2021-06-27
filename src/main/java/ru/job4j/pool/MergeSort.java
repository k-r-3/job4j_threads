package ru.job4j.pool;

import java.util.Arrays;

public class MergeSort {

    public static int[] sort(int[] array) {
        return sort(array, 0, array.length - 1);
    }

    public static int[] sort(int[] array, int from, int to) {
        if (from == to) {
            return new int[] {array[from]};
        }
        int mid = (from + to) / 2;
        return merge(sort(array, from, mid),
                sort(array, mid + 1, to));
    }

    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int resCursor = 0;
        int leftCursor = 0;
        int rightCursor = 0;
        while (resCursor != result.length) {
            if (leftCursor == left.length) {
                result[resCursor++] = right[rightCursor++];
            } else if (rightCursor == right.length) {
                result[resCursor++] = left[leftCursor++];
            } else if (left[leftCursor] < right[rightCursor]) {
                result[resCursor++] = left[leftCursor++];
            } else {
                result[resCursor++] = right[rightCursor++];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(MergeSort.sort(new int[]{1, 13, 45, 2, 9, 7})));
    }
}
