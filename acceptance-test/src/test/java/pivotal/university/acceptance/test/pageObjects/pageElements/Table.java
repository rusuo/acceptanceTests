package pivotal.university.acceptance.test.pageObjects.pageElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Table {
    private WebElement element;
    private List<WebElement> tableRows;

    public Table(WebElement element) {
        this.element = element;
        this.tableRows = element.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
    }

    public int getRowCount() {
        return tableRows.size();
    }

    public WebElement getCell(int row, int col) {
        return tableRows.get(row).findElements(By.tagName("td")).get(col);
    }

    public WebElement getInputCell(int row, int col){
        return tableRows.get(row).findElements(By.tagName("input")).get(col);
    }
}
