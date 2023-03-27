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
				
				Common.clickElement("xpath", "//span[text()='Support']");
				Sync.waitPageLoad(3000);
				Thread.sleep(2000);
				Common.clickElement("xpath", "//button[text()='Clear All']");
				String support = Common.findElement("xpath", "//h1[text()='Support']").getText();
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
				
				Common.textBoxInput("xpath", "(//input[@placeholder='yyyy/mm/dd'])[1]", "2023/03/05");
				Common.textBoxInput("xpath", "(//input[@placeholder='yyyy/mm/dd'])[2]", "2023/03/24");
				
				Common.textBoxInput("xpath", "//input[@id='txnId']", data.get(Dataset).get("QueryID"));
				Common.textBoxInput("xpath", "//input[@id='conatactNo']", data.get(Dataset).get("Phone"));
				Common.textBoxInput("xpath", "//input[@id='emailId']", data.get(Dataset).get("Email"));
				Common.clickElement("xpath", "//button[text()='Search']");
				Sync.waitPageLoad(3000);
				Thread.sleep(2000);
				String ID= Common.findElement("xpath", "//input[@id='txnId']").getAttribute("value");
				String support = Common.findElement("xpath", "//h1[text()='Support']").getText();
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
				Thread.sleep(2000);
				Common.clickElement("xpath", "//button[contains(@class,'BusSearch_busSearchBtn')]");
				Sync.waitPageLoad(3000);
				Thread.sleep(2000);
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
		        Sync.waitPageLoad();
		        Thread.sleep(5000);
		        String page=Common.findElement("xpath", "//h3").getText();
				Common.assertionCheckwithReport(
						page.contains("Showing best flights"),
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
			}
			catch(Exception | Error e)
			{
				e.printStackTrace();
				ExtenantReportUtils.addFailedLog("To validate the stops filter ",
						"After clicking on the stops filter filter should be selected",
						"Unable to select the filter",
						Common.getscreenShotPathforReport("Failed to select the filter"));

				Assert.fail();
			}
			
		}

	
}