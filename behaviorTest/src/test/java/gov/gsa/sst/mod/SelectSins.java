package gov.gsa.sst.mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.commonpage.CommonUtilPage;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.solicitationclause.CommercialSalesPracticePage;
import util.ActionById;
import util.ActionByName;

public class SelectSins extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(SelectSins.class);
	public ExecutionContext executionContext;

	public SelectSins(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub

	}

	public void selectSins(JsonObject jsonObj) {
		List<String> list = new ArrayList<String>();
		JsonArray array = jsonObj.getAsJsonArray("sinDetails");
		Iterator<JsonElement> iterator = array.iterator();

		while (iterator.hasNext()) {
			JsonElement element = iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			list.add(sinObj.get("sin").getAsString());
			ActionById.selectByText(driver, "contractSins", sinObj.get("sin").getAsString(), TIMEOUT_IN_SECS);
			Octo_SeleniumLibrary.clickElement(driver, By.id( "src2Target"));
		}
		if (jsonObj.has("percentage"))
			ActionByName.sendKeys(driver, "percentage", jsonObj.get("percentage").getAsString(), TIMEOUT_IN_SECS);
		ActionById.sendKeys(driver, "modDesc", jsonObj.get("descOfModRequest").getAsString(), TIMEOUT_IN_SECS);
	}

	/**
	 * Perform this action for pricing and additions mod
	 */
	public void reviseCsp() {
		JsonObject scenarioObj = executionContext.getCurrentScenarioObj();
		if (scenarioObj.has("commercialSalesPractice")) {
			if (scenarioObj.getAsJsonObject("commercialSalesPractice").size() > 0) {
				log.info("Adding revise CSP data");
				Octo_SeleniumLibrary.clickElement(driver, By.id( "cspRequiredYes"));
				Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[@name='change' and @value='Save']"));
				JsonObject cspObj = scenarioObj.getAsJsonObject("commercialSalesPractice");
				CommercialSalesPracticePage cspPage = new CommercialSalesPracticePage(executionContext);
				cspPage.populateFormForMod(cspObj);
			} else {
				log.info("Revise CSP is present but ignored");
			}
		} else if (ActionByName.isDisplayed(driver, "cspRequired", TIMEOUT_IN_SECS)) {
			log.info("Responding with No for revise CSP");
			Octo_SeleniumLibrary.clickElement(driver, By.id( "cspRequiredNo"));
			Octo_SeleniumLibrary.clickElement(driver, By.name( "change"));
		} else
			log.info("'Commercial Sales Practice' data is not provided.");
	}

	private void clickSaveAndContinue() { // Use value instead of id as the id is different for FPT and nonFPT pages
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[@value='Save and Continue']"));
	}

	/**
	 * Selects sins and completes CSP (if applicable)
	 *
	 * @param jsonObj
	 */
	public void populateForm(JsonObject jsonObj) {
		String modType = executionContext.getCurrentScenarioObj().get("modTypes").getAsJsonArray().get(0).getAsString();
		log.info("Populating the " + modType + " page");
		reviseCsp();
		// Only Add Product for FPT has sin selection
		if (jsonObj.has("sinDetails"))
			selectSins(jsonObj);
		else
			addDataFields(jsonObj);
		clickSaveAndContinue();
	}

	public void addDataFields(JsonObject jsonObj) {
		for (Map.Entry<String, JsonElement> element : jsonObj.entrySet()) {
			String elementName = element.getKey();
			if (!elementName.equalsIgnoreCase("sinDetails")) {
				String elementValue = element.getValue().getAsString();
				switch (elementName) {
				case "descOfModRequest":
					if (ActionById.isDisplayed(driver, "modDesc", TIMEOUT_IN_SECS))
						ActionById.sendKeys(driver, "modDesc", jsonObj.get("descOfModRequest").getAsString(),
								TIMEOUT_IN_SECS);
					else
						ActionByName.sendKeys(driver, "ModDetailDescription",
								jsonObj.get("descOfModRequest").getAsString(), TIMEOUT_IN_SECS);
					break;
				case "offerCategory":
					CommonUtilPage.selectRadioOption(driver, "offerCategory", elementValue);
					break;
				case "minOrderLimit":
					ActionById.sendKeys(driver, "molId", elementValue, TIMEOUT_IN_SECS);
					break;
				case "percentage":
					ActionByName.sendKeys(driver, "Percentage", elementValue, TIMEOUT_IN_SECS);
					break;
				case "startDate":
					if (elementValue.isEmpty()) {
						setStartDate(CommonUtilPage.getStartDate());
					} else
						setStartDate(elementValue);
					break;
				case "endDate":
					if (elementValue.isEmpty()) {
						setEndDate(CommonUtilPage.getEndDate());
					} else
						setEndDate(elementValue);
					break;
				case "acknowledge":
					Octo_SeleniumLibrary.clickElement(driver, By.name( "Acknowledge"));
					break;
				case "fastlane":
					updateFastlane(elementValue);
					break;
				default:
					log.warn("Field not supported " + elementName);
					break;
				}
			}
		}
	}

	private void setStartDate(String date) {
		String[] arr = date.split("/");
		ActionByName.selectByValue(driver, "StartDateMonth", arr[0], TIMEOUT_IN_SECS);
		ActionByName.selectByValue(driver, "StartDateDay", arr[1], TIMEOUT_IN_SECS);
		ActionByName.selectByValue(driver, "StartDateYear", arr[2], TIMEOUT_IN_SECS);
	}

	private void setEndDate(String date) {
		String[] arr = date.split("/");
		ActionByName.selectByValue(driver, "EndDateMonth", arr[0], TIMEOUT_IN_SECS);
		ActionByName.selectByValue(driver, "EndDateDay", arr[1], TIMEOUT_IN_SECS);
		ActionByName.selectByValue(driver, "EndDateYear", arr[2], TIMEOUT_IN_SECS);
	}

	/**
	 * This method is needed to handle fastlane mods
	 *
	 * @param fastlane
	 */
	public void updateFastlane(String fastlane) {
		CommonUtilPage.selectRadioOption(driver, "fastlaneRequired", fastlane);
		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//*[@value='Save']"));
	}
}
