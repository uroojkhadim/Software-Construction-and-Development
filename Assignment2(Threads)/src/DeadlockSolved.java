package src;

/**
 * Task 6: Solved deadlock using consistent lock ordering.
 */
public class DeadlockSolved {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        Object lock3 = new Object();

        // Solution: All threads MUST acquire locks in the same order (e.g., lock1 -> lock2 -> lock3)
        
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Holding lock 1...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                System.out.println("Thread 1: Waiting for lock 2...");
                synchronized (lock2) {
                    System.out.println("Thread 1: Holding lock 1 & 2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2: Holding lock 2...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                System.out.println("Thread 2: Waiting for lock 3...");
                synchronized (lock3) {
                    System.out.println("Thread 2: Holding lock 2 & 3");
                }
            }
        });

        Thread t3 = new Thread(() -> {
            // FIXED: Acquire lock1 BEFORE lock3 to maintain order (lock1 -> lock3)
            synchronized (lock1) {
                System.out.println("Thread 3: Holding lock 1...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                System.out.println("Thread 3: Waiting for lock 3...");
                synchronized (lock3) {
                    System.out.println("Thread 3: Holding lock 1 & 3");
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
