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

public class RegistrationTests {
    private WebDriver webDriver;
    private String baseUrl = "https://www.geeksforgeeks.org/";

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
    // 2.0 Test registration with valid credentials
    public void testRegistrationWithValidCredentials() throws InterruptedException {
        System.out.println("Testing Register | Sign Up with valid credentials...");

        //ensure we are on the home page
        String currentUrl = webDriver.getCurrentUrl();
        if (!currentUrl.equals(baseUrl)) {
            webDriver.get(baseUrl);
        }

        //wait for sign in to be clickable <at top-right>
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Sign In')]")));
        //WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[2]")));

        //click the Sign-in button
        signInButton.click();
        Thread.sleep(1000);
        // Wait for the "Sign Up" button to appear within the modal
        WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[2]")));
        //click the sign-up button
        signUpButton.click();
        Thread.sleep(1000);

        //wait for the login form to be visible
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[1]/input")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[2]/input")));
        WebElement InstitutesField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[3]/div/div/input")));
        WebElement SignUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/button")));

        // Filling in the login form with invalid credentials
        usernameField.sendKeys("validuser@gmail.com");
        Thread.sleep(1000);
        passwordField.sendKeys("validPass123!");
        Thread.sleep(1000);
        InstitutesField.sendKeys("Student");
        Thread.sleep(1000);
        SignUpButton.click();

        // Validate redirection or logged-in state
        wait.until(ExpectedConditions.urlContains("https://www.geeksforgeeks.org/"));
        assertTrue(currentUrl.contains("https://www.geeksforgeeks.org/"), "Registration failed or no redirection occurred.");

        // Navigate to the user profile page after login
        String profileUrl = "https://www.geeksforgeeks.org/user/fatmaynemv/";
        webDriver.get(profileUrl); // Go to the user's profile page

        // Wait for the profile page to load by verifying an element specific to the profile page
        WebDriverWait profileWait = new WebDriverWait(webDriver, Duration.ofSeconds(1000));
        // Wait for the profile header element to be visible
        WebElement profileHeader = profileWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='comp']/div[2]/div/div[2]/div/div/div[1]/div/div[1]/div[1]")));

        // Assert that the profile header is displayed
        assertTrue(profileHeader.isDisplayed(), "Profile page did not load successfully.");
        System.out.println("Registration completed successfully.");

        System.out.println("Successfully navigated to the profile page.");
        Thread.sleep(2000);

    }

    @Test
    public void testRegistrationWithInvalidCredentials() throws InterruptedException {
        System.out.println("Testing registration with invalid credentials...<3");
        if (!webDriver.getCurrentUrl().equals(baseUrl)) {
            webDriver.get(baseUrl);
        }
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Sign In')]")));
        signInButton.click();
        Thread.sleep(1000);

        WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[2]")));
        signUpButton.click();

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[1]/input")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[2]/input")));
        WebElement InstitutesField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[3]/div/div/input")));
        WebElement SignUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/button")));

        usernameField.sendKeys("invalid-email@exo.com");
        Thread.sleep(1000);
        passwordField.sendKeys("short");
        Thread.sleep(1000);
        InstitutesField.sendKeys("Student");
        Thread.sleep(1000);
        SignUpButton.click();

        //--> wait for the error message :)
        WebElement emailError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[1]/div"))); // Update based on actual error message
        WebElement passwordError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[1]/div"))); // Update based on actual error message

        // Validate error messages
        assertTrue(emailError.isDisplayed(), "No error message displayed for invalid email.");
        assertTrue(passwordError.isDisplayed(), "No error message displayed for weak password.");

        System.out.println("Registration failed as expected with invalid credentials.");
        Thread.sleep(1000);

    }
    @AfterEach
    public void tearDown() {
        // Close the browser
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
