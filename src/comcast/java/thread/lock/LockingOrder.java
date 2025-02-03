package comcast.java.thread.lock;

class Resource {
    void methodA(Resource other) {
        Resource first = this.hashCode() < other.hashCode() ? this : other;
        Resource second = this.hashCode() < other.hashCode() ? other : this;

        synchronized (first) {
            System.out.println(Thread.currentThread().getName() + " locked " + first);
            synchronized (second) {
                System.out.println(Thread.currentThread().getName() + " locked " + second);
            }
        }
    }
}
public class LockingOrder {
}
