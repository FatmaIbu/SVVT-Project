import org.junit.jupiter.api.AfterEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CodeTesting {
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
    //test the run button outputs the expected output
    public void testingExpectedOutput() {
        System.out.println("Testing the expected output after running the code button...");

        try {
            webDriver.get("https://www.geeksforgeeks.org/python-programming-language-tutorial/");
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

            // Wait until the "Run Code" button is clickable
            WebElement runCodeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"run-code-loader\"]")));

            // Click the "Run Code" button using JavaScript executor to ensure it works
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", runCodeButton);

            // Wait for the expected output to be generated (code has run)
            WebElement expectedOutputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='gfg-panel-generated-0']/div/div[6]/div[1]/div/div/div/div[5]/div/pre/span/span[2]")));
            String expectedOutput = expectedOutputElement.getText().trim();

            // Wait for the actual output to appear
            WebElement actualOutputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='post-1179442']/div[3]/div[1]/pre")));
            String actualOutput = actualOutputElement.getText().trim();

            // Compare the expected and actual outputs
            assertEquals(expectedOutput, actualOutput, "The expected and actual output do not match.");

            // Output the results for inspection
            System.out.println("Expected Output: " + expectedOutput);
            System.out.println("Actual Output: " + actualOutput);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        // Quit the WebDriver after the test is finished
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}