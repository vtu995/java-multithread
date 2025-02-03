package comcast.java.thread.lock;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//class SharedResource {
//    public synchronized void accessResource() {
//        System.out.println(Thread.currentThread().getName() + " accessed the resource");
//    }
//}
class SharedResource {
    private final Lock lock = new ReentrantLock();

    public void accessResource() {
        lock.lock(); // Acquire the lock
        try {
            System.out.println(Thread.currentThread().getName() + " is accessing the resource.");
            Thread.sleep(1000); // Simulate work
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); // Always release the lock
        }
    }
}
public class LockDemo {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Runnable task = () -> {
            for (int i = 0; i < 2; i++) {
                resource.accessResource();
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();
    }
}
