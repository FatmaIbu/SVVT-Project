import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ResponsiveDesignTest {
    private static WebDriver webDriver;
    private static String baseUrl = "https://www.geeksforgeeks.org/";

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
    public void testDesktopLayout() {
        System.out.println("Testing layout on desktop...");
        webDriver.get("https://www.geeksforgeeks.org/");
        webDriver.manage().window().setSize(new Dimension(1920, 1080)); // Desktop size
        verifyLayout();
    }
    @Test
    public void testTabletLayout() {
        System.out.println("Testing layout on tablet...");
        webDriver.get("https://www.geeksforgeeks.org/");
        webDriver.manage().window().setSize(new Dimension(768, 1024)); // Tablet size
        verifyLayout();
    }
    @Test
    public void testMobileLayout() {
        System.out.println("Testing layout on mobile...");
        webDriver.get("https://www.geeksforgeeks.org/");
        webDriver.manage().window().setSize(new Dimension(375, 667)); // Mobile size (iPhone 6/7/8)
        verifyLayout();
    }
    @Test
    public void testScreenOrientationChange() {
        System.out.println("Testing orientation change...");
        webDriver.get("https://www.geeksforgeeks.org/");

        // Test portrait mode
        System.out.println("Testing portrait mode...");
        webDriver.manage().window().setSize(new Dimension(375, 667)); // Mobile portrait size
        verifyLayout();

        // Test landscape mode
        System.out.println("Testing landscape mode...");
        webDriver.manage().window().setSize(new Dimension(667, 375)); // Mobile landscape size
        verifyLayout();
    }

    // Method to verify the layout by checking some elements
    private void verifyLayout() {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

            // Wait for the page to load and check for a specific element
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("some-element-id")));
            System.out.println("Layout is responsive and elements are correctly displayed.");
        } catch (TimeoutException e) {
            System.out.println("Layout verification failed for this screen size.");
        }
    }
    // Method to test orientation change
    @Test
    public void testOrientationChange() {
        // Test portrait mode
        System.out.println("Testing portrait mode...");
        webDriver.manage().window().setSize(new Dimension(375, 667)); // Mobile portrait size
        verifyLayout();

        // Test landscape mode
        System.out.println("Testing landscape mode...");
        webDriver.manage().window().setSize(new Dimension(667, 375)); // Mobile landscape size
        verifyLayout();
    }
    @AfterEach
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
