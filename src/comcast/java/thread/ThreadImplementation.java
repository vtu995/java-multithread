package comcast.java.thread;

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}

public class ThreadImplementation {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
    }
}