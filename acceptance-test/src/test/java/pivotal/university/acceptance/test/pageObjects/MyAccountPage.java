package pivotal.university.acceptance.test.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pivotal.university.acceptance.test.config.Settings;
import pivotal.university.acceptance.test.testData.User;

public class MyAccountPage extends Page{
    private static String pageUrl = Settings.applicationUrl +"?q=user";
    private By currentPassword = By.id("edit-current-pass");
    private By editOption = By.linkText("Edit");

    AddUserPage addUserPage = new AddUserPage(webDriver);

    public MyAccountPage(WebDriver webDriver) {
        super(pageUrl, webDriver);
    }

    public void changePassword(User user){
       //old password
       webDriver.findElement(currentPassword).sendKeys(user.getPassword());
       //get new password and confirm it
       addUserPage.getPasswordTextField().sendKeys(user.setNewPassword());
       addUserPage.getConfirmPasswordTextField().sendKeys(user.getPassword());
       addUserPage.getSubmitButton().click();
    }

    public void gotoEditSection() {
        webDriver.findElement(editOption).click();
    }
}
