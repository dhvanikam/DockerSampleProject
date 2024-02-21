package pageObjects;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import driverFactory.DriverFactory;
import utilities.ConfigReader;

public class TestPage {
	WebDriver driver = DriverFactory.getdriver();
	String depositpage = ConfigReader.getDepositPage();

	String sheetDeposit;
	Properties properties;
	String alertMsg;

	public TestPage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@name='accountno']")
	@CacheLookup
	WebElement txtAccountNO;
	@FindBy(xpath = "//input[@name='balance']")
	@CacheLookup
	WebElement txtAmount;
	@FindBy(xpath = "//input[@name='desc']")
	@CacheLookup
	WebElement textDescription;
	@FindBy(xpath = "//label[@id='message2']")
	@CacheLookup
	WebElement lblAccErrorMsg;
	@FindBy(xpath = "//label[@id='message15']")
	@CacheLookup
	WebElement lblAmountErrorMsg;
	@FindBy(xpath = "//label[@id='message17']")
	@CacheLookup
	WebElement lblDescErrorMsg;
	@FindBy(xpath = "//input[@type='submit']")
	@CacheLookup
	WebElement btnSubmit;
	@FindBy(xpath = "//input[@type='reset']")
	@CacheLookup
	WebElement btnReset;

	public void enterDepositData(String accountNo, String amount, String description) {
		try {
			driver.get(depositpage);
			txtAccountNO.sendKeys(accountNo);
			txtAmount.sendKeys(amount);
			textDescription.sendKeys(description);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enterAccountNumber(String accountNo) {
		try {
			driver.get(depositpage);
			txtAccountNO.sendKeys(accountNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickSubmitButton() throws InterruptedException {
		try {
			btnSubmit.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickAlert() throws InterruptedException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			if (wait.until(ExpectedConditions.alertIsPresent()) == null) {
				System.out.println("alert was not present");
			} else {
				System.out.println("alert was present");
				alertMsg = driver.switchTo().alert().getText();
				System.out.println("Alert messeage" + alertMsg);
				driver.switchTo().alert().accept();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void checkAlertMsg(String expectedMsg) {
		assertEquals(alertMsg, expectedMsg);
	}

	public void checkLblMsgDescripiton(String expectedMsg) {
		assertEquals(lblDescErrorMsg.getText(), expectedMsg);
	}

	public void checkLblMsgAmount(String expectedMsg) {
		assertEquals(lblAccErrorMsg.getText(), expectedMsg);
	}

	public void checkLblMsgAccountNo(String expectedMsg) {
		assertEquals(lblAccErrorMsg.getText(), expectedMsg);
	}

}
