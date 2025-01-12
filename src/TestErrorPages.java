import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestErrorPages {
    private static WebDriver webDriver;
    private static String baseUrl = "https://www.geeksforgeeks.org/"; // Base URL for the website

    @BeforeEach
    public void setup() {
        // Initialize resources & configurations before each test
        System.setProperty("webdriver.chrome.driver", "C:\\webDriver\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");

        webDriver = new ChromeDriver(options); // Assign WebDriver to instance field
        webDriver.get(baseUrl); // Navigate to the base URL
        System.out.println("Setting up resources for GeeksforGeeks Test...");
    }
    @Test
    public void testErrorPages() throws InterruptedException {
        System.out.println("Testing error page handling...");

        // Test 404 Error Page
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        jsExecutor.executeScript("window.open(arguments[0], '_blank');", baseUrl+ "nonexistentpage");

        switchToLastTab();
        System.out.println("Testing 404 error page...");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"main\"]/div[1]/div[2]/h2")));
        assertTrue(errorMessage.isDisplayed(), "Whoops, that page is gone.");

        // Get the page title and assert it matches the expected 404 error title
        String pageTitle = webDriver.getTitle();
        assertTrue(pageTitle.contains("Page Not Found"),
                "Expected Page Not Found-GreeksforGreeks, but found: " + pageTitle);
        Thread.sleep(1000);
        switchToMainTab();
        System.out.println("Successfully navigated back to the homepage from 404 page.");


    }
    @After
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
    private void switchToLastTab() {
        List<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(tabs.size() - 1));
    }
    private void switchToMainTab() {
        List<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(0));
    }
    private void simulateServerError() {
        webDriver.get(baseUrl + "/server-error");
    }
    //GeeksforGeeks may not have a direct URL for testing 500 errors :(


}



