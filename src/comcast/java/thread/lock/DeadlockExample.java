package comcast.java.thread.lock;

//class Resource2 {
//    void methodA(Resource2 other) {
//        synchronized (this) {  // Acquires first lock
//            System.out.println(Thread.currentThread().getName() + " locked " + this);
//            try { Thread.sleep(100); } catch (InterruptedException e) {}
//
//            synchronized (other) {  // Tries to acquire second lock
//                System.out.println(Thread.currentThread().getName() + " locked " + other);
//            }
//        }
//    }
//}
class Resource2 {
    void methodA(Resource2 other) {
        Resource2 first = this.hashCode() < other.hashCode() ? this : other;
        Resource2 second = this.hashCode() < other.hashCode() ? other : this;

        synchronized (first) {
            System.out.println(Thread.currentThread().getName() + " locked " + first);
            synchronized (second) {
                System.out.println(Thread.currentThread().getName() + " locked " + second);
            }
        }
    }
}

public class DeadlockExample {
    public static void main(String[] args) {
        Resource2 r1 = new Resource2();
        Resource2 r2 = new Resource2();

        Thread t1 = new Thread(() -> r1.methodA(r2), "Thread-1");
        Thread t2 = new Thread(() -> r2.methodA(r1), "Thread-2");

        t1.start();
        t2.start();
    }
}
