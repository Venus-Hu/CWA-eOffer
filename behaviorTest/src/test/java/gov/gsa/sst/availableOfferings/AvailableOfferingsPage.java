package gov.gsa.sst.availableOfferings;

import java.util.Iterator;
import java.util.List;
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
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.goodsservices.AddSinPage;
import gov.gsa.sst.goodsservices.GoodsAndServicesPage;
import gov.gsa.sst.util.exceptions.TestExecutionException;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByXpath;
import util.TableElementUtils;

public class AvailableOfferingsPage extends Page {

	private final int TIMEOUT_IN_SECS = 5;
	private static Logger log = LoggerFactory.getLogger(GoodsAndServicesPage.class);
	private String editPreponderanceOfWorkBtn = "idPreponEdit";
	private By headerText = By.xpath("//h1[contains(text(), 'AVAILABLE OFFERINGS')]");
	private ExecutionContext executionContext;
	private AddSinPage sinDetailsPage;

	public AvailableOfferingsPage(ExecutionContext executionContext) {
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
			log.info("Loading Available Offerings page");
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number specified in data does not match the one on UI");
			LeftNavigationMenu.navigateTo(driver, "Available Offerings");
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

	public void clickAddSinDetailsButton() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "idAddSin"));
		Octo_SeleniumLibrary.clickElement(driver, By.id(""));
	}

	public void editPreponderanceOfWorkBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( editPreponderanceOfWorkBtn));
	}

	public void clickSaveAndContinueButton() {
		System.out.println("************* Save offering**");
		Octo_SeleniumLibrary.clickElement(driver, By.name( "saveAndContinue"));
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
			WebElement sinRow = TableElementUtils.getTableRowByCellValue(offersTable, "Number", sinName);
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
			String sinName = (sinObj.get("sin").getAsString().split(" ")[0]).trim();
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
	 * Will return TRUE if element is NOT exist in the page
	 */
	public boolean verifySinDoesNotExist() {
		// TODO check for specific SIN
		return !ActionByXpath.isVisible(driver, "//a[contains(text(),'Delete')]", TIMEOUT_IN_SECS);

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
			String sinName = sinObj.get("sin").getAsString().split(" ")[0].trim();
			WebElement sinRow = getRowBySinName(sinName);
			sinRow.findElement(By.xpath("//a[contains(@title,'Edit')]")).click();
			sinObj.remove("sin");
			sinDetailsPage.populateForm(sinObj);
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
			String sinName = sinObj.get("sin").getAsString().split(" ")[0].trim();
			WebElement sinRow = getRowBySinName(sinName);
			sinRow.findElement(By.xpath(".//a[contains(@title,'Delete')]")).click();
			GenericDialogPage deletePage = new GenericDialogPage(driver);
			deletePage.clickConfirmYes();
		}
	}

	public void deleteAllSin() {
		List<WebElement> addSins = ActionByXpath.getElements(driver, "//a[contains(text(),'Delete')]", TIMEOUT_IN_SECS);
		for (int i = 0; i < addSins.size(); i++) {
			try {
				Octo_SeleniumLibrary.clickElement(driver, By.xpath("(//a[contains(text(),'Delete')])[1]"));
				GenericDialogPage deletePage = new GenericDialogPage(driver);
				deletePage.clickConfirmYes();
			} catch (Exception e) {
				log.info("No SINs present for deletion");
			}
		}
	}

	public void selectNaicsCodeByText(String naicsCode) {
		ActionByName.selectByText(driver, "PreponderanceOfWork", naicsCode, TIMEOUT_IN_SECS);
	}

	/**
	 * Add NAICS code with category and sub category for preponderance work
	 * 
	 * @param preponderanceObj Pass "preponderanceObj" json object
	 */
	public void selectPreponderance(JsonObject preponderanceObj) {
		for (Map.Entry<String, JsonElement> element : preponderanceObj.entrySet()) {
			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			switch (elementName) {
			case "category":
				ActionByName.selectByText(driver, "selectedLargeCategory", elementValue, TIMEOUT_IN_SECS);
				break;
			case "subCategory":
				ActionByName.selectByText(driver, "selectedSubcategory", elementValue, TIMEOUT_IN_SECS);
				break;
			case "sin":
				ActionByName.selectByText(driver, "selectedSin", elementValue, TIMEOUT_IN_SECS);
				break;
			case "preponderanceNaicsCode":
				ActionById.selectByText(driver, "preponderanceOfWorkId", elementValue, TIMEOUT_IN_SECS);
				break;
			default:
				// Do nothing
				break;
			}
		}
//		Octo_SeleniumLibrary.clickElement(driver, By.id( "idPreponAdd", TIMEOUT_IN_SECS);
		Octo_SeleniumLibrary.clickElement(driver, By.id( "idPreponAdd"));
	}

	public void populateForm(JsonObject jsonObj) {
		// Perform Add SIN operation
		JsonArray array = jsonObj.getAsJsonArray("sinDetails");
		Iterator<JsonElement> iterator = array.iterator();

		/*naicsCode = ActionByXpath.getText(driver, "//*[@name='editForm']/p", TIMEOUT_IN_SECS);
		naicsCode = naicsCode.split(" ")[0];*/
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			sinDetailsPage.populateForm(sinObj);
		}
	}

}
