package gov.gsa.sst.commonpage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.util.DataUtil;
import gov.gsa.sst.util.LoadProperties;
import gov.gsa.sst.util.data.Offer;
import util.ActionByClassName;
import util.ActionByLocator;
import util.ActionByWebElement;
import util.ActionByXpath;
import util.PageUtil;

public class CommonUtilPage {
	private static Logger log = LoggerFactory.getLogger(CommonUtilPage.class);
	static int TIMEOUT_IN_SECS = 3;

	/**
	 * Ensure that Solicitation on UI is the same as the one mentioned in the data
	 * file for current scenario
	 *
	 * @param executionContext
	 * @return
	 */
	public static boolean isSolicitationApt(ExecutionContext executionContext) {
		if (executionContext != null && executionContext.getCurrentScenarioObj() != null) {
			if (isUserCorrect(executionContext)) {
				String scheduleNumber = executionContext.getCurrentScenarioObj().get("scheduleNumber").getAsString();
				String actualSch = ActionByLocator.getText(executionContext.getDriver(),
						By.xpath("//tr/td[contains(.,'Schedule')]/following-sibling::td[1]"), 1);
				log.info("Expected sol number is: " + scheduleNumber + " and actual sol number is: " + actualSch);
				return actualSch.contains(scheduleNumber);
			} else {
				log.info("Username does not match the one on UI");
				return false;
			}
		} else
			throw new RuntimeException("Scenario is not defined in the test data file - check your data");
	}

	public static boolean isUserCorrect(ExecutionContext executionContext) {
		if (executionContext != null && executionContext.getCurrentScenarioObj() != null) {
			// If it is cloud env perform user check else skip it for GSA test
			// as it uses certificate
			if (LoadProperties.getProperty("environment").equalsIgnoreCase("cloud")) {
				String userName = executionContext.getCurrentScenarioObj().getAsJsonObject("SignIn").get("name")
						.getAsString();
				log.info("Expected Username is " + userName);
				if (ActionByLocator.isDisplayed(executionContext.getDriver(), By.xpath("//div[@id='control']"), 1)) {

					String actualUser = ActionByLocator.getText(executionContext.getDriver(),
							By.xpath("//div[@id='control']"), 1);
					log.info("Actual user: " + actualUser);
					return actualUser.contains(userName);
				} else
					return false;
			} else
				return true;
		} else
			throw new RuntimeException("Scenario is not defined in the test data file - check your data");
	}

	/**
	 * Check if Duns on UI is same as that in scenario data
	 *
	 * @param executionContext
	 * @return
	 */
//	public static boolean isDunsCorrect(ExecutionContext executionContext) {
//		if (executionContext != null && executionContext.getCurrentScenarioObj() != null) {
//			String duns = DataUtil.getUEI(executionContext);
//			log.info("Expected DUNS is " + duns);
//			String script = "";
//			try {
//				if (executionContext.getDriver().findElement(By.name("dunsNumHidden")) != null) {
//					script = "return document.getElementById('dunsNumHidden').value";
//				}
//			} catch (Exception e) {
//				log.error("Could not find 'dunsNumHidden' variable so look for 'existingDuns'");
//				script = "return document.getElementsByName('dunsNumber')[0].value";
//			}
//			String actualDuns = (String) ((JavascriptExecutor) executionContext.getDriver()).executeScript(script);
//			log.info("Actual DUNS: " + actualDuns);
//			return actualDuns.equalsIgnoreCase(duns);
//		} else
//			throw new RuntimeException("Scenario is not defined in the test data file - check your data");
//	}
	
	public static boolean isUEICorrect(ExecutionContext executionContext) {
		if(executionContext != null && executionContext.getCurrentScenarioObj() != null) {
			String uei = DataUtil.getUEI(executionContext);
			log.info("Expected UEI is " + uei);
			String script = "";
			try {
				System.out.println("******CommonUtilPage:98 executionContent value is: "+executionContext.toString());
				if (executionContext.getDriver().findElement(By.name("dunsNumHidden")) != null) {
					script = "return document.getElementById('dunsNumHidden').value";
				}
			} catch (Exception e) {
				log.error("Could not find 'dunsNumHidden' variable so look for 'existingDuns'");
				script = "return document.getElementsByName('dunsNumber')[0].value";
			}
			String actualUEI = (String) ((JavascriptExecutor) executionContext.getDriver()).executeScript(script);
			log.info("Actual UEI: " + actualUEI);
			return actualUEI.equalsIgnoreCase(uei);
		}else {
			throw new RuntimeException("Scenario is not defined in the test data file - check your data");
		}
	}

