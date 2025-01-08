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

public class AuthenticationTests {
    private static WebDriver webDriver;
    private static String baseUrl = "https://www.geeksforgeeks.org/"; // Base URL for the website

    @BeforeEach
    public void setup() {
        // Initialize resources & configurations before each test
        System.setProperty("webdriver.chrome.driver", "C:\\webDriver\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");  // Optional: Start Chrome maximized
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");

        webDriver = new ChromeDriver(options); // Assign WebDriver to instance field
        webDriver.get(baseUrl); // Navigate to the base URL
        System.out.println("Setting up resources for GeeksforGeeks Test...");
    }

    @Test
    // 1. Test Login with valid credentials
    public void testLoginWithValidCredentials() {
        System.out.println("Testing login with valid credentials...");

        // Ensure we're on the homepage
        String currentUrl = webDriver.getCurrentUrl();
        if (!currentUrl.equals(baseUrl)) {
            webDriver.get(baseUrl);
        }

        // Wait for the "Sign In" button to be clickable (located at top-right)
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(1000));

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
        passwordField.sendKeys("Fatma3@ibu");

        // Click the login button
        loginButton.click();

        // Wait for the login to complete and validate the new page URL or content
        wait.until(ExpectedConditions.urlContains("https://www.geeksforgeeks.org/"));

        // Verify successful login by checking the current URL
        String loggedInUrl = webDriver.getCurrentUrl();
        assertTrue(loggedInUrl.contains("https://www.geeksforgeeks.org/"), "Login failed, URL does not match expected.");

        // Navigate to the user profile page after login
        String profileUrl = "https://www.geeksforgeeks.org/user/fatmaynemv/";
        webDriver.get(profileUrl); // Go to the user's profile page

        // Wait for the profile page to load by verifying an element specific to the profile page
        WebDriverWait profileWait = new WebDriverWait(webDriver, Duration.ofSeconds(1000));
        // Wait for the profile header element to be visible
        WebElement profileHeader = profileWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='comp']/div[2]/div/div[2]/div/div/div[1]/div/div[1]/div[1]")));

        // Assert that the profile header is displayed
        assertTrue(profileHeader.isDisplayed(), "Profile page did not load successfully.");

        System.out.println("Successfully navigated to the profile page.");

    }

    @Test
    // 1.1 Test Login with Invalid credentials
    public void testLoginWithInvalidCredentials(){
        System.out.println("Testing login with invalid credentials...");

        //ensure we are on the home page
        String currentUrl = webDriver.getCurrentUrl();
        if (!currentUrl.equals(baseUrl)) {
            webDriver.get(baseUrl);
        }

        //wait for sign in to be clickable <at top-right>
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(1000));
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Sign In')]")));
        //WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[2]")));

        //click the Sign-in button
        signInButton.click();
        //click the sign-up button
        //signUpButton.click();

        //wait for the login form to be visible
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login']/div/div[2]/div/div[3]/div[1]/input")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login']/div/div[2]/div/div[3]/div[2]/input")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='login']/div/div[2]/div/div[3]/button")));

        // Filling in the login form with invalid credentials
        usernameField.sendKeys("invaliduser@example.com");
        passwordField.sendKeys("InvalidPass123!");
        // Click the login button
        loginButton.click();
        // Wait for the error message or failed login indication
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[1]/div")));

        // Assert that the error message is displayed
        assertTrue(errorMessage.isDisplayed(), "Error message for invalid credentials not displayed.");
        // --> Verify the error message text
        String expectedErrorMessage = "Incorrect login credentials i.e userHandle/email or password";
        String actualErrorMessage = errorMessage.getText();
        assertTrue(actualErrorMessage.contains(expectedErrorMessage), "Unexpected error message displayed: " + actualErrorMessage);

        System.out.println("Invalid credentials test passed.");
    }
    @Test
    // 2.0 Test registration with valid credentials
    public void testRegistrationWithValidCredentials(){
        System.out.println("Testing Register | Sign Up with valid credentials...");

        //ensure we are on the home page
        String currentUrl = webDriver.getCurrentUrl();
        if (!currentUrl.equals(baseUrl)) {
            webDriver.get(baseUrl);
        }

        //wait for sign in to be clickable <at top-right>
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(1000));
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Sign In')]")));
        //WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[2]")));

        //click the Sign-in button
        signInButton.click();
        // Wait for the "Sign Up" button to appear within the modal
        WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[2]")));
        //click the sign-up button
        signUpButton.click();

        //wait for the login form to be visible
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[1]/input")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[2]/input")));
        WebElement InstitutesField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[3]/div/div/input")));
        WebElement SignUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/button")));

        // Filling in the login form with invalid credentials
        usernameField.sendKeys("validuser@gmail.com");
        passwordField.sendKeys("validPass123!");
        InstitutesField.sendKeys("Student");
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

    }

    @Test
    public void testRegistrationWithInvalidCredentials(){
        System.out.println("Testing registration with invalid credentials...<3");
        if (!webDriver.getCurrentUrl().equals(baseUrl)) {
            webDriver.get(baseUrl);
        }
        WebDriverWait wait = new WebDriverWait(webDriver,Duration.ofSeconds(1000));

        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Sign In')]")));
        signInButton.click();

        WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[2]")));
        signUpButton.click();

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[1]/input")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[2]/input")));
        WebElement InstitutesField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[3]/div/div/input")));
        WebElement SignUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/button")));

        usernameField.sendKeys("invalid-email@exo.com");
        passwordField.sendKeys("short");
        InstitutesField.sendKeys("Student");
        SignUpButton.click();

        //--> wait for the error message :)
        WebElement emailError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[1]/div"))); // Update based on actual error message
        WebElement passwordError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div[2]/div/div[3]/div[1]/div"))); // Update based on actual error message

        // Validate error messages
        assertTrue(emailError.isDisplayed(), "No error message displayed for invalid email.");
        assertTrue(passwordError.isDisplayed(), "No error message displayed for weak password.");

        System.out.println("Registration failed as expected with invalid credentials.");


    }
    @AfterEach
    public void tearDown() {
        // Close the browser
        if (webDriver != null) {
            webDriver.quit();
        }
    }


}