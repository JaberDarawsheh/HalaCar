package CarAccessiores;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;



@RunWith(Cucumber.class)
@CucumberOptions(
		plugin= {"html: target/cucumber.html"},
		features= "src/use_cases",
		glue="")

public class Test {

}