	public static void verifyWarningMessage(WebDriver driver, int index, String warning) {
		List<WebElement> list = null;
		try {
			list = ActionByClassName.getElements(driver, "alert-warning", 5);
		} catch (TimeoutException e) {
			throw new RuntimeException("Expected application to display warnings but none were found");
		}
		String actualText = ActionByWebElement.getElement(driver, list.get(index), 5).getText();
		Assert.assertTrue("Expected text: '" + warning + "' should match actual text: '" + actualText + "'",
				actualText.contains(warning));
	}

	public static void verifyErrorMessage(WebDriver driver, int index, String errorMessage) {
		List<WebElement> list = null;
		String actualText = null;
		try {
			list = ActionByClassName.getElements(driver, "appWarning", 5);
			actualText = ActionByWebElement.getElement(driver, list.get(index), 5).getText();
		} catch (TimeoutException e) {
			try {
				actualText = SeleniumHelper.getTextByLocator(driver, By.xpath("//*[@class='actionMessage']//span"));
			} catch (TimeoutException a) {
				throw new RuntimeException("Expected application to display warnings but none were found");
			}
		}

		Assert.assertTrue("Expected text: '" + errorMessage + "' should match actual text: '" + actualText + "'",
				actualText.contains(errorMessage));
	}

	public static void verifyAppMessage(WebDriver driver, int index, String appMessage) {
		List<WebElement> list = null;
		try {
			// in test environment the class element is"appMessage" Dev env
			// is"actionMessage"
			list = ActionByClassName.getElements(driver, "actionMessage", 5);
		} catch (TimeoutException e) {
			throw new RuntimeException("Expected application to display warnings but none were found");
		}
		String actualText = ActionByWebElement.getElement(driver, list.get(index), 5).getText();
		Assert.assertTrue("Expected text: '" + appMessage + "' should match actual text: '" + actualText + "'",
				actualText.contains(appMessage));
	}

	public static void verifyErrorMessage(WebDriver driver, JsonArray array) {

		List<WebElement> list = ActionByClassName.getElements(driver, "appWarning", 5);
		for (int i = 0; i < array.size(); i++) {
			String actualText = ActionByWebElement.getElement(driver, list.get(i + 1), 5).getText();
			String expectedText = array.get(i).getAsString();
			Assert.assertTrue("Expected text: " + expectedText + " should match actual text: " + actualText,
					actualText.contains(expectedText));
		}
	}

	public static void verifyErrorMessagesInExcel(WebDriver driver, JsonArray array, List<String> listMsgs) {

		for (int i = 0; i < array.size(); i++) {
			boolean isMsgFound = false;
			for (int j = 0; j < listMsgs.size(); j++) {
				String actualText = listMsgs.get(i).trim();
				String expectedText = array.get(j).getAsString();
				if (actualText.equalsIgnoreCase(expectedText)) {
					isMsgFound = true;
					break;
				}
				log.info("Expected text: '" + expectedText + "' should match actual text: '" + actualText + "' " + j);
			}
			Assert.assertTrue("Could not validate error: ", isMsgFound);
		}
	}

	/**
	 * This method should be used in cases where error message is not defined in
	 * element with class=appWarning ex. Duns page
	 *
	 * @param index
	 * @param errorMessage
	 */
	public static void verifyErrorOnPage(WebDriver driver, String errorMessage) {
		//WebElement element = driver.findElement(By.xpath("//div/p[contains(text()" + errorMessage + ")]"));
		WebElement element = driver.findElement(By.xpath("//ul[@class='appWarning']/li/span"));
		Assert.assertNotNull("Expected text: " + errorMessage + "not found on page ", element);
	}

	/**
	 * Check if scenario is for Mod or Offer
	 *
	 * @param executionContext
	 * @return
	 */
	public static boolean isOffer(ExecutionContext executionContext) {
		return executionContext.getCurrentScenarioObj().getAsJsonObject("SignIn").get("module").getAsString()
				.equalsIgnoreCase("eOffer");
	}

