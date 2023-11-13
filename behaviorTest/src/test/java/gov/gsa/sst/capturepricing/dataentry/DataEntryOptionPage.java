package gov.gsa.sst.capturepricing.dataentry;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.capturepricing.CaptureDataPage;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.DataUtil;
import util.ActionByCss;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.PageUtil;

public class DataEntryOptionPage extends Page {
	private final int TIMEOUT_IN_SECS = 5;
	private final int SAVE_TIMEOUT_IN_SECS = 20;
	private By headerText = By.xpath("//h1[contains(text(), 'ON-SCREEN PRICING DATA')]");
	private ExecutionContext executionContext;
	private static Logger log = LoggerFactory.getLogger(DataEntryOptionPage.class);

	public DataEntryOptionPage(WebDriver driver) {
		super(driver);
	}

	public DataEntryOptionPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		log.info("Loading On screen data entry page");
		try {
			if (CommonUtilPage.isOffer(executionContext)) {
				boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
				if (!flag)
					throw new Exception("The solicitation number specified in data does not match the one on UI");
			}
			CaptureDataPage cpPage = new CaptureDataPage(executionContext);
			cpPage.get();
			cpPage.selectFormEntryOption();
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			CaptureDataPage cpPage = new CaptureDataPage(executionContext);
			cpPage.get();
			cpPage.selectFormEntryOption();
		}
		try {
			executionContext.testPageFor508("Capture Pricing Form entry");
		} catch (Exception e) {
			log.warn("Section 508 exception: " + e.getMessage());
		}
		Assert.assertTrue("Capture Pricing On-screen data entry page is not loaded", isLoaded());

	}

	@Override
	protected boolean isLoaded() {
		if (driver.getTitle().toUpperCase().contains("PRICE"))
			return true;
		else
			return ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}

	public void selectSINFromList(String sin) {
		if (!ActionById.isDisplayed(driver, "retrieveLineItem", TIMEOUT_IN_SECS))
			PageUtil.checkPageLoad(driver);
		ActionByName.selectByText(driver, "selectedSIN", sin, TIMEOUT_IN_SECS);
		log.info("Selecting SIN: " + sin);
		Octo_SeleniumLibrary.clickElement(driver, By.id("retrieveLineItem"));
	}

	/**
	 * Select the tab you want to add/update/delete data from
	 * 
	 * @param tabName
	 */
	public void selectTab(String tabName) {
		FormEntryUtil.navigateToTab(driver, tabName);
		// TODO ensure that table is loaded will need thread.sleep()
	}

	/**
	 * Populate data for all tabs
	 * 
	 * @param capturePricingDataObj
	 * @param validData             true if being used in positive scenarios else
	 *                              false
	 */
	public void populateForm(JsonObject capturePricingDataObj) {
		populateForm(capturePricingDataObj, true);
	}

	/**
	 * Populate data for all tabs
	 * 
	 * @param capturePricingDataObj
	 * @param isValidData           true if being used in positive scenarios else
	 *                              false
	 */
	public void populateForm(JsonObject capturePricingDataObj, boolean isValidData) {
		// loop through each tab details
		Set<Entry<String, JsonElement>> qcSet = capturePricingDataObj.entrySet();
		Iterator<Entry<String, JsonElement>> iter = qcSet.iterator();
		boolean isSinSelected = false;

		while (iter.hasNext()) {
			Entry<String, JsonElement> element = iter.next();
			String elementName = element.getKey();

			JsonArray sheetDataArray = new JsonArray();
			switch (elementName) {
			default:
				// Populate data for each tab
				log.info("Adding data for tab: " + elementName);
				sheetDataArray = capturePricingDataObj.getAsJsonArray(elementName);
				JsonObject jsonObj = sheetDataArray.get(0).getAsJsonObject();
				if (!isSinSelected) {
					String sinNumber = jsonObj.get("*SIN").getAsString();
					selectSINFromList(sinNumber);
					isSinSelected = true;
				}
				selectTab(elementName);
				FormEntryUtil.enterFormData(driver, elementName, sheetDataArray, false);
				break;
			}
			saveData(elementName, isValidData);
			if (!isValidData)
				validateTabErrors(elementName, sheetDataArray);
		}
	}

	public void populatePricingModForm(JsonArray capturePricingDataObj) {

		Iterator<JsonElement> iter = capturePricingDataObj.iterator();
		while (iter.hasNext()) {
			JsonElement jsonElement = iter.next();
			JsonObject jsonObj = jsonElement.getAsJsonObject();

			ActionByName.selectByValue(driver, "selectedSIN", jsonObj.get("*SIN").getAsString(), TIMEOUT_IN_SECS);
			ActionByName.sendKeys(driver, "mfgNumber", jsonObj.get("*Manufacturer Part Number").getAsString(),
					TIMEOUT_IN_SECS);
			ActionByName.sendKeys(driver, "mfgName", jsonObj.get("*Manufacturer Name").getAsString(), TIMEOUT_IN_SECS);
			if (jsonObj.has("*Zone Number"))
				ActionByName.sendKeys(driver, "zoneNumber", jsonObj.get("*Zone Number").getAsString(), TIMEOUT_IN_SECS);

			// Search for Product
			Octo_SeleniumLibrary.clickElement(driver, By.name("search"));

			// Add more details
			ActionByName.sendKeys(driver, "startDate", DataUtil.getCurrentDate("Start"), TIMEOUT_IN_SECS);
			if (jsonObj.has("EndDate"))
				ActionByName.sendKeys(driver, "EndDate", DataUtil.getCurrentDate("End"), TIMEOUT_IN_SECS);
			ActionByName.sendKeys(driver, "escalationRate", jsonObj.get("Escalation Rate (%)").getAsString(),
					TIMEOUT_IN_SECS);
			ActionByName.sendKeys(driver, "escalationSource", jsonObj.get("Source of Escalation").getAsString(),
					TIMEOUT_IN_SECS);
			ActionByName.sendKeys(driver, "proposedPrice", jsonObj.get("*Proposed Price (Excl. IFF)").getAsString(),
					TIMEOUT_IN_SECS);

			// save Product
			Octo_SeleniumLibrary.clickElement(driver, By.name("submitTRP"));
		}
	}

	/**
	 * Perform submit action at the end of populating each sheet
	 * 
	 * @param tabName tab on which submit action is to be carried out
	 */
	public void saveData(String tabName, boolean isValidData) {

		String confirmText = "";
		switch (tabName) {
		case "Get Started":
			confirmText = performSave("getStarted-save", 1000);
			break;
		case "Delivery":
			confirmText = performSave("delivery-save", 15000);
			break;
		case "Pricing":
			confirmText = performSave("general-save", 10000);
			break;
		case "Dimensions & Shipping":
			confirmText = performSave("DimeShip-save", 1000);
			break;
		case "Special Charges":
			confirmText = performSave("saveSpecialCharges", 1000);
			break;
		case "Special Features":
			confirmText = performSave("spf-save", 12000);
			break;
		case "Additional Photos":
			confirmText = performSave("savePhotos", 10000);
			break;
		case "Zonal Offer Price":
			confirmText = performSave("zones-save", TIMEOUT_IN_SECS);
			break;
		case "Discounts":
			confirmText = performSave("discount-save", TIMEOUT_IN_SECS);
			break;
		case "Options":
			// No popup with failure info
			if (isValidData)
				confirmText = performSave("saveOptions", TIMEOUT_IN_SECS);
			else {
				Octo_SeleniumLibrary.clickElement(driver, By.id("saveOptions"));
				confirmText = ActionById.getText(driver, "errmsg-options", TIMEOUT_IN_SECS);
			}
			break;
		case "Accessories":
			// No data validation occurs here. It happens only after Validate
			// Product
			confirmText = performSave("saveAccessories", TIMEOUT_IN_SECS);
			break;
		default:
			break;
		}
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/* Check for confirmation text if saving valid data */
		if (isValidData) {
			if (tabName.equalsIgnoreCase("Special Charges")) {
				Assert.assertTrue("No success message on Form Entry for " + tabName,
						confirmText.contains("Successfully saved Special Charges for the Product"));
			} else if (tabName.equalsIgnoreCase("Additional Photos")) {
				Assert.assertTrue("No success message on Form Entry for " + tabName,
						confirmText.contains("Photo information successfully saved for SIN:"));
			} else if (tabName.equalsIgnoreCase("Accessories")) {
				Assert.assertTrue("No success message on Form Entry for " + tabName, confirmText
						.contains("Selected Accessories have been successfully associated with the Product."));
			} else if (tabName.equalsIgnoreCase("Options")) {
				Assert.assertTrue("No success message on Form Entry for " + tabName,
						confirmText.contains("Successfully saved Options for the Product."));
				ActionByCss.clickOption(driver, ".ui-button.ui-widget", "OK", SAVE_TIMEOUT_IN_SECS);
			} else {
				Assert.assertTrue("No success message on Form Entry for " + tabName,
						confirmText.contains("Product information successfully saved for SIN:"));
			}
		}
		if (!tabName.equalsIgnoreCase("Options"))
			ActionByCss.clickOption(driver, ".ui-button.ui-widget", "OK", SAVE_TIMEOUT_IN_SECS);
	}

	/**
	 * Click on save button using the tab specific button id
	 * 
	 * @param buttonId
	 * @param waitTime
	 * @return
	 */
	private String performSave(String buttonId, int waitTime) {
		Octo_SeleniumLibrary.clickElement(driver, By.id(buttonId));
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String confirmText = ActionById.getText(driver, "dialog", SAVE_TIMEOUT_IN_SECS);
		log.info("Confirmation text: " + confirmText);
		return confirmText;
	}

	/**
	 * 
	 * @param tabName
	 * @param tabDataArray
	 */
	public void validateTabErrors(String tabName, JsonArray tabDataArray) {
		// Check for errors on a tab
		log.info("Checking for errors on tab: " + tabName);
		FormEntryUtil.verifyErrorsOnTab(driver, tabName, tabDataArray);

	}

	/**
	 * Verify data on Form entry page
	 * 
	 * @param tableDataObject
	 */
	public void validateFormEntryData(JsonObject tableDataObject) {
		// loop through each tab details
		Set<Entry<String, JsonElement>> qcSet = tableDataObject.entrySet();
		Iterator<Entry<String, JsonElement>> iter = qcSet.iterator();
		boolean isSinSelected = false;

		while (iter.hasNext()) {
			Entry<String, JsonElement> element = iter.next();
			String elementName = element.getKey();

			JsonArray sheetDataArray = new JsonArray();

			// Populate data for each tab
			log.info("Adding data for tab: " + elementName);
			sheetDataArray = tableDataObject.getAsJsonArray(elementName);
			JsonObject jsonObj = sheetDataArray.get(0).getAsJsonObject();
			if (!isSinSelected) {
				String sinNumber = jsonObj.get("*SIN").getAsString();
				selectSINFromList(sinNumber);
				isSinSelected = true;
			}
			selectTab(elementName);
			FormEntryUtil.verifyFormEntryData(driver, elementName, sheetDataArray);
		}

	}

	public void backToCapturePricing() {
		Octo_SeleniumLibrary.clickElement(driver, By.id("goToCapturePricingBtnConfirm"));
		Octo_SeleniumLibrary.clickElement(driver, By.xpath(
				"//div[contains(.,'Product Confirmation') and contains(@style, 'display: block')]//button/*[text()='Ok']"));
	}
}
