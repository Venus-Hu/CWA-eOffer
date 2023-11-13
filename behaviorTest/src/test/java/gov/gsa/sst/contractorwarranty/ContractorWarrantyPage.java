package gov.gsa.sst.contractorwarranty;

import java.util.Iterator;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
import gov.gsa.sst.wizardmanagement.WizardManagementPage;
import util.ActionById;
import util.TableElementUtils;

public class ContractorWarrantyPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(ContractorWarrantyPage.class);
	private By headerText = By.xpath("//h1[contains(text(),'Contractor Warranty')]");
	private ExecutionContext executionContext;

	public ContractorWarrantyPage(WebDriver driver) {
		super(driver);
	}

	public ContractorWarrantyPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		log.info("Loading Contractor Warranty page");
		try {
			LeftNavigationMenu.navigateTo(driver, "Contractor Warranty");
		} catch (Exception e) {
			WizardManagementPage wizardPage = new WizardManagementPage(executionContext);
			wizardPage.get();

			if (CommonUtilPage.isOffer(executionContext))
				LeftNavigationMenu.navigateTo(driver, "Contractor Warranty");
			else
				LeftNavigationMenu.navigateTo(driver, "ContractorWarranty");
		}
		try {
			executionContext.testPageFor508("Contractor Warranty");
		} catch (Exception e) {
			log.warn("Section 508 exception: " + e.getMessage());
		}
		Assert.assertTrue("Contractor Warranty is not loaded", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
	}

	public void setSin(String sin) {
		ActionById.selectByText(driver, "sinNum", sin, TIMEOUT_IN_SECS);
		clickSelectBtn();
	}

	public void verifySin(String sin) {
		ActionById.assertEqualsText(driver, "sinNum", sin, TIMEOUT_IN_SECS);
	}

	public void clickSelectBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id("btnSelect"));
	}

	public void selectWarrantyPeriod(String warrantyPeriod) {
		ActionById.selectByText(driver, "warrantyperiod", warrantyPeriod, TIMEOUT_IN_SECS);
	}

	public void addNumOfWarrantPeriods(String numOfPeriods) {
		ActionById.sendKeys(driver, "noOfwarrantyperiods", numOfPeriods, TIMEOUT_IN_SECS);
	}

	public void addDescription(String desc) {
		ActionById.clear(driver, "warrantydescription", TIMEOUT_IN_SECS);
		ActionById.sendKeys(driver, "warrantydescription", desc, TIMEOUT_IN_SECS);
	}

	public void addUpdateAction() {
		Octo_SeleniumLibrary.clickElement(driver, By.id("submit"));
	}

	public void verifyWarrantyDetails(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		WebElement table = ActionById.getElement(driver, "contractorWarrantyTable", TIMEOUT_IN_SECS);

		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			String sin = jsonObj.get("sin").getAsString();

			WebElement row = TableElementUtils.getTableRowByCellValue(table, "SIN", sin);
			Assert.assertNotNull("Could not find row with SIN: " + sin, row);
		}
	}

	/**
	 * "contractorWarranty": [ { "sin": "BMS003", "warrantyPeriod": "Week",
	 * "numOfPeriods": "1223445", "warrantyDescription": "This warranty will last
	 * for a week" }]
	 * 
	 * @param array
	 */
	public void populateForm(JsonArray array) {
		log.info("Populating the contractor warranty data...");
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();

			setSin(jsonObj.get("sin").getAsString());
			// TODO set warranty applicable or not checkbox
			// contrwarrantynotapplicable
			selectWarrantyPeriod(jsonObj.get("warrantyPeriod").getAsString());
			addNumOfWarrantPeriods(jsonObj.get("numOfPeriods").getAsString());
			addDescription(jsonObj.get("warrantyDescription").getAsString());
			addUpdateAction();
		}
	}
}
