package gov.gsa.sst.solicitationprovision;

import java.util.Iterator;

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

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.GenericDialogPage;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionById;
import util.ActionByLinkText;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByPartialLinkText;
import util.ActionByWebElement;
import util.TableElementUtils;

/**
 * This page is part of Solicitation Provision but appears only for Schedule 70.
 * This is a matrix form page
 * 
 * @author amulay
 *
 */
public class ScaMatrixPage extends Page {

	private final int TIMEOUT_IN_SECS = 5;
	private static Logger log = LoggerFactory.getLogger(ScaMatrixPage.class);

	private By delete = By.partialLinkText("Delete");
	private By saveAndContinueButton = By.xpath("//input[contains(@value,'Save and Continue')]");
	private By saveBtn = By
			.xpath("//input[contains(@value, 'Cancel')]/preceding-sibling::input[contains(@value, 'Save')]");
	private By cancelBtn = By.xpath("//input[contains(@value, 'Cancel')]");
	private By txtLaborCategory = By.name("laborCategory");
	private By txtCodeTitle = By.name("eqvCodeTitle");
	private By txtWdNumber = By.name("wdNumber");
	private final static String CONTRACT_LABOR_CATEGORY = "SCA Eligible Contract Labor Category *";
	private final static String WD_NUMBER = "WD Number *";
	private By headerText = By.xpath("//h1[contains(text(), 'SCA Labor Category Matrix')]");
	private ExecutionContext executionContext;

	public ScaMatrixPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		if (ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS)) {
			if (ActionByLocator.isDisplayed(driver, cancelBtn, TIMEOUT_IN_SECS)) { // if we end up in edit page
																					// condition
				LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
				SolicitationProvisionPage solProvisionPage = new SolicitationProvisionPage(executionContext);
				solProvisionPage.selectProvisionAction("SCA Matrix");
			}
			return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
		} else
			return false;
	}

	@Override
	protected void load() {
		try {
			LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
			SolicitationProvisionPage solProvisionPage = new SolicitationProvisionPage(executionContext);
			solProvisionPage.selectProvisionAction("SCA Matrix");

			log.info("Loading Solicitation Provisions - SCA Labor Category Matrix page");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
			SolicitationProvisionPage solProvisionPage = new SolicitationProvisionPage(executionContext);
			solProvisionPage.selectProvisionAction("SCA Matrix");
		}
		try {
			executionContext.testPageFor508("SCA matrix page");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("SA MAtrix page is not loaded.", isLoaded());
	}

	/* Setters do not need row numbers as only 1 row is editable at a time */
	public void setLaborCategory(String category) {
		ActionByName.sendKeys(driver, "laborCategory", category, TIMEOUT_IN_SECS);
	}

	public void setEquivalentCodeTitle(String codeTitle) {
		ActionByLocator.clear(driver, txtCodeTitle, TIMEOUT_IN_SECS);
		ActionByName.sendKeys(driver, "eqvCodeTitle", codeTitle, TIMEOUT_IN_SECS);
	}

	public void setWdNumber(String wdNumber) {
		ActionByLocator.clear(driver, txtWdNumber, TIMEOUT_IN_SECS);
		ActionByName.sendKeys(driver, "wdNumber", wdNumber, TIMEOUT_IN_SECS);
	}

	public void addAction() {
		Octo_SeleniumLibrary.clickElement(driver, By.name( "action"));
	}

	public void submitForm() {
		Octo_SeleniumLibrary.clickElement(driver, saveAndContinueButton);
	}

	/**
	 * "scaLaborCategoryMatrix": [{ "laborCategory": "lc1", "codeTitle": "title 1 ", "wdNum":
	 * "111" }]
	 * 
	 * @param array
	 */
	public void populateForm(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			setLaborCategory(jsonObj.get("laborCategory").getAsString());
			setEquivalentCodeTitle(jsonObj.get("codeTitle").getAsString());
			setWdNumber(jsonObj.get("wdNumber").getAsString());
			addAction();
		}
	}

	public void editMatrix(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			String category = jsonObj.get("laborCategory").getAsString();
			String title = jsonObj.get("codeTitle").getAsString();
			String wdNum = jsonObj.get("wdNumber").getAsString();
			WebElement row = getRowByWdNumber(jsonObj.get("addedWdNumber").getAsString());
			if (row != null) {
				WebElement editLink = row.findElement(By.linkText("Edit"));
				ActionByWebElement.click(driver, editLink, TIMEOUT_IN_SECS);
				ActionByLocator.clear(driver, txtLaborCategory, TIMEOUT_IN_SECS);
				setLaborCategory(category);
				
				setEquivalentCodeTitle(title);
				setWdNumber(wdNum);
				Octo_SeleniumLibrary.clickElement(driver, saveBtn);
			}
		}
	}

	public void deleteAllCategories() {
		while (ActionByLocator.isVisible(driver, delete, 10)) {
			ActionByLinkText.click(driver, "Delete", TIMEOUT_IN_SECS);
			GenericDialogPage deletePage = new GenericDialogPage(driver);
			deletePage.clickConfirmYes();
		}
	}

	public void verifyLaborCategoriesAdded(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			String category = jsonObj.get("laborCategory").getAsString();
			String title = jsonObj.get("codeTitle").getAsString();
			String wdNum = jsonObj.get("wdNumber").getAsString();
			WebElement row = getRowByLaborCategory(category);
			Assert.assertNotNull("Labor category could not be found", row);
			Assert.assertEquals("Code title could not be found for " + title, true, row.getText().contains(title));
			Assert.assertEquals("WD number could not be found for " + wdNum, true, row.getText().contains(wdNum));
		}
	}

	public void editRowByCategory(String category) {
		ActionByPartialLinkText.getElementInElement(driver, getRowByLaborCategory(category), "Edit", TIMEOUT_IN_SECS)
				.click();
	}

	public void deleteRowByCategory(String category) {
		ActionByPartialLinkText.getElementInElement(driver, getRowByLaborCategory(category), "Delete", TIMEOUT_IN_SECS)
				.click();
	}

	/* Getters need to define the column header and cell value */
	public WebElement getRowByLaborCategory(String category) {
		WebElement element = ActionById.getElement(driver, "scaTable", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(element, CONTRACT_LABOR_CATEGORY, category);
		return row;
	}

	public WebElement getRowByWdNumber(String wdNumber) {
		WebElement element = ActionById.getElement(driver, "scaTable", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(element, WD_NUMBER, wdNumber);
		return row;
	}
}
