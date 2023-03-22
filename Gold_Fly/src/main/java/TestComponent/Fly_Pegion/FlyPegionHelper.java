package TestComponent.Fly_Pegion;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import TestLib.Automation_properties;
import TestLib.Common;
import TestLib.Sync;
import Utilities.ExcelReader;
import Utilities.ExtenantReportUtils;

public class FlyPegionHelper {

	String datafile;
	ExcelReader excelData;
	Map<String, Map<String, String>> data = new HashMap<>();
	static ExtenantReportUtils report;
	static Automation_properties automation_properties = Automation_properties.getInstance();

	public FlyPegionHelper(String datafile, String sheetname) {

		excelData = new ExcelReader(datafile, sheetname);
		data = excelData.getExcelValue();
		this.data = data;
		if (Utilities.TestListener.report == null) {
			report = new ExtenantReportUtils("FlyPegion");
			report.createTestcase("FlyPegionTestCases");
		} else {
			this.report = Utilities.TestListener.report;
		}

	}

	public int getpageresponce(String url) throws MalformedURLException, IOException {
		HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
		c.setRequestMethod("HEAD");
		c.connect();
		int r = c.getResponseCode();

		return r;
	}

	public void verifyHomePage() {
		try {
			Sync.waitPageLoad();
			int size = Common.findElements("xpath", "//div[@class='nav-logo']").size();
			Common.assertionCheckwithReport(
					size > 0 && Common.getPageTitle().contains("FlyPigeon"),
					"validating store logo", "System directs the user to the Homepage",
					"Sucessfully user navigates to the Home page", "Failed to navigate to the homepage");
		} catch (Exception | Error e) {
			e.printStackTrace();

			ExtenantReportUtils.addFailedLog("validating store logo", "System directs the user to the Homepage",
					" user unable navigates to the home page", "Failed to navigate to the homepage");
			Assert.fail();
		}

	}
	
	public void click_Login() {
		try {
			Sync.waitElementPresent("xpath", "//button[text()='Login as Agent']");
			Common.clickElement("xpath", "//button[text()='Login as Agent']");
			Sync.waitPageLoad();
			Thread.sleep(3000);
			String login = Common.getText("xpath", "//p[text()='Login to Continue']");
			String welcome = Common.getText("xpath", "//h2[@class='txt-center']");
			System.out.println(login);
			System.out.println(Common.getCurrentURL());
			System.out.println(welcome);
			Common.assertionCheckwithReport(
					login.equals("Login to Continue")&& welcome.equals("Welcome Back") &&(Common.getCurrentURL().contains("/login")),
					"To validate the user navigates to the login page",
					"user should able to land on the login page after clicking on the login button",
					"User Successfully clicked on the login button and Navigate to the login page",
					"User Failed to click the login button and not navigated to login page");
		}
		catch(Exception |Error e) {
			e.printStackTrace();
			ExtenantReportUtils.addFailedLog("To validate the user navigates to the login page",
					"user should able to land on the login page after clicking on the login button",
					"Unable to click on the login button and not Navigated to the login page",
					Common.getscreenShotPathforReport(
							"Failed to click login button and not Navigated to the login page"));
			Assert.fail();
		}
	}
	
	public void login_Details() {
		try {
			Common.textBoxInput("xpath", "//input[@placeholder='Enter your username']", "FPGNLT000033A");
			Common.textBoxInput("xpath", "//input[@placeholder='Enter your password']", "Ilusandy@2001");
			Common.clickElement("xpath", "//button[text()='Login']");
			Sync.waitPageLoad(3000);
			Thread.sleep(5000);
			String dashboard = Common.findElement("xpath", "//a[@href='/dashboard/transactions']").getAttribute("href");
			String logout = Common.findElement("xpath", "//button[text()='Logout']").getText();
			System.out.println(dashboard);
			System.out.println(logout);
			Common.assertionCheckwithReport(
					Common.getPageTitle().contains("FlyPigeon") && dashboard.contains("transactions")&& logout.equals("Logout"),
					"To validate the user lands on Home page after successfull login",
					"After clicking on the signIn button it should navigate to the Home page",
					"user Sucessfully navigate to the Home page after clicking on the signIn button",
					"Failed to signIn and not navigated to the Home page ");

		}
		catch(Exception |Error e) {
			e.printStackTrace();
			ExtenantReportUtils.addFailedLog("To validate the user Navigate to Home page after successfull login",
					"After clicking on the signin button it should navigate to the Home page",
					"Unable to navigate the user to the home after clicking on the SignIn button",
					Common.getscreenShotPathforReport("Failed to signIn and not navigated to the Home page "));

			Assert.fail();
		}
	}
	
