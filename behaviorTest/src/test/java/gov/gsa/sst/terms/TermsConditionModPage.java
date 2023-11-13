package gov.gsa.sst.terms;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

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
import gov.gsa.sst.commonpage.SeleniumHelper;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.exception.ExceptionPage;
import gov.gsa.sst.solicitationclause.CommercialSalesPracticePage;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByXpath;
import util.TableElementUtils;

public class TermsConditionModPage extends Page {
	private final int TIMEOUT_IN_SECS = 2;
	private static Logger log = LoggerFactory.getLogger(TermsConditionModPage.class);
	private ExecutionContext executionContext;
	private String nextLink = "//div/h2[contains(., 'Saved Modifications')]/following-sibling::div//a[text()='Next']";

	public TermsConditionModPage(ExecutionContext executioncontext) {
		super(executioncontext.getDriver());
		this.executionContext = executioncontext;
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Terms and Conditions page");
			LeftNavigationMenu.navigateTo(driver, "Terms and Conditions");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Terms and Conditions");
		}
		try {
			executionContext.testPageFor508("Term and Conditions");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("Could not load Terms and Conditions page for mod", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		String modType = executionContext.getCurrentScenarioObj().getAsJsonArray("modTypes").get(0).getAsString();
		log.info("Checking for mod title: " + modType.toUpperCase());
		String headerText = ActionByXpath.getText(driver, "//h1", TIMEOUT_IN_SECS);
		return headerText.equalsIgnoreCase(modType);
	}

	public void respond() {
		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//a[contains(@title,'Respond')]"));
	}

	public void selectClause(String clause) {
		if (ActionByName.isDisplayed(driver, "clauseType", TIMEOUT_IN_SECS)) {
			ActionByName.selectByText(driver, "clauseType", clause, TIMEOUT_IN_SECS);
			Octo_SeleniumLibrary.clickElement(driver, By.name( "addClause"));
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//a[@title='Respond to Input']"));
		}
	}

	public void populateClauses(JsonArray array) throws InterruptedException {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			String clauseName = jsonObj.get("clause").getAsString();
			selectClause(clauseName);
			switch (clauseName) {
			case "Commercial Sales Practice (CSP-1)":
				addCspDetails(jsonObj);
				break;
			case "Cooperative Purchasing":
				// Select SIN first
				selectSin(jsonObj.get("sin").getAsString());
				addClauseDetails(jsonObj);
				break;
			default:
				addClauseDetails(jsonObj);
				break;
			}
		}
	}

	/**
	 * This method works for all Terms and condition mod except Cooperative
	 * Purchasing
	 * 
	 * @param jsonObj
	 * @throws InterruptedException
	 */
	public void addClauseDetails(JsonObject jsonObj) throws InterruptedException {
		Set<Entry<String, JsonElement>> qcSet = jsonObj.entrySet();
		Iterator<Entry<String, JsonElement>> iter = qcSet.iterator();
		String clauseName = "";
		while (iter.hasNext()) {
			Entry<String, JsonElement> basicInfoElement = iter.next();
			String elementName = basicInfoElement.getKey();
			JsonElement elementValue = basicInfoElement.getValue();
			switch (elementName) {
			case "modDescription":
				ActionByName.clear(driver, "ModDetailDescription", TIMEOUT_IN_SECS);
				ActionByName.sendKeys(driver, "ModDetailDescription", elementValue.getAsString(), TIMEOUT_IN_SECS);
				break;
			case "clause":
				// No action required for this field
				clauseName = elementValue.getAsString();
				break;
			case "sin":
				// No action required for this field
				break;
			default:
//            	PerformActionUtils.performActionByLocatorName(driver,elementName, elementValue.getAsString());
				SeleniumHelper.performActionByLocatorName(driver, elementName, elementValue.getAsString());
				break;
			}
		}
		if (clauseName.equalsIgnoreCase("Cooperative Purchasing")) {
			Octo_SeleniumLibrary.clickElement(driver, By.name( "saveSin"));
		} else {
			Octo_SeleniumLibrary.clickElement(driver, By.name( "saveAndContinue"));
			// Since Save will take you to Revise T& C page, click on respond again to be on
			// Revise Clause/TC page
			// respond();
		}
	}

