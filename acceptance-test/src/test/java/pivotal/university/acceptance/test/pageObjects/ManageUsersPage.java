package pivotal.university.acceptance.test.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pivotal.university.acceptance.test.config.Settings;
import pivotal.university.acceptance.test.pageObjects.pageElements.Dropdown;
import pivotal.university.acceptance.test.pageObjects.pageElements.Table;
import pivotal.university.acceptance.test.utils.frameworkUtils;

import java.util.List;

public class ManageUsersPage extends Page{
    private static String pageUrl = Settings.applicationUrl + "/?q=admin/people";

    private By updateButton = By.id("edit-submit--2");
    private By updateOptions = By.id("edit-operation");
    private By deleteAccountCompletely = By.id("edit-user-cancel-method--5");
    private Page page = new Page("", webDriver);
    private Table users;
    private AddUserPage addUserPage = new AddUserPage(webDriver);

    public ManageUsersPage(WebDriver webDriver) {
        super(pageUrl, webDriver);
    }

    public boolean isUserDisplayedInTable(String userName){

        List<WebElement> tableList = webDriver.findElements(By.tagName("table"));
        for(WebElement table:tableList) {
            if(table.getText().contains(userName)) {
                users = new Table(table);
                return true;
            }
        }

        return false;
    }

    /**
     * set a specific role for a user
     */
    public void updateUser(String role, String userName) {
        int COL_USER_NAME = 1;
        int COL_SELECT = 0;
        Dropdown optionsDropdown = new Dropdown(webDriver.findElement(updateOptions));

        if (isUserDisplayedInTable(userName)){
            for(int i=0; i<users.getRowCount();i++) {

                if (users.getCell(i, COL_USER_NAME).getText().equals(userName)) {
                    users.getInputCell(i, COL_SELECT).click();

                    optionsDropdown.selectValue(role);

                    webDriver.findElement(updateButton).click();
                    break;
                }
            }
        }
    }

    /**
     * Find a user using his username and delete it
     */
    public void deleteUser(String userName) throws InterruptedException {
        int COL_USER_NAME = 1;
        int COL_SELECT = 0;

        Dropdown optionsDropdown = new Dropdown(webDriver.findElement(updateOptions));
        if (isUserDisplayedInTable(userName)){
            for(int i=0; i<users.getRowCount();i++) {

                if (users.getCell(i, COL_USER_NAME).getText().equals(userName)) {
                    users.getInputCell(i, COL_SELECT).click();

                    optionsDropdown.selectValue("Cancel the selected user accounts");

                    webDriver.findElement(updateButton).click();

                    webDriver.findElement(deleteAccountCompletely).click();

                    addUserPage.getSubmitButton().click();

                    for (int j=0; j<Settings.browserTimeout; j++) {
                        if(page.isTextPresentOnPage("has been deleted")){
                            break;
                        }
                        //wait for 1 second and try again
                        frameworkUtils.wait(1);
                    }
                    break;
                }
            }
        }
    }

    public String getUserRole(String userName){
        int COL_USER_NAME = 1;
        int COL_ROLE = 3;

        if (isUserDisplayedInTable(userName)){
            for(int i=0; i<users.getRowCount();i++) {
                if (users.getCell(i, COL_USER_NAME).getText().equals(userName)) {
                   return users.getCell(i, COL_ROLE).getText();
                }
            }
        }

        return "";
    }

    public void editUser(String userName){
        int COL_USER_NAME = 1;

        if (isUserDisplayedInTable(userName)){
            for(int i=0; i<users.getRowCount();i++) {

                if (users.getCell(i, COL_USER_NAME).getText().equals(userName)) {
                    webDriver.findElement(By.linkText("edit")).click();
                    break;
                }
            }
        }

    }
}
