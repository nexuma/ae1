package com.ae1;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class ShellSortTest {

    @Test
    public void testSort() {
        int[] array = {5, 2, 4, 6, 1, 3};
        ShellSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, array);
    }

    @Test
    public void testEmptyArray() {
        int[] array = {};
        ShellSort.sort(array);
        assertArrayEquals(new int[]{}, array);
    }

    @Test
    public void testSingleElementArray() {
        int[] array = {1};
        ShellSort.sort(array);
        assertArrayEquals(new int[]{1}, array);
    }

    @Test
    public void testAlreadySortedArray() {
        int[] array = {1, 2, 3, 4, 5, 6};
        ShellSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, array);
    }

    @Test
    public void testReverseSortedArray() {
        int[] array = {6, 5, 4, 3, 2, 1};
        ShellSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, array);
    }
}