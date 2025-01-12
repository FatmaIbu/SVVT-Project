import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SwitchModeTest {
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
        System.out.println("Setting up resources for Switch Mode Tests...");
    }
    @Test
    public void testSwitchModes(){
        System.out.println("Testing the switch button mode...");
        try {
            if(webDriver == null){
                fail("webDiriver not initilaized");
            }

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

            // Debugging: Print page source to verify element presence
           // System.out.println(webDriver.getPageSource());

            WebElement toggleButton = webDriver.findElement(By.xpath("//*[@id='topMainHeader']//button[contains(@aria-label, 'Toggle GFG Theme')]"));
            WebElement modeIndicator = webDriver.findElement(By.xpath("//*[@id='topMainHeader']//span[contains(@class, 'darkModeTooltipText')]"));

            String initialModetext = modeIndicator.getText();
            System.out.println("Initial mode text: "+initialModetext);

            toggleButton.click();
            Thread.sleep(1000);

            String toggledModetext = modeIndicator.getText();
            System.out.println("Toggled mode text; "+ toggledModetext);
            assertNotEquals("Mode should change after toggle",initialModetext,toggledModetext);

            toggleButton.click();
           Thread.sleep(1000);


        }
        catch (Exception e) {
            e.printStackTrace();
            fail("Test failed: " + e.getMessage());
        }
    }

}
