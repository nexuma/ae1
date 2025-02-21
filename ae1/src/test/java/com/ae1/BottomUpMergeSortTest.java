package com.ae1;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class BottomUpMergeSortTest {

    @Test
    public void testSort() {
        int[] array = {5, 2, 4, 6, 1, 3};
        int[] expected = {1, 2, 3, 4, 5, 6};
        BottomUpMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortEmptyArray() {
        int[] array = {};
        int[] expected = {};
        BottomUpMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortSingleElement() {
        int[] array = {1};
        int[] expected = {1};
        BottomUpMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortAlreadySorted() {
        int[] array = {1, 2, 3, 4, 5, 6};
        int[] expected = {1, 2, 3, 4, 5, 6};
        BottomUpMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortReverseOrder() {
        int[] array = {6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6};
        BottomUpMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortWithDuplicates() {
        int[] array = {5, 2, 4, 6, 1, 3, 2, 5};
        int[] expected = {1, 2, 2, 3, 4, 5, 5, 6};
        BottomUpMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortAllSameElements() {
        int[] array = {1, 1, 1, 1, 1, 1};
        int[] expected = {1, 1, 1, 1, 1, 1};
        BottomUpMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortNegativeNumbers() {
        int[] array = {-3, -1, -4, -2, -5};
        int[] expected = {-5, -4, -3, -2, -1};
        BottomUpMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortMixedNumbers() {
        int[] array = {3, -1, 4, -2, 5, 0};
        int[] expected = {-2, -1, 0, 3, 4, 5};
        BottomUpMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }
}