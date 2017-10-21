package com.happyworker.shopping.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageLoadWaiting {


    public static void waitPageLoadById(final WebDriver driver, final String id) {
        waitPageLoad(driver, By.id(id));
    }

    public static void waitPageLoadByCSS(final WebDriver driver, final String css) {
        waitPageLoad(driver, By.cssSelector(css));
    }

    private static void waitPageLoad(final WebDriver driver, final By waitFactor) {
        new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver arg0) {
                WebElement element = driver.findElement(waitFactor);
                return element.isDisplayed();
            }
        });
    }

}
