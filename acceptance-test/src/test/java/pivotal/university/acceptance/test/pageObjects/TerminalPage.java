package pivotal.university.acceptance.test.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pivotal.university.acceptance.test.config.Settings;
import pivotal.university.acceptance.test.utils.frameworkUtils;

import java.util.List;

public class TerminalPage extends Page {
    private static String pageUrl = Settings.applicationUrl + "/?q=node/11";

    public TerminalPage(WebDriver webDriver) {
        super(pageUrl, webDriver);
    }

    private By terminalWindow = By.id("termDiv");

    public boolean isTerminalWindowPresent()
    {
        return webDriver.findElement(terminalWindow).isDisplayed();
    }

    private List<WebElement> getTerminalRows() {
        return  webDriver.findElement(terminalWindow).findElements(By.tagName("tr"));
    }

    public boolean isMessagePresent(String message)
    {

        for(WebElement terminalRow : getTerminalRows())
        {
            if(terminalRow.getText().contains(message))
            {
                return true;
            }
        }
        return false;
    }


    public String getLastConsoleLine() {
        int nonBlankRows = 0;
        List<WebElement> terminalRows = getTerminalRows();
        //loop backwards from the end of the list of console rows
        for(int i=terminalRows.size()-1; i>=0; i--)
        {
            //strip spaces and console prompt from rows before checking for content
            if(!terminalRows.get(i).getText().trim().equals(""))
            {
                return terminalRows.get(i).getText();
            }
        }
        return "";
    }

    public int getMessageCount() {
        int nonBlankRows = 0;

        List<WebElement> terminalRows = webDriver.findElement(terminalWindow).findElements(By.tagName("tr"));
        for(WebElement terminalRow : getTerminalRows())
        {
            //strip spaces and console prompt from rows before checking for content
            if(!terminalRow.getText().trim().replace("PivotalHD>", "").equals(""))
            {
                nonBlankRows = nonBlankRows + 1;
            }
        }
        return nonBlankRows;
    }

    public void sendCommand(String command) {
        webDriver.findElement(terminalWindow).sendKeys(command + Keys.ENTER);

        //wait until a response is received, up to the global timeout
        for (int i=0; i<Settings.browserTimeout; i++) {
            //assume response received (and stop waiting) if last line doesn't contain our command
            if(!getLastConsoleLine().trim().contains(command)) { break; }

            //wait for 1 second and try again
            frameworkUtils.wait(1);
        }
    }
}