	public void editMod(JsonArray editArray) {
		Iterator<JsonElement> iterator = editArray.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			WebElement row = getRowByName(jsonObj.get("prevMod").getAsString());
			WebElement editMod = row.findElement(By.xpath("//input[@name='savedAction' and @value ='Edit Mod']"));
			if (editMod != null) {
				editMod.click();
			}
		}
	}

	public void editSavedMod(String contractNumber, String modType) {
		log.info("Editing saved emod of type: " + modType);
		JsonObject dataObj = executionContext.getCurrentScenarioObj();
		if (dataObj.has("gsaMod") && dataObj.get("gsaMod").getAsString().equalsIgnoreCase("Y")) {
			if (ActionByXpath.isDisplayed(driver, "//span[@class='servicedropSeven glyphicon glyphicon-plus-sign']", 1))
				Octo_SeleniumLibrary.clickElement(driver, By.xpath(
						"//span[@class='servicedropSeven glyphicon glyphicon-plus-sign']"));
			WebElement element = getRowByContractAndModType(contractNumber, modType, "savedmodstable");
			if (element != null) {
				ActionByLocator
						.getElementInElement(driver, element, By.xpath(".//input[@value='Edit Mod']"), TIMEOUT_IN_SECS)
						.click();
			}
		} else {
			WebElement element = getRowByContractAndModType(contractNumber, modType, "savedmodstable");
			if (element != null) {
				ActionByLocator
						.getElementInElement(driver, element, By.xpath(".//input[@value='Edit Mod'and @name='savedAction']"), TIMEOUT_IN_SECS)
						.click();
			}
		}
	}
	
	public void deleteMod(String contractNumber, String modType) {
		log.info("Checking if mod exists for deletion");
		Boolean isDelete = true;
		System.out.println("******ModType : "+modType);
		System.out.println("******Contract : "+contractNumber);
		while (isDelete) {
			SeleniumHelper.clickAndContinueByLocator_tempFix(driver, By.xpath("//*[@id='savedmods']"));
			isDelete = SeleniumHelper.verifyElementExist(driver, By.xpath("//input[@value='Delete Mod']"));
			if (isDelete) {
				Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[@value='Delete Mod']"));
				Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[@value='Yes']"));
			}
		}

	}

	public WebElement getRowByContractAndModType(String contractNumber, String modType, String tableId) {
		WebElement modRow = null;
		// WebElement modsTable = ActionById.getElement(driver, tableId,
		// TIMEOUT_IN_SECS);
		WebElement modsTable = SeleniumHelper.findElem_tempFix_public(driver, By.id(tableId));
		modRow = TableElementUtils.getTableRowByCellValues(modsTable, "Mod Actions\nClick to sort column", modType,
				"Contract\nClick to sort column", contractNumber);
		if (modRow == null) {
			while (ActionByXpath.isDisplayed(driver, nextLink, TIMEOUT_IN_SECS)) { // search in other pages
				Octo_SeleniumLibrary.clickElement(driver, By.xpath( nextLink));
//				modsTable = ActionById.getElement(driver, tableId, TIMEOUT_IN_SECS);
				modsTable = SeleniumHelper.findElem_tempFix_public(driver, By.id(tableId));
				modRow = TableElementUtils.getTableRowByCellValues(modsTable, "Mod Actions\nClick to sort column",
						modType, "Contract\nClick to sort column", contractNumber);
				if (modRow != null)
					break;
			}
		}
		return modRow;
	}

	private WebElement getRowByName(String editModType) {
		WebElement table = ActionById.getElement(driver, "savedmodstable", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "Mod Actions", editModType);
		return row;
	}

	public void addSinDetails(JsonObject jsonObj) throws InterruptedException {
		Set<Entry<String, JsonElement>> qcSet = jsonObj.entrySet();
		Iterator<Entry<String, JsonElement>> iter = qcSet.iterator();

		while (iter.hasNext()) {
			Entry<String, JsonElement> basicInfoElement = iter.next();
			String elementName = basicInfoElement.getKey();
			JsonElement elementValue = basicInfoElement.getValue();
			switch (elementName) {
			case "sin":

			case "clause":
				// No action required for this field
				break;
			default:
//            	PerformActionUtils.performActionByLocatorName(driver,elementName, elementValue.getAsString());
				SeleniumHelper.performActionByLocatorName(driver, elementName, elementValue.getAsString());
				break;
			}
		}

		Octo_SeleniumLibrary.clickElement(driver, By.name( "saveAndContinue"));
		// Since Save will take you to Revise T& C page, click on respond again to be on
		// Revise Clause/TC page
		respond();
	}

	public void populateExceptions(JsonArray array) {
		ExceptionPage exceptionPage = new ExceptionPage(executionContext);
		exceptionPage.populateForm(array);
	}

	/**
	 * Add CSP page details form
	 * 
	 * @param jsonObj
	 */
	public void addCspDetails(JsonObject jsonObj) {
		CommercialSalesPracticePage cspPage = new CommercialSalesPracticePage(executionContext);
		cspPage.populateFormForMod(jsonObj.get("commercialSalesPractice").getAsJsonObject());
	}

	public void selectSin(String sin) {
		ActionByName.selectByText(driver, "sintype", sin, TIMEOUT_IN_SECS);
		Octo_SeleniumLibrary.clickElement(driver, By.name( "sinType"));
	}
}
