package comcast.java.thread;

public class ThreadLifecycleDemo {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println("*** InSide Thread state: " + Thread.currentThread().getState()); // NEW
            synchronized (lock) {
                try {
                    System.out.println("*** InSide Thread started");
                    Thread.sleep(5000   ); // RUNNABLE
                    System.out.println("*** InSide Thread state: " + Thread.currentThread().getState()); // RUNNABLE
                    lock.wait(100); // WAITING
                    System.out.println("*** InSide Thread resumed");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("*** InSide Thread ended");
        });

        System.out.println("Thread state: " + t.getState()); // NEW
        t.start();
        System.out.println("Thread state: " + t.getState()); // RUNNABLE

        try {
            Thread.sleep(100); // Ensure the thread is running
            synchronized (lock) {
                System.out.println("TM Thread state: " + t.getState()); // BLOCKED
//                lock.notify();
            }
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread state: " + t.getState()); // TERMINATED
    }
}