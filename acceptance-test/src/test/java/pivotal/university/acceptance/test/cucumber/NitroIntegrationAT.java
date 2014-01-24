package pivotal.university.acceptance.test.cucumber;

import cucumber.api.junit.Cucumber;

@Cucumber.Options(features = "target/test-classes/cucumber/examples/", tags = {"~@ignore", "~@pending", "@nitro"}, format = {"html:target/cucumber-report/nitro"})
public class NitroIntegrationAT {
}
