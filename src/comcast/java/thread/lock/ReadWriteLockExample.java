package comcast.java.thread.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

class SharedResource1 {
    private int data = 0;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // Multiple threads can read simultaneously
    public void getData() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reading data: " + data);
        } finally {
            lock.readLock().unlock();
        }
    }

    // Only one thread can write at a time
    public void setData(int value) {
        lock.writeLock().lock();
        try {
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getName() + " writing data: " + value);
            this.data = value;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeLock().unlock();
        }
    }
}

public class ReadWriteLockExample {
    public static void main(String[] args) {
        SharedResource1 resource = new SharedResource1();

        Runnable readTask = () -> resource.getData();
        Runnable writeTask = () -> resource.setData(100);

        Thread t1 = new Thread(readTask, "Reader-1");
        Thread t2 = new Thread(readTask, "Reader-2");
        Thread t4 = new Thread(readTask, "Reader-3");
        Thread t3 = new Thread(writeTask, "Writer-1");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
