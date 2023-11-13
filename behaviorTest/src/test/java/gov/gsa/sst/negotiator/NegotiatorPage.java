package gov.gsa.sst.negotiator;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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
import gov.gsa.sst.commonpage.SeleniumHelper;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionById;
import util.ActionByName;
import util.ActionByTagName;
import util.ActionByWebElement;
import util.ActionByWebElements;
import util.PageUtil;
import util.TableElementUtils;

/**
 * Created by skumar on 2/2/2017.
 */
public class NegotiatorPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(NegotiatorPage.class);
	private ExecutionContext executionContext;

	public NegotiatorPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() throws Error {
		return PageUtil.containsText(driver, "Offerors can add multiple negotiators");
	}

	@Override
	protected void load() {
		log.info("Loading Negotiator list page");
		try {
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number specified in data does not match the one on UI");
			LeftNavigationMenu.navigateTo(driver, "Negotiators");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Negotiators");
		}
		try {
			executionContext.testPageFor508("Negotiator");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("Negotiator page is not loaded", isLoaded());
	}

	public void setName(String input) {
		clearName();
		ActionByName.sendKeys(driver, "name", input, TIMEOUT_IN_SECS);
	}

	public void verifyName(String text) {
		ActionByName.assertEqualsText(driver, "name", text, TIMEOUT_IN_SECS);
	}

	public void setTitle(String input) {
		clearTitle();
		ActionByName.sendKeys(driver, "title", input, TIMEOUT_IN_SECS);
	}

	public String getTitle() {
		return ActionByName.getText(driver, "title", TIMEOUT_IN_SECS);
	}

	public void verifyTitle(String text) {
		ActionByName.assertEqualsText(driver, "title", text, TIMEOUT_IN_SECS);
	}

	public void setPhone(String phone) {
		clearPhone();
		ActionByName.sendKeys(driver, "phone", phone, TIMEOUT_IN_SECS);
	}

	public void verifyPhone(String phoneNumber) {
		ActionByName.assertEqualsText(driver, "phone", phoneNumber, TIMEOUT_IN_SECS);
	}

	public void setPhoneIntl(String input) {
		ActionByName.sendKeys(driver, "phoneInternational", input, TIMEOUT_IN_SECS);
	}

	public void verifyPhoneIntl(String text) {
		ActionByName.assertEqualsText(driver, "phoneInternational", text, TIMEOUT_IN_SECS);
	}

	public void setEmail(String email) {
		clearEmail();
		ActionByName.sendKeys(driver, "email", email, TIMEOUT_IN_SECS);
	}

	public void verifyEmail(String text) {
		ActionByName.assertEqualsText(driver, "email", text, TIMEOUT_IN_SECS);
	}

	public void setFax(String fax) {
		clearFax();
		ActionByName.sendKeys(driver, "fax", fax, TIMEOUT_IN_SECS);
	}

	public void verifyFax(String text) {
		ActionByName.assertEqualsText(driver, "fax", text, TIMEOUT_IN_SECS);
	}

	public void selectRole(String input) {
		ActionByName.selectByText(driver, "role", "Negotiator - " + input, TIMEOUT_IN_SECS);
	}

	public void verifyRole(String text) {
		ActionByName.assertEqualsText(driver, "role", text, TIMEOUT_IN_SECS);
	}

	public void clearName() {
		ActionByName.clear(driver, "name", TIMEOUT_IN_SECS);
	}

	public void clearTitle() {
		ActionByName.clear(driver, "title", TIMEOUT_IN_SECS);
	}

	public void clearPhone() {
		ActionByName.clear(driver, "phone", TIMEOUT_IN_SECS);
	}

	public void clearEmail() {
		ActionByName.clear(driver, "email", TIMEOUT_IN_SECS);
	}

	public void clearFax() {
		ActionByName.clear(driver, "fax", TIMEOUT_IN_SECS);
	}

	public void submitBtn() {
		try {
			SeleniumHelper.scrollDown(driver, 4000);
			Octo_SeleniumLibrary.clickElement(driver, By.name( "processAction"));
		} catch (TimeoutException e) {
			// for use in admin mod
			Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@value,'Add This Negotiator')]"));
		}
	}

	public void addNewNegotiator() {
		Octo_SeleniumLibrary.clickElement(driver, By.name( "addNewNegotiator"));
	}

	public void complete() {
		Octo_SeleniumLibrary.clickElement(driver, By.name( "saveAndContinue"));
	}

	/**
	 * This method populates all fields in UI with values provided in data file
	 * "Negotiator": [ {} ] The Negotiator node can have multiple array elements.
	 * But single element is passed to this method.
	 * 
	 * @param array
	 */
	public void populateForm(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			if (ActionByName.isDisplayed(driver, "addNewNegotiator", TIMEOUT_IN_SECS)) {
				addNewNegotiator();
			}
			JsonElement ele = (JsonElement) iterator.next();
			JsonObject negObj = ele.getAsJsonObject();
			for (Map.Entry<String, JsonElement> element : negObj.entrySet()) {
				String elementName = element.getKey();
				String elementValue = element.getValue().getAsString();
				switch (elementName) {
				case "name":
					setName(elementValue);
					break;
				case "title":
					setTitle(elementValue);
					break;
				case "phone":
					setPhone(elementValue);
					break;
				case "email":
					setEmail(elementValue);
					break;
				case "fax":
					setFax(elementValue);
					break;
				case "phoneIntl":
					setPhoneIntl(elementValue);
					break;
				case "role":
					selectRole(elementValue);
					break;
				default:
					break;
				}
			}
			submitBtn();
		}
	}

	/**
	 * Method to edit a list of negotiators Works for 1 negotiator, need to check
	 * for multiple negotiators added
	 * 
	 * @param editArray
	 */
	public void editNegotiator(JsonArray editArray) {
		Iterator<JsonElement> iterator = editArray.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			WebElement row = getRowByName(jsonObj.get("prevName").getAsString());
			WebElement editElement = row.findElement(By.xpath("//td/a[text()='Edit']"));
			if (editElement != null) {
				editElement.click();
				populateForm(editArray);
			}
		}
	}

	/**
	 * Delete a specific negotiator by name
	 * 
	 * @param negotiatorName
	 */
	public void deleteNegotiatorByName(String negotiatorName) {
		WebElement row = getRowByName(negotiatorName);
		WebElement element = row.findElement(By.xpath("//td/a[text()='Delete']"));
		if (element != null) {
			element.click();
			GenericDialogPage page = new GenericDialogPage(driver);
			page.clickConfirmYes();
		}
	}

	/**
	 * Delete all negotiator specified in the data file
	 */
	public void deleteNegotiator() {
		WebElement table = ActionById.getElement(driver, "negInfo", TIMEOUT_IN_SECS);
		int rowSize = TableElementUtils.getTableRowCount(table);
		GenericDialogPage page = new GenericDialogPage(driver);
		for (int i = 0; i < rowSize; i++) {
			WebElement row = TableElementUtils.getNthTableRow(table, 0);
			WebElement element = row.findElement(By.xpath("//td/a[text()='Delete']"));
			if (element != null) {
				element.click();
				page.clickConfirmYes();
				if (isLoaded()) {
					table = ActionById.getElement(driver, "negInfo", TIMEOUT_IN_SECS);
				}
			}
		}
	}

	public void verifyNegotiatorText() {
		WebElement caption = ActionByTagName.getElement(driver, "caption", TIMEOUT_IN_SECS);
		String text = ActionByWebElement.getText(driver,
				ActionByTagName.getElementInElement(driver, caption, "p", TIMEOUT_IN_SECS), TIMEOUT_IN_SECS);
		Assert.assertTrue(text.contains(
				"Offerors can add multiple negotiators. A minimum of one negotiator must have signature authority, "
						+ "but it is STRONGLY recommended to have at least two valid Negotiators. "
						+ "Negotiators without signature authority may prepare the eOffer but only negotiators with signature authority may submit the eOffer and sign the Contract."));
		Assert.assertTrue(text.contains(
				"Negotiators name and email should be exactly the same as in the digital certificate of that user."));
	}

	public void verifyNegotiatorLabel() {
		WebElement table = ActionById.getElement(driver, "table3", TIMEOUT_IN_SECS);
		List<WebElement> header = ActionByTagName.getElementsInElement(driver, table, "th", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, header, 0, "*Name:", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, header, 1, "*Title:", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, header, 2, "*Phone:", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, header, 3, "If US (XXX-XXX-XXXX):", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, header, 4, "If International (free form text):",
				TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, header, 5, "*Email:", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, header, 6, "Fax:", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, header, 7, "If US (XXX-XXX-XXXX), ", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, header, 7, "If International (free form text):",
				TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, header, 8, "Role:", TIMEOUT_IN_SECS);

		Assert.assertTrue(ActionByName.getElement(driver, "processAction", TIMEOUT_IN_SECS).getAttribute("value")
				.contains("Add This Negotiator"));
	}

	public void verifyNegotiatorAndRole(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			Assert.assertTrue("Failed to find negotiator", isNegotiatorPresent(jsonObj));
		}
	}

	public boolean isNegotiatorPresent(JsonObject jsonObj) {
		String name = jsonObj.get("name").getAsString();
		String role = jsonObj.get("role").getAsString();
		log.info("Looking for Negotiator- " + name + " with role- " + role);
		WebElement row = getRowByName(name);
		if (row == null)
			return false;
		else
			return row.getText().contains(role);
	}

	private WebElement getRowByName(String negotiatorName) {
		WebElement table = ActionById.getElement(driver, "negInfo", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "Name", negotiatorName);
		return row;
	}

	/**
	 * Verifies negotiator entries in the table "Your Negotiator(s) Details" that
	 * opens for "Authorized Negotiator" admin mod.
	 * 
	 * @param negotiator
	 * @return
	 */
	public boolean isNegotiatorAddedToMod(JsonObject negotiator) {
		String name = negotiator.get("name").getAsString();
		String role = negotiator.get("role").getAsString();
		log.info("Looking for Negotiator: " + name + " with role: " + role);
		WebElement row = getRowByNameFromModTable(name);
		if (row == null)
			return false;
		else
			return row.getText().contains(role);
	}

	/**
	 * Get row from table "Your Negotiator(s) Details" found in Negotiator page for
	 * "Authorized Negotiator" admin mod.
	 * 
	 * @param negotiatorName
	 * @return row for desired negotiator name
	 */
	private WebElement getRowByNameFromModTable(String negotiatorName) {
		WebElement table = ActionById.getElement(driver, "negInfo", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "Name", negotiatorName);
		return row;
	}

}