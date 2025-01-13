package benchTest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Bench {

	// Main method
	public static void main(String[] args) {
		// Sizes of arrays to test
		int[] sizes = { 100, 250, 500, 700, 1000, 1250, 5000, 7000, 9000 };
		// 10 trials
		int trials = 10;
		// Formatter set to .000
		DecimalFormat df = new DecimalFormat("0.000");

		// Table header with size columns
		System.out.printf("%-16s", "Size");
		for (int size : sizes) {
			System.out.printf("%10s", size);
		}
		System.out.println();

		// Iterate over each algorithm to test
		for (String sortType : new String[] { "Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort",
				"Bucket Sort" }) {
			System.out.print(String.format("%-16s", sortType));
			// Benchmark each size for the current sorting algorithm
			for (int size : sizes) {
				double avgTime = benchmark(size, trials, sortType);
				System.out.printf("%10s", df.format(avgTime) + " ms");
			}
			System.out.println();
		}
	}

	/*
	 * The following Benchmarking function is adapted code from week 10 
	 * Source: https://vlegalwaymayo.atu.ie/pluginfile.php/1183960/mod_resource/content/0/Main.java 
	 * It contains the random array generator, the use of cloning for repetition  
	 * I kept everything in one class to suit the test style rather than make multiple classes for each algorithm
	 */

	// Benchmark function
	static double benchmark(int n, int reps, String sortType) {
		double totalTime = 0;
		// Generate the array once per size
		int[] original = randomArray(n);

		for (int i = 0; i < reps; i++) {
			// Clone the original array
			int[] arr = original.clone();
			long startTime = System.nanoTime();

			// Select the sorting algorithm based on the sortType
			if ("Bubble Sort".equals(sortType)) {
				bubbleSort(arr);
			} else if ("Selection Sort".equals(sortType)) {
				selectionSort(arr);
			} else if ("Insertion Sort".equals(sortType)) {
				insertionSort(arr);
			} else if ("Quick Sort".equals(sortType)) {
				quickSort(arr, 0, arr.length - 1);
			} else if ("Bucket Sort".equals(sortType)) {
				bucketSort(arr);
			}
			// Calculate the time taken to sort the array
			long endTime = System.nanoTime();
			// Convert nanoseconds to milliseconds
			totalTime += (endTime - startTime) / 1_000_000.0;
		}
		// Return the average time
		return totalTime / reps;
	}

	// Generate an array of random integers
	static int[] randomArray(int n) {
		int[] array = new int[n];
		for (int i = 0; i < n; i++) {
			array[i] = (int) (Math.random() * 100);
		}
		return array;
	}

	/*
	 * Bubble Sort Code is altered with clearer names and my own comments Source:
	 * https://www.geeksforgeeks.org/bubble-sort/?ref=outind
	 */

	static void bubbleSort(int[] array) {
		int length = array.length;
		for (int pass = 0; pass < length - 1; pass++) {
			// Assume array is sorted
			boolean isSorted = true;
			for (int index = 0; index < length - pass - 1; index++) {
				// Compare adjacent elements
				if (array[index] > array[index + 1]) {
					// Swap elements
					int temp = array[index];
					array[index] = array[index + 1];
					array[index + 1] = temp;
					// Mark as not sorted
					isSorted = false;
				}
			}
			// If no swaps then the array is sorted
			if (isSorted) {
				break;
			}
		}
	}

	/*
	 * Insertion Sort Code altered with clearer names and my own comments Source:
	 * https://www.geeksforgeeks.org/insertion-sort/?ref=outind
	 */

	static void insertionSort(int[] array) {
		int length = array.length;
		// Begin from the second element
		for (int current = 1; current < length; current++) {
			// The value to be inserted into the sorted section
			int currentValue = array[current];
			// Find the position to start comparing backwards
			int position = current - 1;

			// Shift elements in the sorted part of the array greater than currentValue
			while (position >= 0 && array[position] > currentValue) {
				// Move to the right
				array[position + 1] = array[position];
				// Move to the next left position
				position--;
			}
			// Insert at correct position
			array[position + 1] = currentValue;
		}
	}

	/*
	 * Selection Sort Code is altered to have clearer names and my own comments
	 * Source: https://www.geeksforgeeks.org/selection-sort/?ref=outind
	 */

	static void selectionSort(int[] array) {
		// Determine number of elements in the array
		int length = array.length;
		// Iterate over each element except last one
		for (int currentIndex = 0; currentIndex < length - 1; currentIndex++) {
			// Assume the current index holds the smallest value
			int minIndex = currentIndex;

			// Search for a smaller value than what's at currentIndex in the remainder of
			// the array
			for (int compareIndex = currentIndex + 1; compareIndex < length; compareIndex++) {
				// Found a new min value
				if (array[compareIndex] < array[minIndex]) {
					// Update the index of the smallest value
					minIndex = compareIndex;
				}
			}
			// Swap the smallest found value with the value at currentIndex
			int temp = array[minIndex];
			array[minIndex] = array[currentIndex];
			array[currentIndex] = temp;
		}
	}

	/*
	 * Quick Sort Code is altered with clearer names and my own comments Source:
	 * https://www.geeksforgeeks.org/quick-sort/?ref=outind altered
	 */

	static void quickSort(int[] array, int start, int end) {
		// Only proceed if it has more than one element
		if (start < end) {
			// Partition the array and get the pivot's final position
			int pivotIndex = partition(array, start, end);
			// Recursively sort the sub-array before the pivot
			quickSort(array, start, pivotIndex - 1);
			// Recursively sort the sub-array after the pivot
			quickSort(array, pivotIndex + 1, end);
		}
	}

	// Method partitions the array segment and returns the index of the pivot element
	static int partition(int[] array, int low, int high) {
		// The rightmost element is chosen as the pivot
		int pivot = array[high];
		// Initialise pointer for placing smaller elements
		int i = low - 1;

		for (int j = low; j < high; j++) {
			// If current element is smaller than the pivot
			if (array[j] < pivot) {
				// Move the smaller element index forward
				i++;
				// Swap the current element with the element at the smaller element index
				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
		// Place the pivot element at the correct position (in sorted order)
		int temp = array[i + 1];
		array[i + 1] = array[high];
		array[high] = temp;
		// Return the index of the pivot element
		return i + 1;
	}

	/*
	 * Bucket Sort 
	 * I chose bucket sort to add some complexity by requiring insertion sort within it 
	 * Using the code source, I altered the code so bucket indexing suits integers 
	 * I also removed collections.sort and instead called on the insertion algorithm 
	 * Source: https://www.geeksforgeeks.org/bucket-sort-2/?ref=shm
	 */

	static void bucketSort(int[] arr) {
		// Get the length of the input array
		int n = arr.length;

		// Find max value in the array to help scale the bucket indices
		int maxVal = getMaxValue(arr);

		// Create a list of buckets where each bucket is a list of integers
		List<List<Integer>> buckets = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			// Initialise each bucket as an empty list
			buckets.add(new ArrayList<>());
		}

		// Distributing elements of the input array into appropriate buckets
		for (int value : arr) {
			// Calculate the index of the bucket for the current element
			int bucketIndex = (value * n) / (maxVal + 1);
			// Add the current element to its corresponding bucket
			buckets.get(bucketIndex).add(value);
		}

		// Sort the elements in each bucket using insertion sort
		// Keep track of the index in the original array
		int index = 0; 
		for (List<Integer> bucket : buckets) {
			if (!bucket.isEmpty()) {
				// Convert List<Integer> to int[] for insertion sort
				int[] bucketArray = bucket.stream().mapToInt(i -> i).toArray();
				insertionSort(bucketArray);
				// Place sorted elements back into the original array
				for (int value : bucketArray) {
					arr[index++] = value;
				}
			}
		}
	}

	// Insertion sort (comments are above)
	static void insertionSort1(int[] array) {
		int length = array.length;
		for (int current = 1; current < length; current++) {
			int currentValue = array[current];
			int position = current - 1;
			while (position >= 0 && array[position] > currentValue) {
				array[position + 1] = array[position];
				position--;
			}
			array[position + 1] = currentValue;
		}
	}

	// Method to find the maximum value in array of integers
	static int getMaxValue(int[] arr) {
		// Initialise `max` with smallest possible integer value
	    int max = Integer.MIN_VALUE;  
	    // Iterate over each element in array
	    for (int value : arr) {
	    	// If current element is greater than the current maximum, update max
	        if (value > max) { 
	            max = value;  
	        }
	    }
	 // Return highest value found in the array
	    return max;  
	}
}

