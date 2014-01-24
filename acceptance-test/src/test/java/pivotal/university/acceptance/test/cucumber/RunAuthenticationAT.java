package pivotal.university.acceptance.test.cucumber;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(features = "target/test-classes/cucumber/examples/", tags = {"~@ignore", "~@pending", "@authentication"}, format = {"html:target/cucumber-report/authentication"})
public class RunAuthenticationAT {
}
