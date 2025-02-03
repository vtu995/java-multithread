package comcast.java.thread.lock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

class SafeTask {
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    void methodA() {
        try {
            if (lock1.tryLock(1, TimeUnit.SECONDS)) {  // Try to acquire Lock1
                System.out.println(Thread.currentThread().getName() + " locked lock1");
                Thread.sleep(50);

                if (lock2.tryLock(1, TimeUnit.SECONDS)) {  // Try to acquire Lock2
                    try {
                        System.out.println(Thread.currentThread().getName() + " locked lock2");
                    } finally {
                        lock2.unlock();
                    }
                }
                lock1.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }
}

public class TryLockExample {
    public static void main(String[] args) {
        SafeTask task = new SafeTask();

        Thread t1 = new Thread(task::methodA, "Thread-1");
        Thread t2 = new Thread(task::methodA, "Thread-2");

        t1.start();
        t2.start();
    }
}
