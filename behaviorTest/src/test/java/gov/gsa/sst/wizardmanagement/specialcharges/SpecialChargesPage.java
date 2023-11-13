package gov.gsa.sst.wizardmanagement.specialcharges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.wizardmanagement.WizardManagementPage;
import util.ActionById;
import util.ActionByLocator;
import util.PageLoad;
import util.TableElementUtils;

public class SpecialChargesPage extends Page {
	private static Logger log = LoggerFactory.getLogger(SpecialChargesPage.class);
	private ExecutionContext executionContext;
	private By headerText = By.xpath("//h1[text()='Special Charges']");
	private final int TIMEOUT_IN_SECS = 10;
	private By viewWizardSummaryLink = By.partialLinkText("View Wizard Summary");
	private By checkbox = By.xpath("//input[@type='checkbox']");
	private String tableElementId = "specialChargesListings";

	public SpecialChargesPage(WebDriver driver) {
		super(driver);
	}
	
	public SpecialChargesPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		log.info("Loading 'Special Charges' page");
		try {
			LeftNavigationMenu.navigateTo(driver, "Special Charges");
			log.info("'Special Charges' page is loaded");
		} catch (Exception e) {
			WizardManagementPage wizardManagent = new WizardManagementPage(executionContext);
			wizardManagent.get();
			
			LeftNavigationMenu.navigateTo(driver, "Special Charges", TIMEOUT_IN_SECS);
		}
		try {
			executionContext.testPageFor508("Special Charges");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("'Special Charges' page is not loaded", isLoaded());
	}

	public void setSin(String sin) {
		ActionById.selectByText(driver, "sinNum", sin, TIMEOUT_IN_SECS);
		//Do not click on Select as it will change the selected  SIN to first SIN in
		// the drop down list
		//clickSelectBtn();
	}

	public void clickSelectBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "btnSelect"));
	}

	public void clickViewWizSummaryLink() {
		Octo_SeleniumLibrary.clickElement(driver, viewWizardSummaryLink);
	}

	public void addUpdateSpecialChargesBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "btnSave"));
	}

	public void clickContinueBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "btnSaveAndcontinue"));
	}

	/**
	 * This method will select all the charge checkboxes for the predefined data
	 * 
	 * @param charges JSON array of element ID's corresponding to a charge
	 */
	public void selectSpecialCharges(JsonArray charges) {
		charges.forEach((charge) -> {
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//label[contains(text(),'"+charge.getAsString()+"')]"));
		});
	}
	
	/**
	 * This method will add special charges for a selected SIN,
	 * for example:
	 * 
	 * "specialCharges": [
	 *	{
	 *		"sin": "20 130",
	 *		"charges": [
	 *			"Installation"
	 *		]
	 *	},
	 *	{
	 *		"sin": "19 120",
	 *		"charges": [
	 *			"One Day Service",
	 *			"Saturday Delivery"
	 *		]
	 *	}
	 * ]
	 * @param array JSON array of special charges
	 */
	public void populateForm(JsonArray array){
		//Perform Add special charges to SIN operation
		  Iterator<JsonElement> iterator=array.iterator();
		  while (iterator.hasNext()) {
		    JsonElement element = (JsonElement)iterator.next();
		    JsonObject jsonObj = element.getAsJsonObject();
		    
		    setSin(jsonObj.get("sin").getAsString());
		    clearCheckBoxes();
		    JsonArray charges = jsonObj.get("charges").getAsJsonArray();
			selectSpecialCharges(charges);
			addUpdateSpecialChargesBtn();
			// Wait for page ready state to be complete
			// to avoid SINS not saved properly
			PageLoad.checkPageLoad(driver, TIMEOUT_IN_SECS);
		  }
		  
		  // Added to handle a PCU mod (e2e scenario 13) where it is necessary
		  // to navigate to this page without changing any items on page but
		  // "continue" button must be clicked for Wizard Management to become "green"
		  if (array.size() == 0 && ActionById.isDisplayed(driver, "btnSaveAndcontinue", TIMEOUT_IN_SECS)) {
			  Octo_SeleniumLibrary.clickElement(driver, By.id( "btnSaveAndcontinue"));
		  }
	}

	public void clearCheckBoxes() {
		List<WebElement> checkboxes = ActionByLocator.getElements(driver, checkbox, TIMEOUT_IN_SECS);
		for (WebElement checkbox : checkboxes) {
			if(checkbox.isSelected())
				checkbox.click();
		}
	}

	public Map<String, List<String>> getSpecialChargesForSIN(JsonArray sinNumbers) {
		Map<String, List<String>> specialChargesMap = new HashMap<>();
		sinNumbers.forEach((sinNumberElement) -> {
			JsonObject sinNumber = sinNumberElement.getAsJsonObject();
			specialChargesMap.put(sinNumber.get("sin").getAsString(), getSpecialChargesForSIN(sinNumber.get("sin").getAsString()));
		});
		return specialChargesMap;
	}
	
	public List<String> getSpecialChargesForSIN(String sinNumber) {
		List<String> specialCharges = new ArrayList<>();
		
		WebElement scRow = getRowBySinNumber(sinNumber);
		String[] chargesArray = scRow.findElement(By.xpath(".//td[2]")).getText().trim().split(",");
		specialCharges.addAll(Arrays.asList(chargesArray));
		specialCharges.replaceAll(String::trim);
		specialCharges.sort((c1,c2) -> c1.compareTo(c2));
		
		return specialCharges;
	}
	
	public void clickEditSpecialCharges(String sinNumber) {
		WebElement scRow = getRowBySinNumber(sinNumber);
		TableElementUtils.getLinkElementFromTableRow(scRow, "Edit");
	}

	private WebElement getRowBySinNumber(String sinNumber) {
		WebElement addedSpecialChargesTable = ActionById.getElement(driver, tableElementId, TIMEOUT_IN_SECS);
		WebElement scRow = TableElementUtils.getTableRowByCellValue(addedSpecialChargesTable, "SIN", sinNumber);
		return scRow;
	}

}
