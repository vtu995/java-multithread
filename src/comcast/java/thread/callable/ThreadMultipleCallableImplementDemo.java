package comcast.java.thread.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import java.util.concurrent.Callable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;


class ApiCallable implements Callable<String> {
    private final String apiUrl;

    public ApiCallable(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public String call() throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        return content.toString();
    }
}

public class ThreadMultipleCallableImplementDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = new ArrayList<>();

        String[] apiUrls = {
                "https://official-joke-api.appspot.com/random_joke",
                "http://universities.hipolabs.com/search?country=United+States",
                "https://api.restful-api.dev/objects/7"
        };

        for (String apiUrl : apiUrls) {
            ApiCallable apiCallable = new ApiCallable(apiUrl);
            Future<String> future = executorService.submit(apiCallable); // Submit Callable to Executor
            futures.add(future);
        }

        for (Future<String> future : futures) {
            try {
                String result = future.get(); // Blocks until the task is complete
                System.out.println("API Response: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
    }
}




