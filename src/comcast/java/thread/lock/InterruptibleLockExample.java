package comcast.java.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

class TaskWithInterrupt implements Runnable {
    private final ReentrantLock lock;

    public TaskWithInterrupt(ReentrantLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " trying to acquire lock...");
        try {
            lock.lockInterruptibly();  // 🔹 Allows interruption while waiting
            System.out.println(Thread.currentThread().getName() + " got the lock.");
            Thread.sleep(5000); // Simulate work
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " was interrupted while waiting.");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}

public class InterruptibleLockExample {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread(new TaskWithInterrupt(lock), "Thread-1");
        Thread t2 = new Thread(new TaskWithInterrupt(lock), "Thread-2");

        t1.start();
        Thread.sleep(100);  // Ensure Thread-1 gets the lock first
        t2.start();

        Thread.sleep(1000);
        System.out.println("Interrupting Thread-2...");
        t2.interrupt();  // 🔹 Interrupt Thread-2 while it is waiting for the lock
    }
}
