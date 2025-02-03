package comcast.java.thread.lock;


class SharedResourceData {
    private int data = 0;

    // Synchronized read (blocks all other threads)
    public synchronized int getData() {
        System.out.println(Thread.currentThread().getName() + " reading data: " + data);
        return data;
    }

    // Synchronized write (also blocks all threads)
    public synchronized void setData(int value) {
        System.out.println(Thread.currentThread().getName() + " writing data: " + value);
        this.data = value;
    }
}

public class MonitorLockSync {
    public static void main(String[] args) {
        SharedResourceData resource = new SharedResourceData();

        Runnable readTask = () -> resource.getData();
        Runnable writeTask = () -> resource.setData(100);

        Thread t1 = new Thread(readTask, "Reader-1");
        Thread t2 = new Thread(readTask, "Reader-2");
        Thread t3 = new Thread(writeTask, "Writer-1");

        t1.start();
        t2.start();
        t3.start();
    }
}