	public static void selectRadioOption(WebDriver driver, String name, String value) {
		if (value.isEmpty()) {
			// Reset radio button works for duke 3.4.0.0
			PageUtil.clearRadioElement(driver, "name", name);
			// else
			// TableElementUtils.clearRadioElement(driver, "name", name);
		} else {
			String xpath = "//*[@name='" + name + "' and @value='" + value + "']";
//			Octo_SeleniumLibrary.clickElement(driver, By.xpath(xpath), 5);
			Octo_SeleniumLibrary.clickElement(driver, By.xpath(xpath));
		}
	}
	
	public static void selectRadioOptionById(WebDriver driver, String id, String value) {
		if (value.isEmpty()) {
			// Reset radio button works for duke 3.4.0.0
			PageUtil.clearRadioElement(driver, "id", id);
			// else
			// TableElementUtils.clearRadioElement(driver, "name", name);
		} else {
			String xpath = "//*[@id='" + id + "' and @value='" + value + "']";
//			SeleniumHelper.clickByLocator_tempFix(driver, By.xpath(xpath), 5);
			SeleniumHelper.clickByLocator_tempFix(driver, By.xpath(xpath), 5);
		}
	}

	/**
	 * This method verifies the application message displayed when the data entered
	 * is successful or similar
	 *
	 * @param appMessage: Saved successfully
	 */
	public static void verifyAppMessage(String appMessage, WebDriver driver) {
		Assert.assertTrue(getAppMessage(driver).contains(appMessage));
	}

	/**
	 * Retrieve messages from UI
	 *
	 * @param driver
	 * @return
	 */
	public static String getAppMessage(WebDriver driver) {
		// locator in the test environment://ul[@class='appMessage']/li
		By locator = By.xpath("//ul[@class='actionMessage']/li");
		return ActionByLocator.getText(driver, locator, 5);
	}

	/**
	 * Gets a message from any page that matches the xpath expression.
	 *
	 * @param driver
	 * @param timeOutInSeconds
	 * @return the message
	 */
	public static String getMessage(WebDriver driver, int timeOutInSeconds) {
		return ActionByXpath.getText(driver, "//h1/following-sibling::p[text()='Thank you for using our application.  Your request for submission has been received. You will be notified by email when review and/or eSignature is required. ']", timeOutInSeconds);
	}

	/**
	 * Verifies the page header text and if the module is eoffer, checks
	 * solicitation number
	 *
	 * @param executionContext
	 * @param headerText
	 * @param timeOutInSeconds
	 * @return
	 */
	public static boolean verifyPageHeader(ExecutionContext executionContext, By headerText, int timeOutInSeconds) {
		if (ActionByLocator.isDisplayed(executionContext.getDriver(), headerText, timeOutInSeconds)) {
			if (isOffer(executionContext))
				return isSolicitationApt(executionContext);
			else
				return true;
		} else
			return false;
	}

	/**
	 * Completes below pages based on the data provided in the scenario
	 *
	 * @param executionContext
	 * @throws Exception
	 */
	public static void completeMod(ExecutionContext executionContext) throws Exception {
		log.info("Completing all pages including Wiz Management for most of the mods");
		new Offer.Builder(executionContext).wizardManagement().completeWizardSubPages().downloadUploadPricingTemplate()
				.ediOption().dataEntryOption().uploadPhotos().productAnalysisReport().finalPricingDocument()
				.solProvisionCorporateExperience().solProvisionPastPerformance().solProvisionQualityControl()
				.solProvisionRelevantExperience().solProvisionSCAMatrix().uploadDocuments().build();
	}

	/**
	 * Completes only Capture pricing, upload photos, PAR & final pricing pages
	 *
	 * @param executionContext
	 * @throws Exception
	 */
	public static void completePricingPagesForMod(ExecutionContext executionContext) throws Exception {
		log.info("Completing pages like Capture Pricing, PAR etc for Pricing mods");
		new Offer.Builder(executionContext).downloadUploadPricingTemplate().ediOption().dataEntryOption().uploadPhotos()
				.productAnalysisReport().finalPricingDocument().uploadDocuments().build();
	}

