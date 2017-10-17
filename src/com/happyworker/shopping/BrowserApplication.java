package com.happyworker.shopping;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BrowserApplication {

    private final static int MAX_WORKING_WINDOW = 10;
    private final static int NUMBER_OF_ORDER_ATTEMPT = 5;

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(MAX_WORKING_WINDOW);
        final SupermanWindow superman = new SupermanWindow();

        for (int i = NUMBER_OF_ORDER_ATTEMPT; i > 0; --i) {
            Runnable shopper = () -> {
                try {
                    superman.openWindow();
                } catch (InterruptedException e) {
                    System.out.println("Failed getting order.");
                }
            };

            executor.execute(shopper);

            // Wait sometime before next window
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Continue finishing previous jobs, no new job will be taken
        executor.shutdown();
    }
}
