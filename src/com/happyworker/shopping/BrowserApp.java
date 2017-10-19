package com.happyworker.shopping;

import com.happyworker.shopping.model.OrderTarget;
import com.happyworker.shopping.service.SupermanShopping;
import com.happyworker.shopping.service.TargetLocator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BrowserApp {

    private final static int MAX_WORKING_WINDOW = 10;
    private final static int NUMBER_OF_ORDER_ATTEMPT = 10;

    public static void main(String[] args) {
        // download chromedriver and update the path to the chromedriver
        System.setProperty("webdriver.chrome.driver", "/Users/zhixianb/workspace/files/chromedriver");

        List<OrderTarget> targets = TargetLocator.findTargets();

        shopping(targets);
    }

    private static void shopping(List<OrderTarget> targets) {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_WORKING_WINDOW);
        final SupermanShopping superman = new SupermanShopping();

        for (int i = NUMBER_OF_ORDER_ATTEMPT; i > 0; --i) {
            int windowNum = i;
            for (OrderTarget target : targets) {
                Runnable shopper = () -> {
                    superman.openWindow(target, windowNum);
                };
                executor.execute(shopper);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            // Wait sometime before next window
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        // Continue finishing previous jobs, no new job will be taken
        executor.shutdown();
    }
}
