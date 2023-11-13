package gov.gsa.sst.solicitationclause;

import java.util.Iterator;
import java.util.List;

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
import util.ActionByLocator;
import util.ActionByPartialLinkText;
import util.ActionByTagName;
import util.ActionByWebElement;
import util.ActionByWebElements;
import util.ActionByXpath;
import util.TableElementUtils;

/**
 * @author amulay
 */
public class PointOfContactPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(PointOfContactPage.class);

	private By headerText = By.xpath("//h1[contains(text(), 'POINT OF CONTACT INFORMATION')]");
	private ExecutionContext executionContext;

	public PointOfContactPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() throws Error {
		if (ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS))
			return CommonUtilPage.isSolicitationApt(executionContext);
		else
			return false;
	}

	@Override
	protected void load() {
		log.info("Loading POC page");
		try {
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number/user specified in data does not match the one on UI");

			LeftNavigationMenu.navigateTo(driver, "Solicitation Clauses");
			SolicitationClausePage solPage = new SolicitationClausePage(executionContext);
			solPage.selectSolicitationClause("Point of Contacts Information");
			log.info("Loading Solicitation clauses - POC page");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Solicitation Clauses");
			SolicitationClausePage solPage = new SolicitationClausePage(executionContext);
			solPage.selectSolicitationClause("Point of Contacts Information");
		}
		try {
			executionContext.testPageFor508("POC");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("POC page is not loaded", isLoaded());
	}

	public void selectContactType(String input) {
		ActionByXpath.selectByText(driver, "//select[@id ='pointofContact']", input, TIMEOUT_IN_SECS);
	}

	public void verifyContactType(String text) {
		ActionById.assertEqualsText(driver, "pointofContact", text, TIMEOUT_IN_SECS);
	}

	public void addPocButton() {
		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//*[@value='Add Point of Contact Details']"));
	}

	public WebElement getRowByName(String input) {
		WebElement table = ActionById.getElement(driver, "yourPocTable", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "Name", input);
		return row;
	}

	public WebElement getRowByNameAndDescription(String name, String description) {
		WebElement table = ActionById.getElement(driver, "yourPocTable", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValues(table, "Name", name, "Description", description);
		return row;
	}

	/**
	 * Add data for POC
	 * 
	 * @param array
	 */
	public void populateForm(JsonArray array) {

		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			selectContactType(jsonObj.get("poc").getAsString());
			addPocButton();
			PocDetailsPage detailsPage = new PocDetailsPage(executionContext);
			detailsPage.populateForm(jsonObj);
		}
	}

	/**
	 * Edit POC data
	 * 
	 * @param array
	 */
	public void updateForm(JsonArray array) {

		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			PocDetailsPage detailsPage = new PocDetailsPage(executionContext);
			detailsPage.populateForm(jsonObj);
		}
	}

	public void editByPOCName(String input) {
		ActionByPartialLinkText.getElementInElement(driver, getRowByName(input), "Edit", TIMEOUT_IN_SECS).click();
	}

	public void editByPOCNameAndDescription(String name, String description) {
		ActionByPartialLinkText
				.getElementInElement(driver, getRowByNameAndDescription(name, description), "Edit", TIMEOUT_IN_SECS)
				.click();
	}

	public void editPoc(JsonArray editArray) {
		Iterator<JsonElement> iterator = editArray.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			editByPOCName(jsonObj.get("prevName").getAsString());
			updateForm(editArray);
		}
	}

	/**
	 * This method will verify the poc details
	 * 
	 * @param dataArray
	 */
	public void verifyPocDetails(JsonArray dataArray) {
		Iterator<JsonElement> iterator = dataArray.iterator();
		while (iterator.hasNext()) {
			JsonElement dataElement = (JsonElement) iterator.next();
			JsonObject jsonObj = dataElement.getAsJsonObject();
			String pocName = jsonObj.get("name").getAsString();
			log.info("Verifying POC: " + pocName);
			editByPOCName(jsonObj.get("name").getAsString());
			PocDetailsPage page = new PocDetailsPage(executionContext);
			page.verifyPOC(jsonObj);
		}
	}

	public void deleteByPOCName(String input) {
		ActionByPartialLinkText.getElementInElement(driver, getRowByName(input), "Delete", TIMEOUT_IN_SECS).click();

	}

	public void deleteByPOCNameAndDescription(String name, String description) {
		ActionByPartialLinkText
				.getElementInElement(driver, getRowByNameAndDescription(name, description), "Delete", TIMEOUT_IN_SECS)
				.click();
	}

	/**
	 * Delete all POC mentioned in the data array
	 * 
	 * @param array
	 */
	public void deletePOC(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		GenericDialogPage page = new GenericDialogPage(driver);
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			String name = jsonObj.get("name").getAsString();
			String desc = jsonObj.get("poc").getAsString();
			deleteByPOCNameAndDescription(name, desc);
			page.clickConfirmYes();
		}
	}

	/**
	 * Delete all POCs in the application
	 */
	public void deletePOC() {
		WebElement table = ActionById.getElement(driver, "yourPocTable", TIMEOUT_IN_SECS); // replace table name after
																							// karens changes
		int rowSize = TableElementUtils.getTableRowCount(table);
		GenericDialogPage page = new GenericDialogPage(driver);
		for (int i = 0; i < rowSize; i++) {
			WebElement row = TableElementUtils.getNthTableRow(table, 1);
			if (!row.getText().contains("No information entered in this section.")) {
				WebElement element = row.findElement(By.xpath("//td/a[text()='Delete']"));
				if (element != null) {
					element.click();
					page.clickConfirmYes();
					if (isLoaded()) {
						table = ActionById.getElement(driver, "yourPocTable", TIMEOUT_IN_SECS); // replace table name
																								// after karens changes
					}
				}
			} else
				break;
		}
	}

	public boolean isPOCPresent(String name) {
		WebElement row = getRowByName(name);
		if (row != null) {
			return true;
		}
		return false;
	}

	public boolean isPOCPresent(String name, String description) {
		WebElement row = getRowByNameAndDescription(name, description);
		if (row != null) {
			return true;
		}
		return false;
	}

	/**
	 * Check if all the POC mentioned in the data file exists in the application
	 * 
	 * @param array
	 */
	public void verifyPocExists(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			String name = jsonObj.get("name").getAsString();
			String desc = jsonObj.get("poc").getAsString();
			verifyPOCPresent(name, desc);
		}
	}

	public void verifyPOCPresent(String name) {
		Assert.assertTrue("POC with name " + name + " not found.", isPOCPresent(name));
	}

	public void verifyPOCPresent(String name, String description) {
		Assert.assertTrue("POC with name " + name + " and description " + description + " not found.",
				isPOCPresent(name, description));
	}

	// DLR 2/15/17 Not sure if I parsed table correctly
	public void verifyPOCText() {

		WebElement caption = ActionByTagName.getElement(driver, "caption", TIMEOUT_IN_SECS);
		String text = ActionByWebElement.getText(driver,
				ActionByTagName.getElementInElement(driver, caption, "p", TIMEOUT_IN_SECS), TIMEOUT_IN_SECS);
		Assert.assertTrue(text.contains("You can add multiple Point of Contact Information but one at a time."));

		WebElement table = ActionByTagName.getElement(driver, "table", TIMEOUT_IN_SECS);
		List<WebElement> list = ActionByTagName.getElementsInElement(driver, table, "ul", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, list, 0,
				"At least one Contract Admin POC is required (Domestic or Overseas)", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, list, 1,
				"At least one Industrial Funding Fee POC is required", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, list, 2, "At least one Order POC is required",
				TIMEOUT_IN_SECS);
	}

	public void verifyPocDoesNotExist(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			String name = jsonObj.get("name").getAsString();
			Assert.assertFalse("POC with name " + name + " exists.", isPOCPresent(name));
		}
	}
	
	public void updateTemplate (String action) {
		switch(action) {
		case "Delete":
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//a[@title='Delete Point of Contacts Information']"));
			break;
		case "Edit":
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//a[@title='Edit Point of Contacts Information']"));
			break;
		default:
			break;
		}
	}

}
