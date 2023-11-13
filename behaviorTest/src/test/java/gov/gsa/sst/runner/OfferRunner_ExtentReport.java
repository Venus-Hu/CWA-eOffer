package gov.gsa.sst.runner;

import org.junit.runner.RunWith;


import annotations.TestDataFiles;
import cucumber.api.CucumberOptions;
import cukes.MergedDataInjectedCucumber;

import java.io.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import com.cucumber.listener.Reporter;
import com.cucumber.listener.ExtentCucumberFormatter;




@RunWith(MergedDataInjectedCucumber.class)
//@RunWith(Cucumber.class)
@CucumberOptions(
		
		plugin = { "com.cucumber.listener.ExtentCucumberFormatter:target/extent-reports/report.html" },

//		 plugin = { "com.cucumber.listener.ExtentCucumberFormatter:target/extent-reports/report.html"},
		tags = { "@smokeTest" }, 
		features = {"src/test/resources/features/eOffer-new" }, 
		glue = { "org.openqa", "gov.gsa.sst" })

@TestDataFiles(files = { "src/test/resources/data/gsaTest/offer/offer_gsa.json" })

public class OfferRunner_ExtentReport {

//	@BeforeClass
//	public static void startReport() {
//		ExtentReports extentReports = new ExtentReports();
//		extentReports.flush();
//	}

	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig(new File("src/test/resources/config/extent-config.xml"));
		
        Reporter.setSystemInfo("user", System.getProperty("user.name"));
        Reporter.setSystemInfo("os", "Mac OSX");
        Reporter.setTestRunnerOutput("Sample test runner output message");
	}
}
