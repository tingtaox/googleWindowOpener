import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

public class BaseTest {

    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    public void createDriver() throws MalformedURLException, UnexpectedException {
        // download chromedriver and update the path to the chromedriver
        System.setProperty("webdriver.chrome.driver", "/Users/zhixianb/workspace/files/chromedriver");
        ChromeDriver driver = new ChromeDriver();
        webDriver.set(driver);
    }

    public WebDriver getWebDeriver() {
        return webDriver.get();
    }

}
