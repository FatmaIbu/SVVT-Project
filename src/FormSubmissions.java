import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class FormSubmissions {
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
    public void testValidFormSubmission() {
        System.out.println("Testing Valid submissions...");
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            // Scroll down the page to make sure the "Contact Us" section is visible
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("window.scrollBy(0, 1500);");
            Thread.sleep(3000);
            // Navigate to the contact form
            WebElement contactFormLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"comp\"]/div[3]/footer/div[1]/div[1]/div[5]")));
            contactFormLink.click();
            // Switch to the new tab
            ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(1)); // Switch to the new tab
            // Scroll down the page to make sure the "Contact Us" section is visible
            JavascriptExecutor jS = (JavascriptExecutor) webDriver;
            jS.executeScript("window.scrollBy(0, 15000);");
            Thread.sleep(1000);


            // Wait for the form to be visible in the new tab
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[1]/input")));
            WebElement emailField = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[2]/input"));
            WebElement phonenemberField = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[3]/input"));
            WebElement companynameField = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[4]/input"));
            WebElement designationField = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[5]/input"));
            WebElement ProjectedField = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[6]/input"));
            // Fill out the form with valid data
            nameField.sendKeys("John Doe"); Thread.sleep(150);
            emailField.sendKeys("johndoe@example.com");Thread.sleep(150);
            phonenemberField.sendKeys("61644774");Thread.sleep(150);
            companynameField.sendKeys("This is a test message.");Thread.sleep(150);
            designationField.sendKeys("This is a test message.");Thread.sleep(150);
            ProjectedField.sendKeys("1");Thread.sleep(150);

            // Submit the form
            WebElement submitButton = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/button"));
            submitButton.click();

            // Attempt to handle an alert if it appears (alert handling)
            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                String alertMessage = alert.getText();
                assertTrue(alertMessage.contains("Thanks"), "Alert message is not as expected.");
                alert.accept();
            } catch (TimeoutException e) {
                // If no alert, look for a success message element (toast or modal handling)
                WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"contact-us\"]/div[3]")));
                assertTrue(successMessage.isDisplayed(), "Success message not displayed.");
            };

            System.out.println("Valid form submission test passed.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed: " + e.getMessage());
        }
    }
    @Test
    public void testInvalidFormSubmission() {
        System.out.println("Testing invalid form submission...");
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            // Scroll down the page to make sure the "Contact Us" section is visible
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("window.scrollBy(0, 1000);");
            Thread.sleep(3000);
            // Navigate to the contact form
            WebElement contactFormLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"comp\"]/div[3]/footer/div[1]/div[1]/div[5]")));
            contactFormLink.click();
            // Switch to the new tab
            ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(1)); // Switch to the new tab
            // Scroll down the page to make sure the "Contact Us" section is visible
            JavascriptExecutor jS = (JavascriptExecutor) webDriver;
            jS.executeScript("window.scrollBy(0, 15000);");
            Thread.sleep(1000);


            // Wait for the form to be visible in the new tab
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[1]/input")));
            WebElement emailField = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[2]/input"));
            WebElement phonenemberField = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[3]/input"));
            WebElement companynameField = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[4]/input"));
            WebElement designationField = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[5]/input"));
            WebElement ProjectedField = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[6]/input"));
            // Fill out the form with valid data
            nameField.sendKeys(""); Thread.sleep(500);
            emailField.sendKeys("");Thread.sleep(500);
            phonenemberField.sendKeys("");Thread.sleep(500);
            companynameField.sendKeys("");Thread.sleep(500);
            designationField.sendKeys("");Thread.sleep(500);
            ProjectedField.sendKeys("");Thread.sleep(500);

            // Submit the form
            WebElement submitButton = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/button"));
            submitButton.click();

            // Wait for error messages
            WebElement nameErrormsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[1]/span")));
            WebElement emailErrormsg = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[2]/span"));
            WebElement phonenemberErrormsg = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[3]/span"));
            WebElement companynameErrormsg = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[4]/span"));
            WebElement designationErrormsg = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[5]/span"));
            //WebElement ProjectedErrormsg = webDriver.findElement(By.xpath("//*[@id=\"contact-us\"]/div[2]/form/div[6]/input"));
            // Assert that the error messages are displayed
            assertTrue(nameErrormsg.isDisplayed(), "This field is required");
            assertTrue(emailErrormsg.isDisplayed(), "This field is required");
            assertTrue(phonenemberErrormsg.isDisplayed(), "Phone number must be 4-12 digit long");
            assertTrue(companynameErrormsg.isDisplayed(), "This field is required");
            assertTrue(designationErrormsg.isDisplayed(), "This field is required");
            //assertTrue(ProjectedErrormsg.isDisplayed(), "Message error message not displayed.");

            System.out.println("Invalid form submission test passed.");

        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed: " + e.getMessage());
        }
    }




}
