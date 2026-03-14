package src;

import java.util.Random;

/**
 * Task 3: Print English alphabets (A-Z) using random characters and sleep.
 */
public class AlphabetPrinting implements Runnable {
    private volatile boolean running = true;

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 26 && running; i++) {
            // Getting a random character from A to Z
            char randomChar = (char) ('A' + random.nextInt(26));
            System.out.println("Alphabet: " + randomChar);
            
            try {
                // Fluctuating visualization through sleep (between 100 to 500 ms)
                Thread.sleep(100 + random.nextInt(400));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopTask() {
        running = false;
    }

    public static void main(String[] args) {
        AlphabetPrinting alphabetTask = new AlphabetPrinting();
        Thread thread = new Thread(alphabetTask);
        
        System.out.println("Starting Alphabet Printing Thread...");
        thread.start();

        // Let it run for a while, then demonstrate stop mechanism
        try {
            Thread.sleep(5000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Stopping the task...");
        alphabetTask.stopTask();
    }
}
