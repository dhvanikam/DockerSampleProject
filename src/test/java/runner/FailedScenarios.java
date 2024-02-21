package runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(monochrome = true,
  features = "@target/rerun.txt", // Cucumber picks the failed scenarios from this file
		plugin = { "pretty", "html:target/site/cucumber-pretty", 
				"json:target/cucumber.json" })
public class FailedScenarios extends AbstractTestNGCucumberTests {
	@DataProvider(parallel = false)
	public Object[][] scenarios() {

		return super.scenarios();
	}
}
