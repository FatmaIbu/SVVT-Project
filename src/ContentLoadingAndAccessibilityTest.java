import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class ContentLoadingAndAccessibilityTest {
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
        System.out.println("Setting up resources for ContentLoading & Accessibility Tests...");
    }

    @Test
    public void testContentLoadingAndAccessibilityOfImages() {
        webDriver.get("https://www.geeksforgeeks.org/courses?itm_source=geeksforgeeks&itm_medium=subheader&itm_campaign=three90-2025");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

        // Step 1: Validate image loading
        System.out.println("Checking image loading...");
        List<WebElement> images = webDriver.findElements(By.tagName("img"));
        for (int i = 0; i < images.size(); i++) {
            try {
                // Re-locate the element in case it becomes stale
                WebElement image = images.get(i);
                String src = image.getAttribute("src");

                // Ensure the 'src' attribute exists
                if (src != null && !src.isEmpty()) {
                    WebElement img = wait.until(ExpectedConditions.visibilityOf(image));
                    img.click(); // Trigger loading or validation (if needed)
                    System.out.println("Image loaded: " + src);
                } else {
                    System.out.println("Image at index " + i + " does not have a valid 'src' attribute.");
                }
            } catch (StaleElementReferenceException e) {
                // Catch StaleElementReferenceException and re-locate the element without using the index
                System.out.println("Stale element encountered for image at index " + i + ". Retrying...");

                // Re-fetch all images in the DOM again
                List<WebElement> updatedImages = webDriver.findElements(By.tagName("img"));

                // Use the new list of images and handle the current image (it could be at a different index)
                if (i < updatedImages.size()) {
                    WebElement image = updatedImages.get(i);
                    String src = image.getAttribute("src");
                    if (src != null && !src.isEmpty()) {
                        WebElement img = wait.until(ExpectedConditions.visibilityOf(image));
                        img.click(); // Trigger loading or validation (if needed)
                        System.out.println("Image re-loaded: " + src);
                    }
                } else {
                    System.out.println("Image at index " + i + " no longer exists after reload.");
                }
            }
        }

    }

    @Test
    public void testContentLoadingAndAccessibilityOfVideos() {
        webDriver.get("https://www.geeksforgeeks.org/courses?itm_source=geeksforgeeks&itm_medium=subheader&itm_campaign=three90-2025");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

        // Step 1: Validate image loading
        System.out.println("Checking video loading...");
        List<WebElement> videos = webDriver.findElements(By.tagName("video"));
        for (int i = 0; i < videos.size(); i++) {
            try {
                WebElement video = videos.get(i);

                // Wait for the video element to be loaded
                wait.until(ExpectedConditions.visibilityOf(video));
                System.out.println("Video loaded.");
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element encountered for video at index " + i + ". Retrying...");

                // Re-fetch all videos in the DOM
                List<WebElement> updatedVideos = webDriver.findElements(By.tagName("video"));

                if (i < updatedVideos.size()) {
                    WebElement video = updatedVideos.get(i);
                    wait.until(ExpectedConditions.visibilityOf(video)); // Wait until the video is visible
                    System.out.println("Video re-loaded.");
                } else {
                    System.out.println("Video at index " + i + " no longer exists after reload.");
                }
            }
        }
    }

    @Test
    public void testContentLoadingAndAccessibilityOfLinks() {
        webDriver.get("https://www.geeksforgeeks.org/courses?itm_source=geeksforgeeks&itm_medium=subheader&itm_campaign=three90-2025");
        System.out.println("Checking for broken links...");

        // Create a WebDriverWait instance for the entire check process
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

        // Find all the links on the page
        List<WebElement> links = webDriver.findElements(By.tagName("a"));
        System.out.println("Total links found: " + links.size());

        for (int i = 0; i < links.size(); i++) {
            WebElement link = links.get(i);
            String linkHref = link.getAttribute("href");

            if (linkHref == null || linkHref.isEmpty()) {
                System.out.println("Link at index " + i + " has no 'href' attribute.");
                continue;
            }

            try {
                System.out.println("Checking link: " + linkHref);
                // Navigate to the link
                webDriver.get(linkHref);

                // Wait for the page to load and check if the URL contains the expected domain
                wait.until(ExpectedConditions.urlContains("geeksforgeeks.org"));
                System.out.println("Link works: " + linkHref);

                // Optionally navigate back after the check
                webDriver.navigate().back();

                // Re-fetch links after navigating back to avoid stale element errors
                links = webDriver.findElements(By.tagName("a"));
            } catch (StaleElementReferenceException e) {
                // Re-fetch the link because the previous link reference became stale
                System.out.println("Stale element encountered for link at index " + i + ". Re-fetching the links...");
                links = webDriver.findElements(By.tagName("a")); // Re-fetch all links
                i--;  // Stay at the same index to recheck the same link
                continue;  // Retry the current link
            } catch (TimeoutException e) {
                System.out.println("Timed out waiting for link to load: " + linkHref);
            } catch (Exception e) {
                System.out.println("Error occurred for link: " + linkHref + " - " + e.getMessage());
            }
        }
    }

    // Helper method to check if a link is stale
    private boolean isLinkStale(WebElement link) {
        try {
            // Try interacting with the link to see if it's still valid
            link.getAttribute("href");
            return false;  // The link is not stale
        } catch (StaleElementReferenceException e) {
            return true;  // The link is stale
        }
    }
    @After
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }


}




