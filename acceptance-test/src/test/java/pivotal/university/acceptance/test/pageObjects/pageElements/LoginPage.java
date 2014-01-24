package pivotal.university.acceptance.test.pageObjects.pageElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pivotal.university.acceptance.test.testData.User;

/**
 * Created with IntelliJ IDEA.
 * User: oanarusu
 * Date: 01/11/2013
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class LoginPage {

    private WebDriver webDriver;

    private By usernameField = By.id("edit-name");
    private By passwordField = By.id("edit-pass");
    private By logInButton = By.id("edit-submit");
    private By logoutLink = By.partialLinkText("Log out");
    private By loginLink = By.linkText("Log in");

    public void goToLoginPage(){
        webDriver.findElement(loginLink).click();
    }

    public void login(User user) {
        goToLoginPage();

        webDriver.findElement(usernameField).sendKeys(user.getUsername());
        webDriver.findElement(passwordField).sendKeys(user.getPassword());
        webDriver.findElement(logInButton).click();
    }


    public boolean isLogoutButtonVisible(){
        return webDriver.findElements(logoutLink).size()>0;
    }

    public void logout(){
        if (isLogoutButtonVisible()){
            webDriver.findElement(logoutLink).click();
        }
    }

}
