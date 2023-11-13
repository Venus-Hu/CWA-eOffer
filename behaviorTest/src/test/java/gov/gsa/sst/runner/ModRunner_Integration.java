package gov.gsa.sst.runner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;
import annotations.TestDataFiles;
import cukes.MergedDataInjectedCucumber;

import cucumber.api.CucumberOptions;

@RunWith(MergedDataInjectedCucumber.class)
@CucumberOptions(plugin = { "pretty",
		"com.cucumber.listener.ExtentCucumberFormatter:target/extent-reports/eMod_Integration_Report.html" },

		tags = { "@debug", "~@skip", "~@wip", "~@fpt" },

		features = { "src/test/resources/features/eMod/" }, glue = { "com.karsun.kic.tan", "org.openqa",
				"gov.gsa.sst" })
@TestDataFiles(files = { "src/test/resources/data/gsaTest/emod/additions_gsa.json",
		"src/test/resources/data/gsaTest/emod/administrative_gsa.json",
		"src/test/resources/data/gsaTest/emod/cancel_gsa.json",
		"src/test/resources/data/gsaTest/emod/deletions_gsa.json",
		"src/test/resources/data/gsaTest/emod/legal_gsa.json", "src/test/resources/data/gsaTest/emod/pricing_gsa.json",
		"src/test/resources/data/gsaTest/emod/productCatalog_gsa.json",
		"src/test/resources/data/gsaTest/emod/technical_gsa.json",
		"src/test/resources/data/gsaTest/emod/terms_gsa.json" })
public class ModRunner_Integration {
	public ModRunner_Integration() {
		// TODO Auto-generated constructor stub
	}

	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig(new File("src/test/resources/config/extent-config-Integration.xml"));

		Reporter.setSystemInfo("user", System.getProperty("user.name"));
		Reporter.setSystemInfo("os", "Mac OSX");
		Reporter.setSystemInfo("Running environment", "eMod-Test Env");
//        Reporter.setTestRunnerOutput("Sample test runner output message");
	}
}
