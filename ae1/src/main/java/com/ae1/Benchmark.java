package com.ae1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Benchmark {

    private static final long CUTOFF_TIME_NS = TimeUnit.MILLISECONDS.toNanos(10); 
    private static final Logger logger = Logger.getLogger(Benchmark.class.getName());

    public static void main(String[] args) {
        // Define datasets for benchmarking (I hope the paths work on your machine)
        String[] datasetsGroup1 = {
            Paths.get("ae1", "src", "test", "resources", "testdata", "int10.txt").toString(),
            Paths.get("ae1", "src", "test", "resources", "testdata", "int20k.txt").toString(),
            Paths.get("ae1", "src", "test", "resources", "testdata", "int50.txt").toString(),
            Paths.get("ae1", "src", "test", "resources", "testdata", "int100.txt").toString(),
            Paths.get("ae1", "src", "test", "resources", "testdata", "int500k.txt").toString(),
            Paths.get("ae1", "src", "test", "resources", "testdata", "int1000.txt").toString(),
            Paths.get("ae1", "src", "test", "resources", "testdata", "intBig.txt").toString(),
        };

        String[] datasetsGroup2 = {
            Paths.get("ae1", "src", "test", "resources", "testdata", "bad.txt").toString()
        };

        String[] datasetsGroup3 = {
            Paths.get("ae1", "src", "test", "resources", "testdata", "dutch.txt").toString()
        };

        try {
            // Benchmark and write results for each group of datasets
            benchmarkAndWriteResults(datasetsGroup1, "benchmark_results_group1.csv");
            benchmarkAndWriteResults(datasetsGroup2, "benchmark_results_group2.csv");
            benchmarkAndWriteResults(datasetsGroup3, "benchmark_results_group3.csv");
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File not found during benchmarking", e);
        }
    }

    /**
     * Benchmarks the sorting algorithms on the provided datasets and writes the results to a CSV file.
     *
     * @param datasets      Array of dataset file paths.
     * @param outputFileName Name of the output CSV file.
     * @throws FileNotFoundException if a dataset file is not found.
     */
    private static void benchmarkAndWriteResults(String[] datasets, String outputFileName) throws FileNotFoundException {
        List<Result> results = Collections.synchronizedList(new ArrayList<>());

        // Create a thread pool for concurrent execution
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (String dataset : datasets) {
            // Get the dataset path
            Path path = Paths.get(dataset);
            String datasetName = path.getFileName().toString();
            int extensionIndex = datasetName.lastIndexOf('.');
            if (extensionIndex > 0) {
                datasetName = datasetName.substring(0, extensionIndex);
            }
            logger.log(Level.INFO, "Benchmarking dataset: {0}", datasetName);

            // Check if the file exists
            File file = path.toFile();
            System.out.println("Checking file: " + file.getAbsolutePath()); // Debug statement
            if (!file.exists()) {
                logger.log(Level.SEVERE, "File not found: " + datasetName);
                continue;
            }

            int[] data;
            try {
                // Read data from the dataset file
                data = DataUtils.readArray(dataset);
            } catch (FileNotFoundException e) {
                logger.log(Level.SEVERE, "File not found: " + datasetName, e);
                continue;
            }

            // Submit tasks for each sorting algorithm 
            submitBenchmarkTask(executor, results, datasetName, data, "ThreeWayQuickSort", ThreeWayQuickSort::sort);
            submitBenchmarkTask(executor, results, datasetName, data, "QuickSort", QuickSort::sort);
            submitBenchmarkTask(executor, results, datasetName, data, "MedianOfThreeQuickSort", MedianOfThreeQuickSort::sort);
            submitBenchmarkTask(executor, results, datasetName, data, "BottomUpMergeSort", BottomUpMergeSort::sort);
            submitBenchmarkTask(executor, results, datasetName, data, "HybridInsertionQuickSort", (arr, left, right) -> HybridInsertionQuickSort.sortCutOff(arr, left, right, 2));
            submitBenchmarkTask(executor, results, datasetName, data, "HybridInsertionMergeSort", HybridInsertionMergeSort::sort);
            submitBenchmarkTask(executor, results, datasetName, data, "MergeSort", MergeSort::sort);
            submitBenchmarkTask(executor, results, datasetName, data, "InsertionSort", InsertionSort::sort);
            submitBenchmarkTask(executor, results, datasetName, data, "ShellSort", (arr, left, right) -> ShellSort.sort(arr));
            submitBenchmarkTask(executor, results, datasetName, data, "SelectionSort", (arr, left, right) -> SelectionSort.sort(arr));
        }

        executor.shutdown();
        try {
            // Wait for all tasks to complete
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "An error occurred while waiting for tasks to complete", e);
        }

        // Group results by dataset
        Map<String, Map<String, Long>> groupedResults = new HashMap<>();
        for (Result result : results) {
            groupedResults
                .computeIfAbsent(result.dataset, k -> new HashMap<>())
                .put(result.algorithmName, result.avgTime);
        }

        // Sort datasets in the desired order
        List<String> sortedDatasets = Arrays.asList("int10", "int50", "int100", "int1000", "int20k", "int500k", "intBig");

        // Write results to CSV
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {
            writer.println("Dataset,ThreeWayQuickSort,QuickSort,MedianOfThreeQuickSort,BottomUpMergeSort,HybridInsertionQuickSort,HybridInsertionMergeSort,MergeSort,InsertionSort,ShellSort,SelectionSort");
            for (String datasetName : sortedDatasets) {
                Map<String, Long> algorithmResults = groupedResults.get(datasetName);
                writer.printf("%s", datasetName);
                for (String algorithm : Arrays.asList("ThreeWayQuickSort", "QuickSort", "MedianOfThreeQuickSort", "BottomUpMergeSort", "HybridInsertionQuickSort", "HybridInsertionMergeSort", "MergeSort", "InsertionSort", "ShellSort", "SelectionSort")) {
                    writer.printf(",%d", algorithmResults != null ? algorithmResults.getOrDefault(algorithm, 0L) : 0L);
                }
                writer.println();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while writing the benchmark results", e);
        }
    }

    /**
     * Submits a benchmark task to the executor service.
     *
     * @param executor      The executor service.
     * @param results       The list to store results.
     * @param dataset       The name of the dataset.
     * @param data          The data to be sorted.
     * @param algorithmName The name of the sorting algorithm.
     * @param algorithm     The sorting algorithm.
     */
    private static void submitBenchmarkTask(ExecutorService executor, List<Result> results, String dataset, int[] data, String algorithmName, SortAlgorithm algorithm) {
        executor.submit(() -> {
            // Benchmark the sorting algorithm and add the result to the list
            long avgTime = benchmarkSort(data, algorithm);
            results.add(new Result(dataset, algorithmName, avgTime));
            logger.log(Level.INFO, "{0} completed for dataset: {1}", new Object[]{algorithmName, dataset});
        });
    }

    /**
     * Benchmarks the sorting algorithm by running it multiple times.
     *
     * @param data      The data to be sorted.
     * @param algorithm The sorting algorithm.
     * @return The average time taken to sort the data.
     */
    private static long benchmarkSort(int[] data, SortAlgorithm algorithm) {
       //  I sorted things this way so it would work in Latex
        long totalTime = 0;
        int runs = 0;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            int[] copy = Arrays.copyOf(data, data.length);
            Callable<Void> task = () -> {
                // Sort the array using the provided algorithm
                algorithm.sort(copy, 0, copy.length - 1);
                return null;
            };
            Future<Void> future = executor.submit(task);
            long startTime = System.nanoTime();
            try {
                // Measure the time taken to sort the array
                future.get(CUTOFF_TIME_NS, TimeUnit.NANOSECONDS);
                long endTime = System.nanoTime();
                long duration = endTime - startTime;
                totalTime += duration;
                runs++;
            } catch (TimeoutException e) {
                future.cancel(true);
                logger.log(Level.WARNING, "Early cutoff reached for dataset after {0} runs.", runs);
                break;
            } catch (InterruptedException | ExecutionException e) {
                logger.log(Level.SEVERE, "An error occurred during sorting", e);
            }
        }
        executor.shutdown();
        return runs > 0 ? totalTime / runs : CUTOFF_TIME_NS;
    }

    // All algorithms are implemented in separate classes and have a sort method
    // with the same signature
    @FunctionalInterface
    interface SortAlgorithm {
        void sort(int[] array, int left, int right);
    }

    private static class Result {
        String dataset;
        String algorithmName;
        long avgTime;

        Result(String dataset, String algorithmName, long avgTime) {
            this.dataset = dataset;
            this.algorithmName = algorithmName;
            this.avgTime = avgTime;
        }
    }
}