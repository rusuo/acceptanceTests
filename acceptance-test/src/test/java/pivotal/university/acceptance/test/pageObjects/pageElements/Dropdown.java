package pivotal.university.acceptance.test.pageObjects.pageElements;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pivotal.university.acceptance.test.config.Settings;

import java.util.List;

public class Dropdown {
    private Select dropdown;
    private WebElement element;

    public Dropdown(WebElement element) {
        this.element = element;
        this.dropdown = new Select(element);
    }

    /**
     * Select an entry in the dropdown by visible text
     *
     * @param value display text in dropdown
     */
    public void selectValue(String value) {
        dropdown.selectByVisibleText(value);
    }

    /**
     * Get the currently selected value
     *
     * @return selected text value
     */
    public String getSelectedValue() {
        return dropdown.getFirstSelectedOption().getText();
    }

    public List<WebElement> getSelectionOptions() {
        return dropdown.getOptions();
    }
}
