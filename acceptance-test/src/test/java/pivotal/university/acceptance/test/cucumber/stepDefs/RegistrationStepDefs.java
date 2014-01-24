package pivotal.university.acceptance.test.cucumber.stepDefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.runtime.PendingException;
import pivotal.university.acceptance.test.cucumber.SharedDriver;
import pivotal.university.acceptance.test.cucumber.UserList;
import pivotal.university.acceptance.test.pageObjects.LandingPage;
import pivotal.university.acceptance.test.pageObjects.Page;
import pivotal.university.acceptance.test.pageObjects.RegisterNewAccountPage;
import pivotal.university.acceptance.test.testData.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegistrationStepDefs{

    private RegisterNewAccountPage registerNewAccountPage;
    private UserList userList;
    private Page page;
    private LandingPage landingPage;

    public RegistrationStepDefs(SharedDriver webDriver, UserList userList){
        registerNewAccountPage = new RegisterNewAccountPage(webDriver) ;
        this.userList = userList;
        page = new Page("",webDriver);
        landingPage = new LandingPage(webDriver);
     }

    @Given("^I am on registration page$")
    public void I_am_on_registration_page() throws Throwable {
       registerNewAccountPage.goToPage();
       assertTrue (registerNewAccountPage.isCurrentPage());
    }

    @And("^I create a new account$")
    public void I_create_a_new_account() throws Throwable {
        userList.addUser();
        registerNewAccountPage.registerAccount(userList.getUser(0));
 //       assertTrue("Message on the page does not match", page.isTextPresentOnPage("A validation e-mail has been sent to your e-mail address. In order to gain full access to the site, you will need to follow the instructions in that message."));
    }

    @Then("^I see the full content of the site$")
    public void I_see_the_full_content_of_the_site() throws Throwable {
        assertTrue(page.isLogoutButtonVisible());
        assertTrue("Actual page title: " + page.getPageTitle() + "does not match with your string", page.getPageTitle().contains("Home Page"));
        page.logout();
    }

    @Given("^I have registered a new account$")
    public void I_have_registered_a_new_account() throws Throwable {
        registerNewAccountPage.goToPage();
        assertTrue (registerNewAccountPage.isCurrentPage());
        assertTrue(registerNewAccountPage.isRegisterLinkDisplayed());
        userList.addUser();
        registerNewAccountPage.registerAccount(userList.getUser(0));
     //   assertTrue("Message on the page does not match", page.isTextPresentOnPage("A validation e-mail has been sent to your e-mail address. In order to gain full access to the site, you will need to follow the instructions in that message."));
        page.logout();
    }


}
