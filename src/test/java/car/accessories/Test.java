package car.accessories;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"html:target/cucumber-report.html"},
		features = "use_cases",
		glue = "")
public class Test {
}
