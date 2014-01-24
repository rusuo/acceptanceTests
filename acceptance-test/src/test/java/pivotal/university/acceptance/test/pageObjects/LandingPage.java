package pivotal.university.acceptance.test.pageObjects;

import org.openqa.selenium.WebDriver;
import pivotal.university.acceptance.test.config.Settings;

public class LandingPage extends Page {

    private static String pageUrl = Settings.applicationUrl + "/?q=node/1";

    public LandingPage(WebDriver webDriver) {
        super(pageUrl, webDriver);
    }
}
