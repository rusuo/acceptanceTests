package pivotal.university.acceptance.test.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pivotal.university.acceptance.test.config.Settings;
import pivotal.university.acceptance.test.utils.frameworkUtils;

public class PivotalHDProductPage extends Page {
    private static String pageUrl = Settings.applicationUrl + "";

    private By managementMenu = By.id("block-system-management");

    public PivotalHDProductPage(WebDriver webDriver) {
        super(pageUrl, webDriver);
    }

    private By beginTrainingButton = By.linkText("Free training");
    TerminalPage terminalPage = new TerminalPage(webDriver);

    public void beginTraining() throws InterruptedException {

        webDriver.findElement(beginTrainingButton).click();

        for (int i=0; i<Settings.browserTimeout; i++) {
            //assume response received (and stop waiting) if last line doesn't contain our command
            if(!terminalPage.isTextPresentOnPage("connected to a PivotalHD node")) {
                break;
            }

            //wait for 1 second and try again
            frameworkUtils.wait(1);
        }


    }

    public boolean isManagementMenuDisplayed(){
        return webDriver.findElement(managementMenu).isDisplayed();
    }
}
