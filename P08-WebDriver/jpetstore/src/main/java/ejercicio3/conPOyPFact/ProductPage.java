package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {
    private WebDriver driver;

    @FindBy(xpath = "//font[b]") // Etiqueta típica de nombres en JPetStore
    private WebElement productName;

    @FindBy(xpath = "//table//tr[last()]//td[1]") // Celda del precio
    private WebElement productPrice;

    @FindBy(xpath = "//div[@id='QuickLinks']//img[contains(@src, 'dogs')]")
    private WebElement linkDogs;

    @FindBy(linkText = "Add to Cart")
    private WebElement addToCartBtn;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getProductPrice() {
        return productPrice.getText();
    }

    // Código exacto proporcionado en el guion (Paso 7)
    public CategoryPage openDogsInNewTab() {
        String url = linkDogs.getDomProperty("href");
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);
        return PageFactory.initElements(driver, CategoryPage.class);
    }

    public CartPage addToCart() {
        addToCartBtn.click();
        return PageFactory.initElements(driver, CartPage.class);
    }
}