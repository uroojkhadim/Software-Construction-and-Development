package src;

/**
 * Task 6: Demonstrate deadlock using 3 locks and 3 threads.
 */
public class DeadlockDemo {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        Object lock3 = new Object();

        // Thread 1: Locks 1, then 2
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

        // Thread 2: Locks 2, then 3
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

        // Thread 3: Locks 3, then 1 (Circular dependency -> Deadlock!)
        Thread t3 = new Thread(() -> {
            synchronized (lock3) {
                System.out.println("Thread 3: Holding lock 3...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                System.out.println("Thread 3: Waiting for lock 1...");
                synchronized (lock1) {
                    System.out.println("Thread 3: Holding lock 3 & 1");
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
