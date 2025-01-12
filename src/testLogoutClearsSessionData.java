import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class testLogoutClearsSessionData {
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
    // Test Logout clears session data
    public void testLogoutToClearsSessionData() throws InterruptedException {
        System.out.println("Testing logout clears session data...");
        // Ensure we're on the homepage
        String currentUrl = webDriver.getCurrentUrl();
        if (!currentUrl.equals(baseUrl)) {
            webDriver.get(baseUrl);
        }

        // Wait for the "Sign In" button to be clickable (located at top-right)
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

        // Locate the "Sign In" button using XPath
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Sign In')]")));

        // Click the "Sign In" button
        signInButton.click();

        // Wait for the login form to be visible and intractable
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login']/div/div[2]/div/div[3]/div[1]/input")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login']/div/div[2]/div/div[3]/div[2]/input")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='login']/div/div[2]/div/div[3]/button")));

        // Fill in the login form (ensure the fields are intractable)
        usernameField.sendKeys("fatma.younes@stu.ibu.edu.ba");
        Thread.sleep(1000);
        passwordField.sendKeys("Fatma3@ibu");

        // Click the login button
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("https://www.geeksforgeeks.org/"));
        String loggedInUrl = webDriver.getCurrentUrl();
        assertTrue(loggedInUrl.contains("https://www.geeksforgeeks.org/"), "Login failed, URL does not match expected.");


        // Locate and click the user avatar or menu that contains the "Logout" option
        WebElement userMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"topMainHeader\"]/div/div/div[1]/div")));
        userMenu.click();

        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"topMainHeader\"]/div/div/div[1]/ul/a[8]")));
        logoutButton.click();

        // Wait for the page to load and ensure that we're redirected to the login page

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Sign In')]")));

        WebElement signInButtonAfterLogout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Sign In')]")));
        assertTrue(signInButtonAfterLogout.isDisplayed(), "Logout failed, user is not redirected to login page.");

        System.out.println("Logout cleared session data.");
    }

    @Test
    // Test Session Continuity During Page Refresh or Navigation
    public void testSessionContintuity() throws InterruptedException {
        System.out.println("Testing logout clears session data...");
        // Ensure we're on the homepage
        String currentUrl = webDriver.getCurrentUrl();
        if (!currentUrl.equals(baseUrl)) {
            webDriver.get(baseUrl);
        }

        // Wait for the "Sign In" button to be clickable (located at top-right)
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

        // Locate the "Sign In" button using XPath
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Sign In')]")));

        // Click the "Sign In" button
        signInButton.click();

        // Wait for the login form to be visible and intractable
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login']/div/div[2]/div/div[3]/div[1]/input")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login']/div/div[2]/div/div[3]/div[2]/input")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='login']/div/div[2]/div/div[3]/button")));

        // Fill in the login form (ensure the fields are intractable)
        usernameField.sendKeys("fatma.younes@stu.ibu.edu.ba");
        Thread.sleep(1000);
        passwordField.sendKeys("Fatma3@ibu");

        // Click the login button
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("https://www.geeksforgeeks.org/"));
        String loggedInUrl = webDriver.getCurrentUrl();
        assertTrue(loggedInUrl.contains("https://www.geeksforgeeks.org/"), "Login failed, URL does not match expected.");

        // Locate and click the user avatar or menu that contains the "Logout" option
        WebElement userMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"topMainHeader\"]/div/div/div[1]/div")));
        userMenu.click();
        //--------------------------------------------------
        Thread.sleep(1000);
        // Refresh the page and ensure the user remains logged in
        webDriver.navigate().refresh();
        // Wait for the page to reload and verify user is still logged in
        wait.until(ExpectedConditions.urlContains("https://www.geeksforgeeks.org/"));
        assertTrue(currentUrl.contains("https://www.geeksforgeeks.org/"), "Session did not persist after refresh.");

        // Navigate to another page and verify session persists
        webDriver.get("https://www.geeksforgeeks.org/articles/");
        wait.until(ExpectedConditions.urlContains("https://www.geeksforgeeks.org/articles/"));
        currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.contains("https://www.geeksforgeeks.org/articles/"), "Session did not persist during navigation.");

        System.out.println("Session continuity validated.");

    }
    @AfterEach
    public void tearDown() {
        // Close the browser
        if (webDriver != null) {
            webDriver.quit();
        }
    }

}
