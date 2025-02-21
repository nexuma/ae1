package com.ae1.video;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class VideoMinHeapTest {

    private VideoMinHeap minHeap;

    @Before
    public void setUp() {
        minHeap = new VideoMinHeap(3);
    }

    @Test
    public void testInsertAndHeapifyUp() {
        minHeap.insert(new Video(1, "Video 1", 500));
        minHeap.insert(new Video(2, "Video 2", 1500));
        minHeap.insert(new Video(3, "Video 3", 300));

        System.out.println("Heap after inserting 3 elements:");
        for (Video v : minHeap.heap) {
            System.out.println(v.id + " - " + v.views);
        }

        assertEquals(3, minHeap.size);
        assertTrue(minHeap.heap[0].views <= minHeap.heap[1].views);
        assertTrue(minHeap.heap[0].views <= minHeap.heap[2].views);
    }

    @Test
    public void testInsertAndHeapifyDown() {
        minHeap.insert(new Video(1, "Video 1", 500));
        minHeap.insert(new Video(2, "Video 2", 1500));
        minHeap.insert(new Video(3, "Video 3", 300));
        minHeap.insert(new Video(4, "Video 4", 2000));

        assertEquals(3, minHeap.size);
        assertEquals(500, minHeap.heap[0].views);
        assertEquals(2000, minHeap.heap[2].views);
    }

    @Test
    public void testGetTopKVideos() {
        minHeap.insert(new Video(1, "Video 1", 500));
        minHeap.insert(new Video(2, "Video 2", 1500));
        minHeap.insert(new Video(3, "Video 3", 300));
        minHeap.insert(new Video(4, "Video 4", 2000));
        minHeap.insert(new Video(5, "Video 5", 1200));

        List<Video> topVideos = Arrays.asList(minHeap.heap);
        topVideos.sort((a, b) -> Integer.compare(b.views, a.views));

        assertEquals(3, topVideos.size());
        assertEquals(2000, topVideos.get(0).views);
        assertEquals(1500, topVideos.get(1).views);
        assertEquals(1200, topVideos.get(2).views);
    }
}