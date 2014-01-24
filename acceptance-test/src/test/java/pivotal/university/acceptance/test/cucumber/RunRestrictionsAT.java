package pivotal.university.acceptance.test.cucumber;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(features = "target/test-classes/cucumber/examples/", tags = {"@restrictions"}, format = {"html:target/cucumber-report/restrictions"})

public class RunRestrictionsAT {
}
