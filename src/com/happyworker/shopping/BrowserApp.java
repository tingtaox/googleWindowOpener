package com.happyworker.shopping;

import com.happyworker.shopping.model.OrderTarget;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BrowserApp {

    private final static int MAX_WORKING_WINDOW = 10;
    private final static int NUMBER_OF_ORDER_ATTEMPT = 5;

    public static void main(String[] args) {
        // download chromedriver and update the path to the chromedriver
        System.setProperty("webdriver.chrome.driver", "/Users/zhixianb/workspace/files/chromedriver");

        List<OrderTarget> targets = new ArrayList<>();
        // TODO: 10/18/17 past url and size here.
        targets.add(OrderTarget.builder()
                .url("http://www.supremenewyork.com/shop/jackets/izbgqk5od/jd3no7sar?alt=1")
                .productSize("Large")
                .build());
        targets.add(OrderTarget.builder()
                .url("http://www.supremenewyork.com/shop/hats/e5ftzpd3r/ba5hj0kyx")
                .build());

        ExecutorService executor = Executors.newFixedThreadPool(MAX_WORKING_WINDOW);
        final SupermanShopping superman = new SupermanShopping();

        for (int i = NUMBER_OF_ORDER_ATTEMPT; i > 0; --i) {
            int windowNum = i;
            for (OrderTarget target : targets) {
                Runnable shopper = () -> {
                    superman.openWindow(target, windowNum);
                };
                executor.execute(shopper);
            }

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
