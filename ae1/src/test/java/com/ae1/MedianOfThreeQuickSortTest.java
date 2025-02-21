package com.ae1;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class MedianOfThreeQuickSortTest {

    @Test
    public void testSortMedian3() {
        int[] array = {3, 6, 8, 10, 1, 2, 1};
        int[] expected = {1, 1, 2, 3, 6, 8, 10};
        MedianOfThreeQuickSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortMedian3EmptyArray() {
        int[] array = {};
        int[] expected = {};
        MedianOfThreeQuickSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortMedian3SingleElementArray() {
        int[] array = {1};
        int[] expected = {1};
        MedianOfThreeQuickSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortMedian3AlreadySortedArray() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        MedianOfThreeQuickSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortMedian3ReverseSortedArray() {
        int[] array = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        MedianOfThreeQuickSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }
}