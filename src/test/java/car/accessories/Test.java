package car.accessories;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"html:target/cucumber-report.html"},
		features = "use_cases",
		glue = "")
public class Test {
}
