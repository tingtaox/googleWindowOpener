package com.happyworker.shopping.service;

import com.happyworker.shopping.model.OrderTarget;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductFinderTest {

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/zhixianb/workspace/files/chromedriver");
    }

    @Test
    void findTargets() {
        ProductFinder productFinder = new ProductFinder();

        List<List<String>> keyWords = Arrays.asList(
                Arrays.asList("New Era", "Reflective", "Headband"),
                Arrays.asList("Arabic", "Hooded ", "Sweatshirt")
        );
        System.out.println(Arrays.deepToString(keyWords.toArray()));

        List<OrderTarget> targets = productFinder.findTargets(keyWords);
        for (OrderTarget target: targets) {
            System.out.println(target.getUrl());
        }

        System.out.println("test ended.");

    }

}
