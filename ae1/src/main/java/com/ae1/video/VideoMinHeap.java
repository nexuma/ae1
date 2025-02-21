package com.ae1.video;

public class VideoMinHeap {

    Video[] heap;
    int size = 0;

    public VideoMinHeap(int k) {
        heap = new Video[k];
    }

    public void insert(Video video) {
        if (size < heap.length) {
            heap[size] = video;
            heapifyUp(size);
            size++;
        } else if (video.views > heap[0].views) {
            heap[0] = video;
            heapifyDown(0);
        }
    }

    private void heapifyDown(int index) {
        int smallest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < size && heap[left].views < heap[smallest].views) {
            smallest = left;
        }
        if (right < size && heap[right].views < heap[smallest].views) {
            smallest = right;
        }
        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    private void heapifyUp(int index) {
        int parent = (index - 1) / 2;
        while (index > 0 && heap[index].views < heap[parent].views) {
            swap(index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    private void swap(int i, int j) {
        Video temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
