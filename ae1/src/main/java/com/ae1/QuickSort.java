package com.ae1;

public class QuickSort {

    public static int partition(int a[], int p, int r) {
        int x = a[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (a[j] <= x) {
                i++;
                ArrayUtils.swap(a, i, j);
            }
        }
        ArrayUtils.swap(a, i + 1, r);
        return i + 1;
    }

    public static void sort(int a[], int p, int r) {
        if (r <= p) return;
        int q = partition(a, p, r);
        sort(a, p, q - 1);
        sort(a, q + 1, r);
    }

}
