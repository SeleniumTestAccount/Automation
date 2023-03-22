package TestExecute.Flypegion;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestComponent.Fly_Pegion.FlyPegionHelper;
import TestLib.Common;
import TestLib.Login;

public class FP_002_Account_Registration_Status {

	String datafile = "FlyPegion//FlyPegionTestData.xlsx";
	FlyPegionHelper FlyPegion = new FlyPegionHelper(datafile, "DataSet");

	@Test(retryAnalyzer = Utilities.RetryAnalyzer.class)
	public void Verify_Account_Registrationt_Functionality() {
		try {
			FlyPegion.verifyHomePage();
			FlyPegion.click_Login();
			FlyPegion.click_Registration_Status();
			FlyPegion.registration_Status_check();
			
			

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


