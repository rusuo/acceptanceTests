package pivotal.university.acceptance.test.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pivotal.university.acceptance.test.testData.User;

import java.util.List;

public class Page {
    protected final String pageUrl;
    protected final WebDriver webDriver;

    private By usernameField = By.id("edit-name");
    private By passwordField = By.id("edit-pass");
    private By logInButton = By.id("edit-submit");
    private By logoutLink = By.partialLinkText("Log out");
    private By myAccountButton = By.linkText("My account");

    private By loginLink = By.linkText("Log in");

    public Page(String pageUrl, WebDriver webDriver) {
        this.pageUrl = pageUrl;
        this.webDriver = webDriver;
    }

    public void goToPage() {
        webDriver.get(pageUrl);
    }

    public boolean isCurrentPage() {
        return webDriver.getCurrentUrl().contains(pageUrl);
    }

    public boolean isTextPresentOnPage(String text) {
        List<WebElement> elements = webDriver.findElements(By.xpath("//*[contains(.,'" + text + "')]"));
        return elements.size() > 0;
    }

    public boolean isLogoutButtonVisible(){
        return webDriver.findElements(logoutLink).size()>0;
    }

    public void login(User user) {
        goToLoginPage();

        webDriver.findElement(usernameField).clear();
        webDriver.findElement(usernameField).sendKeys(user.getUsername());
        webDriver.findElement(passwordField).sendKeys(user.getPassword());
        webDriver.findElement(logInButton).click();
    }

    public void goToLoginPage(){
        webDriver.findElement(loginLink).click();
    }

    public void logout(){
        if (isLogoutButtonVisible()){
            webDriver.findElement(logoutLink).click();
        }
    }

    public void gotoMyAccount() {
        webDriver.findElement(myAccountButton).click();
    }

    public String getPageTitle(){
        return webDriver.getTitle();
    }

    public WebElement getUsernameTextField(){
        return webDriver.findElement(usernameField);
    }

    public WebElement getPasswordTextField(){
        return webDriver.findElement(passwordField);
    }
}
