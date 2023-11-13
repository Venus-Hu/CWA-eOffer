package gov.gsa.sst.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cucumber.listener.Reporter;
import com.google.gson.JsonArray;

import comment.Steps;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.dunspage.DunsPage;
import gov.gsa.sst.modlist.ModListPage;
import gov.gsa.sst.offerslist.OffersListPage;
import gov.gsa.sst.runner.OfferRunner_Regression;
import gov.gsa.sst.util.DataUtil;
import gov.gsa.sst.util.LoadProperties;
import gov.gsa.sst.util.data.Offer;
import gov.gsa.sst.util.ws.SOAPClient;
import util.ActionByPartialLinkText;

@Component
public class CommonSteps extends Steps {
	private static Logger log = LoggerFactory.getLogger(CommonSteps.class);

	@Then("^Error message is displayed: \"([^\"]*)\"$")
	public void error_message_is_displayed(String errMsg) {
		CommonUtilPage.verifyErrorMessage(executionContext.getDriver(), 1, errMsg);
	}

	@Then("^Error message is displayed:$")
	public void error_message_is_displayed_with_newline(String errMsg) {
		CommonUtilPage.verifyErrorMessage(executionContext.getDriver(), 1, errMsg);
	}

	@Then("^Error message with quotes is displayed: (.*)$")
	public void error_message_in_quotes_displayed(String errMsg) {
		// Remove the first and last / from the error message. DO NOT use
		// replaceAll
		errMsg = errMsg.replaceFirst("/", "");
		errMsg = errMsg.substring(0, errMsg.length() - 1);
		CommonUtilPage.verifyErrorMessage(executionContext.getDriver(), 1, errMsg);
	}

	@Then("^Error page is displayed and errors corrected$")
	public void error_page_is_displayed() {
		Octo_SeleniumLibrary.clickElement(executionContext.getDriver(), By.name("crossValidationContinue"));
	}

	@Then("^Application message with quotes is displayed: (.*)$")
	public void application_message_in_quotes_displayed(String appMsg) {
		// appMsg = appMsg.replaceFirst("/", "");
		appMsg = appMsg.substring(0, appMsg.length() - 1);
		CommonUtilPage.verifyAppMessage(executionContext.getDriver(), 0, appMsg);
	}

	@Then("^the submission page throws the following error \"([^\"]*)\"$")
	public void the_submission_page_throws_error(String message) {
		CommonUtilPage.verifySubmissionPageError(executionContext.getDriver(), message);
	}

	@Then("^Warning with quotes is displayed: (.*)$")
	public void warning_in_quotes_displayed(String warningMsg) {
		// Remove the first and last / from the error message. DO NOT use
		// replaceAll
		warningMsg = warningMsg.replaceFirst("/", "");
		warningMsg = warningMsg.substring(0, warningMsg.length() - 1);
		CommonUtilPage.verifyWarningMessage(executionContext.getDriver(), 0, warningMsg);
	}

