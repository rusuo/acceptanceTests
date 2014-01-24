package pivotal.university.acceptance.test.cucumber.stepDefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pivotal.university.acceptance.test.config.Messages;
import pivotal.university.acceptance.test.config.Settings;
import pivotal.university.acceptance.test.cucumber.SharedDriver;
import pivotal.university.acceptance.test.pageObjects.Page;
import pivotal.university.acceptance.test.pageObjects.PivotalHDProductPage;
import pivotal.university.acceptance.test.pageObjects.TerminalPage;
import pivotal.university.acceptance.test.pageObjects.TutorialWindowPage;
import pivotal.university.acceptance.test.utils.frameworkUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TerminalStepDefs {
    private TerminalPage terminalPage;
    private TutorialWindowPage tutorialWindowPage;
    private PivotalHDProductPage pivotalHDProductPage;
    private Page page;

    public TerminalStepDefs(SharedDriver webDriver)
    {
        terminalPage = new TerminalPage(webDriver);
        tutorialWindowPage = new TutorialWindowPage(webDriver);
        pivotalHDProductPage = new PivotalHDProductPage(webDriver);
        page = new Page("", webDriver);
    }

    @When("^I submit the command \"([^\"]*)\" to the terminal$")
    public void submit_the_command_to_the_terminal(String terminalCommand) throws Throwable {
        terminalPage.sendCommand(terminalCommand);
    }

    @Then("^I should see a terminal window$")
    public void I_should_see_a_terminal_window() throws Throwable {
        assertTrue(terminalPage.isTerminalWindowPresent());
    }

    @Then("^the terminal window should show the \"([^\"]*)\" message$")
    public void the_terminal_window_should_show_the_message(String messageKey) throws Throwable {
        String expectedMessage = Messages.getMessage(messageKey);
        assertTrue("Expected message '" + Messages.getMessage(messageKey) + "' is not present in the terminal", terminalPage.isMessagePresent(expectedMessage));
    }

    @Then("^the terminal window should contain '(\\d+)' messages$")
    public void the_terminal_window_should_contain_messages(int expectedMessageCount) throws Throwable {
        assertEquals(expectedMessageCount, terminalPage.getMessageCount());
    }

    @Then("^I should see the command \"([^\"]*)\" in the terminal window$")
    public void I_should_see_the_command_in_the_terminal_window(String command) throws Throwable {
        Thread.sleep(3000);
        assertTrue("messages don't match", terminalPage.isMessagePresent(command));
    }

    @And("^I start the tutorial$")
    public void I_start_the_tutorial() throws Throwable {
         tutorialWindowPage.goToNextStepOfTutorial();
    }

    @When("^I click to paste the commands from the tutorial window$")
    public void I_click_to_paste_the_commands_from_the_tutorial_window() throws Throwable {
        while (!tutorialWindowPage.getMessageDescription().equals("You have now finished the tutorial.")){
            tutorialWindowPage.clickCommandToPasteInTutorial();
            tutorialWindowPage.goToNextStepOfTutorial();
        }
    }

    @When("^I type the commands shown in the tutorial$")
    public void I_type_the_commands_shown_in_the_tutorial() throws Throwable {
        String command;
        while (!tutorialWindowPage.getMessageDescription().equals("You have now finished the tutorial.")){
            command = tutorialWindowPage.getCommand();
            terminalPage.sendCommand(command);
            for (int j=0; j< Settings.browserTimeout; j++) {
                if(page.isTextPresentOnPage("OK")){
                    break;
                }
                //wait for 1 second and try again
                frameworkUtils.wait(1);
            }
            assertTrue(tutorialWindowPage.isNextButtonEnabled());
            tutorialWindowPage.goToNextStepOfTutorial();
        }
    }

    @Then("^I should receive the message that I finished the tutorial$")
    public void I_should_receive_the_message_that_I_finished_the_tutorial() throws Throwable {
        assertEquals(tutorialWindowPage.getMessageDescription(), "You have now finished the tutorial.");
    }

    @Then("^I shouldn't be able to continue to my tutorial$")
    public void I_shouldn_t_be_able_to_continue_to_my_tutorial() throws Throwable {
        assertFalse(tutorialWindowPage.isNextButtonEnabled());
    }

    @And("^the terminal should show \"([^\"]*)\" rows returned$")
    public void the_terminal_should_show_rows_returned(String number) throws Throwable {
        if (number.equals("1")){
          assertTrue("Message in the terminal" + terminalPage.getLastConsoleLine(), terminalPage.isMessagePresent(number + " row affected."));
        } else {
          assertTrue("Message in the terminal" + terminalPage.getLastConsoleLine(), terminalPage.isMessagePresent(number + " rows returned."));
        }
    }

    @When("^I complete the tutorial$")
    public void I_complete_the_tutorial() throws Throwable {
     //   pivotalHDProductPage.beginTraining();
        terminalPage.goToPage();

        for (int i=0; i< Settings.browserTimeout; i++) {
            //assume response received (and stop waiting) if last line doesn't contain our command
            if(!terminalPage.isTextPresentOnPage("connected to a PivotalHD node")) {
                break;
            }

            //wait for 1 second and try again
            frameworkUtils.wait(1);
        }

        tutorialWindowPage.goToNextStepOfTutorial();
        while (!tutorialWindowPage.getMessageDescription().equals("You have now finished the tutorial.")){
            tutorialWindowPage.clickCommandToPasteInTutorial();
            tutorialWindowPage.goToNextStepOfTutorial();
        }
       tutorialWindowPage.learnMore();
    }

    @And("^I click on Learn more without finishing the tutorial$")
    public void I_click_on_Learn_more_without_finishing_the_tutorial() throws Throwable {
        tutorialWindowPage.learnMore();
    }
}
