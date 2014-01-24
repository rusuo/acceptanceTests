package pivotal.university.acceptance.test.cucumber;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(features = "target/test-classes/cucumber/examples/", tags = {"~@ignore", "~@pending", "@terminal"}, format = {"html:target/cucumber-report/terminal"})
public class RunTerminalAT {
}
