package stepDefinition;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.TestPage;

public class TestSteps {
	TestPage page = new TestPage();

	@Then("User should be presented {string} message")
	public void user_should_be_presented_message(String lblMsg) {
		page.checkLblMsgAccountNo(lblMsg);
	}

	@Then("User should be blocked from entering any data")
	public void user_should_be_blocked_from_entering_any_data() {
		System.out.println("test");
	}

	@Given("User enter valid {string},{string},{string}")
	public void user_enter_valid(String accNO, String amnt, String desc) {
		page.enterDepositData(accNO, amnt, desc);
	}

	@Given("User enter characters in {string} field")
	public void user_enter_characters_in_field(String accountno) {
		page.enterAccountNumber(accountno);
	}

	@Given("User enter blank space in {string} field")
	public void user_enter_blank_space_in_field(String accountno) {
		page.enterAccountNumber(accountno);
	}

	@Given("User enter negetive number in {string} field")
	public void user_enter_negetive_number_in_field(String accountno) {
		page.enterAccountNumber(accountno);
	}

	@Given("User enter more than {int} digits in {string} field")
	public void user_enter_more_than_digits_in_field(Integer int1, String accountno) {
		page.enterAccountNumber(accountno);
	}

	@Given("User enter space in between {string} field")
	public void user_enter_space_in_between_field(String accountno) {
		page.enterAccountNumber(accountno);
	}

	@Given("User enter special Charater in {string} Field")
	public void user_enter_special_charater_in_field(String accountno) {
		page.enterAccountNumber(accountno);
	}

	@When("Click on submit button")
	public void click_on_submit_button() throws InterruptedException {
		page.clickSubmitButton();
		System.out.println("click on submit button");
	}

	@Then("User should see {string} alert message")
	public void user_should_see_alert_message(String alertMsg) throws InterruptedException {
		page.clickAlert();
		System.out.println("Assert the valid data");
	}

	@Then("user should see {string} message")
	public void user_should_see_message(String string) {
		System.out.println("test");
	}
}