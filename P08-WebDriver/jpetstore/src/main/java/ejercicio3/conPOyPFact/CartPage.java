package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage {
    private WebDriver driver;

    // Buscamos dentro del tbody la primera fila (tr[1]) y sacamos su tercera columna (td[3] = Description)
    @FindBy(xpath = "//tbody[contains(@class, 'table-group-divider')]/tr[1]/td[3]")
    private WebElement firstItemName;

    // Buscamos en el pie de tabla (tfoot) la fila de totales, y cogemos la cuarta columna (td[4] = Total Cost)
    @FindBy(xpath = "//tfoot/tr/td[4]")
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