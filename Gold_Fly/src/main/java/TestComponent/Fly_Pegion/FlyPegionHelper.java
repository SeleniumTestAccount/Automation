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
			Sync.waitPageLoad(3000);
			Thread.sleep(1000);
			String alert = Common.findElement("xpath", "//div[@class='Toastify']/div").getAttribute("class");
			System.out.println(alert);
			Common.assertionCheckwithReport(
					Common.getPageTitle().contains("FlyPigeon") && alert.contains("Toastify") ,
					"To validate the Invalid login Credentials",
					"After clicking on the login button it should display  alert as Invalid login credentials",
					"It displays the alert Invalid login credentials after clicking on the login button",
					"Failed to login and display alert as Invalid login credentials ");

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

	public void Sign_up() {
		// TODO Auto-generated method stub
		
		try
		{
			Sync.waitElementPresent("xpath", "//input[@id='agent']");
			Common.clickElement("xpath", "//input[@id='agent']");
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
			 Thread.sleep(2000);
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
				
				Common.textBoxInput("xpath", "//input[@placeholder='Enter your username']", "FPGNCI000041A");
				Common.textBoxInput("xpath", "//input[@placeholder='Enter your password']", "Testauto@23");
				Common.clickElement("xpath", "//button[text()='Login']");
				Sync.waitPageLoad(3000);
				Thread.sleep(1000);
				String alert = Common.findElement("xpath", "//div[@class='Toastify']/div").getAttribute("class");
				System.out.println(alert);
				Common.assertionCheckwithReport(
						Common.getPageTitle().contains("FlyPigeon") && alert.contains("Toastify") ,
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

	
}