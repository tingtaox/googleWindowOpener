package com.happyworker.shopping.service;

import static com.happyworker.shopping.BrowserApp.TO_BUY;
import com.happyworker.shopping.billing.BillingElements;
import com.happyworker.shopping.billing.BillingInfo;
import com.happyworker.shopping.model.OrderTarget;
import static com.happyworker.shopping.util.PageLoadWaiting.waitPageLoadByCSS;
import static com.happyworker.shopping.util.PageLoadWaiting.waitPageLoadById;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class SupermanShopping {

    public void openWindow(OrderTarget target, int num) {
        System.out.println(target.getUrl() + ", Window number " + num + ", Start shopping ~~~~~~~~ ");

        WebDriver driver;
        try {
            driver = new ChromeDriver();

            driver.get(target.getUrl());
            // Product page, 1. select productSize, 2. add to cart
            System.out.println("Product page: " + driver.getCurrentUrl());
            waitPageLoadById(driver, "s");
            // Set size if needed
            if (target.getProductSize() != null) {
                selectSize(driver, target.getProductSize());
            }
            addToCart(driver);
            goCheckOut(driver);

            //Checkout page, 1. fill milling billing, 2. fill credit card billing, 3. click process payment button ==> order finished.
            System.out.println("Checkout page: " + driver.getCurrentUrl());
            waitPageLoadById(driver, BillingElements.ORDER_BILL_NAME_ELEMENT_ID);

            // Wait to avoid robot check, filling zip code calls backend.
            Thread.sleep(2000);
            fillBillingInfo(driver);
            fillCreditCardInfo(driver);
            clickAcknowledge(driver);

            // Wait to avoid robot check
            Thread.sleep(3000);
            if (TO_BUY) {
                clickProcessPayment(driver);
            }

            System.out.println(target.getUrl() + ", closing window " + num);
            driver.close();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void clickAcknowledge(WebDriver driver) {
        driver.findElement(By.cssSelector("label.has-checkbox.terms")).click();
    }

    private void clickProcessPayment(WebDriver driver) {
        driver.findElement(By.cssSelector("input.button.checkout")).click();
    }

    private void fillCreditCardInfo(WebDriver driver) {
        driver.findElement(By.id(BillingElements.CREDIT_CARD_NUMBER_ELEMENT_ID)).sendKeys(BillingInfo.CREDIT_CARD_NUMBER);
        driver.findElement(By.id(BillingElements.CREDIT_CARD_CVV_ELEMENT_ID)).sendKeys(BillingInfo.CREDIT_CARD_CVV);
        driver.findElement(By.id(BillingElements.CREDIT_CARD_EXP_MONTH_ELEMENT_ID)).sendKeys(BillingInfo.CREDIT_CARD_EXP_MONTH);
        driver.findElement(By.id(BillingElements.CREDIT_CARD_EXP_YEAR_ELEMENT_ID)).sendKeys(BillingInfo.CREDIT_CARD_EXP_YEAR);
    }

    private void fillBillingInfo(WebDriver driver) {
        driver.findElement(By.id(BillingElements.ORDER_BILL_NAME_ELEMENT_ID)).sendKeys(BillingInfo.ORDER_BILL_NAME);
        driver.findElement(By.id(BillingElements.ORDER_EMAIL_ELEMENT_ID)).sendKeys(BillingInfo.ORDER_EMAIL);
        driver.findElement(By.id(BillingElements.ORDER_TEL_NUMBER_ELEMENT_ID)).sendKeys(BillingInfo.ORDER_TEL_NUMBER);
        driver.findElement(By.id(BillingElements.ORDER_ADDRESS_ELEMENT_ID)).sendKeys(BillingInfo.ORDER_ADDRESS);
        driver.findElement(By.id(BillingElements.ORDER_ZIP_ELEMENT_ID)).sendKeys(BillingInfo.ORDER_ZIP);
        driver.findElement(By.id(BillingElements.ORDER_CITY_ELEMENT_ID)).sendKeys(BillingInfo.ORDER_CITY);
        driver.findElement(By.id(BillingElements.ORDER_STATE_ELEMENT_ID)).sendKeys(BillingInfo.ORDER_STATE);
        driver.findElement(By.id(BillingElements.ORDER_COUNTRY_ELEMENT_ID)).sendKeys(BillingInfo.ORDER_COUNTRY);
    }

    /**
     * Go to checkout by clicking "Checkout" Button
     * Another approach, go to https://www.supremenewyork.com/checkout directly.
     */
    private void goCheckOut(WebDriver driver) {
        // Some good refer, find by multiple class name: https://stackoverflow.com/a/21714006/3735335
        waitPageLoadByCSS(driver, "a.button.checkout");
        driver.findElement(By.cssSelector("a.button.checkout")).click();
    }

    private void addToCart(WebDriver driver) {
        driver.findElement(By.name("commit")).click();
    }

    private void selectSize(WebDriver driver, String size) {
        driver.findElement(By.id("s")).sendKeys(size);
    }


}
