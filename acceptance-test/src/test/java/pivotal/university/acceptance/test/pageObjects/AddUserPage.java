package pivotal.university.acceptance.test.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pivotal.university.acceptance.test.config.Settings;
import pivotal.university.acceptance.test.testData.User;

public class AddUserPage extends Page {
    private static String pageUrl = Settings.applicationUrl + "/?q=admin/people/create";

    private By emailAddressTextField = By.id("edit-mail");
    private By passwordTextField = By.id("edit-pass-pass1");
    private By confirmPasswordTextField = By.id("edit-pass-pass2");
    private By notifyUsersOfNewAccount = By.id("edit-notify");
    private By submitButton = By.id("edit-submit");
    private By unverifiedUserCheckBox = By.id("edit-roles-4");
    private By verifiedUserCheckBox = By.id("edit-roles-5");
    Page page = new Page("",webDriver);

    public AddUserPage(WebDriver webDriver) {
        super(pageUrl, webDriver);
    }

    public void addUserDetails(User user) {
        page.getUsernameTextField().sendKeys(user.getUsername());
        webDriver.findElement(emailAddressTextField).sendKeys(user.getEmailAddress());
        webDriver.findElement(passwordTextField).sendKeys(user.getPassword());
        webDriver.findElement(confirmPasswordTextField).sendKeys(user.getPassword());
        webDriver.findElement(notifyUsersOfNewAccount).click();
        webDriver.findElement(submitButton).click();
    }

    public WebElement getPasswordTextField(){
        return webDriver.findElement(passwordTextField);
    }

    public WebElement getConfirmPasswordTextField(){
        return webDriver.findElement(confirmPasswordTextField);
    }

    public WebElement getSubmitButton(){
        return webDriver.findElement(submitButton);
    }

    /**
     * remove 'unverified user' role and set 'verified user' role
     */
    public void setVerifiedUserRole(){
        webDriver.findElement(unverifiedUserCheckBox).click();
        webDriver.findElement(verifiedUserCheckBox).click();
        webDriver.findElement(submitButton).click();
    }
}
