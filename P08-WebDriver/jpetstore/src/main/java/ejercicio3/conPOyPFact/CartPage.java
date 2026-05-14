package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage {
    private WebDriver driver;

    @FindBy(xpath = "//table//tr[2]//td[3]") // Columna de descripción
    private WebElement firstItemName;

    @FindBy(xpath = "//table//tr[last()]//td[1]") // Fila del total
    private WebElement totalAmount;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getFirstItemName() {
        return firstItemName.getText();
    }

    public String getTotal() {
        return totalAmount.getText();
    }
}