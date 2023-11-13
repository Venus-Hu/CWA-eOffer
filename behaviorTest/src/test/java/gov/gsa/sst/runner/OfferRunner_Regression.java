package gov.gsa.sst.runner;

import org.junit.runner.RunWith;

import annotations.TestDataFiles;
import cukes.MergedDataInjectedCucumber;

import cucumber.api.CucumberOptions;
import java.io.*;
import org.junit.AfterClass;

import com.cucumber.listener.Reporter;

@RunWith(MergedDataInjectedCucumber.class)
@CucumberOptions(plugin = { "pretty",
		"com.cucumber.listener.ExtentCucumberFormatter:target/extent-reports/eOffer_Regression_Report.html" },

		//	 tags = { "@debug", "~@skip" ,"~@wip","~@fpt"},

		tags = { "@all_env", "~@skip", "~@wip", "~@fpt" },

		features = { "src/test/resources/features/eOffer-new" }, glue = { "com.karsun.kic.tan", "org.openqa",
				"gov.gsa.sst" })
@TestDataFiles(files = {

		"src/test/resources/data/gsaTest/offer/offer_gsa.json"

})
public class OfferRunner_Regression {

	public OfferRunner_Regression() {
		// TODO Auto-generated constructor stub
	}

	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig(new File("src/test/resources/config/extent-config-Regression.xml"));

		Reporter.setSystemInfo("user", System.getProperty("user.name"));
		Reporter.setSystemInfo("os", "Mac OSX");
		Reporter.setSystemInfo("Running environment", "eOffer-Test Env");
//        Reporter.setTestRunnerOutput("Sample test runner output message");
	}
}