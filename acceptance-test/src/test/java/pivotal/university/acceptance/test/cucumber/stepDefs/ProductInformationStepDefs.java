package pivotal.university.acceptance.test.cucumber.stepDefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import pivotal.university.acceptance.test.config.Settings;
import pivotal.university.acceptance.test.cucumber.SharedDriver;
import pivotal.university.acceptance.test.pageObjects.PivotalHDProductPage;
import pivotal.university.acceptance.test.pageObjects.TerminalPage;
import pivotal.university.acceptance.test.utils.frameworkUtils;

import static org.junit.Assert.assertTrue;

public class ProductInformationStepDefs {
    private PivotalHDProductPage pivotalHDProductPage;
    private TerminalPage terminalPage;

    public ProductInformationStepDefs(SharedDriver webDriver)
    {
        pivotalHDProductPage = new PivotalHDProductPage(webDriver);
        terminalPage = new TerminalPage(webDriver);
    }

    @Given("^I am on the Pivotal HD product page$")
    public void I_am_on_the_Pivotal_HD_product_page() throws Throwable {
        pivotalHDProductPage.goToPage();
        assertTrue(pivotalHDProductPage.isCurrentPage());
    }

    //TODO : add the java code to a method and not leave it here
    @When("^I view the interactive training page$")
    public void I_view_the_interactive_training_page() throws Throwable {
        //pivotalHDProductPage.beginTraining();
        terminalPage.goToPage();

        for (int i=0; i< Settings.browserTimeout; i++) {
            //assume response received (and stop waiting) if last line doesn't contain our command
            if(!terminalPage.isTextPresentOnPage("connected to a PivotalHD node")) {
                break;
            }

            //wait for 1 second and try again
            frameworkUtils.wait(1);
        }
    }
}
