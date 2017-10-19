package com.happyworker.shopping.service;

import com.happyworker.shopping.model.OrderTarget;
import java.util.ArrayList;
import java.util.List;

public class TargetLocator {


    public static List<OrderTarget> findTargets() {
        List<OrderTarget> targets = new ArrayList<>();

        // todo Create logic here!!!!

        return targets;


        //            driver.get("http://www.supremenewyork.com/shop/new");
//
//            // New release list page, find list of elements to buy and click to each product page.
//            System.out.println("New release list page: " + driver.getCurrentUrl());
//            WebElement shopNewCart = driver.findElement(By.id("container"));
//            List<WebElement> newReleases = shopNewCart.findElements(By.className("inner-article"));
//            System.out.println("Number of new release found: " + newReleases.productSize());
//
//            List<WebElement> toBuyItems = selectToBuy(newReleases);
//
//            // TODO: 10/8/17 get first item only for now, ignoring others, need figure out logic to select products
//            assert !toBuyItems.isEmpty();
//            WebElement toBuyElement = toBuyItems.get(0);
//            toBuyElement.click();




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
