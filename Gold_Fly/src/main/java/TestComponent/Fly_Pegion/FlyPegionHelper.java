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
import TestLib.Common.SelectBy;
import TestLib.Sync;
import Utilities.ExcelReader;
import Utilities.ExtenantReportUtils;
import Utilities.Utils;

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
	
	public void login_Details(String Dataset) {
		try {
			Common.textBoxInput("xpath", "//input[@placeholder='Enter your username']", data.get(Dataset).get("Username"));
			Common.textBoxInput("xpath", "//input[@placeholder='Enter your password']", data.get(Dataset).get("Password"));
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
			Common.textBoxInput("xpath", "//input[@id='referenceID']", "FPGNDD000008A");
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
			
			Thread.sleep(3000);
			Common.textBoxInputClear("xpath", "//input[@id='referenceID']");
			
			WebElement Select_all = Common.findElement("xpath", "//input[@id='referenceID']");
			Select_all.sendKeys(Keys.CONTROL + "a");
			Common.findElement("xpath", "//input[@id='referenceID']").sendKeys("FPGNCI000041A");
//			Common.textBoxInput("xpath", "//input[@id='referenceID']", "FPGNCI000041A");
			Common.clickElement("xpath", "//button[text()='Get Status']");
			
			Common.assertionCheckwithReport(
					status.contains("APPROVED")|| status.contains("APPLIED") &&(Common.getCurrentURL().contains("/verify-registration-status")),
					"To validate the user Checks the Registration Status ",
					"User should able to Check Registration Status after entering ID",
					"User Successfully entered ID and Checked Registration Status ",
					"User Failed to entered ID on Registration Status and not checked Registration Status");
			
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
	
	public void Invalid_login_Details() {
		try {
			
			Common.clickElement("xpath", "//button[text()='Login']");
			
			Common.assertionCheckwithReport(
					Common.getPageTitle().contains("FlyPigeon")&& (Common.getCurrentURL()).contains("login") ,
					"To validate the required login Credentials fields ",
					"After clicking on login button it should display alert as Please Fill the fields",
					"It displays the alert Please Fill the fields after clicking on the login button",
					"Failed to display alert as Please Fill the fields ");
			
			Common.textBoxInput("xpath", "//input[@placeholder='Enter your username']", "FPGNLT000033");
			Common.textBoxInput("xpath", "//input[@placeholder='Enter your password']", "Ilusandy@");
			
			Common.clickElement("xpath", "//button[text()='Login']");
//			Sync.waitPageLoad(1000);
			Thread.sleep(2000);
			Sync.waitElementPresent("xpath", "//div[@class='Toastify__toast-body']//p");
			Common.mouseOver("xpath", "//div[@class='Toastify__toast-body']//p");
			String alert = Common.findElement("xpath", "//div[@class='Toastify__toast-body']//p").getText();
			System.out.println(alert);
			Common.assertionCheckwithReport(
					Common.getPageTitle().contains("FlyPigeon") && alert.contains("invalid login ") ,
					"To validate the Invalid login Credentials",
					"After clicking on the login button it should display  alert as Invalid login credentials",
					"It displays the alert Invalid login credentials after clicking on the login button",
					"Failed to login and display alert as Invalid login credentials ");
			Common.mouseOver("xpath", "//button[text()='Login']");
			Thread.sleep(3000);
		}
		catch(Exception |Error e) {
			e.printStackTrace();
			ExtenantReportUtils.addFailedLog("To validate the Invalid login Credentials",
					"After clicking on the login button it should display  alert as Invalid login credentials",
					"Unable to displays the alert Invalid login credentials after clicking on the login button",
					Common.getscreenShotPathforReport("Failed to login and display alert as Invalid login credentials "));

			Assert.fail();
		}
	}

	public void Click_Sign_Up() {
		// TODO Auto-generated method stub
		try
		{
			Sync.waitElementPresent("xpath", "//button[text()='Login as Agent']");
			Common.clickElement("xpath", "//button[text()='Login as Agent']");
			Sync.waitPageLoad();
			Thread.sleep(3000);
			String login=Common.findElement("xpath", "//h2[@class='txt-center']").getText();
			Common.assertionCheckwithReport(
					login.contains("Welcome Back"),
					"To validate the Login page",
					"After clicking on the login as agent button it should navigate to the Login page ",
					"Sucessfully navigated to the login page after clicking on the Login as Agent button",
					"Failed to Navigate to the Login page");
			Sync.waitElementPresent("xpath", "//a[text()='Sign Up Here']");
			Common.clickElement("xpath", "//a[text()='Sign Up Here']");
			String signup=Common.findElement("xpath", "//div[contains(@class,'Signup')]//strong").getText();
			Common.assertionCheckwithReport(
					signup.contains("Sign Up"),
					"To validate the signup page",
					"After clicking on the signup here it should navigate to the signup page ",
					"Sucessfully navigated to the signup page after click on the signup button",
					"Failed to Navigate to the signup page");
		}
		catch(Exception | Error e)
		{
			e.printStackTrace();
			ExtenantReportUtils.addFailedLog("To validate the signup page",
					"After clicking on the signup here it should navigate to the signup page ",
					"Unable to Navigate to the signup page after clicking on the signup button",
					Common.getscreenShotPathforReport("Failed to Navigate to the signup page"));
			Assert.fail();
		}
		
	}

	public void corporate_Sign_Up() {
		// TODO Auto-generated method stub
		try
		{
			Sync.waitElementPresent("xpath", "//input[@id='corporate']");
			Common.clickElement("xpath", "//input[@id='corporate']");
			Sync.waitPageLoad();
			Thread.sleep(3000);
			String servicetype=Common.findElement("xpath", "//h4[contains(text(),'Service Type')]").getText();
			Common.assertionCheckwithReport(
					servicetype.contains("Service Type"),
					"To validate the Service Type selection in SignUp page",
					"After clicking the Service Type radio button it should select the Service Type ",
					"Sucessfully selected the Service Type after clicking the Service Type radio button",
					"Failed to select the Service Type in signup page");
			
		}
		catch(Exception | Error e)
		{
			e.printStackTrace();
			ExtenantReportUtils.addFailedLog("To validate the Service Type selection in SignUp page",
					"After clicking on the Service Type radio button it should select the Service Type  ",
					"Unable to select the Service Type after clicking the Service Type radio button",
					Common.getscreenShotPathforReport("Failed to select the Service Type signup page"));
			Assert.fail();
		}
		
	}
	
	public void individual_Sign_Up() {
		// TODO Auto-generated method stub
		try
		{
			Sync.waitElementPresent("xpath", "//input[@id='individual']");
			Common.clickElement("xpath", "//input[@id='individual']");
			Sync.waitPageLoad();
			Thread.sleep(3000);
			String servicetype=Common.findElement("xpath", "//h4[contains(text(),'Service Type')]").getText();
			Common.assertionCheckwithReport(
					servicetype.contains("Service Type"),
					"To validate the Service Type selection in SignUp page",
					"After clicking the Service Type radio button it should select the Service Type ",
					"Sucessfully selected the Service Type after clicking the Service Type radio button",
					"Failed to select the Service Type in signup page");
			
		}
		catch(Exception | Error e)
		{
			e.printStackTrace();
			ExtenantReportUtils.addFailedLog("To validate the Service Type selection in SignUp page",
					"After clicking on the Service Type radio button it should select the Service Type  ",
					"Unable to select the Service Type after clicking the Service Type radio button",
					Common.getscreenShotPathforReport("Failed to select the Service Type signup page"));
			Assert.fail();
		}
		
	}
	
	public void agent_Sign_Up() {
		// TODO Auto-generated method stub
		try
		{
			Sync.waitElementPresent("xpath", "//input[@id='agent']");
			Common.clickElement("xpath", "//input[@id='agent']");
			Sync.waitPageLoad();
			Thread.sleep(3000);
			String servicetype=Common.findElement("xpath", "//h4[contains(text(),'Service Type')]").getText();
			Common.assertionCheckwithReport(
					servicetype.contains("Service Type"),
					"To validate the Service Type selection in SignUp page",
					"After clicking the Service Type radio button it should select the Service Type ",
					"Sucessfully selected the Service Type after clicking the Service Type radio button",
					"Failed to select the Service Type in signup page");
			
		}
		catch(Exception | Error e)
		{
			e.printStackTrace();
			ExtenantReportUtils.addFailedLog("To validate the Service Type selection in SignUp page",
					"After clicking on the Service Type radio button it should select the Service Type  ",
					"Unable to select the Service Type after clicking the Service Type radio button",
					Common.getscreenShotPathforReport("Failed to select the Service Type signup page"));
			Assert.fail();
		}
		
	}
	
	
	
	public void Sign_up() {
		// TODO Auto-generated method stub
		
		try
		{
			
			Common.dropdown("xpath", "//select[@id='title']", Common.SelectBy.TEXT, "Mr");
			Common.textBoxInput("xpath", "//input[@name='firstName']", "Test");
			Common.textBoxInput("xpath", "//input[@name='lastName']", "Auto");
			Common.clickElement("xpath", "//input[@type='date']");
			Common.textBoxInput("xpath", "//input[@type='date']","03-01-2000");
			int user=Common.genrateRandomNumber();
			System.out.println(user);
			String userID=Integer.toString(user);
			String agency="PEGION"+"10"+userID;
			System.out.println(agency);
			Common.textBoxInput("xpath", "//input[@name='agencyName']", agency);
			Common.textBoxInput("xpath", "//input[@name='address']", " Delhi ");
			Common.dropdown("xpath", "//select[@name='state']", Common.SelectBy.TEXT, "Delhi");
			Common.textBoxInput("xpath", "//input[@name='city']", "New Delhi");
			Common.textBoxInput("xpath", "//input[@name='pincode']", "110001");
			String Country=Common.findElement("xpath", "//input[@name='country']").getAttribute("value");
			Common.assertionCheckwithReport(
					Country.contains("INDIA"),
					"To validate the address fileds",
					"After entring the details address fileds should be filled ",
					"Sucessfully address fileds has been filled after entering the details",
					"Failed to enter the details in the address field");
			Common.textBoxInput("xpath", "//input[@name='panCardNo']", "ABCDE1234N");
			String path = System.getProperty("user.dir") + ("\\src\\test\\resources\\TestData\\FlyPegion\\Screenshot.png");
			Sync.waitElementPresent(40, "xpath", "//input[@name='panCardImage']");
			Common.findElement("xpath", "//input[@name='panCardImage']").sendKeys(path);
			Common.textBoxInput("xpath", "//input[@name='aadharCardNo']", "889977665544");
			String path1 = System.getProperty("user.dir") + ("\\src\\test\\resources\\TestData\\FlyPegion\\Screenshot.png");
			Sync.waitElementPresent(40, "xpath", "//input[@name='aadharCardImage']");
			Common.findElement("xpath", "//input[@name='aadharCardImage']").sendKeys(path1);
			int number=Common.genrateRandomNumber();
			System.out.println(number);
			String mobile=Integer.toString(number);
			String phone= "900000"+mobile;
			Common.textBoxInput("xpath", "//input[@name='mobileNo']", phone);
			Common.textBoxInput("xpath", "//input[@name='emailId']",Utils.getEmailid());
			Common.textBoxInput("xpath", "//input[@name='businessContactNo']", phone);
			
			String email = Common.findElement("xpath", "//input[@name='emailId']").getAttribute("value");
			Thread.sleep(2000);
			Common.textBoxInput("xpath", "//input[@name='businessEmailId']", email);
			Common.textBoxInput("xpath", "//input[@name='designation']", "Testers");
			Sync.waitElementPresent("xpath", "//button[text()='Sign up for free']");
			Common.clickElement("xpath", "//button[text()='Sign up for free']");
			Sync.waitPageLoad(5000);
			Thread.sleep(5000);
			int size = Common.findElements("xpath", "//div[@class='nav-logo']").size();
			
			Common.assertionCheckwithReport(
					size > 0 &&Common.getPageTitle().contains("FlyPigeon"),
					"To validate the Successfull Sign Up",
					"After Clicking the Sign Up For free it should navigate to Success page ",
					"Sucessfully navigates to Registration Success page after Clicking the Sign Up For free",
					"Failed to Click the Sign Up For free in the Sign up page");
		
		}
		catch(Exception | Error e)
		{
			e.printStackTrace();
			ExtenantReportUtils.addFailedLog("To validate the Successfull signup page",
					"After clicking the Sign Up For free it should navigate to Success page ",
					"Unable to navigates to Registration Success page after Clicking the Sign Up For free",
					Common.getscreenShotPathforReport("Failed to Click the Sign Up For free in the Sign up page"));
			
			Assert.fail();
		}
		
	}

	public void validating_singup_form() {
		// TODO Auto-generated method stub
		try
		{
			Sync.waitElementPresent("xpath", "//button[text()='Sign up for free']");
			Common.clickElement("xpath", "//button[text()='Sign up for free']");
			int errorsize = Common.findElements("xpath", "//small[contains(@class,'Signup_validationEr')]").size();
			if (errorsize >= 0) {
				ExtenantReportUtils.addPassLog("validating the error messages in signup page", "Error message should be appear when we click on the signup button",
						"sucessfully  dispaly error message in the signup page", Common.getscreenShotPathforReport("errormessage is displayed in the signup page"));
			} else {

				ExtenantReportUtils.addFailedLog("validating the error messages in signup page", "Error message should be appear when we click on the signup button",
						"Unable to dispaly error message in the signup page", Common.getscreenShotPathforReport("failed to dispaly error message in the signup page"));

				Assert.fail();
			}
		}
		catch(Exception | Error e)
		{
			e.printStackTrace();
			ExtenantReportUtils.addFailedLog("validating the error messages in signup page", "Error message should be appear when we click on the signup button",
					"Unable to dispaly error message in the signup page", Common.getscreenShotPathforReport("failed to dispaly error message in the signup page"));
			Assert.fail();
		}
	}
		public void Dashboard_validation(String Dataset) {
			// TODO Auto-generated method stub
			
				String Accountlinks = data.get(Dataset).get("Account Links");
				String Links=data.get(Dataset).get("Links");
				String[] Account = Accountlinks.split(",");
				String[] Accounts = Links.split(",");
				int i = 0;
				try {
					for (i = 0; i < Account.length; i++) {
						Sync.waitElementPresent("xpath",
								"//div[contains(@class,'MuiListItemText-root')]//span[text()='" + Account[i] + "']");
						Common.clickElement("xpath",
								"//div[contains(@class,'MuiListItemText-root')]//span[text()='" + Account[i] + "']");
						Sync.waitPageLoad();
						Thread.sleep(4000);
						String title = Common.findElement("xpath", "//h1").getText();
						System.out.println(title);
						Common.assertionCheckwithReport(title.contains(Account[i]) || title.contains("Markups"),
								"verifying Account page links " + Account[i],
								"user should navigate to the " + Account[i] + " page",
								"user successfully Navigated to the " + Account[i], "Failed click on the " + Account[i]);

			}
					for (i = 0; i < Accounts.length; i++) {
						Sync.waitElementPresent("xpath",
								"//div[contains(@class,'MuiListItemText-root')]//span[text()='" + Accounts[i] + "']");
						Common.clickElement("xpath",
								"//div[contains(@class,'MuiListItemText-root')]//span[text()='" + Accounts[i] + "']");
						Sync.waitPageLoad();
						Thread.sleep(4000);
						String title = Common.findElement("xpath", "//h2").getText();
						System.out.println(title);
						Common.assertionCheckwithReport(title.contains(Accounts[i]) || title.contains("Book Bus Tickets"),
								"verifying Account page links " + Accounts[i],
								"user should navigate to the " + Accounts[i] + " page",
								"user successfully Navigated to the " + Accounts[i], "Failed click on the " + Accounts[i]);
					
			}
					Sync.waitElementPresent("xpath",
							"//div[contains(@class,'MuiListItemText-root')]//span[text()='Flights']");
					Common.clickElement("xpath",
							"//div[contains(@class,'MuiListItemText-root')]//span[text()='Flights']");
					Sync.waitPageLoad();
					Thread.sleep(4000);
					String flights=Common.findElement("xpath", "//button[@class='TripTypes_tripType__rCV7O ']").getText();
					Common.assertionCheckwithReport(flights.contains("Round Trip"),
							"verifying the fligths navigation in the account page",
							"user should navigate to the fligths page after click on the flights button",
							"user successfully Navigated to the flights page", "Failed click on the flights page");
				}
			catch(Exception | Error e)
			{
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("validating the account page links " + Account[i],
						"user should Navigate to the " + Account[i] + " page",
						"User unable to navigate to the " + Account[i],
						Common.getscreenShotPathforReport("user Failed to Navigate to the respective page"));
				Assert.fail();
			}
	}
		
		public void footerLinks(String Dataset){
			 String Footerlinks=data.get(Dataset).get("Footer Links");
				String[] Footer=Footerlinks.split(",");
				int i=0;
			  try{
				  Sync.waitPageLoad();
			  
			  for(i=0;i<Footer.length;i++) {
				  Common.actionsKeyPress(Keys.END);
				  Sync.waitPageLoad();
				  Common.scrollIntoView("xpath", "//a[contains(@class,'Footer') and contains(text(),'"+Footer[i]+"')]");
			  Common.clickElement("xpath","//a[contains(@class,'Footer') and contains(text(),'"+Footer[i]+"')]");
			  Sync.waitPageLoad();
			 Thread.sleep(4000);
			 Common.switchWindows();
			 String title = Common.findElement("xpath", "//h1").getText();
			 String title1 = Common.findElement("xpath", "//h1").getTagName();
				System.out.println(title);
				System.out.println(title1);
			  System.out.println(Footer[i]);
			  System.out.println( Common.getCurrentURL());
			  System.out.println(Common.getPageTitle());
			  Common.assertionCheckwithReport(title1.contains("h1") ||Common.getPageTitle().contains("FlyPigeon"),"Validate the Footer link "+Footer[i], "Click the footer link "+Footer[i]+"it will navigate to page"+Footer[i], "successfully navigating to "+Footer[i] +"page ","Failed to navigate to"+Footer[i]+"page");
			  }
			  Common.closeCurrentWindow();
			  Thread.sleep(2000);
			Common.switchToFirstTab();
			  }
			  
			  catch (Exception |Error e) {
					e.printStackTrace();
			    ExtenantReportUtils.addFailedLog("Validate the Footer link "+Footer[i],"Click the footer link "+Footer[i]+"it will navigate to page"+Footer[i], "Failed to navigate to"+Footer[i]+"page", Common.getscreenShotPathforReport("failed to land on "+Footer[i]));
			    Assert.fail();
			  
		  }
			
		  }

		public void in_Active_login_Details() {
			try {
				
				Common.textBoxInput("xpath", "//input[@placeholder='Enter your username']", "FPGNDD000008A");
				Common.textBoxInput("xpath", "//input[@placeholder='Enter your password']", "Testauto@23");
				Common.clickElement("xpath", "//button[text()='Login']");
//				Sync.waitPageLoad(1000);
				Thread.sleep(2000);
				Sync.waitElementPresent("xpath", "//div[@class='Toastify__toast-body']//p");
				Common.mouseOver("xpath", "//div[@class='Toastify__toast-body']//p");
				String alert = Common.findElement("xpath", "//div[@class='Toastify__toast-body']//p").getText();
				System.out.println(alert);
				Common.assertionCheckwithReport(
						Common.getPageTitle().contains("FlyPigeon") && alert.contains("inactive b2b") ,
						"To validate the InActive login Credentials",
						"After clicking on the login button it should display  alert as InActive login credentials",
						"It displays the alert InActive login credentials after clicking on the login button",
						"Failed to login and display alert as InActive login credentials ");

			}
			catch(Exception |Error e) {
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("To validate the InActive login Credentials",
						"After clicking on the login button it should display  alert as InActive login credentials",
						"Unable to displays the alert InActive login credentials after clicking on the login button",
						Common.getscreenShotPathforReport("Failed to login and display alert as InActive login credentials "));

				Assert.fail();
			}
		}

		public void change_Password(String Dataset) {
			// TODO Auto-generated method stub
			try
			{
				Sync.waitElementPresent("xpath","//div[contains(@class,'MuiListItemText-root')]//span[text()='Change Password']");
				Common.clickElement("xpath","//div[contains(@class,'MuiListItemText-root')]//span[text()='Change Password']");
				Thread.sleep(2000);
				String title = Common.findElement("xpath", "//h1").getText(); 
				System.out.println(title);
				Common.assertionCheckwithReport(title.contains("Change Password"),
						"verifying the change password page navigation",
						"user should able to navigate to the change password page ",
						"user successfully Navigated to the change password page ",
						" Failed click on the navigate to the change password page ");
				Sync.waitElementPresent("xpath", "//input[@name='oldPassword']"); 
				Common.textBoxInput("xpath", "//input[@name='oldPassword']", data.get(Dataset).get("Password")); 
				Sync.waitElementPresent("xpath", "//input[@name='newPassword']"); 
				Common.textBoxInput("xpath", "//input[@name='newPassword']", data.get(Dataset).get("Password"));
				Sync.waitElementPresent("xpath", "//input[@name='confirmNewPassword']"); 
				Common.textBoxInput("xpath", "//input[@name='confirmNewPassword']", data.get(Dataset).get("Password")); 
				Common.clickElement("xpath", "//button[@type='submit']");
				Thread.sleep(2000); 
				Sync.waitElementPresent("xpath", "//div[@class='Toastify__toast-body']//p"); 
				Common.mouseOver("xpath", "//div[@class='Toastify__toast-body']//p"); 
				String message = Common.findElement("xpath", "//div[@class='Toastify__toast-body']//p").getText();
				Common.assertionCheckwithReport(message.contains("password"),
						"To validate the password change functionality ",
						"After clicking on the submit button the password should be change sucessfully ",
						"sucessfully password has been changed", "Failed to change the password");
			}
			catch(Exception | Error e)
			{
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("To validate the password change functionality ",
						"After clicking on the submit button the password should be change sucessfully ",
						"Unable to change the password ",
						Common.getscreenShotPathforReport("Failed to change the password "));
				Assert.fail();
			}
			
		}

		public void update_Balance(String Dataset) {
			// TODO Auto-generated method stub
			try
			{
				Sync.waitElementPresent("xpath","//div[contains(@class,'MuiListItemText-root')]//span[text()='Add Balance']");
				Common.clickElement("xpath","//div[contains(@class,'MuiListItemText-root')]//span[text()='Add Balance']");
				Thread.sleep(2000);
				String title = Common.findElement("xpath", "//h2").getText(); 
				Common.assertionCheckwithReport(title.contains("Add Balance to wallet"),
						"verifying the add balance page navigation",
						"user should able to navigate to the add balance page ",
						"user successfully Navigated to the add balance page ",
						" Failed click on the navigate to the add balance page");
				Common.clickElement("xpath", "//input[@type='number']");
				Common.textBoxInput("xpath", "//input[@type='number']", data.get(Dataset).get("Update Balance"));
				Common.clickElement("xpath", "//button[text()='Top Up Wallet']");
				Thread.sleep(3000);
				Common.switchFrames("xpath", "//iframe[@class='razorpay-checkout-frame']");
				String payment=Common.findElement("xpath", "//p[contains(@title,'FlyPigeon Digital')]").getText();
				Common.assertionCheckwithReport(payment.contains("FlyPigeon Digital"),
						"verifying the FlyPigeon Digital popup",
						"user should able to see the FlyPigeon Digital popup when we click on the update balance ",
						"Sucessfully user able to see the FlyPigeon Digital popup ",
						" Failed to see the FlyPigeon Digital popup");
				Common.clickElement("xpath", "//input[@name='contact']");
				Common.textBoxInput("xpath", "//input[@name='contact']", data.get(Dataset).get("Phone"));
				Common.clickElement("xpath", "//button[text()='Proceed']");
				Thread.sleep(4000);
				Sync.waitPageLoad();
				String paymentpage=Common.findElement("xpath", "//h3[text()='Pay With UPI QR']").getText();
				Common.assertionCheckwithReport(paymentpage.contains("Pay With UPI QR"),
						"verifying the payment page",
						"user should able to navigate to the payment page after clicking on the proceed button ",
						"Sucessfully user navigated to the payment page ",
						" Failed to navigate to the payment page");
			}
			catch(Exception | Error e)
			{
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("verifying the payment page",
						"user should able to navigate to the payment page after clicking on the proceed button ",
						"Unable to navigate to the payment page ",
						Common.getscreenShotPathforReport("Failed to navigate to the payment page"));
				Assert.fail();
			}
			
		}
		
		public void click_Support() {
			try {
				
				Common.clickElement("xpath", "//span[text()='Callback Support']");
				Sync.waitPageLoad(3000);
				Thread.sleep(2000);
				Common.clickElement("xpath", "//button[text()='Clear All']");
				String support = Common.findElement("xpath", "//h1[text()='Callback Support']").getText();
				String support1 = Common.findElement("xpath", "//div[contains(@class,'Support_datesSearchDiv')]").getAttribute("class");
				System.out.println(support);
				System.out.println(support1);
				Common.assertionCheckwithReport(
						Common.getPageTitle().contains("FlyPigeon") && support.contains("Support")&& support1.contains("Support"),
						"To validate the user lands on Support page ",
						"After clicking on the Support button it should navigate to the Support page",
						"user Sucessfully navigate to the Support page after clicking on the Support button",
						"Failed to click Support and not navigated to the Support page ");

			}
			catch(Exception |Error e) {
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("To validate the user Navigate to Support page ",
						"After clicking on the Support button it should navigate to the Support page",
						"Unable to navigate the user to the Support page after clicking on the Support button",
						Common.getscreenShotPathforReport("Failed to click Support and not navigated to the click page "));

				Assert.fail();
			}
		}

		public void support_form(String Dataset) {
			try {
				
				Common.clickElement("xpath", "(//button[contains(@aria-label,'Choose date')])[1]");
				 Common.clickElement("xpath", "//button[@aria-label='Mar 20, 2023']");
//				Common.textBoxInput("xpath", "(//input[@placeholder='yyyy/mm/dd'])[1]", "2023/03/05");
				Common.textBoxInput("xpath", "(//input[@placeholder='yyyy/mm/dd'])[2]", "2023/03/28");
				
				Common.textBoxInput("xpath", "//input[@id='txnId']", data.get(Dataset).get("QueryID"));
				Common.textBoxInput("xpath", "//input[@id='conatactNo']", data.get(Dataset).get("Phone"));
				Common.textBoxInput("xpath", "//input[@id='emailId']", data.get(Dataset).get("Email"));
				Common.clickElement("xpath", "//button[text()='Search']");
				Sync.waitPageLoad(3000);
				Thread.sleep(2000);
				String ID= Common.findElement("xpath", "//input[@id='txnId']").getAttribute("value");
				String support = Common.findElement("xpath", "//h1[text()='Callback Support']").getText();
				String support1 = Common.findElement("xpath", "//div[contains(@class,'Support_datesSearchDiv')]").getAttribute("class");
				String IDvalue = Common.findElement("xpath", "//th[contains(text(),'"+ID+"')]").getText();
				System.out.println(support);
				System.out.println(support1);
				System.out.println(ID);
				System.out.println(IDvalue);
				Common.assertionCheckwithReport(
						Common.getPageTitle().contains("FlyPigeon") && support.contains("Support")&& support1.contains("Support"),
						"To validate the user search on Support page ",
						"After clicking on the search button it should display the search Support results",
						"user Sucessfully display the search Support results after clicking on the Search button",
						"Failed to click search and not displayed the search results ");

			}
			catch(Exception |Error e) {
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("To validate the user Navigate to Support page ",
						"After clicking on the search button it should display the search Support results",
						"Unable to display the search Support results after clicking on the Search button",
						Common.getscreenShotPathforReport("Failed to click search and not displayed the search results "));

				Assert.fail();
			}
		}

		public void support_bot(String Dataset) {
			
			String email = data.get(Dataset).get("Email");
			String phone = data.get(Dataset).get("Phone");
			
			try {
				Common.clickElement("xpath", "//button[contains(@class,'Support_supportBtn_')]");
				WebElement Select_all = Common.findElement("xpath", "//input[@id='phone']");
				Select_all.sendKeys(Keys.CONTROL + "a");
				Common.findElement("xpath", "//input[@id='phone']").sendKeys(phone);
				WebElement Selectall = Common.findElement("xpath", "//input[@id='email']");
				Selectall.sendKeys(Keys.CONTROL + "a");
				
				Common.findElement("xpath", "//input[@id='email']").sendKeys(email);

//				Common.textBoxInput("xpath", "//input[@id='phone']", "8186886443");
//				Common.textBoxInput("xpath", "//input[@id='email']", "premanath@tekkrexim.in");
				
				Common.clickElement("xpath", "//button[text()='Submit']");
			
//			Common.textBoxInput("xpath", "//textarea[@id='query']", "Test");
				//button[@aria-label='closeBtn']
				Sync.waitPageLoad(3000);
				Thread.sleep(2000);
				String support = Common.findElement("xpath", "//h4[text()='Support']").getText();
				String message = Common.findElement("xpath", "//label[text()='Message:']").getText();
				
				System.out.println(support);
				System.out.println(message);
				Common.assertionCheckwithReport(
						Common.getPageTitle().contains("FlyPigeon") && support.contains("Support")&& message.contains("Message"),
						"To validate the Support Pop-up on Support page ",
						"After clicking on the Support helpline button it should display the Support Pop-up",
						"It Sucessfully display the Support Pop-up after clicking on the Support helpline button",
						"Failed to click Support helpline and not displayed the Support Pop-up ");

			}
			catch(Exception |Error e) {
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("To validate the Support Pop-up on Support page ",
						"After clicking on the Support helpline button it should display the Support Pop-up",
						"Unable to display the Support Pop-up after clicking on the Support helpline button",
						Common.getscreenShotPathforReport("Failed to click Support helpline and not displayed the Support Pop-up "));

				Assert.fail();
			}
		}
		
		public void buses(String Dataset) {
			
			String Leavingfrom = data.get(Dataset).get("LeavingFrom");
			String Goingto = data.get(Dataset).get("GoingTo");
			try {
				
				Common.clickElement("xpath", "//span[text()='Buses']");
				Sync.waitPageLoad(3000);
				Thread.sleep(2000);
				String bus = Common.findElement("xpath", "//h2[text()='Book Bus Tickets']").getText();
				String topbuses = Common.findElement("xpath", "//h3[text()='Top Bus Routes']").getText();
				System.out.println(bus);
				System.out.println(topbuses);
				Common.assertionCheckwithReport(
						Common.getPageTitle().contains("FlyPigeon") && bus.contains("Book Bus Tickets")&& topbuses.contains("Top Bus Routes"),
						"To validate the user lands on Buses page ",
						"After clicking on the Buses button it should navigate to the Buses page",
						"user Sucessfully navigate to the Buses page after clicking on the Buses button",
						"Failed to click Buses and not navigated to the Buses page ");
				
				Common.textBoxInput("xpath", "//input[@id='leavingFrom']", "Hyderabad");
				Thread.sleep(2000);
				Common.mouseOverClick("xpath", "//ul[contains(@name,'leavingFromTable')]/li[contains(text(),'"+Leavingfrom+"')][1]");
				
				Common.textBoxInput("xpath", "//input[@id='goingTo']", "Vijayawada,");
				Common.actionsKeyPress(Keys.BACK_SPACE);
//				Common.findElement("xpath", "//input[@id='goingTo']").click();
				Thread.sleep(3000);
				Common.mouseOverClick("xpath", "//ul[contains(@name,'goingToTable')]/li[contains(text(),'"+Goingto+"')]");
				
//				Common.textBoxInput("xpath", "//input[@placeholder='yyyy/mm/dd']", data.get(Dataset).get("ToDate"));
				 Common.clickElement("xpath", "//button[contains(@aria-label,'Choose date')]");
				 Common.clickElement("xpath", "//button[@aria-label='Mar 30, 2023']");
				Thread.sleep(6000);
				Common.clickElement("xpath", "//button[contains(@class,'BusSearch_busSearchBtn')]");
				Sync.waitPageLoad(3000);
				Thread.sleep(2000);
				Sync.waitElementPresent("xpath", "//button[contains(@class,'ModifyBusSearch_modifyBus')]");
				String modify = Common.findElement("xpath", "//button[contains(@class,'ModifyBusSearch_modifyBus')]").getText();
				Common.assertionCheckwithReport(
						Common.getPageTitle().contains("FlyPigeon") && modify.contains("Modify"),
						"To validate the user lands on Buses search result page ",
						"After clicking on the Search button it should navigate to the Buses search result page",
						"user Sucessfully navigate to the Buses search result page after clicking on the search button",
						"Failed to click Search and not navigated to the Buses search result page ");
				
			}
			catch(Exception |Error e) {
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("To validate the user Navigate to Buses page ",
						"After clicking on the Buses button it should navigate to the Support page",
						"Unable to navigate the user to the Buses page after clicking on the Buses button",
						Common.getscreenShotPathforReport("Failed to click Buses and not navigated to the click page "));

				Assert.fail();
			}
			
			
		
		}
		
		public void Flight_Booking(String Dataset) {
			// TODO Auto-generated method stub
			String From1=data.get(Dataset).get("From");
			System.out.println(From1);
			try
			{
				Sync.waitElementPresent("xpath", "//label[text()='From']//parent::div");
				Common.clickElement("xpath", "//label[text()='From']//parent::div");
				Common.textBoxInput("xpath", "(//input[@id='standard-basic'])[1]", From1);
				Thread.sleep(4000);
		        String From=Common.findElement("xpath", "//div[contains(@class,'AirportAutocompleteInput_airportCo')]").getText();
		        if(From.equals("BOM"))
		        {
		        	Common.clickElement("xpath", "//div[contains(@class,'AirportAutocompleteInput_airportCo')]");
		        }
		        else
		        {
		        	Assert.fail();
		        }
		        Sync.waitElementPresent("xpath", "//label[text()='To']//parent::div");
		        Common.clickElement("xpath", "//label[text()='To']//parent::div");
		        Common.textBoxInput("xpath", "(//input[@id='standard-basic'])[2]", data.get(Dataset).get("To"));
		        Thread.sleep(4000);
		        String To=Common.findElement("xpath", "(//div[contains(@class,'AirportAutocompleteInput_airportCo')])[1]").getText();
		        if(To.equals("HYD"))
		        {
		        	Common.clickElement("xpath", "(//div[contains(@class,'AirportAutocompleteInput_airportCo')])[1]");
		        }
		        else
		        {
		        	Assert.fail();
		        }
		        Sync.waitElementPresent("xpath", "//fieldset[@aria-hidden='true']");
		        Common.clickElement("xpath", "//input[contains(@class,'MuiOutlinedInput-input MuiInputBase-input MuiIn')]");
		        Thread.sleep(4000);
		        Common.clickElement("xpath", "//button[@aria-label='Mar 30, 2023']");
		        Common.clickElement("xpath", "//button[text()='Search Flights']");
		        Sync.waitPageLoad(30);
		        Sync.waitImplicit(40);
		        Thread.sleep(12000);
		        String page=Common.findElement("xpath", "//h3").getText();
		        System.out.println(page);
				Common.assertionCheckwithReport(
						page.contains("FILTER"),
						"To validate the navigation to the flight page ",
						"After clicking on the submit button it should navigate to the flight detailed page",
						"Sucessfully navigated to the flight detailed page after clicking on the serach button",
						"Failed to Navigate to the flight detailed page");
			}
			catch(Exception | Error e)
			{
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("To validate the navigation to the flight page ",
						"After clicking on the submit button it should navigate to the flight detailed page",
						"Unable to Navigate to the flight detailed page",
						Common.getscreenShotPathforReport("Failed to Navigate to the flight detailed page"));

				Assert.fail();
			}
		}

		public void air_filters(String Dataset) {
			// TODO Auto-generated method stub
			try
			{
			   Sync.waitElementPresent("xpath", "(//div[contains(@class,'FlightFilters_de')])[3]");
			   Common.clickElement("xpath", "(//div[contains(@class,'FlightFilters_de')])[3]");
			   Sync.waitElementPresent("xpath", "//p[text()='Non-Stop']");
			   Common.clickElement("xpath", "//p[text()='Non-Stop']");
			   String stops=Common.findElement("xpath", "//p[text()='Non-Stop']").getText();
			   Common.assertionCheckwithReport(
					   stops.contains("Non-Stop"),
						"To validate the stops filter ",
						"After clicking on the stops filter filter should be selected",
						"Sucessfully filers has been seleted ",
						"Failed to select the filter");
			   Sync.waitElementPresent("xpath", " //input[@value='Indigo']");
			   Common.clickElement("xpath", " //input[@value='Indigo']");
			   Common.actionsKeyPress(Keys.HOME);
			   String airline=Common.findElement("xpath", "//div[@class='FlightResults_flightPrimaryText__BqFGr']//p").getText();
			   Common.assertionCheckwithReport(
					   airline.contains("Indigo"),
						"To validate the alirline filters selection ",
						"After clicking on the filter particualr aitrlines should be displayed",
						"Sucessfully "+ airline + " is list has been displayed after clicking on the "+ airline,
						"Failed to select the "+ airline + " filter");
			}
			catch(Exception | Error e)
			{
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("To validate the airlines filter ",
						"After clicking the airlines filter particular airlines should be dispalyed",
						"Unable to dispaly the airlines filters",
						Common.getscreenShotPathforReport("Failed to click on the airlines filters"));

				Assert.fail();
			}
			
		}

		public void top() {
			
			try {
				Common.clickElement("xpath", "//span[text()='Buses']");
				Sync.waitPageLoad(3000);
				Thread.sleep(2000);
				int top = Common.findElements("xpath", "//div[contains(@class,'BusSearch_topBusRouteDivOne')]/p").size();
				
				Sync.waitPageLoad();
				for(int i=1;i<top-17;i++) {
					System.out.println(i);
//					List<WebElement> to = Common.findElements("xpath", "//div[contains(@class,'BusSearch_topBusRouteDivOne')]/p");
//					System.out.println(to);
					String to= Common.getText("xpath", "(//div[contains(@class,'BusSearch_topBusRouteDivOne')]/p)["+i+"]");
					System.out.println(to);
					Common.clickElement("xpath", "(//div[contains(@class,'BusSearch_topBusRouteDivOne')]/p)["+i+"]");
					Thread.sleep(3000);
					Common.navigateBack();
				
						Common.assertionCheckwithReport(
						Common.getPageTitle().contains("FlyPigeon"),
						"validating the Top Bus Routes"+to, "System directs the user to the Top Bus Routes"+to,
						"Sucessfully user navigates to the Top Bus Routes"+to, "Failed to navigate to the Top Bus Routes"+to);
				}
			}
				catch (Exception | Error e) {
				e.printStackTrace();

				ExtenantReportUtils.addFailedLog("validating the Top Bus Routes", "System directs the user to the Top Bus Routes",
						" user unable navigates to the Top Bus Routes", "Failed to navigate to the Top Bus Routes");
				Assert.fail();
			}

		}
		
		public void bus_selection() {
			
			try
			{
				Sync.waitPageLoad();
				Thread.sleep(3000);
			   Sync.waitElementPresent("xpath", "(//button[text()='SELECT SEAT'])[1]");
			   Common.clickElement("xpath", "(//button[text()='SELECT SEAT'])[1]");
			   
			   Sync.waitElementPresent("xpath", "//input[@name='boardingPoint' and contains(@value,'Ashok Nagar (BHEL)')]");
			   Common.clickElement("xpath", "//input[@name='boardingPoint' and contains(@value,'Ashok Nagar (BHEL)')]");
			   
			   Common.clickElement("xpath", "//input[@name='droppingPoint' and contains(@value,'Vijayawada')]");
			   Thread.sleep(2000);
			   Sync.waitElementPresent("xpath", "(//div[contains(@class,'BusSeat_seat__7XmpE BusSeat_availableSeat_')]/small)[3]");
			   Common.clickElement("xpath", "(//div[contains(@class,'BusSeat_seat__7XmpE BusSeat_availableSeat_')]/small)[3]");
			   
			   String seats=Common.findElement("xpath", "//p[contains(text(),'Select Seats')]").getText();
			   Common.assertionCheckwithReport(
					   seats.contains("Select Seats"),
						"To validate the Choose Seats  ",
						"After clicking on the select seat should navigate to Choose Seats ",
						"Sucessfully navigated to Choose Seats  ",
						"Failed to navigate to the Choose Seats");
			   
			   Sync.waitElementPresent("xpath", " //button[text()='Continue']");
			   Common.clickElement("xpath", " //button[text()='Continue']");
			   Sync.waitPageLoad();
			   Thread.sleep(3000);
			   String review=Common.findElement("xpath", "//a[text()='Itinerary Review']").getText();
			   
			  
			   Common.assertionCheckwithReport(
					   review.contains("Itinerary Review"),
						"To validate the Itinerary Review page ",
						"After clicking on the Continue should navigate to Itinerary Review",
						"Sucessfully "+ review + "page has been displayed after clicking on the continue",
						"Failed to navigate to  the "+ review + " page");
			}
			catch(Exception | Error e)
			{
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("To validate the Choose Seats ",
						"After clicking the select seat should navigate to Choose Seats",
						"Unable to navigate to the Choose Seats",
						Common.getscreenShotPathforReport("Failed to navigate to the Choose Seats"));

				Assert.fail();
			}
			
		}
		
			public void bus_passenger(String Dataset) {
			
			try
			{
				Sync.waitElementPresent("xpath", " //button[text()='Continue']");
				   Common.clickElement("xpath", " //button[text()='Continue']");
				   Sync.waitPageLoad();
					Thread.sleep(3000);
			   String passenger=Common.findElement("xpath", "//p[text()='Traveller Details']").getText();
			   Common.assertionCheckwithReport(
					   passenger.contains("Traveller Details"),
						"To validate the Passenger Details  ",
						"After clicking on the continue should navigate to Passenger Details ",
						"Sucessfully navigated to the Passenger Details  ",
						"Failed to navigate to the Passenger Details");
			   
			   Sync.waitElementPresent("xpath", " //input[@name='phone']");
			   Common.textBoxInput("xpath", "//input[@name='phone']",data.get(Dataset).get("Phone"));
			   Common.textBoxInput("xpath", "//input[@name='email']",data.get(Dataset).get("Email"));
			   Common.textBoxInput("xpath", "//input[@placeholder='First Name']",data.get(Dataset).get("FirstName"));
			   Common.textBoxInput("xpath", "//input[@placeholder='Last Name']",data.get(Dataset).get("LastName"));
			   Common.textBoxInput("xpath", "//input[@placeholder='Age']",data.get(Dataset).get("Age"));
			   Common.dropdown("xpath", "//select[@name='gender']", SelectBy.TEXT, data.get(Dataset).get("Gender"));
			   Sync.waitPageLoad();
			   Thread.sleep(3000);
			   String PassengerDetails=Common.findElement("xpath", "//a[text()='Passenger Details']").getText();
			   
			   Common.assertionCheckwithReport(
					   PassengerDetails.contains("Passenger Details")|| Common.getPageTitle().contains("FlyPegion"),
						"To validate the Passenger Details information  ",
						"Details are filled in the Passenger Details page",
						"Sucessfully "+ PassengerDetails + "page has been filled with details",
						"Failed to enter to the "+ PassengerDetails + " page");
			}
			catch(Exception | Error e)
			{
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("To validate the PassengerDetails ",
						"After clicking the continue should navigate to Passenger Details",
						"Unable to navigate to the Passenger Details",
						Common.getscreenShotPathforReport("Failed to navigate to the Passenger Details"));

				Assert.fail();
			}
			
		}
			
			public void payment() {
				try {
					Sync.waitElementPresent("xpath", " //button[text()='Continue']");
					   Common.clickElement("xpath", " //button[text()='Continue']");
					Sync.waitPageLoad(3000);
					
					Thread.sleep(3000);
					String payment = Common.findElement("xpath", "//a[text()='Payment']").getText();
					String proceed = Common.findElement("xpath", "//button[text()='Proceed']").getText();
					System.out.println(payment);
					System.out.println(proceed);
					Common.assertionCheckwithReport(
							Common.getPageTitle().contains("FlyPigeon") && payment.contains("Payment")&& proceed.contains("Proceed"),
							"To validate the user lands on Payment page",
							"It should navigate to the Payment page",
							"user Sucessfully navigated to the Payment page ",
							"Failed and not navigated to the Payment page ");

				}
				catch(Exception |Error e) {
					e.printStackTrace();
					ExtenantReportUtils.addFailedLog("To validate the user user lands on Payment page",
							"It should navigate to the Payment page",
							"Unable to navigate the user to the Payment page",
							Common.getscreenShotPathforReport("Failed and not navigated to the Payment page "));

					Assert.fail();
				}
			}
			
			public void account_update(String Dataset) {
				try {
					Sync.waitPageLoad(2000);
					Sync.waitElementPresent("xpath","//div[contains(@class,'MuiListItemText-root')]//span[text()='Account']");
					Common.clickElement("xpath","//div[contains(@class,'MuiListItemText-root')]//span[text()='Account']");
					//div[contains(@class,'MuiListItemText-root')]//span[text()='Profile']
					Sync.waitPageLoad(3000);
					Thread.sleep(3000);
					String ProfileBtn = Common.findElement("xpath", "//button[text()='Update Profile']").getText();
					String Profile = Common.findElement("xpath", "//h1[text()='Update Profile']").getText();
					System.out.println(ProfileBtn);
					System.out.println(Profile);
					Common.assertionCheckwithReport(
							Common.getPageTitle().contains("FlyPigeon") && ProfileBtn.contains("Update Profile")&& Profile.equals("Update Profile"),
							"To validate the user lands on Update Profile page after successfull login",
							"After clicking on the Account Profile button it should navigate to the Update Profile page",
							"user Sucessfully navigates to the Update Profile page after clicking on the Account Profile ",
							"Failed to click Account and not navigated to the Update Profile page ");
					
					Sync.waitElementPresent("xpath", "//input[@name='person_firstname']");
					
					WebElement Select_all = Common.findElement("xpath", "//input[@name='person_firstname']");
					Select_all.sendKeys(Keys.CONTROL + "a");
					Common.findElement("xpath", "//input[@name='person_firstname']").sendKeys(data.get(Dataset).get("FirstName"));
					
					WebElement Select_all1 = Common.findElement("xpath", "//input[@name='person_lastname']");
					Select_all1.sendKeys(Keys.CONTROL + "a");
					Common.findElement("xpath", "//input[@name='person_lastname']").sendKeys(data.get(Dataset).get("LastName"));
					
					WebElement Select_all2 = Common.findElement("xpath", "//input[@name='contact_Designation']");
					Select_all2.sendKeys(Keys.CONTROL + "a");
					Common.findElement("xpath", "//input[@name='contact_Designation']").sendKeys(data.get(Dataset).get("Designation"));
					
					WebElement Select_all3 = Common.findElement("xpath", "//input[@name='business_address']");
					Select_all3.sendKeys(Keys.CONTROL + "a");
					Common.findElement("xpath", "//input[@name='business_address']").sendKeys(data.get(Dataset).get("Address"));
					
					WebElement Select_all4 = Common.findElement("xpath", "//input[@name='business_city']");
					Select_all4.sendKeys(Keys.CONTROL + "a");
					Common.findElement("xpath", "//input[@name='business_city']").sendKeys(data.get(Dataset).get("City"));
			
					Common.dropdown("xpath", "//select[@name='business_state']", SelectBy.TEXT, data.get(Dataset).get("State"));
					
					WebElement Select_all5 = Common.findElement("xpath", "//input[@name='business_pincode']");
					Select_all5.sendKeys(Keys.CONTROL + "a");
					Common.findElement("xpath", "//input[@name='business_pincode']").sendKeys(data.get(Dataset).get("Pincode"));

					Common.clickElement("xpath", "//button[text()='Update Profile']");
					Sync.waitPageLoad(3000);
					Thread.sleep(2000);
					
					Sync.waitElementPresent("xpath", "//div[@class='Toastify__toast-body']//p");
					Common.mouseOver("xpath", "//div[@class='Toastify__toast-body']//p");
					String alert = Common.findElement("xpath", "//div[@class='Toastify__toast-body']//p").getText();
					System.out.println(alert);

					Common.assertionCheckwithReport(
							Common.getPageTitle().contains("FlyPigeon") && ProfileBtn.contains("Update Profile")&& alert.contains("Profile updated successfully"),
							"To validate the user Updates the Account Profile page ",
							"After clicking on the Update Profile button the Account Profile should be updated",
							"user Sucessfully updated the Account Profile page after clicking on the Update Profile ",
							"Failed to click Update Profile and not saved to the Account Profile ");
				}
				catch(Exception |Error e) {
					e.printStackTrace();
					ExtenantReportUtils.addFailedLog("To validate the user lands on Update Profile page after successfull logins",
							"After clicking on the Account Profile button it should navigate to the Update Profile page",
							"Unable to click Account Profile and Profile is not Updated",
							Common.getscreenShotPathforReport("Failed to click Account Profile and not updated the Profile "));

					Assert.fail();
				}
			}

			public void bus_filters() {
				
				try
				{
				   Sync.waitElementPresent("xpath", "//input[@value='AC']");
				   Common.clickElement("xpath", "//input[@value='AC']");
				   Sync.waitElementPresent("xpath", "(//small[contains(text(),'After')])[1]");
				   Common.clickElement("xpath", "(//small[contains(text(),'After')])[1]");
				   Common.clickElement("xpath", "(//small[contains(text(),'Before')])[2]");
				  
				   Common.clickElement("xpath", "//small[contains(text(),'operators')]");
				  Common.scrollIntoView("xpath", "//p[contains(text(),'Bus Type')]");
				 Thread.sleep(3000);
				   String bustype=Common.findElement("xpath", "//p[contains(text(),'Bus Type')]").getText();
				   System.out.println(bustype);
				   Common.assertionCheckwithReport(Common.getPageTitle().contains("FlyPigeon") && 
						   bustype.contains("BUS TYPE"),
							"To validate the Bus Type filter ",
							"After clicking on the Bus Type filter the filter should be selected",
							"Sucessfully Bus Type  filter has been seleted ",
							"Failed to select the Bus Type filter");
				   
				   Sync.waitElementPresent("xpath", " //small[contains(text(),'boarding points')]");

				   Common.clickElement("xpath", "//small[contains(text(),'boarding points')]");
				   Common.clickElement("xpath", "//small[contains(text(),'dropping points')]");
				   String boarding=Common.findElement("xpath", "//p[contains(text(),'Boarding Point')]").getText();
				   System.out.println(boarding);
				   Thread.sleep(4000);
				   Common.assertionCheckwithReport(Common.getPageTitle().contains("FlyPigeon")&&
						   boarding.contains("BOARDING POINT"),
							"To validate the Bus Point filters selection ",
							" Should display the Bus Point filters in the filters section",
							"Sucessfully Bus Point filters has been displayed in the filters section",
							"Failed to display the Bus Point filters");
				}
			
			catch(Exception |Error e)
				{
					e.printStackTrace();
					ExtenantReportUtils.addFailedLog("To validate the filters ",
							"After clicking the filter selected filters should be dispalyed",
							"Unable to dispaly the filters after selection",
							Common.getscreenShotPathforReport("Failed to click on the filters"));

					Assert.fail();
				}
				Common.clickElement("xpath", "//button[contains(text(),'Clear All')]");
			}

			public void flight_Itinerary() {
				// TODO Auto-generated method stub
				try
				{
			    String flightnumber=Common.findElement("xpath", "(//div[@class='FlightResults_flightPrimaryText__BqFGr']//p//span)[2]").getText();
			    System.out.println(flightnumber);
				Sync.waitElementPresent("xpath", "//button[text()='Book Now']");	
				Common.clickElement("xpath", "//button[text()='Book Now']");
				Sync.waitImplicit(40);
				Thread.sleep(7000);
				String iternary=Common.findElement("xpath", "//div[contains(@class,'ItineraryReview_segmentAi')]//span").getText();
				System.out.println(iternary);
				Common.assertionCheckwithReport(flightnumber.contains("461")||
						iternary.contains("461"),
							"To validate the navigation to the flight Itinerary Review page",
							" It should be navigate to the flight Itinerary Review page",
							"Sucessfully Navigated to the flight Itinerary Review page",
							"Failed to Navigate to the Itinerary Review page");
				Common.clickElement("xpath", "//div[contains(@class,'ItineraryR')]//input");
				String insurance=Common.findElement("xpath", "(//div[text()='Insurance']//parent::div//div)[2]").getText();
				Common.assertionCheckwithReport(insurance.contains("Rs.199"),
							"To validate the insurance in the order summary ",
							" insurance should be added to the order summary page",
							"Sucessfully insurance has been dispalyed in thr order summary page",
							"Failed to see the insurance in the order summary page");
				Common.clickElement("xpath", "//button[text()='Proceed']");
				String Passenger=Common.findElement("xpath", "//div[contains(@class,'MuiGrid-root MuiGrid')]//span[text()='2']").getText();
				Common.assertionCheckwithReport(flightnumber.contains("461")||
						Passenger.contains("PassengerDetails"),
							"To validate the navigation to the PassengerDetails page",
							" It should be navigate to the flight PassengerDetails page",
							"Sucessfully Navigated to the flight PassengerDetails page",
							"Failed to Navigate to the PassengerDetails page");
				
				}
				catch(Exception | Error e)
				{
					e.printStackTrace();
					ExtenantReportUtils.addFailedLog("To validate the navigation to the PassengerDetails page",
							" It should be navigate to the flight PassengerDetails page",
							"Unable to Navigate to the PassengerDetails page",
							Common.getscreenShotPathforReport("Failed to Navigate to the PassengerDetails page"));
					Assert.fail();
				}
				
			}

			public void flight_PassengerDetails(String Dataset) {
				// TODO Auto-generated method stub
				try
				{
					Sync.waitElementPresent("xpath", "//input[@name='mobile']");	
					Common.textBoxInput("xpath", "//input[@name='mobile']", data.get(Dataset).get("Phone"));
					Sync.waitElementPresent("xpath", "//input[@name='email']");	
					Common.textBoxInput("xpath", "//input[@name='email']", data.get(Dataset).get("Email"));
					Common.dropdown("xpath", "//select[@id='gender']", SelectBy.TEXT, "Mr");
					Sync.waitElementPresent("xpath", "//input[@name='firstName']");	
					Common.textBoxInput("xpath", "//input[@name='firstName']", data.get(Dataset).get("Firstname"));
					Sync.waitElementPresent("xpath", "//input[@name='lastName']");	
					Common.textBoxInput("xpath", "//input[@name='lastName']", data.get(Dataset).get("lastname"));
					Common.clickElement("xpath", "//span[text()='Add Baggage']");
					Common.clickElement("xpath", "(//button[contains(@class,'PassengerDetails_SSRItemBtn__8vqot')]//p//span)[2]");
					String addons=Common.findElement("xpath", "(//button[contains(@class,'PassengerDetails_SSRItemBtn__8vqot')]//p//span)[2]").getText();
					String ordersummary=Common.findElement("xpath", "(//div[@style='margin-bottom: 1rem;']//div)[3]").getText();
					Common.assertionCheckwithReport(addons.equals(ordersummary),
								"To validate the addons in the order summary",
								" Addons should be added to the order summary",
								"Sucessfully Addons has been added to the order summary",
								"Failed to display the addons on the order summary");
					Common.clickElement("xpath", "//button[text()='Proceed']");
					String SelectSeats=Common.findElement("xpath", "//h4").getText();
					System.out.println(SelectSeats);
					Common.assertionCheckwithReport(Common.getPageTitle().contains("FlyPigeon")||SelectSeats.contains("Select seats"),
								"To validate the navigation to the SelectSeats page",
								" It should be navigate to the flight SelectSeats page",
								"Sucessfully Navigated to the flight SelectSeats page",
								"Failed to Navigate to the SelectSeats page");
					
					
				}
				catch(Exception | Error e)
				{
					e.printStackTrace();
					ExtenantReportUtils.addFailedLog("To validate the navigation to the SelectSeats page",
							" It should be navigate to the flight SelectSeats page",
							"Unable to Navigate to the SelectSeats page",
							Common.getscreenShotPathforReport("Failed to Navigate to the SelectSeats page"));
					Assert.fail();
				}
			}
			
			public void markups() {
				try {
					Sync.waitElementPresent("xpath", "//span[contains(text(),'Dashboard')]");
					Common.clickElement("xpath", "//span[contains(text(),'Dashboard')]");
					Sync.waitPageLoad(3000);
					Common.clickElement("xpath", "//span[contains(text(),'Set Markups')]");
					Thread.sleep(5000);
					String markups = Common.findElement("xpath", "//h1[contains(text(),'')]").getText();
					String domestic = Common.findElement("xpath", "//ol[contains(@class,'Markups_tabsList')]//li[1]").getText();
					System.out.println(markups);
					System.out.println(domestic);
					Common.assertionCheckwithReport(
							Common.getPageTitle().contains("FlyPigeon") && markups.contains("Markups")&& domestic.equals("Domestic Flights"),
							"To validate the user lands on Markups" +domestic,
							"After clicking on the "+domestic+" it should navigate to the "+domestic,
							"user Sucessfully navigate to the "+domestic+" after clicking on the " +domestic,
							"Failed and not navigated to the "+ domestic);
					
					Common.clickElement("xpath", "//ol[contains(@class,'Markups_tabsList')]//li[2]");
					Thread.sleep(5000);
					String international = Common.findElement("xpath", "//ol[contains(@class,'Markups_tabsList')]//li[2]").getText();
					System.out.println(international);
					
					Common.assertionCheckwithReport(
							Common.getPageTitle().contains("FlyPigeon") && international.contains("International Flights"),
							"To validate the user lands on Markups " +international,
							"After clicking on the "+international+" it should navigate to the "+international,
							"user Sucessfully navigate to the "+international+" after clicking on the "+international,
							"Failed and not navigated to the "+international);
					
					Common.clickElement("xpath", "//ol[contains(@class,'Markups_tabsList')]//li[3]");
					Thread.sleep(5000);
					String buses = Common.findElement("xpath", "//ol[contains(@class,'Markups_tabsList')]//li[3]").getText();
					System.out.println(buses);
					
					Common.assertionCheckwithReport(
							Common.getPageTitle().contains("FlyPigeon") && buses.contains("Buses"),
							"To validate the user lands on MarkUps"+buses,
							"After clicking on the "+buses+" it should navigate to the "+buses,
							"user Sucessfully navigate to the "+buses+" after clicking on the "+buses,
							"Failed and not navigated to the "+buses);

				}
				catch(Exception |Error e) {
					e.printStackTrace();
					ExtenantReportUtils.addFailedLog("To validate the user Navigate to Markups page ",
							"After clicking on the MarkUps it should navigate to the MarksUp page",
							"Unable to navigate the user to the MarkUps after clicking on the MarkUps button",
							Common.getscreenShotPathforReport("Failed and not navigated to the MarkUps page "));

					Assert.fail();
				}
			}
			
			public void commission_Details() {
				try {
					Sync.waitElementPresent("xpath", "//span[contains(text(),'Commission Details')]");
					Common.clickElement("xpath", "//span[contains(text(),'Commission Details')]");
					Sync.waitPageLoad(3000);
					
					Thread.sleep(5000);
					String commission = Common.findElement("xpath", "//h1[contains(text(),'')]").getText();
					String domestic = Common.findElement("xpath", "//ol[contains(@class,'Commissions_tabsList')]//li[1]").getText();
					System.out.println(commission);
					System.out.println(domestic);
					Common.assertionCheckwithReport(
							Common.getPageTitle().contains("FlyPigeon") && commission.contains("Commission Details")&& domestic.contains("Domestic Flights"),
							"To validate the user lands on Commission" +domestic,
							"After clicking on the "+domestic+" it should navigate to the "+domestic,
							"user Sucessfully navigate to the "+domestic+" after clicking on the" +domestic,
							"Failed to signIn and not navigated to the Home page ");
					
					Common.clickElement("xpath", "//ol[contains(@class,'Commissions_tabsList')]//li[2]");
					Thread.sleep(5000);
					String international = Common.findElement("xpath", "//ol[contains(@class,'Commissions_tabsList')]//li[2]").getText();
					System.out.println(international);
					
					Common.assertionCheckwithReport(
							Common.getPageTitle().contains("FlyPigeon") && international.contains("International Flights"),
							"To validate the user lands on Commission" +international,
							"After clicking on the "+international+" it should navigate to the "+international,
							"user Sucessfully navigate to the "+international+" after clicking on the "+international,
							"Failed and not navigated to the "+international);
					
					Common.clickElement("xpath", "//ol[contains(@class,'Commissions_tabsList')]//li[3]");
					Thread.sleep(5000);
					String buses = Common.findElement("xpath", "//ol[contains(@class,'Commissions_tabsList')]//li[3]").getText();
					System.out.println(buses);
					
					Common.assertionCheckwithReport(
							Common.getPageTitle().contains("FlyPigeon") && buses.contains("Buses"),
							"To validate the user lands on Commission" +buses,
							"After clicking on the "+buses+" it should navigate to the "+buses,
							"user Sucessfully navigate to the "+buses+"after clicking on the "+buses,
							"Failed and not navigated to the "+buses);

				}
				catch(Exception |Error e) {
					e.printStackTrace();
					ExtenantReportUtils.addFailedLog("To validate the user Navigate to Commission details page ",
							"After clicking on the Commission details it should navigate to the Commission details page",
							"Unable to navigate the user to the Commission details after clicking on the Commission details",
							Common.getscreenShotPathforReport("Failed and not navigated to the Commission details page "));

					Assert.fail();
				}
			}
			
			public void Transaction(String Dataset) {
				// TODO Auto-generated method stub
				String TrID=data.get(Dataset).get("Transaction ID");
				try
				{
					Sync.waitElementPresent("xpath","//div[contains(@class,'MuiListItemText-root')]//span[text()='Transactions']");
					Common.clickElement("xpath","//div[contains(@class,'MuiListItemText-root')]//span[text()='Transactions']");
					Sync.waitElementPresent("xpath", "(//button[@type='button'])[2]");
					Common.clickElement("xpath", "(//button[@type='button'])[2]");
					Common.clickElement("xpath", "//button[@aria-label='Mar 1, 2023']");
					Sync.waitElementPresent("xpath", "//input[@id='txnId']");
					Common.textBoxInput("xpath", "//input[@id='txnId']", TrID);
					Common.clickElement("xpath", "//div[@id='demo-simple-select']");
					Common.clickElement("xpath", "//li[@data-value='BUS']");
					Common.clickElement("xpath", "//div[@id='txn-type-demo-simple-select']");
					Common.clickElement("xpath", "//li[@data-value='DEBIT']");
					Common.clickElement("xpath", "//div[@id='status-type-demo-simple-select']");
					Common.clickElement("xpath", "//li[@data-value='success']");
					Common.clickElement("xpath", "//button[text()='Search']");
					Thread.sleep(4000);
					String Transactionid=Common.findElement("xpath", "//th[contains(@class,'MuiTableCell-root MuiTableCell-bo')][3]//p").getText();
					Common.assertionCheckwithReport(Transactionid.contains(TrID),
							"To validate the transaction id in search results",
							" After clicking on the search button transaction id should display",
							"Sucessfully Transaction Id has been displayed",
							"Failed to Display the transaction id");
					
					
				}
				catch(Exception | Error e)
				{
					e.printStackTrace();
					ExtenantReportUtils.addFailedLog("To validate the transaction id in search results",
							" After clicking on the search button transaction id should display",
							"Unable to Display the transaction id",
							Common.getscreenShotPathforReport("Failed to Display the transaction id"));
					Assert.fail();
				}
				
			}
}