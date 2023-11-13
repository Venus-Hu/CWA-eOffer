package gov.gsa.sst.discounts;

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
import gov.gsa.sst.commonpage.GenericDialogPage;
import gov.gsa.sst.wizardmanagement.WizardManagementPage;
import util.ActionById;
import util.ActionByLocator;
import util.PageUtil;
import util.TableElementUtils;

public class DiscountsPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private By startRange = By.id("breakBegin");
	private By endRange = By.id("breakEnd");
	private By discountPercentage = By.id("discountPercent");
	private By discountCondition = By.id("discountComment");
	private By viewWizardSummaryLink = By.partialLinkText("View Wizard Summary");
	private By addDiscountBtn = By.name("createDiscount");
	private By continueBtn = By.id("saveAndContinueToNextLevel");

	private static Logger log = LoggerFactory.getLogger(DiscountsPage.class);
	private ExecutionContext executionContext;
	private By headerText = By.xpath("//h1[text()='DISCOUNTS']");

	public DiscountsPage(WebDriver driver) {
		super(driver);
	}

	public DiscountsPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		if (ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS)) {
			if (CommonUtilPage.isOffer(executionContext))
				return CommonUtilPage.isSolicitationApt(executionContext);
			else
				return true;
		} else
			return false;
	}

	@Override
	protected void load() {
		log.info("Loading 'Discounts' page");
		try {
			LeftNavigationMenu.navigateTo(driver, "Discounts", TIMEOUT_IN_SECS);
		} catch (Exception e) {
			WizardManagementPage wizardManagent = new WizardManagementPage(executionContext);
			wizardManagent.get();

			LeftNavigationMenu.navigateTo(driver, "Discounts", TIMEOUT_IN_SECS);
		}
		try {
			executionContext.testPageFor508("Discounts");
		} catch (Exception e) {
			log.warn("Section 508 exception: " + e.getMessage());
		}
		Assert.assertTrue("'Discounts' page is not loaded", isLoaded());
	}

	public void clickContinueBtn() {
		Octo_SeleniumLibrary.clickElement(driver, continueBtn);
	}

	public void clickViewWizSummaryLink() {
		Octo_SeleniumLibrary.clickElement(driver, viewWizardSummaryLink);
	}

	public void clickAddDiscountBtn() {
		Octo_SeleniumLibrary.clickElement(driver, addDiscountBtn);
	}

	public void setStartRange(String input) {
		clear(startRange);
		ActionByLocator.sendKeys(driver, startRange, input, TIMEOUT_IN_SECS);
	}

	public void setEndRange(String input) {
		clear(endRange);
		ActionByLocator.sendKeys(driver, endRange, input, TIMEOUT_IN_SECS);
	}

	public void setDiscount(String input) {
		clear(discountPercentage);
		ActionByLocator.sendKeys(driver, discountPercentage, input, TIMEOUT_IN_SECS);
	}

	public void setDiscountCondition(String input) {
		clear(discountCondition);
		ActionByLocator.sendKeys(driver, discountCondition, input, TIMEOUT_IN_SECS);
	}

	/**
	 * This method will populate discounts form for an offer or for selected SINs
	 * 
	 * json data for discounts
	 * 
	 * @param discountsArray
	 * 
	 *                       "discounts": [ { "sin": "", "startRange": "2",
	 *                       "endRange": "50", "discount": "0.2" }, { "sin": "",
	 *                       "startRange": "51", "endRange": "100", "discount":
	 *                       "0.5"
	 * 
	 *                       } ]
	 */
	public void populateForm(JsonArray discountsArray) {
		log.info("Loading Discounts data");
		Iterator<JsonElement> iterator = discountsArray.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();

			if (jsonObj.get("sin") != null) {
				ActionById.selectByText(driver, "sinId", jsonObj.get("sin").getAsString(), TIMEOUT_IN_SECS);
				Octo_SeleniumLibrary.clickElement(driver, By.id("btnSelect"));
			}

			String startRange = jsonObj.get("startRange").getAsString();
			String endRange = jsonObj.get("endRange").getAsString();
			String discount = jsonObj.get("discount").getAsString();
			setStartRange(startRange);
			setEndRange(endRange);
			setDiscount(discount);
			Octo_SeleniumLibrary.clickElement(driver, By.name("createDiscount"));
			PageUtil.checkPageLoad(driver);
		}
	}

	public void editDiscounts(JsonArray discountsArray) {
		log.info("Updating existing Discounts data");
		Iterator<JsonElement> iterator = discountsArray.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			// Not needed for Offer level edit
			/*
			 * if (jsonObj.get("sin") != null){ String sinName =
			 * jsonObj.get("sin").getAsString(); ActionById.selectByText(driver, "sinId",
			 * sinName, TIMEOUT_IN_SECS); Octo_SeleniumLibrary.clickElement(driver, By.id(
			 * "btnSelect", TIMEOUT_IN_SECS); }
			 */
			WebElement row = getDiscountsTableRowByIndex(jsonObj.get("rowIndex").getAsInt());
			row.findElement(By.xpath(".//a[@title='Edit Discount Details']")).click();
			String startRange = jsonObj.get("startRange").getAsString();
			String endRange = jsonObj.get("endRange").getAsString();
			String discount = jsonObj.get("discount").getAsString();

			setStartRange(startRange);
			setEndRange(endRange);
			setDiscount(discount);
			Octo_SeleniumLibrary.clickElement(driver, By.name("editDiscount"));
			PageUtil.checkPageLoad(driver);
		}
	}

	public void deleteDiscounts(JsonArray discountsArray) {
		log.info("Deleting existing Discounts");
		int rowCount = discountsArray.size();
		int index = 1;
		while (rowCount > 0) {
			// Not needed for Offer level delete
			/*
			 * if (jsonObj.get("sin") != null){ String sinName =
			 * jsonObj.get("sin").getAsString(); ActionById.selectByText(driver, "sinId",
			 * sinName, TIMEOUT_IN_SECS); Octo_SeleniumLibrary.clickElement(driver, By.id(
			 * "btnSelect", TIMEOUT_IN_SECS); }
			 */
			WebElement row = getDiscountsTableRowByIndex(index);
			row.findElement(By.xpath(".//a[@title='Delete Discount Details']")).click();
			Octo_SeleniumLibrary.clickElement(driver, By.name("deleteDiscount"));
			GenericDialogPage page = new GenericDialogPage(executionContext.getDriver(), "Delete Discount Confirmation",
					By.xpath("//button[contains(.,'Ok')]"));
			page.clickConfirmYes();
			PageUtil.checkPageLoad(driver);
			rowCount--;
		}
	}

	public boolean isDiscountPresentBySin(String sinNumber) {
		WebElement table = checkForDiscountTable();
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "SIN", sinNumber);
		if (row != null)
			return true;
		else
			return false;
	}

	public WebElement checkForDiscountTable() {
		if (ActionById.isDisplayed(driver, "discountListingsTable", TIMEOUT_IN_SECS)) {
			WebElement table = ActionById.getElement(driver, "discountListingsTable", TIMEOUT_IN_SECS);
			return table;
		} else
			return null;
	}

	public boolean isDiscountPresentByStartRange(String startRange) {
		WebElement table = checkForDiscountTable();
		if (table == null) {
			log.info("Could not find Disocunts table");
			return false;
		}
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "Start Range", startRange);
		if (row != null)
			return true;
		else
			return false;
	}

	public void verifyDiscountsList(JsonArray discountsArray) {
		Iterator<JsonElement> iterator = discountsArray.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();

			if (jsonObj.get("sin") != null) {
				String sin = jsonObj.get("sin").getAsString();
				log.info("Verifying the discount row with sin " + sin);
				Assert.assertTrue("Discount row is not found in the list", isDiscountPresentBySin(sin));
			} else {
				String startRange = jsonObj.get("startRange").getAsString();
				log.info("Verifying the discount row with start range value " + startRange);
				Assert.assertTrue("Discount row is not found in the list", isDiscountPresentByStartRange(startRange));
			}
		}
	}

	private void clear(By locator) {
		ActionByLocator.clear(driver, locator, TIMEOUT_IN_SECS);
	}

	private WebElement getDiscountsTableRowByIndex(int index) {
		WebElement table = checkForDiscountTable();
		WebElement row = TableElementUtils.getNthTableRow(table, index);
		return row;
	}

	/**
	 * Delete all discounts for offer
	 */
	public void deleteAllDiscounts() {
		while (ActionByLocator.isDisplayed(driver, By.xpath(".//a[@title='Delete Discount Details']"),
				TIMEOUT_IN_SECS)) {
			WebElement discount = ActionByLocator.getElement(driver, By.xpath(".//a[@title='Delete Discount Details']"),
					TIMEOUT_IN_SECS);
			discount.click();

			// 'Delete Discount' page
			Octo_SeleniumLibrary.clickElement(driver, By.name("deleteDiscount"));

			// 'Delete Discount Confirmation' UI
			GenericDialogPage page = new GenericDialogPage(executionContext.getDriver(), "Delete Discount Confirmation",
					By.xpath("//button[contains(.,'Ok')]"));
			page.clickConfirmYes();
			PageUtil.checkPageLoad(driver);
		}
	}
}
