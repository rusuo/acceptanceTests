package pivotal.university.acceptance.test.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pivotal.university.acceptance.test.config.Settings;
import pivotal.university.acceptance.test.testData.User;

public class RegisterNewAccountPage extends Page{
    private static String pageUrl = Settings.applicationUrl + "/?q=user/register";

    public RegisterNewAccountPage(WebDriver webDriver) {
        super(pageUrl, webDriver);
    }

    private By registerLink = By.id("register-link");
    private By usernameTextField = By.id("edit-name--2");
    private By emailTextField = By.id("edit-mail");
    private By passwordTextField = By.id("edit-pass-pass1");
    private By confirmPassTextField = By.id("edit-pass-pass2");
    private By submitButton = By.id("edit-submit--2");

    public boolean isRegisterLinkDisplayed(){
        return webDriver.findElement(registerLink).isDisplayed();
    }

    public void registerAccount(User user){
        webDriver.findElement(usernameTextField).sendKeys(user.getUsername());
        webDriver.findElement(emailTextField).sendKeys(user.getEmailAddress());
        webDriver.findElement(passwordTextField).sendKeys(user.getPassword());
        webDriver.findElement(confirmPassTextField).sendKeys(user.getPassword());
        webDriver.findElement(submitButton).click();
    }

}
