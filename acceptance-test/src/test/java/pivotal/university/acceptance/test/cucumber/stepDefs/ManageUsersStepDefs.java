package pivotal.university.acceptance.test.cucumber.stepDefs;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import pivotal.university.acceptance.test.config.Messages;
import pivotal.university.acceptance.test.config.Settings;
import pivotal.university.acceptance.test.cucumber.SharedDriver;
import pivotal.university.acceptance.test.cucumber.UserList;
import pivotal.university.acceptance.test.pageObjects.*;
import pivotal.university.acceptance.test.testData.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ManageUsersStepDefs {
    private PivotalHDProductPage pivotalHDProductPage;
    private ManageUsersPage manageUsersPage;
    private AddUserPage addUserPage;
    private UserList userList;
    private MyAccountPage myAccountPage;
    private UserAccountPage userAccount;
    private Page page;
    private User user;

    public ManageUsersStepDefs(SharedDriver webDriver, UserList userList)
    {
        pivotalHDProductPage = new PivotalHDProductPage(webDriver);
        manageUsersPage = new ManageUsersPage(webDriver);
        addUserPage = new AddUserPage(webDriver);
        this.userList = userList;
        myAccountPage = new MyAccountPage(webDriver);
        userAccount = new UserAccountPage(webDriver);
        page = new Page("",webDriver);
        user = new User();
    }

    @Given("^I am logged in as an admin$")
    public void I_am_logged_in_as_an_admin() throws Throwable {
        pivotalHDProductPage.goToPage();

        user.setUsername(Settings.adminUsername);
        user.setPassword(Settings.adminPassword);
        page.login(user);

        assertTrue(page.isLogoutButtonVisible());
        //?menu displayed?
    }

    @When("^I view the the People management page$")
    public void I_view_the_the_People_management_page() throws Throwable {
        manageUsersPage.goToPage();
        assertTrue(manageUsersPage.isCurrentPage());
    }

    /**
     * Verify that the table of users contains an entry for "admin" user
     */
    @Then("^I should see the list of existing users$")
    public void I_should_see_the_list_of_existing_users() throws Throwable {
        assertTrue("The specified user '" + user.getAdminUsername() + "' is not visible in the table", manageUsersPage.isUserDisplayedInTable(user.getAdminUsername()));
    }

    @And("^I am on add user page$")
    public void I_am_on_add_user_page() throws Throwable {
       addUserPage.goToPage();
        assertTrue(addUserPage.isCurrentPage());
    }

    @When("^I complete the details for the new account with valid data$")
    public void I_complete_the_details_for_the_new_account_with_valid_data() throws Throwable {
        userList.addUser();
        addUserPage.addUserDetails(userList.getUser(0));
    }

    @Then("^I should see the \"([^\"]*)\" message$")
    public void I_should_see_the_message(String message) throws Throwable {
        String expectedMessage = Messages.getMessage(message);
        assertTrue(Messages.getMessage(message), page.isTextPresentOnPage(expectedMessage));
    }

    @When("^I complete the details for the new account with invalid username$")
    public void I_complete_the_details_for_the_new_account_with_invalid_username() throws Throwable {
        userList.addUser();
        userList.getUser(0).setUsername("");
        addUserPage.addUserDetails(userList.getUser(0));
    }

    @When("^I log in with my account$")
    public void I_log_in_with_my_account() throws Throwable {
        pivotalHDProductPage.login(userList.getUser(0));
        assertTrue(page.isLogoutButtonVisible());
    }

    @Given("^I log in as an admin from user page$")
    public void I_log_in_as_an_admin_from_user_page() throws Throwable {
         userAccount.goToPage();
         assertTrue(userAccount.isCurrentPage());

         user.setUsername(Settings.adminUsername);
         user.setPassword(Settings.adminPassword);
         page.login(user);

         assertTrue(page.isLogoutButtonVisible());
    }

    @Given("^I have an account created for me without admin rights$")
    public void I_have_an_account_created_for_me_without_admin_rights() throws Throwable {

        user.setUsername(Settings.adminUsername);
        user.setPassword(Settings.adminPassword);
        page.login(user);

        addUserPage.goToPage();
        assertTrue(addUserPage.isCurrentPage());

        //create a new user and adds it to the list
        userList.addUser();
        addUserPage.addUserDetails(userList.getUser(0));

        page.logout();
    }

    @And("^I modify my password$")
    public void I_modify_my_password() throws Throwable {
        page.gotoMyAccount();
        myAccountPage.gotoEditSection();
        myAccountPage.changePassword(userList.getUser(0));
    }

    @Then("^I can login with the new password$")
    public void I_can_login_with_the_new_password() throws Throwable {
        page.logout();
        page.login(userList.getUser(0));
        assertTrue(page.isLogoutButtonVisible());
        page.logout();
    }

    @Then("^the new account should have admin access$")
    public void the_new_account_should_have_admin_access() throws Throwable {
        page.logout();

        page.login(userList.getUser(0));
       // assertTrue("Toolbar not displayed on the page",pivotalHDProductPage.isManagementMenuDisplayed());
    }

    @When("^I delete a user$")
    public void I_delete_a_user() throws InterruptedException {
        manageUsersPage.goToPage();
        assertTrue(manageUsersPage.isCurrentPage());
        manageUsersPage.deleteUser(userList.getUser(0).getUsername());
    }

    @Then("^I only see home page and free training option$")
    public void I_only_see_home_page_and_free_training_option() throws Throwable {
      //   assertEquals(page.getPageTitle(), "Home page");
         assertFalse(page.getPageTitle().equals("Landing page"));
         assertFalse(pivotalHDProductPage.isManagementMenuDisplayed());
    }

    @When("^I access people page as a non logged in user$")
    public void I_access_people_page_as_a_non_logged_in_user() throws Throwable {
        manageUsersPage.goToPage();
        assertTrue(manageUsersPage.isCurrentPage());
    }

    @Then("^I should be deny the access$")
    public void I_should_be_deny_the_access() throws Throwable {
       assertTrue(page.getPageTitle().contains("Access Denied"));
    }

    @And("^I create a test account$")
    public void I_create_a_test_account() throws Throwable {
        addUserPage.goToPage();
        userList.addUser();
        addUserPage.addUserDetails(userList.getUser(0));
    }

    @And("^The new user can login$")
    public void The_new_user_can_login() throws Throwable {
         page.logout();

         page.login(userList.getUser(0));
         page.logout();
    }

    @And("^I delete the user$")
    public void I_delete_the_user() throws Throwable {
        manageUsersPage.goToPage();
        manageUsersPage.deleteUser(userList.getUser(0).getUsername());
        page.logout();
    }

    @Then("^the user can't login anymore$")
    public void the_user_can_t_login_anymore() throws Throwable {
        page.login(userList.getUser(0));
        assertTrue(page.isTextPresentOnPage("Sorry, unrecognized username or password. Have you forgotten your password?"));
    }

    @And("^the user sees \"([^\"]*)\" message$")
    public void the_user_sees_message(String message) throws Throwable {
        String expectedMessage = Messages.getMessage(message);
        assertTrue(Messages.getMessage(message), page.isTextPresentOnPage(expectedMessage));
    }

    @Then("^the new account has the \"([^\"]*)\" role$")
    public void the_new_account_has_the_role(String role) throws Throwable {
        assertTrue(manageUsersPage.getUserRole(userList.getUser(0).getUsername()).contains(role));
    }

    @When("^I change the role for the new account to \"([^\"]*)\"$")
    public void I_change_the_role_for_the_new_account_to(String role) throws Throwable {
        manageUsersPage.goToPage();
        assertTrue(manageUsersPage.isCurrentPage());
        manageUsersPage.updateUser(role, userList.getUser(0).getUsername());
    }

    @And("^I edit the role for the new account to \"([^\"]*)\"$")
    public void I_edit_the_role_for_the_new_account_to(String arg1) throws Throwable {
        manageUsersPage.editUser(userList.getUser(0).getUsername());
        addUserPage.setVerifiedUserRole();
    }

   @After("@authentication, @nitro")
    public void deleteAllTestUsers() throws InterruptedException {
        page.logout();

        user.setUsername(Settings.adminUsername);
        user.setPassword(Settings.adminPassword);
        pivotalHDProductPage.login(user);

        manageUsersPage.goToPage();

        if (userList.getUserList().size()>0){
          for(User user:userList.getUserList()) {
            manageUsersPage.deleteUser(user.getUsername());
          }
        }

         page.logout();
    }

    @After("@restrictions, @nitro")
    public void logout() throws InterruptedException {
        page.logout();
    }

}
