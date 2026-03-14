package src;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Task 1: Demonstration of threads and tasks.
 */
public class Task1Demo {
    public static void main(String[] args) {
        // Creating a thread pool with 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submitting 5 tasks to the thread pool
        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("Executing Task " + taskId + " on Thread: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate task execution
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }
}
