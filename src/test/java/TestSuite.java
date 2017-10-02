import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

public class TestSuite extends BaseTest {

    @Test
    public void openWindow1() throws MalformedURLException, UnexpectedException {
        createDriver();
        WebDriver driver = getWebDeriver();
        driver.get("http://www.google.com");
    }

    @Test
    public void openWindow2() throws MalformedURLException, UnexpectedException {
        createDriver();
        WebDriver driver = getWebDeriver();
        driver.get("http://www.google.com");
    }

    @Test
    public void openWindow3() throws MalformedURLException, UnexpectedException {
        createDriver();
        WebDriver driver = getWebDeriver();
        driver.get("http://www.google.com");
    }

    @Test
    public void openWindow4() throws MalformedURLException, UnexpectedException {
        createDriver();
        WebDriver driver = getWebDeriver();
        driver.get("http://www.google.com");
    }
}
