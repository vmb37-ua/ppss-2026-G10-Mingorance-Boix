package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public LoginPage clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign In"))).click();
        return new LoginPage(driver);
    }

    public String getWelcomeMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#jpetstore-content > div.container.py-4.px-3.px-lg-4 > div.text-end.pb-2"))).getText();
    }

    public MyAccountPage clickMyAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("dropdownMenuButton"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Account"))).click();
        return new MyAccountPage(driver);
    }
}