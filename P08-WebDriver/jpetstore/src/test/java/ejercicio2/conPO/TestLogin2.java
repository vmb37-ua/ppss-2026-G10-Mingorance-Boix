package ejercicio2.conPO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLogin2 {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions co = new ChromeOptions();
        co.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        co.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.EAGER);

        boolean headless = Boolean.parseBoolean(System.getProperty("chromeHeadless"));
        if (headless) {
            co.addArguments("--headless=new");
            co.addArguments("--window-size=1920,1080");
            co.addArguments("--disable-gpu");
            co.addArguments("--no-sandbox");
            co.addArguments("--disable-dev-shm-usage");
        }

        driver = new ChromeDriver(co);
    }

    @AfterEach
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void R3_requierement_PO_loginOK_should_login_with_success_when_user_account_exists() {
        // Arrange
        driver.get("https://jpetstore.aspectran.com");
        HomePage homePage = new HomePage(driver);

        // Act
        String initialTitle = homePage.getTitle();
        LoginPage loginPage = homePage.clickSignIn();
        String formTitle = loginPage.getFormTitle();

        HomePage loggedInHomePage = loginPage.login("j2ee", "j2ee");
        String welcomeMsg = loggedInHomePage.getWelcomeMessage();

        MyAccountPage myAccountPage = loggedInHomePage.clickMyAccount();
        String accountTitle = myAccountPage.getTitle();
        String firstName = myAccountPage.getFirstName();

        // Assert
        assertEquals("JPetStore Demo", initialTitle);
        assertTrue(formTitle.contains("Please enter your username and password."));
        assertEquals("User Information", accountTitle);
        assertEquals("Welcome " + firstName + "!", welcomeMsg);
    }

    @Test
    public void R4_requierement_PO_loginFailed_should_fail_when_user_account_not_exists() {
        // Arrange
        driver.get("https://jpetstore.aspectran.com");
        HomePage homePage = new HomePage(driver);

        // Act
        String initialTitle = homePage.getTitle();
        LoginPage loginPage = homePage.clickSignIn();
        String formTitle = loginPage.getFormTitle();

        loginPage.loginFailed("j2ee", "abcd");
        String errorMsg = loginPage.getErrorMessage();

        // Assert
        assertEquals("JPetStore Demo", initialTitle);
        assertTrue(formTitle.contains("Please enter your username and password."));
        assertTrue(errorMsg.contains("Invalid username or password.  Signon failed."));
    }
}