	@Then("^\"([^\"]*)\" details are required and corresponding menu is displayed$")
	public void menu_is_displayed_on_navigation_panel(String menuName) {
		Assert.assertTrue(menuName + " menu is not displayed",
				LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), menuName));
	}

	@Then("^\"([^\"]*)\" details are NOT required and corresponding menu is NOT displayed$")
	public void menu_is_not_displayed_on_navigation_panel(String menuName) {
		Assert.assertFalse(menuName + " menu is displayed",
				LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), menuName));
	}

	@Then("^\"([^\"]*)\" menu is complete$")
	public void menu_is_complete(String menuName) {
		Assert.assertTrue(menuName + " is not complete",
				LeftNavigationMenu.isStepComplete(executionContext.getDriver(), menuName));
	}

	@Then("^\"([^\"]*)\" menu is incomplete$")
	public void menu_is_incomplete(String menuName) {
		Assert.assertFalse(menuName + " is complete",
				LeftNavigationMenu.isStepComplete(executionContext.getDriver(), menuName));
	}

	@Then("^mod is created successfully and \"([^\"]*)\" menu is displayed$")
	public void mod_is_created_successfully_and_menu_is_displayed(String menuItem) {
		menu_is_displayed_on_navigation_panel(menuItem);
	}

	@When("^Validate \"([^\"]*)\" page has errors$")
	public void validateError(String arg1) {
		String objName = "";
		switch (arg1) {
		case "Corporate Experience":
			objName = "corporateExperience";
			break;
		case "Select Components":
			objName = "productCatalogUpdateMod";
			break;
		case "Capture Product Data":
			objName = "downloadUploadTemplate";
			break;
		default:
			break;
		}
		JsonArray array = executionContext.getCurrentScenarioObj().getAsJsonObject(objName)
				.getAsJsonArray("errorMessages");
		CommonUtilPage.verifyErrorMessage(executionContext.getDriver(), array);
	}

	@Before
	public void setup(Scenario scenario) throws Exception {
		log.info("Starting to set up scenraio");
		executionContext.setUpScenario(scenario);
		executionContext.getCurrentScenarioObj().addProperty("scenarioName", scenario.getName());
		if (!LoadProperties.executeBeforeSteps()) {
			log.info("Stopped execution of scenario cleanup steps");
			return;
		}
		log.info("Executing setup() clean up steps for scenario: " + scenario.getName());
//		System.out.println("**********is this eOffer: " + CommonUtilPage.isOffer(executionContext));
		if (CommonUtilPage.isOffer(executionContext))
			offerSetup(scenario);
		else
			modSetup(scenario);
		log.info("Completing setup task...");
	}

	private void offerSetup(Scenario scenario) throws Exception {
		String scenarioName = scenario.getName();
		log.info("Call dunsPage in offer set up");
		DunsPage dunsPage = new DunsPage(executionContext);
		dunsPage.get();
		// dunsPage.enterDunsNumber(executionContext.getCurrentScenarioObj().get("DUNS").getAsString());
		dunsPage.enterUEINumber(executionContext.getCurrentScenarioObj().get("UEI").getAsString());
		dunsPage.submitForm();
		log.info("Cleaning data for '" + scenarioName + "'");
		deleteOfferFromOfferListPage();
		if (scenarioName.contains("SubK") || scenarioName.startsWith("Clause exceptions")) {
			log.info("Setting data for '" + scenarioName + "' scenario");
			// There is no way to reset the subk page hence offer deletion
			new Offer.Builder(executionContext).standardResponses().build();

		}
	}

	@After
	public void tearDownScenario(Scenario scenario) {

		log.info("Flush report after each scenarios!");
		if (true) { //( scenario.isFailed()) {
		takeSnapShot(executionContext.getDriver(), "target/extent-reports/screenshot/" + scenario.getName());
		}

	}

	/**
	 * This function will take screenshot
	 * 
	 * @param webdriver
	 * @param fileWithPath
	 */
	public static void takeSnapShot(WebDriver webdriver, String fileWithPath) {
		try {
			// Convert web driver object to TakeScreenshot
			TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

			// Call getScreenshotAs method to create image file
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

			// Move image file to new destination
			File destinationPath = new File(System.getProperty("user.dir") + "/" + fileWithPath + ".png");

			// Copy file at destination
			FileUtils.copyFile(SrcFile, destinationPath);

			Reporter.addScreenCaptureFromPath(destinationPath.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


//	@After
//	public static void sendEmailNotificationAfterTests() {
//
//		Result result = JUnitCore.runClasses(MergedDataInjectedCucumber.class);
//
//		if (result.getFailureCount() > 0) {
//			StringBuilder testResultBuilder = new StringBuilder();
//			for (Failure failure : result.getFailures()) {
//				testResultBuilder.append(failure.toString());
//				testResultBuilder.append("\n");
//			}
//			String testResults = testResultBuilder.toString();
//			String emailcontent = String.format("Your tests executed with failures. Test results:\n%s\n\nHTML Report:\n%s", testResults, getHTMLReport());
//			JUnitEmailNotification.sendEmailNotification(emailcontent);
//		} else {
//			String testResults = "All tests passed successfully";
//
//			String emailcontent = String.format("Your tests executed with failures. Test results:\n%s\n\nHTML Report:\n%s", testResults, getHTMLReport());
//			JUnitEmailNotification.sendEmailNotification(emailcontent);
//		}
//
//	}

	private static String getHTMLReport() {
		try {
			// Read the contents of the HTML report file
			String htmlReportPath = "build/eoffer-gsaTest-report/index.html";
			byte[] reportBytes = Files.readAllBytes(Paths.get(htmlReportPath));
			String htmlReportContent = new String(reportBytes);

			return htmlReportContent;
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to load HTML report.";
		}
	}

	private void modSetup(Scenario scenario) {
		String scenarioName = scenario.getName();
		if (scenarioName.contains("GSA initiated ")) {
			try {
				SOAPClient.createGSAMod(executionContext.getCurrentScenarioObj());
			} catch (Exception e) {
				log.error("Issues creating GSA initiated mod: " + e.getMessage());
			}
		} else if (scenarioName.contains("Mod")) {
			// clearBrowserCookies(executionContext.getDriver());
			// Always start a scenario by logging again with scenario specified
			// DUNS and contract.
			log.info("Mod setup: Logging with correct DUNS and contract");
			DunsPage dunsPage = new DunsPage(executionContext);
			dunsPage.get();
			// dunsPage.enterDunsNumber(executionContext.getCurrentScenarioObj().get("DUNS").getAsString());
			dunsPage.enterUEINumber(executionContext.getCurrentScenarioObj().get("UEI").getAsString());
			dunsPage.submitForm();

			ModListPage page = new ModListPage(executionContext);
			page.get();

			String contractNumber = GetUEIandContract.getContractNumberByFilter(
					executionContext.getCurrentScenarioObj().get("contractNumber").getAsString());

			int modCount = executionContext.getCurrentScenarioObj().get("modTypes").getAsJsonArray().size();
			if (modCount == 1) {
				String modType = executionContext.getCurrentScenarioObj().get("modTypes").getAsJsonArray().get(0)
						.getAsString();
				page.deleteMod(contractNumber, modType);
				log.info("Deleted mod for '" + contractNumber + "' and mod type " + modType);
			} else {
				page.deleteMod(contractNumber, "Selected Mod Actions");
			}
		}
	}

	@Deprecated
	public void deleteFPTFromCPT() throws MalformedURLException, FileNotFoundException, IOException {
		String solicitationNumber = executionContext.getCurrentScenarioObj().get("solicitationNumber").getAsString();
		String solicitationRefreshNumber = executionContext.getCurrentScenarioObj().get("solicitationRefreshNumber")
				.getAsString();
		String duns = DataUtil.getUEI(executionContext);

		log.info("Deleting FPT Offer '" + solicitationNumber + "' for DUNS " + duns + " and schedule '"
				+ executionContext.getCurrentScenarioObj().get("scheduleNumber").getAsString() + "'");
		SOAPClient.deleteOffer(solicitationNumber, solicitationRefreshNumber, duns);
	}

	@Deprecated
	public void createEditOffer() {
		OffersListPage offerListPage = new OffersListPage(executionContext);
		String scheduleNumber = executionContext.getCurrentScenarioObj().get("scheduleNumber").getAsString();
		if (ActionByPartialLinkText.isDisplayed(executionContext.getDriver(), "My eOffers", 3)) {
			ActionByPartialLinkText.click(executionContext.getDriver(), "My eOffers", 10);
			offerListPage.editOffer(scheduleNumber);
		} else {
			offerListPage.get();
			offerListPage.editOffer(scheduleNumber);
		}
		String dunsNumber = DataUtil.getUEI(executionContext);
		CorporateInformationPage corpPage = new CorporateInformationPage(executionContext);
		if (!corpPage.isUEIMatch(dunsNumber)) {
			DunsPage dunsPage = new DunsPage(executionContext);
			dunsPage.get();
			dunsPage.enterDunsNumber(dunsNumber);
			dunsPage.submitForm();
		}
	}

	private void deleteOfferFromOfferListPage() {

		if (executionContext.getCurrentScenarioObj().has("scheduleNumber")) {

			OffersListPage offersListPage = new OffersListPage(executionContext);
			offersListPage.get();
			String scheduleNumber = executionContext.getCurrentScenarioObj().get("scheduleNumber").getAsString();

			System.out.println("************************Delete Offer in If****************"
					+ offersListPage.isSolicitationInTable("savedeofferstable", scheduleNumber));
			if (offersListPage.isSolicitationInTable("savedeofferstable", scheduleNumber)) {
				log.info("Deleting offer for schedule number " + scheduleNumber);
				offersListPage.deleteSavedOfferBySolicitation(scheduleNumber);
			}
		}
	}

	public void clearBrowserCookies(WebDriver driver) {
		log.info("Delete all cookies");
		driver.manage().deleteAllCookies();
	}
}
