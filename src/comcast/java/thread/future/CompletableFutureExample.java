package comcast.java.thread.future;

import java.util.concurrent.*;

public class CompletableFutureExample {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            return "Hello from CompletableFuture!";
        });

        System.out.println("Task started, doing other work...");

        // Non-blocking check
        future.thenAccept(result -> System.out.println("Result: " + result));
        System.out.println("Doing other work..."); //Main thread is free while task runs asynchronously!
        Thread.sleep(3000);  // Wait for task completion
    }
}