	public static void completeModPages(ExecutionContext executionContext) throws Exception {
		JsonArray modTypes = executionContext.getCurrentScenarioObj().getAsJsonArray("modTypes");
		switch (modTypes.get(0).getAsString()) {
		// Skip Wizard management
		case "Add Product(s)":
		case "Delete Product(s)":
		case "Economic Price Adjustments (EPA) with Commercial Price List (increase)":
			completePricingPagesForMod(executionContext);
			break;
		case "Delete SIN":
		case "Manage Discounts":
		default:
			completeMod(executionContext);
			break;
		}
	}

	public static List<String> retrieveModTypes(ExecutionContext executionContext) {
		JsonArray modTypes = executionContext.getCurrentScenarioObj().getAsJsonArray("modTypes");
		List<String> modList = new ArrayList<>();
		modTypes.forEach(item -> {
			modList.add(item.getAsString());
		});
		return modList;
	}

	/**
	 * Calculates the start date as next day (current day + 1)
	 *
	 * @return
	 */
	public static String getStartDate() {
		return LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
	}

	/**
	 * Calculates the end date as one year from tomorrow
	 *
	 * @return
	 */
	public static String getEndDate() {
		return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
	}

	/**
	 * Verify contents of a PDF document. The 'uploadedDocuments' objects should
	 * have an object 'pagesToInspect' with the following format:
	 *
	 * "pagesToInspect": [ { "pageNumber": 1, "expectedLines": [ "Authorized
	 * Negotiator", "Agent Authorization Letter agent_auth_letter.txt" ] } ]
	 *
	 * Array should have one object per page to be verified along with the array of
	 * lines to be checked on the page.
	 *
	 * @param uploadedDocuments
	 * @return map with verification details
	 * @throws InvalidPasswordException
	 * @throws IOException
	 */
	public static Map<String, Boolean> verifyPDFDocuments(JsonArray uploadedDocuments)
			throws InvalidPasswordException, IOException {
		Map<String, Boolean> verifiedDocs = new HashMap<>();
		for (JsonElement documentToVerify : uploadedDocuments) {
			JsonObject document = documentToVerify.getAsJsonObject();

			String fileName = DataUtil.getFilename(document.get("filename").getAsString());
			if (fileName.isEmpty() || !fileName.toLowerCase().contains("pdf")) {
				log.warn("File '" + fileName + "' is empty or extension is not PDF - skipping further validation");
				continue;
			}

			if (!document.has("pagesToInspect")) {
				log.warn(fileName + " does not have 'pagesToInspect' - skipping further validation");
				continue;
			}

			Iterator<JsonElement> iterator = document.get("pagesToInspect").getAsJsonArray().iterator();
			while (iterator.hasNext()) {
				log.info("Verify PDF contents for file " + fileName);
				JsonObject pageToVerify = iterator.next().getAsJsonObject();

				int pageNumber = pageToVerify.get("pageNumber").getAsInt();
				JsonArray expectedLines = pageToVerify.get("expectedLines").getAsJsonArray();
				verifiedDocs.putAll(DataUtil.doesPDFPageContainsLines(fileName, pageNumber, expectedLines));
			}
		}
		return verifiedDocs;
	}

	/**
	 * Verify elements in page objects
	 *
	 * @param driver
	 * @param pageObjectName
	 */
	public static void verifyPageObjectElements(WebDriver driver, String pageObjectName) {
		Map<String, List<String>> elements = DataUtil.getPageObjectElements(pageObjectName);
		elements.forEach((tag, textList) -> {
			textList.forEach(text -> {
				Assert.assertTrue("'" + text + "' not found",
						ActionByLocator.isDisplayed(driver, By.xpath("//" + tag + "[contains(.,'" + text + "')]"), 5));
			});
		});
	}

	public static void verifySubmissionPageError(WebDriver driver, String message) {
		String error = driver.findElement(By.xpath("//div[@id='instructions']")).getText().toString();
		log.info("expected: " + message);
		log.info("actual text: " + error);
		Assert.assertTrue(error.equalsIgnoreCase(message));
	}

	/**
	 * It will return true if we land on eMod Page. It will return false if we land
	 * on eOffer Page.
	 */
	public static boolean identifyEmod(JsonObject jsonObject) {
		String module = jsonObject.getAsJsonObject("SignIn").get("module").getAsString();
		boolean result = true;

		if (module.contains("eOffer")) {
			result = false;
		}

		return result;
	}

}
