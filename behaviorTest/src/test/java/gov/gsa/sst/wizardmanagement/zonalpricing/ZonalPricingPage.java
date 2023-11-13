package gov.gsa.sst.wizardmanagement.zonalpricing;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByTagName;
import util.ActionByWebElements;
import util.TableElementUtils;

/**
 * @author sjayatirtha
 *
 */
public class ZonalPricingPage extends Page {

	private final int TIMEOUT_IN_SECS = 30;
	
	private By viewWizSummaryLink = By.partialLinkText("View Wizard Summary");
	private static Logger log = LoggerFactory.getLogger(ZonalPricingPage.class);
	
	private By headerText = By.xpath("//h1[contains(text(), 'ZONAL PRICING')]");
	private By modheader = By.xpath("//h1[contains(text(), 'Zones List')]");
	private ExecutionContext executionContext;
	
	private By instructionsElement = By.xpath("//div[@id='collapseOne']");
	private String instructionLine1 = "Select Zone Number.";
	private String instructionLine2 = "Select CONUS or NON-CONUS. By selecting CONUS, you will be selecting all CONUS states. By selecting NON-CONUS, you will be selecting all NON-CONUS states/territories. If you are adding a custom zone, select all states/territories associated with the Zone Number.";
	private String instructionLine3 = "All states must be assigned to a zone.";
	private String instructionLine4 = "Click \"Add Zone\" to save your data.";
	
	public ZonalPricingPage(WebDriver driver) {
		super(driver);
	}
	
	public ZonalPricingPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		log.info("Loading Zonal Pricing page");
		try {
			// Need to use driver as ActionBy* calls do not throw exceptions
			LeftNavigationMenu.navigateTo(driver, "Zonal Pricing");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();
			
			LeftNavigationMenu.navigateTo(driver, "Zonal Pricing");
		}
		try {
			executionContext.testPageFor508("Zonal Pricing");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Zonal Pricing page is not loaded", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		boolean isReadOnlyPage =false;
		if( !CommonUtilPage.verifyPageHeader(executionContext, headerText, 3)){
			//Check if page has Zones List as header indicating its a read only page
			isReadOnlyPage = ActionByLocator.isDisplayed(driver, modheader, 1);
			log.info("Checking if read only Zonal Pricing page is Loaded: " + isReadOnlyPage);
			return isReadOnlyPage;
		}else
			return true;
	}

	public void clickCancelBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "cancelCreateZoneBtn"));
	}

	public void clickAddZoneBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "addZoneBtn"));
	}

	public void clickContinueBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "saveAndContinueToNextLevel"));
	}

	public void setZoneNumber(String zoneNumber) {
		ActionById.selectByText(driver, "zoneNumber", zoneNumber, TIMEOUT_IN_SECS);
	}
	
	public void clickViewWizSummaryLink() {
		Octo_SeleniumLibrary.clickElement(driver, viewWizSummaryLink);
	}
	
	public String getZoneNumber() {
		return ActionById.getText(driver, "zoneNumber", TIMEOUT_IN_SECS);
	}

	public void verifyZoneNumber(String zoneNumber) {
		ActionById.assertEqualsText(driver, "zoneNumber", zoneNumber, TIMEOUT_IN_SECS);
	}

	public void verifyZonalPricingInstructionText() {
		WebElement divElement = ActionByLocator.getElement(driver, instructionsElement, TIMEOUT_IN_SECS);
		List<WebElement> list = ActionByTagName.getElementsInElement(driver, divElement, "li", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, list, 0, instructionLine1, TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, list, 1, instructionLine2, TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, list, 2, instructionLine3, TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, list, 3, instructionLine4, TIMEOUT_IN_SECS);
	}
	
	/**
	 * Data should be like this 
	 * 
	 * "zoneNumber": "02",
	   "zonalArea": "NON-CONUS",
	   "states": "All"
	 *  
	 * OR
	 * 
	 * "zoneNumber": "01",
	   "zonalArea": "CONUS",
	   "states": "AL,AR,AZ,CA,CO,CT"
	 *            
	 */
	public void populateForm(JsonArray zonalArray) {
		if(!CommonUtilPage.verifyPageHeader(executionContext, modheader, TIMEOUT_IN_SECS)) {
			String chkBoxStateLocator = "";
			Iterator<JsonElement> iterator = zonalArray.iterator();
			while (iterator.hasNext()) {
				JsonElement element = (JsonElement) iterator.next();
				JsonObject jsonObj = element.getAsJsonObject();
				String zoneNumber = jsonObj.get("zoneNumber").getAsString();
				String zonalArea = jsonObj.get("zonalArea").getAsString();
				String states = jsonObj.get("states").getAsString();
				ActionById.selectByText(driver, "zoneNumber", zoneNumber, TIMEOUT_IN_SECS);
				
				// select different states
				if(!states.contains("All")){
					if (zonalArea.equalsIgnoreCase("conus")) {
						chkBoxStateLocator = "chkBoxStates";
					} else if (zonalArea.equalsIgnoreCase("nonconus")) {
						chkBoxStateLocator = "chkBoxCommonWealth";
					}
					List<String> statesList = Arrays.asList(states.split(","));
					
					for (int i = 0; i < statesList.size(); i++) {
						Octo_SeleniumLibrary.clickElement(driver, By.id(chkBoxStateLocator + statesList.get(i)));
					}
				}else{   // select all states for a zonal area 
					switch (zonalArea.toLowerCase()){
					case "conus":
						Octo_SeleniumLibrary.clickElement(driver, By.id( "zoneAreaConus"));
						break;
					case "non-conus":
						Octo_SeleniumLibrary.clickElement(driver, By.id( "zoneAreaNonConus"));
						break;
					}
				}
				clickAddZoneBtn();
			}
		}else
			log.info("The Zonal Pricing page is read only page for this mod!");
	}
	
	public void verifyAddedZones(JsonArray zonalArray){
			Iterator<JsonElement> iterator = zonalArray.iterator();
			while (iterator.hasNext()) {
				JsonElement element = (JsonElement) iterator.next();
				JsonObject jsonObj = element.getAsJsonObject();
				String zoneNumber = jsonObj.get("zoneNumber").getAsString();
				log.info("Finding the zone with zonal number " + zoneNumber);				
				WebElement row = getRowByZoneNumber(zoneNumber);
				Assert.assertTrue("Zone number " + zoneNumber + " was not added", (row != null));
			}
	}

	public WebElement getRowByZoneNumber(String zoneNumber) {
		WebElement div = ActionByLocator.getElement(driver, By.id("table2"), TIMEOUT_IN_SECS);
		WebElement table = ActionByTagName.getElementInElement(driver, div, "table", TIMEOUT_IN_SECS);
		WebElement zoneRow = TableElementUtils.getTableRowByCellValue(table, "Zone Number", zoneNumber);
		return zoneRow;
	}

	public void editAZone(String zoneNumber) {
	
		WebElement zoneRow = getRowByZoneNumber(zoneNumber);
		TableElementUtils.getLinkElementFromTableRow(zoneRow, "Edit");
	}

	public void deleteAZone(String zoneNumber) {
		WebElement zoneRow = getRowByZoneNumber(zoneNumber);
		TableElementUtils.getLinkElementFromTableRow(zoneRow, "Delete");
	}

}
