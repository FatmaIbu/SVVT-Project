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

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class NavigationLinksTest {
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
    public void testNavigationLinks() throws InterruptedException {
        // Locate all links
        List<WebElement> navLinks = webDriver.findElements(By.cssSelector("ul.containerSubheader a"));

        // Assert that the navigation links are found>
        assertFalse(navLinks.isEmpty(), "No navigation links found.");

        for (WebElement link : navLinks) {
            String linkHref = link.getAttribute("href");
            assertNotNull("Navigation link is missing href attribute.", linkHref);

            // Open the link in a new tab
            JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
            jsExecutor.executeScript("window.open(arguments[0], '_blank');", linkHref);

            switchToLastTab();

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("title")));
            String pageTitle = webDriver.getTitle();
            assertNotNull("Page title is null for link: " + linkHref, pageTitle);
            Thread.sleep(500);

            webDriver.close();
            switchToMainTab();
            Thread.sleep(800);
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
    @Test
    public void testInternalLinksInRows() throws InterruptedException {

        List<WebElement> topicRows = webDriver.findElements(By.cssSelector(".HomePageTopicsContainer_homePageTopicsContainer__G7Ad5"));

        assertFalse(topicRows.isEmpty(), "No topic rows found on the page.");

        for (WebElement row : topicRows) {
            // Locate all links within the current row
            List<WebElement> links = row.findElements(By.tagName("a"));


            assertFalse(links.isEmpty(), "No links found in row: " + row.getText());

            for (WebElement link : links) {
                String linkHref = link.getAttribute("href");
                assertNotNull("Link is missing href attribute.", linkHref);

                JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
                jsExecutor.executeScript("window.open(arguments[0], '_blank');", linkHref);
                Thread.sleep(200);
                switchToLastTab();

                // Wait for the page to load and validate the title
                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("title")));
                String pageTitle = webDriver.getTitle();
                assertNotNull("Page title is null for link: " + linkHref, pageTitle);

                // validate the URL
                String currentUrl = webDriver.getCurrentUrl();
                assertTrue("URL does not belong to GeeksforGeeks: " + currentUrl, currentUrl.contains("geeksforgeeks.org"));

                // switch back to the main tab
                webDriver.close();
                switchToMainTab();

                System.out.println("Internal links validated successfully: "+ link);
            }
        }
    }

    @Test
    public void testExternalLinksOpenInNewTab() throws URISyntaxException, InterruptedException {
        // Scroll down the page to make sure the "Contact Us" section is visible
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0, 7000);");
        Thread.sleep(1000);
        // Locating all external links in the footer
        List<WebElement> externalLinks = webDriver.findElements(By.cssSelector(".footer-container_branding-social a"));

        // Assert that there are external links present
        assertFalse(externalLinks.isEmpty(), "No external links found in the footer branding social container.");

        for (WebElement link : externalLinks) {
            String href = link.getAttribute("href");
            String target = link.getAttribute("target");

            // Ensure the link has a href attribute
            assertNotNull("Link is missing href attribute.", href);

            // Extract the domain of the link using URI
            URI uri = new URI(href);
            String host = uri.getHost();

            // Check if the link is external by comparing its domain
            boolean isExternal = !host.endsWith("geeksforgeeks.org");
            assertTrue("Link is incorrectly identified as external: " + href, isExternal);

            // Assert the link opens in a new tab
            assertEquals("_blank", target, "External link does not open in a new tab: " + href);

            // Ensure the link has the correct 'rel' attributes for security
            String rel = link.getAttribute("rel");
            assertNotNull("External link is missing rel attribute: " + href);
            assertTrue("External link does not have correct rel attributes: " + href,
                    rel.contains("noopener") && rel.contains("noreferrer"));

            // Print the success message for each link after validation
            System.out.println("External link validated successfully: " + href);
            Thread.sleep(10);
        }
    }

    @After
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}