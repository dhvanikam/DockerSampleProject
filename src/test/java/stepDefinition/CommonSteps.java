package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonSteps {
	@Given("The user is on banking website login page")
	public void the_user_is_on_banking_website_login_page() {
		System.out.println("All fields");
	}

	@When("The user enter valid {string} and {string}")
	public void the_user_enter_valid_and(String string, String string2) {
		System.out.println("All fields");
	}

	@When("The user click on login button")
	public void the_user_click_on_login_button() {
		System.out.println("All fields");
	}

	@Then("The user redirected to homepage")
	public void the_user_redirected_to_homepage() {
		System.out.println("All fields");
	}
}
