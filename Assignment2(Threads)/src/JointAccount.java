package src;

/**
 * Task 4: Simple concurrency with synchronized joint bank account.
 */
class Account {
    private int balance = 50000;

    // Synchronized method to handle atomic withdrawal
    public synchronized void withdraw(String name, int amount) {
        System.out.println(name + " attempting to withdraw " + amount);
        if (balance >= amount) {
            System.out.println(name + " checking balance... Balance sufficient: " + balance);
            try {
                Thread.sleep(1000); // Simulate processing time
            } catch (InterruptedException e) {}
            balance -= amount;
            System.out.println(name + " completed withdrawal. New Balance: " + balance);
        } else {
            System.out.println(name + " attempted to withdraw " + amount + " but balance is insufficient: " + balance);
        }
    }
}

public class JointAccount {
    public static void main(String[] args) {
        Account jointAccount = new Account();

        // User A thread
        Thread userA = new Thread(() -> {
            jointAccount.withdraw("User A", 45000);
        });

        // User B thread
        Thread userB = new Thread(() -> {
            jointAccount.withdraw("User B", 20000);
        });

        userA.start();
        userB.start();
    }
}
