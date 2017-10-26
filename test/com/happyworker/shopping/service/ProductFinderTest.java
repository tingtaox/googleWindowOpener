package com.happyworker.shopping.service;

import com.happyworker.shopping.model.OrderTarget;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class ProductFinderTest {

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/zhixianb/workspace/files/chromedriver");
    }

    @Test
    public void findTargetsWithRetrySuccess() throws Exception {
        ProductFinder productFinder = new ProductFinder();
        List<List<String>> keyWords = Arrays.asList(
                Arrays.asList("New Era", "Reflective", "Headband")
        );

        assertFalse(productFinder.findTargetsWithRetry(keyWords, 3).isEmpty());
    }


    @Test
    public void findTargetsWithRetryInvalidUrlFail() throws Exception {
        ProductFinder productFinder = new ProductFinder();
        productFinder.SUPREME_HOME_URL = "not valid url random string ";
        List<List<String>> keyWords = Arrays.asList(
                Arrays.asList("New Era", "Reflective", "Headband")
        );

        assertEquals(0, productFinder.findTargetsWithRetry(keyWords, 3).size());
    }


    @Test
    public void findTargetsSuccess() throws IOException {
        ProductFinder productFinder = new ProductFinder();

        List<List<String>> keyWords = Arrays.asList(
                Arrays.asList("New Era", "Reflective", "Headband"),
                Arrays.asList("Heather Stripe Beanie")
//                Arrays.asList("Arabic", "Hooded ", "Sweatshirt")
        );
        System.out.println(Arrays.deepToString(keyWords.toArray()));

        List<OrderTarget> targets = productFinder.findTargets(keyWords);

        System.out.println("test ended.");

    }

    @Test
    public void test_product_item_number() throws Exception {
        Document document = Jsoup
                .connect("http://www.supremenewyork.com/shop/sweatshirts/w01un8x59/zrkyos4uv")
                .timeout(3000)
                .get();

        System.out.println(document.select("#details h1"));

        Elements elements = document.select("#details ul.styles li");

        assertFalse(elements.isEmpty());

        System.out.println("Item number: " + elements.size());
        System.out.println(elements);

    }
}
