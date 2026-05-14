package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getFormTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(), 'Please enter your username and password.')]"))).getText();
    }

    public HomePage login(String username, String password) {
        fillLoginForm(username, password);
        return new HomePage(driver);
    }

    public LoginPage loginFailed(String username, String password) {
        fillLoginForm(username, password);
        return this;
    }

    private void fillLoginForm(String username, String password) {
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        userField.clear();
        userField.sendKeys(username);

        WebElement passField = driver.findElement(By.name("password"));
        passField.clear();
        passField.sendKeys(password);

        driver.findElement(By.xpath("//button[text()='Login']")).click();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'alert-danger')]"))).getText();
    }
}