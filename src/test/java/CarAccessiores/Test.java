package CarAccessiores;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "use_cases",
		plugin= {"html: C:/Users/hamoo/Desktop/cucumber_report"},
		monochrome = true,
		snippets = CucumberOptions.SnippetType.CAMELCASE,glue = {""})
public class Test {

}

