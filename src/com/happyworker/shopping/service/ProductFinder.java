package com.happyworker.shopping.service;

import com.happyworker.shopping.model.OrderTarget;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProductFinder {
    String SUPREME_HOME_URL = "http://www.supremenewyork.com";

    public List<OrderTarget> findTargetsWithRetry(final List<List<String>> keyWords, int retryNum) {

        try {
            return findTargets(keyWords);
        } catch (Exception e) {
            --retryNum;
            System.out.println("Last find targets failed, retry left: " + retryNum);
            if (retryNum > 0) {
                try {
                    Thread.sleep(new Random().nextInt(3)*1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return findTargetsWithRetry(keyWords, retryNum);
            } else {
                System.out.println("Failed after all retry.");
                return new ArrayList<>();
            }
        }

    }

        /**
         * Select target by clicking through and check title.
         * If a title contains given key words, pick it.
         */
    public List<OrderTarget> findTargets(final List<List<String>> keyWords) throws IOException {
        List<OrderTarget> targets = new ArrayList<>();

        //Get link list for new released items
        Document newReleasePage = Jsoup
                .connect(SUPREME_HOME_URL + "/shop/new")
                .timeout(3000)
                .get();
        Element shopNewCart = newReleasePage.getElementById("container");
        Elements releaseElements = shopNewCart.getElementsByTag("a");
        System.out.println("Number of new release found: " + releaseElements.size());

        List<String> releaseLinks = new ArrayList<>();
        for (Element element : releaseElements) {
            releaseLinks.add(SUPREME_HOME_URL + element.attr("href"));
        }

        // Check title of each link, check title from the link, find matched links and add them to results
        for (int i = releaseLinks.size() - 1; i >= 0; ) {
            Document productPage = Jsoup.connect(releaseLinks.get(i)).timeout(3000).get();
            String title =  productPage.select("#details h1").text();

            if (isRight(keyWords, title)) {
                System.out.println("steve test, selected link: " + releaseLinks.get(i));
                targets.add(OrderTarget.builder().url(releaseLinks.get(i)).build());
                i--;
            } else {
                i -= productPage.select("#details ul.styles li").size();
            }

        }

        return targets;


    }

    /**
     * Check if a title meet requirement: match any of given key-list.
     */
    private boolean isRight(List<List<String>> titleKeys, String title) {
        for (int i = titleKeys.size() - 1; i >= 0; --i) {
            // if found title match one of the given key list, return true and end.
            if (doesMatch(titleKeys.get(i), title)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Given keys list, check if a title matches.
     * If in a list, any word is not contained, the list failed to match.
     */
    private boolean doesMatch(List<String> keys, String title) {
        for (String key : keys) {
            if (!title.contains(key)) {
                return false;
            }
        }
        return true;
    }

}
