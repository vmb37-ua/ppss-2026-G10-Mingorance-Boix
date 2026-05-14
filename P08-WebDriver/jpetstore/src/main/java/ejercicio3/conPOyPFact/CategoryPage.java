package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CategoryPage {
    private WebDriver driver;

    @FindBy(tagName = "h2")
    private WebElement categoryTitle;

    @FindBy(xpath = "//table//tr[2]//td[1]//a")
    private WebElement firstProductLink;

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return categoryTitle.getText();
    }

    public ProductPage selectFirstProduct() {
        firstProductLink.click();
        return PageFactory.initElements(driver, ProductPage.class);
    }
}