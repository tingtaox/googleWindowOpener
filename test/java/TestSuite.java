import static billing.BillingElements.CREDIT_CARD_CVV_ELEMENT_ID;
import static billing.BillingElements.CREDIT_CARD_EXP_MONTH_ELEMENT_ID;
import static billing.BillingElements.CREDIT_CARD_EXP_YEAR_ELEMENT_ID;
import static billing.BillingElements.CREDIT_CARD_NUMBER_ELEMENT_ID;
import static billing.BillingElements.ORDER_ADDRESS_ELEMENT_ID;
import static billing.BillingElements.ORDER_BILL_NAME_ELEMENT_ID;
import static billing.BillingElements.ORDER_CITY_ELEMENT_ID;
import static billing.BillingElements.ORDER_COUNTRY_ELEMENT_ID;
import static billing.BillingElements.ORDER_EMAIL_ELEMENT_ID;
import static billing.BillingElements.ORDER_STATE_ELEMENT_ID;
import static billing.BillingElements.ORDER_TEL_NUMBER_ELEMENT_ID;
import static billing.BillingElements.ORDER_ZIP_ELEMENT_ID;
import static billing.BillingInfo.CREDIT_CARD_CVV;
import static billing.BillingInfo.CREDIT_CARD_EXP_MONTH;
import static billing.BillingInfo.CREDIT_CARD_EXP_YEAR;
import static billing.BillingInfo.CREDIT_CARD_NUMBER;
import static billing.BillingInfo.ORDER_ADDRESS;
import static billing.BillingInfo.ORDER_BILL_NAME;
import static billing.BillingInfo.ORDER_CITY;
import static billing.BillingInfo.ORDER_COUNTRY;
import static billing.BillingInfo.ORDER_EMAIL;
import static billing.BillingInfo.ORDER_STATE;
import static billing.BillingInfo.ORDER_TEL_NUMBER;
import static billing.BillingInfo.ORDER_ZIP;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class TestSuite extends BaseTest {

    @Test
    public void openWindow1() throws MalformedURLException, UnexpectedException, InterruptedException {
        System.out.println("Start shopping ~~~~~~~~ ");

        createDriver();
        WebDriver driver = getWebDriver();

        driver.get("http://www.supremenewyork.com/shop/new");

        // New release list page, find list of elements to buy and click to each product page.
        System.out.println("New release list page: " + driver.getCurrentUrl());
        WebElement shopNewCart = driver.findElement(By.id("container"));
        List<WebElement> newReleases = shopNewCart.findElements(By.className("inner-article"));
        System.out.println("Number of new release found: " + newReleases.size());

        List<WebElement> toBuyItems = selectToBuy(newReleases);

        // TODO: 10/8/17 get first item only for now, ignoring others
        assert !toBuyItems.isEmpty();
        WebElement toBuyElement = toBuyItems.get(0);
        toBuyElement.click();

        // Product page, 1. select size, 2. add to cart
        System.out.println("Product page: " + driver.getCurrentUrl());
        waitPageLoadById(driver, "s");
        selectSize(driver);
        addToCart(driver);
        goCheckOut(driver);

        //Checkout page, 1. fill milling billing, 2. fill credit card billing, 3. click process payment button ==> order finished.
        System.out.println("Checkout page: " + driver.getCurrentUrl());
        waitPageLoadById(driver, ORDER_BILL_NAME_ELEMENT_ID);
        fillBillingInfo(driver);
        fillCreditCardInfo(driver);
        clickAcknowledge(driver);

        // Wait for some time before checkout.
        Thread.sleep(5000);
        clickProcessPayment(driver);


    }

    private void clickAcknowledge(WebDriver driver) {
        driver.findElement(By.cssSelector("label.has-checkbox.terms")).click();
    }

    private void clickProcessPayment(WebDriver driver) {
        // uncomment
        // driver.findElement(By.cssSelector("input.button.checkout")).click();
    }

    private void fillCreditCardInfo(WebDriver driver) {
        driver.findElement(By.id(CREDIT_CARD_NUMBER_ELEMENT_ID)).sendKeys(CREDIT_CARD_NUMBER);
        driver.findElement(By.id(CREDIT_CARD_CVV_ELEMENT_ID)).sendKeys(CREDIT_CARD_CVV);
        driver.findElement(By.id(CREDIT_CARD_EXP_MONTH_ELEMENT_ID)).sendKeys(CREDIT_CARD_EXP_MONTH);
        driver.findElement(By.id(CREDIT_CARD_EXP_YEAR_ELEMENT_ID)).sendKeys(CREDIT_CARD_EXP_YEAR);
    }

    private void fillBillingInfo(WebDriver driver) {
        driver.findElement(By.id(ORDER_BILL_NAME_ELEMENT_ID)).sendKeys(ORDER_BILL_NAME);
        driver.findElement(By.id(ORDER_EMAIL_ELEMENT_ID)).sendKeys(ORDER_EMAIL);
        driver.findElement(By.id(ORDER_TEL_NUMBER_ELEMENT_ID)).sendKeys(ORDER_TEL_NUMBER);
        driver.findElement(By.id(ORDER_ADDRESS_ELEMENT_ID)).sendKeys(ORDER_ADDRESS);
        driver.findElement(By.id(ORDER_ZIP_ELEMENT_ID)).sendKeys(ORDER_ZIP);
        driver.findElement(By.id(ORDER_CITY_ELEMENT_ID)).sendKeys(ORDER_CITY);
        driver.findElement(By.id(ORDER_STATE_ELEMENT_ID)).sendKeys(ORDER_STATE);
        driver.findElement(By.id(ORDER_COUNTRY_ELEMENT_ID)).sendKeys(ORDER_COUNTRY);
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

    private void waitPageLoadById(final WebDriver driver, final String id) {
        waitPageLoad(driver, By.id(id));
    }

    private void waitPageLoadByCSS(final WebDriver driver, final String css) {
        waitPageLoad(driver, By.cssSelector(css));
    }

    private void waitPageLoad(final WebDriver driver, final By waitFactor) {
        new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver arg0) {
                WebElement element = driver.findElement(waitFactor);
                return element.isDisplayed();
            }
        });
    }

    private void addToCart(WebDriver driver) {
        driver.findElement(By.name("commit")).click();
    }

    private void selectSize(WebDriver driver) {
        driver.findElement(By.id("s")).sendKeys("Large");
    }

    private List<WebElement> selectToBuy(List<WebElement> newReleases) {
        List<WebElement> toBuyElements = new ArrayList<WebElement>();
        for (WebElement element : newReleases) {
            if (ifWorthBuy(element)) {
                toBuyElements.add(element);
            }
        }

        return toBuyElements;
    }

    private boolean ifWorthBuy(WebElement newReleases) {
        // TODO: 10/8/17 Fill to buy algorithm, can simply filter by name
        return true;
    }


}
