package comcast.java.thread.future;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ChainFutureCallable {
    public static void main(String[] args) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            // Simulate API call
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            return "First API result";
        });

        CompletableFuture<String> future2 = future1.thenCompose(result1 ->
                CompletableFuture.supplyAsync(() -> {
                    // Simulate second API call using result of first
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                    return "Second API result based on: " + result1;
                })
        );

        future2.thenAccept(result2 -> System.out.println("Final result: " + result2));

        // Keep the main thread alive to see the result
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
    }
}



//public class ThenApplyThenAcceptExample {
//    public static void main(String[] args) {
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            // Simulate API call
//            try { Thread.sleep(1000); } catch (InterruptedException e) {}
//            return "First API result";
//        });
//
//        CompletableFuture<String> processedFuture = future.thenApply(result -> {
//            // Process the result
//            return "Processed: " + result;
//        });
//
//        processedFuture.thenAccept(finalResult -> {
//            // Consume the final result
//            System.out.println("Final result: " + finalResult);
//        });
//
//        // Keep the main thread alive to see the result
//        try { Thread.sleep(2000); } catch (InterruptedException e) {}
//    }
//}