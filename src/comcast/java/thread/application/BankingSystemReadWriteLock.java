package comcast.java.thread.application;

import java.util.concurrent.locks.ReentrantReadWriteLock;

class BankAccount {
    private double balance;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void checkBalance(String user) {
        lock.readLock().lock(); // Multiple readers can access simultaneously
        try {
            System.out.println(user + " is checking balance: $" + balance);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void withdraw(String user, double amount) {
        lock.writeLock().lock(); // Only one writer at a time
        try {
            if (balance >= amount) {
                System.out.println(user + " is withdrawing: $" + amount);
                Thread.sleep(1000);
                balance -= amount;
                System.out.println(user + " completed withdrawal. New balance: $" + balance);
            } else {
                System.out.println(user + " attempted to withdraw $" + amount + " but insufficient funds.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}

public class BankingSystemReadWriteLock {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(5000);

        Runnable checkTask = () -> account.checkBalance(Thread.currentThread().getName());
        Runnable withdrawTask = () -> account.withdraw(Thread.currentThread().getName(), 2000);

        Thread t1 = new Thread(checkTask, "User1");
        Thread t2 = new Thread(checkTask, "User2");
        Thread t3 = new Thread(checkTask, "User3");
        Thread t4 = new Thread(withdrawTask, "User4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
