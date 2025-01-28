BenchTest Sorting Algorithm Performance Benchmarking

Part of Computational Thinking with Algorithms module for the Higher Diploma in Science – Computing (Software Development). 
This project is a Java-based benchmarking tool designed to evaluate the performance of five different sorting algorithms on arrays of varying sizes.
The results provide insights into the relative efficiency of the algorithms under different scenarios.

Features

Supported Sorting Algorithms
Bubble Sort - A simple comparison-based sorting algorithm.
Selection Sort - Finds the minimum element and places it in the sorted portion.
Insertion Sort - Builds the sorted array one element at a time.
Quick Sort - A divide-and-conquer algorithm using partitioning.
Bucket Sort - A distribution-based sorting algorithm with insertion sort for internal sorting.

Benchmarking
Measures the average execution time (in milliseconds) for each sorting algorithm.
Benchmarks are run over a fixed number of trials (default: 10).
Array sizes tested: 100, 250, 500, 700, 1000, 1250, 5000, 7000, 9000.

Output
Displays results in a tabular format, with the algorithm names as rows and array sizes as columns.


How It Works

Random Array Generation:
Arrays of integers are generated with random values between 0 and 99.

Cloning for Repetition:
Each test uses a clone of the original array to ensure consistency across trials.

Timing:
The execution time for each sorting algorithm is measured using System.nanoTime().



Code Highlights
Bubble Sort:
Optimized with an isSorted flag to reduce unnecessary passes.

Quick Sort:
Uses the last element as the pivot and recursive partitioning for efficiency.

Bucket Sort:
Distributes elements into buckets based on a scaling factor and uses Insertion Sort for sorting within buckets.

Benchmarking Function:
Implements cloning to ensure the same input array for each trial.
