package pivotal.university.acceptance.test.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pivotal.university.acceptance.test.config.Settings;

public class TutorialWindowPage extends Page{

    private static String pageUrl = Settings.applicationUrl + "/?q=node/11";

    public TutorialWindowPage(WebDriver webDriver) {
        super(pageUrl, webDriver);
    }

    private By command = By.xpath("//div[@id='step-query']/pre");
    private By linkToPasteTheCommand = By.partialLinkText("click here to paste this query into the terminal");
    private By nextButton = By.className("next");
    private By stepDescription = By.id("step-description");
    private By learnMoreButton = By.linkText("Now take the quiz...");

    public void clickCommandToPasteInTutorial(){
        webDriver.findElement(linkToPasteTheCommand).click();
    }

    public void goToNextStepOfTutorial(){
        webDriver.findElement(nextButton).click();
    }

    public String getMessageDescription(){
        return webDriver.findElement(stepDescription).getText();
    }

    public String getCommand(){
        String[] text = webDriver.findElement(command).getText().split(";");
        return text[0] + ";";
    }

    public boolean isNextButtonEnabled(){
        return webDriver.findElement(nextButton).isEnabled();
    }

    public void learnMore(){
        webDriver.findElement(learnMoreButton).click();
    }
}

