package gov.gsa.sst.goodsservices;

import java.util.Map;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.SeleniumHelper;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;

/**
 * This page is a sub page of Goods and services. The elements on this page can
 * change depending on the schedule.
 *
 * @author sjayatirtha
 *
 */

public class AddSinPage extends Page {

	private static Logger log = LoggerFactory.getLogger(AddSinPage.class);
	private By errorText = By.xpath("//*[@id='content']/table/tbody/tr[1]/td/div");
	private final int TIMEOUT_IN_SECS = 10;
	ExecutionContext executionContext;

	public AddSinPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return driver.getTitle().equalsIgnoreCase("SIN Information");
	}

	@Override
	protected void load() {
		log.info("Load the Add Sin Page for Goods and Services");
		try {
			executionContext.testPageFor508("Add SIN details page");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
	}

	/** NAICS code **/
	/*
	 * public void selectNaicsCodeByText(String naicsCode) {
	 * ActionByName.selectByText(driver, "NaicsCode", naicsCode, TIMEOUT_IN_SECS); }
	 *
	 * public void getSelectedNaics() { ActionByName.getText(driver, "NaicsCode",
	 * TIMEOUT_IN_SECS); }
	 *
	 * public void verifyNaics(String naicsCode) {
	 * ActionByName.assertEqualsText(driver, "NaicsCode", naicsCode,
	 * TIMEOUT_IN_SECS); }
	 */

	/** Scope **/
	public void selectScopeByText(String scope) {
		ActionByName.selectByText(driver, "Scope", scope, TIMEOUT_IN_SECS);
	}

	public void getSelectedScope() {
		ActionByName.getText(driver, "Scope", TIMEOUT_IN_SECS);
	}

	public void verifyScope(String scope) {
		ActionByName.assertEqualsText(driver, "Scope", scope, TIMEOUT_IN_SECS);
	}

	/** Estimated Sales **/
	public void setEstimatedSales(String estSales) {
		//ActionById.clear(driver, "EstimatedSales", TIMEOUT_IN_SECS);
		SeleniumHelper.clear_tempFix(driver, By.id("EstimatedSales"));
//		ActionById.sendKeys(driver,  "EstimatedSales", estSales, TIMEOUT_IN_SECS);
		SeleniumHelper.sendKey_tempFix(driver, By.id("EstimatedSales"), estSales);
//		try {
//		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//label[@for='EstimatedSales']", TIMEOUT_IN_SECS);
//		}catch(Exception e) {
//			System.out.println("**********skip to click lable");
//		}
	}

	public String getEstimatedSales() {
		return ActionById.getText(driver, "EstimatedSales", TIMEOUT_IN_SECS);
	}

	public void verifyEstimatedSales(String estSales) {
		ActionById.assertEqualsText(driver, "EstimatedSales", estSales, TIMEOUT_IN_SECS);
	}

	/** Maximum Order limit **/
	public void setMaxOrderLimit(String order) {
		ActionByName.sendKeys(driver, "MaxOrderLimit", order, TIMEOUT_IN_SECS);
	}

	public void verifyMaxOrderLimit(String maximumOrderLimit) {
		ActionByName.assertEqualsText(driver, "MaxOrderLimit", maximumOrderLimit, TIMEOUT_IN_SECS);
	}

	public void clickAddThisSinBtn() {
		// Need this locator as there is a hidden element with same name
		//Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[contains(@name,'saveSin') and @type='submit']", TIMEOUT_IN_SECS);
		SeleniumHelper.clickHiddenElement(driver, "//input[contains(@name,'saveSin') and @type='submit']", "xpath");
	}

	/**
	 * This will populate the Sin details page "sinDetails": [ { "sinGroup": "sin":
	 * "", "naicsCode": "", "coOpPurchasing": "", "scope": "", "estimatedSales": "",
	 * "commercialDeliverySchedule": "", "stateCommercialDelivery": "",
	 * "expeditedDelivery": "", "additionalServiceCharge": "", "maxOrderLimit":"",
	 * "offerCategory": "" }]
	 *
	 * @param sinObj
	 */
	public void populateForm(JsonObject sinObj) {
		boolean skipSubmit = false;
		for (Map.Entry<String, JsonElement> element : sinObj.entrySet()) {
			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			log.info("**checking the sin enter issue - populateForm: "+elementName+"  "+elementValue);
			switch (elementName) {
			case "naicsCode":
				// selectNaicsCodeByText(elementValue);
				break;
			case "scope":
				selectScopeByText(elementValue);
				break;
			case "estimatedSales":
				setEstimatedSales(elementValue);
				break;
			case "maxOrderLimit":
				// setMaxOrderLimit(elementValue);
				break;
			case "coOpPurchasing":
				CommonUtilPage.selectRadioOption(driver, "CooperativePurchasing", elementValue);
				break;
			case "commercialDeliverySchedule":
				CommonUtilPage.selectRadioOption(driver, "CommercialDeliverySchedule", elementValue);
				break;
			case "stateCommercialDelivery":
				ActionByName.sendKeys(driver, "StateCommercialDeliveryTime", elementValue, TIMEOUT_IN_SECS);
				break;
			case "expeditedDelivery":
				ActionByName.sendKeys(driver, "ExpeditedDeliveryTime", elementValue, TIMEOUT_IN_SECS);
				break;
			case "additionalServiceCharge":
				CommonUtilPage.selectRadioOption(driver, "AdditionalServiceCharge", elementValue);
				break;
			case "sin":
				addSinCategory(sinObj);
				break;
			case "isTDR":
				if (elementValue.isEmpty())
					skipSubmit = true;
				break;
			case "category":
			case "subCategory":
				// Do nothing
				break;
			default:
				try {
//					PerformActionUtils.performActionByLocatorName(driver, elementName, elementValue);
					SeleniumHelper.performActionByLocatorName(driver, elementName, elementValue);
				} catch (Exception e) {
					log.error("Execptiopn thrown: " + e.getMessage());
					e.printStackTrace();
				}
				break;
			}
		}

		if (!skipSubmit && !ActionByLocator.isDisplayed(driver, errorText, TIMEOUT_IN_SECS)) {
			clickAddThisSinBtn();
		} else if (!skipSubmit && ActionByLocator.isDisplayed(driver, errorText, TIMEOUT_IN_SECS)) {
			log.info("Error message display");
		}
	}

	public void addSinCategory(JsonObject sinCategoryObj) {
		for (Map.Entry<String, JsonElement> element : sinCategoryObj.entrySet()) {
			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			log.info("**checking the sin enter issue: "+elementName+"  "+elementValue);
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
		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[contains(@name,'sinType') and @type='Submit']"));
		if (sinCategoryObj.has("isTDR")) {
			String isTdr = sinCategoryObj.get("isTDR").getAsString();
			if (!isTdr.isEmpty())
				CommonUtilPage.selectRadioOption(driver, "tdrOptInResponse", isTdr.substring(0, 1));
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//*[@id='id_tdrResponseSave' and @value='Submit Response']"));
		}
	}
}
