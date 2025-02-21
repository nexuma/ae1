package com.ae1;

public class MedianOfThreeQuickSort {

    public static void sort(int a[], int p, int r) {
        if (r <= p) return;
        int mid = (p + r) / 2;
        if (a[mid] < a[p]) ArrayUtils.swap(a, p, mid);
        if (a[r] < a[p]) ArrayUtils.swap(a, p, r);
        if (a[r] < a[mid]) ArrayUtils.swap(a, mid, r);
        ArrayUtils.swap(a, mid, r - 1);
        int q = QuickSort.partition(a, p, r - 1);
        sort(a, p, q - 1);
        sort(a, q + 1, r);
    }

}
