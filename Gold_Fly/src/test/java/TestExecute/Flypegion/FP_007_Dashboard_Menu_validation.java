package TestExecute.Flypegion;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import TestComponent.Fly_Pegion.FlyPegionHelper;
import TestLib.Common;
import TestLib.Login;

public class FP_007_Dashboard_Menu_validation {

	String datafile = "FlyPegion//FlyPegionTestData.xlsx";
	FlyPegionHelper FlyPegion = new FlyPegionHelper(datafile, "DataSet");

	@Test(retryAnalyzer = Utilities.RetryAnalyzer.class)
	public void Verify_Dashboard_Menu_Functionality() {
		try {
			FlyPegion.verifyHomePage();
			FlyPegion.click_Login();
			FlyPegion.login_Details("Account");
			FlyPegion.Dashboard_validation("Account");

		} catch (Exception e) {

		}
	}

	@AfterTest
	public void clearBrowser() {
		Common.closeAll();

	}

	@BeforeTest
	public void startTest() throws Exception {
		System.setProperty("configFile", "Flypegion\\config.properties");
		Login.signIn();
	}

}


