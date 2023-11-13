package gov.gsa.sst.goodsservices;

import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.GenericDialogPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.exceptions.TestExecutionException;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByPartialLinkText;
import util.TableElementUtils;

/**
 * @author sjayatirtha
 *
 */
public class GoodsAndServicesPage extends Page {
	private final int TIMEOUT_IN_SECS = 5;
	private static Logger log = LoggerFactory.getLogger(GoodsAndServicesPage.class);
	private By editPreponderanceOfWorkBtn = By.xpath("//input[@value='Edit Preponderance of Work']");
	private By headerText = By.xpath("//h1[contains(text(), 'AVAILABLE OFFERINGS')]");
	private ExecutionContext executionContext;
	private AddSinPage sinDetailsPage;

	public GoodsAndServicesPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
		this.sinDetailsPage = new AddSinPage(executionContext);
	}

	@Override
	protected boolean isLoaded() {
		if (ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS)) {
			return CommonUtilPage.isSolicitationApt(executionContext);
		} else
			return false;
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Goods and Services page");
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number specified in data does not match the one on UI");
			LeftNavigationMenu.navigateTo(driver, "Goods and Services");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Available Offerings");
		}
		try {
			executionContext.testPageFor508("Available Offerings");
		} catch (Exception e) {
			log.warn("Section 508 exception: " + e.getMessage());
		}
		Assert.assertTrue("Available Offerings page is not loaded", isLoaded());
	}

	public void selectSin(String sin) {
		ActionByName.selectByText(driver, "sintype", sin, TIMEOUT_IN_SECS);
	}

	public void selectSINGroupFromDropdown(String sinGroup) {
		ActionByName.selectByText(driver, "sinGroupSelected", sinGroup, TIMEOUT_IN_SECS);
	}

	public void selectNaicsCodeByText(String naicsCode) {
		ActionByName.selectByText(driver, "PreponderanceOfWork", naicsCode, TIMEOUT_IN_SECS);
	}

	public void clickAddSinDetailsButton() {
		// Need this locator as there is a select element with same name
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@name,'sinType') and @type='Submit']"));
	}

	public void clickSelectSINGroupButton() {
		// Need this locator as there is a hidden element with same name
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@name,'sinGroup') and @type='Submit']"));
	} // for schedule 70

	/**
	 * @param sinName
	 * @param actionName - Please pass these values - Edit / Delete / View
	 */
	public void clickActionForSin(String sinName, String actionName) {
		WebElement row = getRowBySinName(sinName);
		if (row != null)
			ActionByPartialLinkText.getElementInElement(driver, row, actionName, TIMEOUT_IN_SECS).click();
		else
			log.warn("SIN " + sinName + "does not exists");
	}

	public void addPreponderanceOfWorkBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.name("sinTypeAction"));
	}

	public void editPreponderanceOfWorkBtn() {
		Octo_SeleniumLibrary.clickElement(driver, editPreponderanceOfWorkBtn);
	}

	public void clickSaveAndContinueButton() {
		Octo_SeleniumLibrary.clickElement(driver, By.name("saveAndContinue"));
	}

	public void selectOfferCategory(String offerType) {
		// Commenting out this functionality as part of FPT roll back.
		// OM-3641, OM-4983
		// CommonUtilPage.selectRadioOption(driver, "offerCategory", offerType);
	}

	/**
	 * Retrieve the row element where the specified SIN exists
	 * 
	 * @param sinName
	 * @return
	 */
	public WebElement getRowBySinName(String sinName) {
		if (ActionByLocator.isDisplayed(driver, By.xpath("//div[@class='table2']"), 1)) {
			WebElement offersTable = ActionById.getElement(driver, "yourSINs", TIMEOUT_IN_SECS);
			WebElement sinRow = TableElementUtils.getTableRowByCellValue(offersTable, "Name", sinName);
			return sinRow;
		}
		return null;
	}

	/**
	 * Verify the SIN data under "Your SIN(s)"
	 * 
	 * @param jsonObj
	 */
	public void verifySinExists(JsonArray jsonArray) {
		Iterator<JsonElement> iterator = jsonArray.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			String sinName = (sinObj.get("sin").getAsString().split("-")[0]).trim();
			WebElement sinRow = getRowBySinName(sinName);
			if (sinRow == null)
				throw new TestExecutionException("Could not find row with SIN: " + sinName);
			boolean flag = sinRow.findElement(By.xpath("//td[contains(.,'" + sinName + "')]")).isDisplayed();
			Assert.assertTrue("Failed to validate SIN ", flag);
			if (sinObj.has("sinGroup")) {
				flag = false;
				flag = sinRow.findElement(By.xpath("//td[contains(.,'" + sinObj.get("sinGroup").getAsString() + "')]"))
						.isDisplayed();
				Assert.assertTrue("Failed to validate SIN group", flag);
			}
			log.info("Verified that the SIN: " + sinName + " is found in the table.");
		}
	}

	/**
	 * Verify that SIN is missing from "Your SIN(s)" table
	 * 
	 * @param jsonArray
	 */
	public void verifySinDoesNotExist(JsonArray jsonArray) {
		Iterator<JsonElement> iterator = jsonArray.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			String sinName = sinObj.get("sin").getAsString().split("-")[0];

			WebElement sinRow = getRowBySinName(sinName);
			if (sinRow != null)
				throw new TestExecutionException("Found row with SIN: " + sinName);
			log.info("Verified that the SIN: " + sinName + " is not found in the table.");
		}
	}

	/**
	 * This will populate the Goods and services page
	 * 
	 * @param jsonObj Pass "goodsAndServices" object
	 */
	public void populateForm(JsonObject jsonObj) {
		// Perform Add SIN operation
		JsonArray array = jsonObj.getAsJsonArray("sinDetails");
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			sinDetailsPage.populateForm(sinObj);
		}
		// Add NAICS code with category
		selectPreponderance(jsonObj.getAsJsonObject("preponderance"));

		// Offer category only for FPT
		/*
		 * String offerCategory = ""; if
		 * (!jsonObj.get("offerCategory").getAsString().isEmpty()) { offerCategory =
		 * jsonObj.get("offerCategory").getAsString();
		 * selectOfferCategory(offerCategory); }
		 */
		clickSaveAndContinueButton();
	}

	/**
	 * Get a handle on AddSinPage
	 * 
	 * @return
	 */
	public AddSinPage getSinPage() {
		if (sinDetailsPage != null)
			return sinDetailsPage;
		else {
			sinDetailsPage = new AddSinPage(executionContext);
			return sinDetailsPage;
		}
	}

	/**
	 * Edit SIN details
	 * 
	 * @param jsonObj
	 */
	public void editSin(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			String sinName = sinObj.get("sin").getAsString().split("-")[0].trim();
			WebElement sinRow = getRowBySinName(sinName);
			sinRow.findElement(By.xpath("//a[contains(@title,'Edit')]")).click();
			if (sinObj.get("sinGroup") != null)
				sinObj.remove("sinGroup");
			sinObj.remove("sin");
			getSinPage().populateForm(sinObj);
		}
	}

	/**
	 * Delete specified SINs
	 * 
	 * @param jsonObj
	 */
	public void deleteSin(JsonObject jsonObj) {
		JsonArray array = jsonObj.getAsJsonArray("sinDetails");
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			String sinName = sinObj.get("sin").getAsString().split("-")[0].trim();
			WebElement sinRow = getRowBySinName(sinName);
			sinRow.findElement(By.xpath(".//a[contains(@title,'Delete')]")).click();
			GenericDialogPage deletePage = new GenericDialogPage(driver);
			deletePage.clickConfirmYes();
		}
	}

	public void deleteAllSin() {
		WebElement offersTable = ActionById.getElement(driver, "yourSINs", TIMEOUT_IN_SECS);
		int rowCount = TableElementUtils.getTableRowCount(offersTable);

		for (int i = 0; i < rowCount; i++) {
			try {
				WebElement sinRow = TableElementUtils.getLinkElementFromTableRow(offersTable, "Delete");
				sinRow.findElement(By.xpath("//a[contains(@title,'Delete')]")).click();
				GenericDialogPage deletePage = new GenericDialogPage(driver);
				deletePage.clickConfirmYes();
				offersTable = ActionById.getElement(driver, "yourSINs", TIMEOUT_IN_SECS);
			} catch (Exception e) {
				log.info("No SINs present for deletion");
			}
		}
	}

	/**
	 * Add NAICS code with category and sub category for preponderance work
	 * 
	 * @param goodsSrvcObj Pass "goodsAndServices" json object
	 */
	public void selectPreponderance(JsonObject goodsSrvcObj) {
		for (Map.Entry<String, JsonElement> element : goodsSrvcObj.entrySet()) {
			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			switch (elementName) {
			case "category":
				ActionById.selectByText(driver, "selectedLargeCategoryId", elementValue, TIMEOUT_IN_SECS);
				break;
			case "subCategory":
				ActionById.selectByText(driver, "selectedSubcategoryId", elementValue, TIMEOUT_IN_SECS);
				break;
			case "sin":
				ActionById.selectByText(driver, "selectedSinId", elementValue, TIMEOUT_IN_SECS);
				break;
			case "preponderanceNaicsCode":
				selectNaicsCodeByText(goodsSrvcObj.get("preponderanceNaicsCode").getAsString());
				break;
			default:
				// Do nothing
				break;
			}
		}
		addPreponderanceOfWorkBtn();
	}
}
