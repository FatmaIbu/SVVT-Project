import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumIntroTest {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        // Set the system property for ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\webDriver\\chromedriver-win64\\chromedriver.exe");

        // Initialize ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); //allowing it to bypass the COR

        // Initialize the WebDriver-->to set various configurations for the Chrome browser.
        webDriver = new ChromeDriver(options);

        // Set the base URL for testing
        baseUrl = "https://www.geeksforgeeks.org/";
    }
    //1. testing title
    @Test
    public void testTitle() throws InterruptedException {
        // Navigate to the base URL
        webDriver.get(baseUrl);

        // Getting the title of the page and check if it matches the expected title
        String actualTitle = webDriver.getTitle();
        //Printing out the Actual Title
        System.out.println("Actual title: " + actualTitle);

        // Assert that the title is as expected
        assertEquals("GeeksforGeeks | A computer science portal for geeks", actualTitle, "Title does not match");

        Thread.sleep(2000);
    }
//2. testing redirection
    @Test
    void testRedirect() throws InterruptedException {
        // Navigate to the given URL
        webDriver.get("https://www.geeksforgeeks.org/");
        Thread.sleep(2000);

        // Get the current URL and check if it matches the expected URL
        String currentUrl = webDriver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        // Assert that the URL is as expected
        assertEquals("https://www.geeksforgeeks.org/", currentUrl,"current URL does not match the expected URL");
    }
    @AfterAll
    public static void tearDown() {
        // Close the browser
        if (webDriver != null) {
            webDriver.quit(); // quit() is better than close() as it closes the browser entirely
        }
    }
}

