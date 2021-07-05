package ru.job4j.pool;

public class BinarySearch {

    public static int binSearch(int[] arr, int key) {
        int result = -1;
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) / 2);
            if (arr[mid] < key) {
                left = mid + 1;
            } else if (arr[mid] > key) {
                right = mid - 1;
            } else if (arr[mid] == key) {
                result = mid;
                return result;
            }
        }
        return result;
    }

    public static int recursiveSearch(int[] arr, int key) {
        return recursiveSearch(arr, key, 0, arr.length - 1);
    }

    public static int recursiveSearch(int[] arr, int key, int low, int high) {
        int mid = low + ((high - low) / 2);
        if (high < low) {
            return -1;
        }
        if (arr[mid] == key) {
            return mid;
        } else if (arr[mid] < key) {
          return recursiveSearch(arr, key, mid + 1, high);
        } else {
            return recursiveSearch(arr, key, low, mid - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(BinarySearch.binSearch((new int[] {1, 2, 7, 9, 13, 45}), 27));
        System.out.println(BinarySearch.recursiveSearch((new int[] {1, 2, 7, 9, 13, 45}), 13));
    }
}
