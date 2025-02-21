package com.ae1;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;


public class HybridInsertionMergeSortTest {

    @Test
    public void testSort() {
        int[] array = {5, 2, 4, 6, 1, 3};
        int[] expected = {1, 2, 3, 4, 5, 6};
        HybridInsertionMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortEmptyArray() {
        int[] array = {};
        int[] expected = {};
        HybridInsertionMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortSingleElement() {
        int[] array = {1};
        int[] expected = {1};
        HybridInsertionMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortAlreadySorted() {
        int[] array = {1, 2, 3, 4, 5, 6};
        int[] expected = {1, 2, 3, 4, 5, 6};
        HybridInsertionMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortReverseOrder() {
        int[] array = {6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6};
        HybridInsertionMergeSort.sort(array, 0, array.length - 1);
        assertArrayEquals(expected, array);
    }

    @Test
    public void testSortCutOff() {
        int[] array = {5, 2, 4, 6, 1, 3};
        int[] expected = {1, 2, 3, 4, 5, 6};
        HybridInsertionMergeSort.sortCutOff(array, 0, array.length - 1, 2);
        assertArrayEquals(expected, array);
    }
}