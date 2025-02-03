package comcast.java.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class TaskWithTimeout {
    private final ReentrantLock lock = new ReentrantLock();

    void doWork() {
        try {
            if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {  // 🔹 Try for 500ms
                try {
                    System.out.println(Thread.currentThread().getName() + " acquired lock");
                    Thread.sleep(1000);
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " could not acquire lock in time");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " was interrupted");
        }
    }
}

public class TimeoutLockExample {
    public static void main(String[] args) {
        TaskWithTimeout task = new TaskWithTimeout();

        Thread t1 = new Thread(task::doWork, "Thread-1");
        Thread t2 = new Thread(task::doWork, "Thread-2");

        t1.start();
        t2.start();
    }
}
