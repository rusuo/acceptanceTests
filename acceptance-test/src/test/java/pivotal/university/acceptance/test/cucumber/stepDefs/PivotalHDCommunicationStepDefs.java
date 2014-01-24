package pivotal.university.acceptance.test.cucumber.stepDefs;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import pivotal.university.acceptance.test.config.Messages;
import pivotal.university.acceptance.test.services.PivotalHDCommunicationService;
import pivotal.university.acceptance.test.testData.HttpClientSession;
import pivotal.university.acceptance.test.testData.PivotalHDUser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PivotalHDCommunicationStepDefs {
    private PivotalHDUser pivotalHDUser = new PivotalHDUser();
    private HttpClientSession session;
    private PivotalHDCommunicationService pivotalHDCommunicationService;

    public PivotalHDCommunicationStepDefs (HttpClientSession session) {
        this.session = session;
        this.pivotalHDCommunicationService = new PivotalHDCommunicationService(session);
    }

    @Given("^I have a pivotal HD account$")
    public void I_have_a_pivotal_HD_account() throws Throwable {
        pivotalHDCommunicationService.requestNewAccount();

        pivotalHDUser.setUsername(getValueFromJson(session.getLastResult(), "username"));
        pivotalHDUser.setPassword(getValueFromJson(session.getLastResult(), "password"));
    }

    @When("^I request a new pivotal HD account$")
    public void I_request_a_new_pivotal_HD_account() throws Throwable {
       pivotalHDCommunicationService.requestNewAccount();

       pivotalHDUser.setUsername(getValueFromJson(session.getLastResult(), "username"));
       pivotalHDUser.setPassword(getValueFromJson(session.getLastResult(), "password"));
    }

    @When("^I submit the command '(.+)'$")
    public void I_submit_the_command(String command) throws Throwable {
        pivotalHDCommunicationService.submitSqlCommand(pivotalHDUser, command);
    }

    @When("^I submit the command '(.+)' with an incorrect password$")
    public void I_submit_the_command_with_invalid_password(String command) throws Throwable {
        PivotalHDUser user = new PivotalHDUser();
        user.setUsername(pivotalHDUser.getUsername());
        user.setPassword("nonsense");
        pivotalHDCommunicationService.submitSqlCommand(user, command);
    }

    @Then("^the service should return status code '(\\d+)'$")
    public void the_service_should_return_status_code_(int expectedResponseCode) throws Throwable {
        assertEquals(expectedResponseCode, session.getLastStatusCode());
    }

    @Then("^the service response should include a '(.+)' status message$")
    public void the_service_response_should_include_a_success_message(String expectedStatus) throws Throwable {
        assertEquals(expectedStatus, getValueFromJson(session.getLastResult(), "status"));
    }

    @Then("^the service response should include a username and password$")
    public void the_service_response_should_include_a_username_and_password() throws Throwable {
        assertNotEquals("", pivotalHDUser.getPassword());
        assertNotEquals("", pivotalHDUser.getUsername());
    }

    private String getValueFromJson(String json, String key) {
        String value = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonParser jp = mapper.getJsonFactory().createJsonParser(json);
            JsonNode jsonNode = mapper.readTree(jp);

            value = jsonNode.findValue(key).asText();
        } catch (Exception e) {
            throw new RuntimeException("Value with key '" + key + "' not found in json '" + json + "'");
        }
        return value;
    }

    @Then("^the service response should include a '(.+)' error message$")
    public void the_service_response_should_include_an_error_message(String messageKey) throws Throwable {
        assertTrue(getValueFromJson(session.getLastResult(), "errorMessage").contains(Messages.getMessage(messageKey)));
    }

    @Given("^I request a new tutorial$")
    public void I_request_a_new_tutorial() throws Throwable {
        pivotalHDCommunicationService.requestNewTutorial();
    }

    @And("^the service response should include a '(.+)' with '(.+)' message$")
    public void the_service_response_should_include_a_id_with_hawq_message(String expectedTag, String expectedInfo) throws Throwable {
        assertEquals(expectedInfo, getValueFromJson(session.getLastResult(), expectedTag));
    }

    @After("@api")
    public void embedServiceResponse(Scenario scenario) {
        if(scenario.isFailed()){
            scenario.write("Last request: " + session.getLastRequest());
            scenario.write("Last result: " + session.getLastResult());
        }
    }

}