	public void logout() {
		try {
			Sync.waitPageLoad();
			Sync.waitElementPresent("xpath", "//button[text()='Logout']");
			Common.clickElement("xpath", "//button[text()='Logout']");
			String login = Common.getText("xpath", "//p[text()='Login to Continue']");
			String welcome = Common.getText("xpath", "//h2[@class='txt-center']");
			System.out.println(login);
			System.out.println(Common.getCurrentURL());
			System.out.println(welcome);
			Common.assertionCheckwithReport(
					login.equals("Login to Continue")&& welcome.equals("Welcome Back") &&(Common.getCurrentURL().contains("/login")),
					"To validate the user navigates to the login page after logout",
					"user should able to land on the login page after clicking on the logout button",
					"User Successfully clicked on the logout button and Navigate to the login page",
					"User Failed to click the logout button and not navigated to login page");
		
		} catch (Exception | Error e) {
			e.printStackTrace();

			ExtenantReportUtils.addFailedLog("validating the user navigates to login page after logout", "System directs the user to the login page",
					" user unable to navigates to the login page", "Failed to navigate to the Login page");
			Assert.fail();
		}

	}
	
	public void Click_Forgot_Pswd() {
		try {
			Common.clickElement("xpath", "//a[text()='Forgot password?']");
			Sync.waitPageLoad(3000);
			Thread.sleep(5000);
			String ForgotPswd = Common.findElement("xpath", "//h2[text()='Forgot Password']").getText();
			System.out.println(ForgotPswd);
			Common.textBoxInput("xpath", "//input[@id='mobile']", "8328354100");
			
			Common.assertionCheckwithReport(
					ForgotPswd.contains("Forgot Password")&& (Common.getCurrentURL()).contains("forgot"),
					"To validate the user lands on Forgot Password page",
					"After clicking on the Forgot Password Link it should navigate to the Forgot Password page",
					"user Sucessfully navigate to the Forgot Password page after clicking on the Forgot Password Link",
					"Failed to Click Forgot Password not navigated to Forgot Password page ");
			
			Common.clickElement("xpath", "//button[text()='Continue']");
			Sync.waitPageLoad(5000);
			Thread.sleep(6000);
			Sync.waitPageLoad(5000);
			String otp= Common.getText("xpath", "//label[text()='Enter OTP']");
			System.out.println(otp);
			String form = Common.findElement("xpath", "//form[contains(@class,'ForgotPassword_verifyOtp')]").getAttribute("class");
					System.out.println(form);
			String pswd = Common.findElement("xpath", "//button[text()='Change Password']").getText();
			System.out.println(pswd);
			Common.assertionCheckwithReport(
					otp.contains("Enter OTP") && (Common.getCurrentURL()).contains("forgot")
							&& form.contains("ForgotPassword_verifyOtp") && pswd.contains("Change Password"),
					"To validate the user lands on Forgot Password OTP and New password Page ",
					"After Entering Mobile no and Continue it should navigate to the OTP New password page",
					"user Sucessfully navigate to OTP New password page after Entering Mobile No",
					"Failed to Enter no not navigated to OTP New password page ");

		}
		catch(Exception |Error e) {
			e.printStackTrace();
			ExtenantReportUtils.addFailedLog("To validate the user Navigate to Forgot Password page",
					"After clicking on the Forgot Password Link it should navigate to the Forgot Password page",
					"Unable to navigate the user to the Forgot Password page after clicking on the Forgot Password button",
					Common.getscreenShotPathforReport("Failed to Click Forgot Password and not navigated to the Forgot Password page "));

			Assert.fail();
		}
	}
	
