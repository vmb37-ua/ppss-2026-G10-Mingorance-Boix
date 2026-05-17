package ejercicio3.conPOyPFact;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestComprareProducts {

    private WebDriver driver;

    @BeforeAll
    public static void setupGlobal() {
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

        WebDriver driverTemporal = new ChromeDriver(co);
        Cookies.storeCookiesToFile("https://jpetstore.aspectran.com", "j2ee", "j2ee", "cookies.data");
    }

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

        driver = new ChromeDriver(co);

        Cookies.loadCookiesFromFile(driver, "https://jpetstore.aspectran.com", "cookies.data");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void R5_requirement_compareProducts_And_BuySelectedOne() {
        // Arrange
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);

        // Act
        String homeTitle = homePage.getTitle();
        boolean isLogged = homePage.isMyOrdersButtonPresent();

        CategoryPage categoryPage = homePage.goToCats();
        String catTitle = categoryPage.getTitle();

        ProductPage catPage = categoryPage.selectFirstProduct();
        String catName = catPage.getProductName();
        String catPrice = catPage.getProductPrice();

        String ventanaGato = driver.getWindowHandle();

        CategoryPage dogsCategoryPage = catPage.openDogsInNewTab();
        String dogTitle = dogsCategoryPage.getTitle();

        ProductPage dogPage = dogsCategoryPage.selectFirstProduct();
        String dogName = dogPage.getProductName();

        driver.close();
        driver.switchTo().window(ventanaGato);

        CartPage cartPage = catPage.addToCart();

        String itemInCart = cartPage.getFirstItemName();
        String totalInCart = cartPage.getTotal();

        // Assert
        assertEquals("JPetStore Demo", homeTitle);
        assertTrue(isLogged);
        assertEquals("Cats", catTitle);
        assertEquals("Dogs", dogTitle);
        assertNotEquals(catName, dogName);
        assertTrue(itemInCart.contains(catName));
        assertEquals(totalInCart, catPrice);
    }
}