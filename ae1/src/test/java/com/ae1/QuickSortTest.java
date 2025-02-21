package com.ae1;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class QuickSortTest {

    @Test
    public void testSortEmptyArray() {
        int[] array = {};
        QuickSort.sort(array, 0, array.length - 1);
        assertArrayEquals(new int[]{}, array);
    }

    @Test
    public void testSortSingleElementArray() {
        int[] array = {1};
        QuickSort.sort(array, 0, array.length - 1);
        assertArrayEquals(new int[]{1}, array);
    }

    @Test
    public void testSortAlreadySortedArray() {
        int[] array = {1, 2, 3, 4, 5};
        QuickSort.sort(array, 0, array.length - 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    public void testSortReverseSortedArray() {
        int[] array = {5, 4, 3, 2, 1};
        QuickSort.sort(array, 0, array.length - 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    public void testSortUnsortedArray() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        QuickSort.sort(array, 0, array.length - 1);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9}, array);
    }

    @Test
    public void testSortArrayWithDuplicates() {
        int[] array = {4, 2, 2, 8, 3, 3, 1};
        QuickSort.sort(array, 0, array.length - 1);
        assertArrayEquals(new int[]{1, 2, 2, 3, 3, 4, 8}, array);
    }
}
