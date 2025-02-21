package com.ae1.video;

import java.util.Arrays;
import java.util.List;

public class TopKVideos {

    private static final int K = 5;

    public static List<Video> findTopKVideos(List<Video> videos, int k) {
        VideoMinHeap minHeap = new VideoMinHeap(k);

        for (Video video : videos) {
            minHeap.insert(video);
        }

        List<Video> result = Arrays.asList(minHeap.heap);
        result.sort((a, b) -> Integer.compare(b.views, a.views));

        return result;
    }

    public static void main(String[] args) {
        List<Video> videos = Arrays.asList(
            new Video(1, "Introduction to Java", 500),
            new Video(2, "Advanced Java Programming", 1500),
            new Video(3, "Java Data Structures", 300),
            new Video(4, "Java Design Patterns", 2000),
            new Video(5, "Spring Framework Basics", 1200),
            new Video(6, "Hibernate ORM Tutorial", 700),
            new Video(7, "Microservices with Spring Boot", 4000),
            new Video(8, "Java Concurrency", 900)
        );

        List<Video> topVideos = findTopKVideos(videos, K);

        System.out.println("Top " + K + " most viewed videos:");
        for (Video video : topVideos) {
            System.out.println("Video ID: " + video.id + ", Title: " + video.title + ", Views: " + video.views);
        }
    }
}