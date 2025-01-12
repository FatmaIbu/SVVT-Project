import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityTest {
    private WebDriver webDriver;
    private String baseUrl = "https://www.geeksforgeeks.org/";

    @BeforeEach
    public void setup() {

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
    public void testHttpsRedirection() {
        System.out.println("Testing HTTPS redirection...");
        webDriver.get(baseUrl);
        assertTrue(webDriver.getCurrentUrl().startsWith("https"), "Website is not redirecting to HTTPS.");
    }

    @Test
    public void testInputHandling() throws InterruptedException {
        System.out.println("Testing input handling...");
        webDriver.get("https://www.geeksforgeeks.org/");

        // Locate the search field
        WebElement searchField = webDriver.findElement(By.xpath("//*[@id=\"comp\"]/div[2]/div[1]/div[2]/input"));

        // Input various edge case values
        String[] testInputs = new String[]{
                "normal search",              // Normal input
                "' OR '1'='1",                // SQL-like input
                "<script>alert('XSS');</script>",  // XSS-like input
                "&*%$@@",                     // Unicode characters
                " "                          // Empty space
        };

        for (String input : testInputs) {
            System.out.println("Testing input: " + input);

            // Clear and enter the test input
            searchField.clear();
            searchField.sendKeys(input);

            Thread.sleep(1000);
            //searchField.sendKeys(Keys.ENTER);


            // Wait for results page to load (adjust wait conditions if necessary)
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"comp\"]/div[2]/div[1]/div[2]/div")));

            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"comp\"]/div[2]/div[1]/div[2]/div")));
                System.out.println("Search results loaded for input: " + input);
            } catch (TimeoutException e) {
                System.out.println("No search results found for input: " + input);
                continue; // Skip the assertion if no results are found
            }

            WebElement resultContainer =wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"comp\"]/div[2]/div[1]/div[2]/div")));

            //System.out.println("Current URL after search: " + currentUrl);
            // Assert that the input did not cause unexpected behavior (e.g., blank page, error page)
            assertTrue(resultContainer.isDisplayed(), "Search result container should be displayed for input: " + input);
        }
/*
    @Test
    public void testSqlInjectionPrevention() {
        System.out.println("Testing SQL injection prevention...");
        webDriver.get(baseUrl);
        WebElement usernameField = webDriver.findElement(By.name("username"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.id("loginBtn"));

        usernameField.sendKeys("' OR '1'='1");
        passwordField.sendKeys("' OR '1'='1");
        loginButton.click();

        WebElement error = webDriver.findElement(By.id("error"));
        assertTrue(error.isDisplayed(), "SQL injection vulnerability exists.");
    }

    @Test
    public void testXssPrevention() {
        System.out.println("Testing XSS prevention...");
        webDriver.get(baseUrl);
        WebElement searchField = webDriver.findElement(By.name("query"));
        searchField.sendKeys("<script>alert('XSS');</script>");
        searchField.submit();

        // Verify no alert is triggered
        try {
            webDriver.switchTo().alert();
            fail("XSS vulnerability exists.");
        } catch (NoAlertPresentException e) {
            System.out.println("No XSS vulnerability detected.");
        }
    }

 */
    }
}