	public void click_Registration_Status() {
		try {
			Sync.waitElementPresent("xpath", "//a[text()='Check registration status']");
			Common.clickElement("xpath", "//a[text()='Check registration status']");
			Sync.waitPageLoad();
			Thread.sleep(3000);
			String reg = Common.getText("xpath", "//h2[text()='Registration Status']");
			String refID = Common.getText("xpath", "//label[@for='referenceID']");
			System.out.println(reg);
			System.out.println(Common.getCurrentURL());
			System.out.println(refID);
			Common.assertionCheckwithReport(
					reg.equals("Registration Status")&& refID.equals("Reference ID") &&(Common.getCurrentURL().contains("/verify-registration-status")),
					"To validate the user navigates to the Registration Status page",
					"user should able to land on Registration Status page after clicking on Check Registration Status",
					"User Successfully clicked on Check Registration Status and Navigates to Registration Status",
					"User Failed to click the Registration Status and not navigated to Registration Status");
			
		}
		catch(Exception |Error e) {
			e.printStackTrace();
			ExtenantReportUtils.addFailedLog("To validate the user navigates to the login page",
					"user should able to land on the login page after clicking on the login button",
					"Unable to click on the login button and not Navigated to the login page",
					Common.getscreenShotPathforReport(
							"Failed to click login button and not Navigated to the login page"));
			Assert.fail();
		}
	}
	
	public void registration_Status_check() {
		try {
			Sync.waitElementPresent("xpath", "//input[@id='referenceID']");
			Common.textBoxInput("xpath", "//input[@id='referenceID']", "FPGNLT000033A");
			Common.clickElement("xpath", "//button[text()='Get Status']");
			Sync.waitPageLoad();
			Thread.sleep(3000);
			String status = Common.getText("xpath", "//h3[text()='Status: ']");
			System.out.println(status);
			System.out.println(Common.getCurrentURL());
			Common.assertionCheckwithReport(
					status.contains("APPROVED")|| status.contains("APPLIED") &&(Common.getCurrentURL().contains("/verify-registration-status")),
					"To validate the user Checks the Registration Status ",
					"User should able to Check Registration Status after entering ID",
					"User Successfully entered ID and Checked Registration Status ",
					"User Failed to entered ID on Registration Status and not checked Registration Status");
			
//			Thread.sleep(3000);
//			Common.textBoxInputClear("xpath", "//input[@id='referenceID']");
//			
//			WebElement Select_Customergroups = Common.findElement("xpath", "//input[@id='referenceID']");
//			Select_Customergroups.sendKeys(Keys.CONTROL + "a");
//			Common.textBoxInput("xpath", "//input[@id='referenceID']", "FPGNCI000041A");
//			Common.clickElement("xpath", "//button[text()='Get Status']");
//			
//			Common.assertionCheckwithReport(
//					status.contains("APPROVED")|| status.contains("APPLIED") &&(Common.getCurrentURL().contains("/verify-registration-status")),
//					"To validate the user Checks the Registration Status ",
//					"User should able to Check Registration Status after entering ID",
//					"User Successfully entered ID and Checked Registration Status ",
//					"User Failed to entered ID on Registration Status and not checked Registration Status");
			
		}
		catch(Exception |Error e) {
			e.printStackTrace();
			ExtenantReportUtils.addFailedLog("To validate the user navigates to the login page",
					"user should able to land on the login page after clicking on the login button",
					"Unable to click on the login button and not Navigated to the login page",
					Common.getscreenShotPathforReport(
							"Failed to click login button and not Navigated to the login page"));
			Assert.fail();
		}
	}
	
	
}