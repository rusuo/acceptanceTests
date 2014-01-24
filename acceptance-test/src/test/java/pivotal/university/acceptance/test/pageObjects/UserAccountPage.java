package pivotal.university.acceptance.test.pageObjects;

import org.openqa.selenium.WebDriver;
import pivotal.university.acceptance.test.config.Settings;

public class UserAccountPage extends Page{

    private static String pageUrl = Settings.applicationUrl + "/?q=user";

    public UserAccountPage(WebDriver webDriver) {
        super(pageUrl, webDriver);
    }

}
