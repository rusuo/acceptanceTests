package pivotal.university.acceptance.test.cucumber.stepDefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pivotal.university.acceptance.test.cucumber.SharedDriver;
import pivotal.university.acceptance.test.cucumber.UserList;
import pivotal.university.acceptance.test.pageObjects.PivotalHDProductPage;
import pivotal.university.acceptance.test.pageObjects.QuizListPage;
import pivotal.university.acceptance.test.pageObjects.RegisterNewAccountPage;
import pivotal.university.acceptance.test.services.NitroAPITests;
import pivotal.university.acceptance.test.testData.HttpClientSession;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TakeAquizStepDef {

    private QuizListPage quizListPage;
    private NitroAPITests nitroAPITests;
    private HttpClientSession session;
    private PivotalHDProductPage pivotalHDProductPage;
    private UserList userList;

    private RegisterNewAccountPage registerNewAccountPage;
    private RegistrationStepDefs registrationStepDefs;
    private TerminalStepDefs terminalStepDefs;

    public TakeAquizStepDef(HttpClientSession session, SharedDriver webdriver, UserList userList)
    {
        this.session = session;
        this.userList = userList;
        this.nitroAPITests = new NitroAPITests(session, userList);
        quizListPage = new QuizListPage(webdriver);
        terminalStepDefs = new TerminalStepDefs(webdriver);
        pivotalHDProductPage = new PivotalHDProductPage(webdriver);
        registerNewAccountPage = new RegisterNewAccountPage(webdriver);
        registrationStepDefs = new RegistrationStepDefs(webdriver, userList);
    }

    @And("^I am on the quiz page$")
    public void I_am_on_the_quiz_page() throws Throwable {
         quizListPage.goToQuiz001();
    }

    @When("^I complete the quiz \"([^\"]*)\" answering \"([^\"]*)\"$")
    public void I_complete_the_quiz_answering(String quizName, String correctAnswer) throws Throwable {
      //  quizListPage.takeQuiz(quizName);
        quizListPage.selectAnswer(correctAnswer);
    }

    @Then("^my user in Nitro should show that the action \"([^\"]*)\" is \"([^\"]*)\"$")
    public void my_user_in_Nitro_should_show_that_the_action_is(String actionName, String actionCompleted) throws Throwable {
        nitroAPITests.getSessionKeyRequest();
        nitroAPITests.getMissionStatus();

        if (actionCompleted.equals("true")){
           assertTrue(nitroAPITests.getMissionResult(session.getLastResult(), actionName).equals("true"));
        } else {
            assertTrue(nitroAPITests.getMissionResult(session.getLastResult(), actionName).equals("false"));
        }
    }

    @And("^missions completed for my user are \"([^\"]*)\"$")
    public void missions_completed_for_my_user_are(String number) throws Throwable {
        if (number.equals("0")){
            assertFalse(nitroAPITests.isMissionCompleted(session.getLastResult()));
        }else{
            assertTrue(nitroAPITests.isMissionCompleted(session.getLastResult()));
        }
    }

    @Given("^I completed the tutorial and I registered$")
    public void I_completed_the_tutorial_and_I_registered() throws Throwable {
        pivotalHDProductPage.beginTraining();
        terminalStepDefs.I_complete_the_tutorial();
        registerNewAccountPage.goToPage();
        userList.addUser();
        registerNewAccountPage.registerAccount(userList.getUser(0));
    }

    @Given("^I am a logged in user and I finished the tutorial$")
    public void I_am_a_logged_in_user_and_I_finished_the_tutorial() throws Throwable {
        registrationStepDefs.I_have_registered_a_new_account();
        pivotalHDProductPage.login(userList.getUser(0));
        pivotalHDProductPage.beginTraining();
        terminalStepDefs.I_complete_the_tutorial();
    }
}
