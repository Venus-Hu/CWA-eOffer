package gov.gsa.sst.additions;

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
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.exceptions.TestExecutionException;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.TableElementUtils;

public class AddSinModPage extends AdditionsPage {

	private final int TIMEOUT_IN_SECS = 3;

	private String addSINDetailsBtn = "saveSin";

	private String saveAndContinueBtn = "saveAndContinue";
	private static Logger log = LoggerFactory.getLogger(AddSinModPage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'Select SINs')]");
	private ExecutionContext executionContext;

	public AddSinModPage(ExecutionContext executionContext) {
		super(executionContext);
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Add Sin page");
			LeftNavigationMenu.navigateTo(driver, "Additions");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Additions");
		}
		try {
			executionContext.testPageFor508("Add SIN mod");
		} catch (Exception e) {
			log.warn("Section 508 exception: " + e.getMessage());
		}
		Assert.assertTrue("Could not load Addition page for Add SIN mod", isLoaded());
	}

	// public void selectSIN(String sin) {
	// ActionByName.selectByText(driver, "sintype", sin, TIMEOUT_IN_SECS);
	// }

	public void clickAddSINDetailsBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.name(addSINDetailsBtn));
	}

	public void clickSaveandContinueBtn() {
		if (ActionByName.isDisplayed(driver, saveAndContinueBtn, TIMEOUT_IN_SECS)) {
			Octo_SeleniumLibrary.clickElement(driver, By.name(saveAndContinueBtn));
		} else
			log.info("Save and continue button not found");

	}

	public void populateForm(JsonObject sinDetailsObj) {
		log.info("Populating SIN details for Add SIN mod");
		selectAndAddSins(sinDetailsObj);
		reviseCspForAddSinMod();
		if (sinDetailsObj.has("offerCategory"))
			selectOfferCategory(sinDetailsObj);
		if (sinDetailsObj.has("fastlane"))
			updateFastlane(sinDetailsObj);
		clickSaveandContinueBtn();
	}

	public void selectOfferCategory(JsonObject sinDetailsObj) {
		String offerCategory;
		if (!sinDetailsObj.get("offerCategory").getAsString().isEmpty()) {
			offerCategory = sinDetailsObj.get("offerCategory").getAsString();
			CommonUtilPage.selectRadioOption(driver, "offerCategory", offerCategory);
		}
	}

	public void selectAndAddSins(JsonObject sinDetailsObj) {
		JsonArray array = sinDetailsObj.getAsJsonArray("sinDetails");
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			populateSinDetails(sinObj);
		}
	}

	// MCC PhaseII changes for Add Sin Mod:
	public void addSinCategory(JsonObject sinCategoryObj) {
		for (Map.Entry<String, JsonElement> element : sinCategoryObj.entrySet()) {
			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			log.info("**checking the sin enter issue - addSinModPage: " + elementName + "  " + elementValue);
			System.out.println("**checking the sin enter issue - addSinModPage: " + elementName + "  " + elementValue);
			switch (elementName) {
			case "sin":
				ActionByName.selectByText(driver, "sintype", elementValue, TIMEOUT_IN_SECS);

				break;
			case "category":
				ActionByName.selectByText(driver, "largeCategory", elementValue, TIMEOUT_IN_SECS);
				break;
			case "subCategory":
				ActionByName.selectByText(driver, "subCategory", elementValue, TIMEOUT_IN_SECS);
				break;
			default:
				// Do nothing
				break;

			}
		}
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@name,'sinType') and @type='Submit']"));
		if (sinCategoryObj.has("isTDR")) {
			String isTdr = sinCategoryObj.get("isTDR").getAsString();
			if (!isTdr.isEmpty())
				CommonUtilPage.selectRadioOption(driver, "tdrOptInResponse", isTdr.substring(0, 1));
			Octo_SeleniumLibrary.clickElement(driver,
					By.xpath("//*[@id='id_tdrResponseSave' and @value='Submit Response']"));
		}

	}

	/**
	 * Add one SIN at a time
	 *
	 * @param sinObj
	 */
	public void populateSinDetails(JsonObject sinObj) {
		log.info("Selecting scope for Sin");
		for (Map.Entry<String, JsonElement> element : sinObj.entrySet()) {
			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			switch (elementName) {
			case "sin":
				// selectSIN(elementValue);
				addSinCategory(sinObj);
				// clickAddSINDetailsBtn();
//				if (sinObj.has("isTDR")) {
//					String isTdr = sinObj.get("isTDR").getAsString().substring(0, 1);
//					CommonUtilPage.selectRadioOption(driver, "tdrOptInResponse", isTdr);
//					Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//*[@id='id_tdrResponseSave' and @value='Submit Response']",
//							TIMEOUT_IN_SECS);
//				}
				break;
			case "scope":
				ActionByName.selectByText(driver, "Scope", elementValue, TIMEOUT_IN_SECS);
				break;
			case "stateOrLocal":
				CommonUtilPage.selectRadioOption(driver, "SinStateAndLocal", elementValue);
				break;
			case "category":
			case "subCategory":
				// Do nothing
				break;
			default:
				log.warn("Field not supported: " + elementName);
				break;
			}
		}
		Octo_SeleniumLibrary.clickElement(driver, By.name("saveSin"));
	}

	private WebElement getRowBySinName(String sinName) {
		WebElement offersTable = ActionById.getElement(driver, "yourSinList", TIMEOUT_IN_SECS);
		WebElement sinRow = TableElementUtils.getTableRowByCellValue(offersTable, "Name", sinName);
		return sinRow;
	}

	public void verifySinsAdded(JsonArray jsonArray) {
		Iterator<JsonElement> iterator = jsonArray.iterator();
		while (iterator.hasNext()) {
			JsonElement element = iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			String sinName = (sinObj.get("sin").getAsString().split(" ")[0]).trim();
			WebElement sinRow = getRowBySinName(sinName);
			if (sinRow == null)
				throw new TestExecutionException("Could not find row with SIN: " + sinName);
			boolean flag = sinRow.findElement(By.xpath("//td[contains(.,'" + sinName + "')]")).isDisplayed();
			Assert.assertTrue("Failed to validate SIN ", flag);
			log.info("Verified that the SIN: " + sinName + " is found in the table.");
		}
	}

	public void verifyOfferCategoryIsDisplayed() {
		WebElement offersTable = ActionById.getElement(driver, "yourSinList", TIMEOUT_IN_SECS);
		Assert.assertTrue("Offer category is not displayed for an FPT mod", offersTable.isDisplayed());
	}
}
