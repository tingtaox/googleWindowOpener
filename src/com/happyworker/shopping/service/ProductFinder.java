package com.happyworker.shopping.service;

import static com.happyworker.shopping.BrowserApp.FINDER_JUMP_LENGTH;
import com.happyworker.shopping.model.OrderTarget;
import com.happyworker.shopping.util.PageLoadWaiting;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ProductFinder {

    /**
     * Select target by clicking through and check title.
     * If a title contains given key words, pick it.
     */
    public List<OrderTarget> findTargets(final List<List<String>> keyWords) {

        List<OrderTarget> targets = new ArrayList<>();

        WebDriver driver = new ChromeDriver();
        driver.get("http://www.supremenewyork.com/shop/new");
        PageLoadWaiting.waitPageLoadById(driver, "container");

        // New release list page, find list of elements to buy and click to each product page.
        WebElement shopNewCart = driver.findElement(By.id("container"));
        List<WebElement> releaseElements = shopNewCart.findElements(By.tagName("a"));
        System.out.println("Number of new release found: " + releaseElements.size());

        List<String> releaseLinks = new ArrayList<>();
        for (WebElement element : releaseElements) {
            releaseLinks.add(element.getAttribute("href"));
        }

        for (int i = releaseLinks.size() - 1; i >= 0; i -= FINDER_JUMP_LENGTH) {

            driver.navigate().to(releaseLinks.get(i));
            PageLoadWaiting.waitPageLoadById(driver, "details");
            WebElement detailsEl = driver.findElement(By.id("details"));
            String title = detailsEl.findElement(By.cssSelector("h1")).getText();

            if (isRight(keyWords, title)) {
                System.out.println("steve test: " + releaseLinks.get(i));
                targets.add(OrderTarget.builder().url(releaseLinks.get(i)).build());
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

//    private List<WebElement> selectToBuy(List<WebElement> newReleases) {
//        List<WebElement> toBuyElements = new ArrayList<WebElement>();
//        for (WebElement element : newReleases) {
//            if (ifWorthBuy(element)) {
//                toBuyElements.add(element);
//            }
//        }
//
//        return toBuyElements;
//    }
//
//    private boolean ifWorthBuy(WebElement newReleases) {
//        // TODO: 10/8/17 Fill to buy algorithm, can simply filter by name
//        return true;
//    }

}
