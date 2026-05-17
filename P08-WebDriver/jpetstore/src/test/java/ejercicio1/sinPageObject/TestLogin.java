package ejercicio1.sinPageObject;

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
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLogin {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        ChromeOptions co = new ChromeOptions();
        co.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        co.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.EAGER);

        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        co.setExperimentalOption("prefs", prefs);

        co.addArguments("--incognito");
        co.addArguments("--disable-features=PasswordLeakDetection");
        co.addArguments("--disable-popup-blocking");
        co.addArguments("--disable-save-password-bubble");

        boolean headless = Boolean.parseBoolean(System.getProperty("chromeHeadless"));
        if (headless) {
            co.addArguments("--headless=new");
            co.addArguments("--window-size=1920,1080");
            co.addArguments("--disable-gpu");
            co.addArguments("--no-sandbox");
            co.addArguments("--disable-dev-shm-usage");
        }

        driver = new ChromeDriver(co);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void R1_requirement_loginOK_should_login_with_success_when_user_account_exists() {
        driver.get("https://jpetstore.aspectran.com");
        assertEquals("JPetStore Demo", driver.getTitle());

        WebElement signInLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign In")));
        signInLink.click();

        WebElement formTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5[contains(text(), 'Please enter your username and password.')]")));
        assertTrue(formTitle.getText().contains("Please enter your username and password."));

        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.clear();
        usernameInput.sendKeys("j2ee");

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.clear();
        passwordInput.sendKeys("j2ee");

        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Login']"));
        loginButton.click();

        WebElement welcomeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#jpetstore-content > div.container.py-4.px-3.px-lg-4 > div.text-end.pb-2")));
        String welcomeMessage = welcomeElement.getText();

        WebElement userDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("dropdownMenuButton")));
        userDropdown.click();

        WebElement myAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Account")));
        myAccountLink.click();

        WebElement userInfoHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='User Information']")));
        assertEquals("User Information", userInfoHeading.getText());

        WebElement firstNameInput = driver.findElement(By.name("firstName"));
        String nombreReal = firstNameInput.getDomProperty("value");

        assertEquals("Welcome " + nombreReal + "!", welcomeMessage);
    }

    @Test
    public void R2_requirement_loginFailed_should_fail_when_user_account_not_exists() {
        driver.get("https://jpetstore.aspectran.com");
        assertEquals("JPetStore Demo", driver.getTitle());

        WebElement signInLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign In")));
        signInLink.click();

        WebElement formTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5[contains(text(), 'Please enter your username and password.')]")));
        assertTrue(formTitle.getText().contains("Please enter your username and password."));

        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.clear();
        usernameInput.sendKeys("j2ee");

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.clear();
        passwordInput.sendKeys("abcd");

        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Login']"));
        loginButton.click();

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'alert-danger')]")));
        assertTrue(errorMsg.getText().contains("Invalid username or password. Signon failed."));
    }
}