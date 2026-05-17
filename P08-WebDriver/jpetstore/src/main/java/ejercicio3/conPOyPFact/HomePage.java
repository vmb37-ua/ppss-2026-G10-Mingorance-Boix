package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {
    private WebDriver driver;

    // Usamos List para comprobar si existe sin que salte excepción
    @FindBy(linkText = "My Orders")
    private List<WebElement> myOrdersButtonList;

    @FindBy(linkText = "Cats")
    private WebElement linkCats;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public boolean isMyOrdersButtonPresent() {
        return !myOrdersButtonList.isEmpty();
    }

    public CategoryPage goToCats() {
        linkCats.click();
        return PageFactory.initElements(driver, CategoryPage.class);
    }
}