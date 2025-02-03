package comcast.java.thread.sync;

class Counter {
    private int count = 0;

//    public void increment() {
//        count++;
//    }
//
//    public int getCount() {
//        return count;
//    }

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }

}
public class RaceConditionDemo {
    public static void main(String[] args) {

            Counter counter = new Counter();

            Runnable task = () -> {
                for (int i = 0; i < 100000; i++) {
                    counter.increment();
                }
            };

            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
            Thread thread3 = new Thread(task);
            Thread thread4 = new Thread(task);

            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();

            try {
                thread1.join();
                thread2.join();
                thread3.join();
                thread4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Final count: " + counter.getCount());

    }
}
