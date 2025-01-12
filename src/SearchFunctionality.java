import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SearchFunctionality {
    private WebDriver webDriver;
    private String baseUrl = "https://www.geeksforgeeks.org/";

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\webDriver\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        webDriver.get(baseUrl);
        System.out.println("Setting up resources for Search Functionality Tests...");
    }

    @Test
    //1.0 Testing with valid keywords
    public void testSearchWithValidKeywords() {
        System.out.println("Testing search with valid keywords...");
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"comp\"]/div[2]/div[1]/div[2]/input")));
            // Enter the search query and initiate search
            searchBar.sendKeys("Sorting algorithms");
            Thread.sleep(1000);
            searchBar.sendKeys(Keys.ENTER);

            // Wait for search results to load
            WebElement resultsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id=\"modal\"]")));
            // Scroll down inside the results container
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", resultsContainer);
            Thread.sleep(2000);

            // Fetch&Validate search results>
            List<WebElement> results = resultsContainer.findElements(By.tagName("a"));
            //Printing the size of the results...
            System.out.println("Number of results found: " + results.size());
            for (WebElement result : results) {
                System.out.println("Result text: " + result.getText());
            }

            assertTrue(results.stream()
                            .anyMatch(result -> result.getText().toLowerCase().contains("sorting algorithms")),
                    "Expected result containing 'Sorting Algorithms' not found! Results: " +
                            results.stream().map(WebElement::getText).toList()
            );
            System.out.println("Search with valid keywords completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    //1.1 Testing with invalid keywords
    public void testSearchWithInvalidKeywords(){
        System.out.println("Testing search with invalid inputs...");
        try{
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"comp\"]/div[2]/div[1]/div[2]/input")));
            searchBar.sendKeys("#$%^$@@");
            Thread.sleep(1000);
            searchBar.sendKeys(Keys.ENTER);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"comp\"]/div[2]/div[1]/div/div[3]")));
            //verify the results>
            WebElement resultContainer = webDriver.findElement(By.xpath("//*[@id=\"modal\"]"));
            assertTrue(resultContainer.getText().contains("No Result Found!") || resultContainer.getText().isEmpty(),
                    "Invalid input did not return expected results!"
                    );
        }
        catch(Exception e){
            e.printStackTrace();
            fail("Test failed:"+ e.getMessage());
        }

    }

    @AfterEach
    public void teardown() {
        if (webDriver != null) {
            webDriver.quit();
        }
        System.out.println("Teardown completed.");
    }

}
