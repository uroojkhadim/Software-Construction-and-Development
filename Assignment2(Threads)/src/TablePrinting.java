package src;

/**
 * Task 2: Print two tables concurrently.
 */
public class TablePrinting {
    public static void main(String[] args) {
        // Thread for Roll Number table
        Thread rollThread = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("2020-SE-092 x " + i + " = " + (i * 92)); // Simple math example
                try { Thread.sleep(500); } catch (InterruptedException e) { }
            }
        });

        // Thread for Date of Birth table
        Thread dobThread = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("05-April Iteration: " + i);
                try { Thread.sleep(700); } catch (InterruptedException e) { }
            }
        });

        rollThread.start();
        dobThread.start();
    }
}
