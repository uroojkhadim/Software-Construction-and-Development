package src;

/**
 * Task 5: Inter-thread communication for printer job.
 */
class PrinterTray {
    private int availablePages = 10;

    // Method to print pages (wait if not enough)
    public synchronized void printPages(int pagesToPrint) {
        System.out.println("User requests to print " + pagesToPrint + " pages. Available: " + availablePages);
        
        while (availablePages < pagesToPrint) {
            System.out.println("Insufficient pages! Waiting for tray to be refilled...");
            try {
                wait(); // Wait for refill notification
            } catch (InterruptedException e) {}
        }

        availablePages -= pagesToPrint;
        System.out.println("Printed " + pagesToPrint + " pages successfully. Remaining pages in tray: " + availablePages);
    }

    // Method to refill tray and notify waiting threads
    public synchronized void refillTray(int pagesToAdd) {
        System.out.println("Refilling printer tray with " + pagesToAdd + " pages...");
        availablePages += pagesToAdd;
        System.out.println("Tray refilled. New Available: " + availablePages);
        notify(); // Notify the waiting print thread
    }
}

public class PrinterJob {
    public static void main(String[] args) {
        PrinterTray tray = new PrinterTray();

        // Print thread: wants to print 15 pages
        Thread printThread = new Thread(() -> {
            tray.printPages(15);
        });

        // Refill thread: will add 10 pages after some time
        Thread refillThread = new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate delay in refilling
            } catch (InterruptedException e) {}
            tray.refillTray(10);
        });

        printThread.start();
        refillThread.start();
    }
}
