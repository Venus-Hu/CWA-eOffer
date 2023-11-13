package gov.gsa.sst.technical;

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
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.GenericDialogPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionByLinkText;
import util.ActionByName;
import util.ActionByXpath;
import util.TableElementUtils;


public class TechnicalModPage extends Page{
	
	private final int TIMEOUT_IN_SECS = 2;
	private static Logger log = LoggerFactory.getLogger(TechnicalModPage.class);
	private ExecutionContext executionContext;
	
	public TechnicalModPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}
	
	@Override
	protected void load() {
		try {
			log.info("Loading Technical Mod page");
			LeftNavigationMenu.navigateTo(driver, "Technical");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Technical");
		}
		try {
			executionContext.testPageFor508("Technical mod");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Could not load Technical page for mod", isLoaded());
	}
	
	@Override
	protected boolean isLoaded() {
		String modType = executionContext.getCurrentScenarioObj().getAsJsonArray("modTypes").get(0).getAsString();
		return ActionByXpath.isDisplayed(driver,"//h1[contains(text(), '" + modType + "')]", TIMEOUT_IN_SECS);
	}
	
	/**
	 * Respond to specific clause
	 * @param clause
	 */
	public void selectClause(String clause){
	if(executionContext.getCurrentScenarioObj().getAsJsonArray("modTypes").size() > 1)
		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//a[@title='Respond to Input']"));
	}
	
	/**
	 * Add SIN details for Technical mod- change Geographic coverage
	 * @param array
	 */
	public void addSinDetails(JsonArray array){
		log.info("Adding SIN details for mod");
		Iterator<JsonElement> iterator = array.iterator();
        while (iterator.hasNext()) {
            JsonElement element = (JsonElement) iterator.next();
            JsonObject jsonObj = element.getAsJsonObject();
            Set<Entry<String, JsonElement>> qcSet = jsonObj.entrySet();
            Iterator<Entry<String, JsonElement>> iter = qcSet.iterator();
            while (iter.hasNext()) {
                Entry<String, JsonElement> basicInfoElement = iter.next();
                String elementName = basicInfoElement.getKey();
                JsonElement elementValue = basicInfoElement.getValue();
                switch (elementName) {
				case "sin":
					ActionByName.selectByText(driver, "sintype",  elementValue.getAsString(), TIMEOUT_IN_SECS);
					Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[contains(@name,'sinType') and @type='Submit']"));
					break;
				case "scope":
					ActionByName.selectByText(driver, "scope", elementValue.getAsString(), TIMEOUT_IN_SECS);
					break;
				default:
					log.warn("Unexpected element found: " + elementName  );
					break;
				}
               
            }
            Octo_SeleniumLibrary.clickElement(driver, By.name( "saveSin"));
        }
	}
	
	public void populateForm(JsonObject jsonObj){
		String clauseName = jsonObj.get("clause").getAsString();
		selectClause(clauseName);
		
		if(jsonObj.has("sinDetails"))
			addSinDetails(jsonObj.get("sinDetails").getAsJsonArray());
		if(jsonObj.has("fastlane")) {
			updateFastlane(jsonObj.get("fastlane").getAsString());
		}
		Set<Entry<String, JsonElement>> qcSet = jsonObj.entrySet();
        Iterator<Entry<String, JsonElement>> iter = qcSet.iterator();
        while (iter.hasNext()) {
            Entry<String, JsonElement> basicInfoElement = iter.next();
            String elementName = basicInfoElement.getKey();
            JsonElement elementValue = basicInfoElement.getValue();
            switch (elementName) {
	            case "modDescription":
	            		ActionByName.clear(driver, "ModDetailDescription", TIMEOUT_IN_SECS);
	                	ActionByName.sendKeys(driver, "ModDetailDescription", elementValue.getAsString(), TIMEOUT_IN_SECS);
	                break;
	            case "sinDetails":
	            	//Do nothing - SINs taken care of already
	            	break;
                default:
                	log.info("This element is not supported by populate Technical mod: " + elementName);
	                break;
             }
        }
        Octo_SeleniumLibrary.clickElement(driver, By.name( "saveAndContinue"));
	}
	
	public void deleteSin(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
        while (iterator.hasNext()) {
            JsonElement element = (JsonElement) iterator.next();
            JsonObject jsonObj = element.getAsJsonObject();
            String sinName = jsonObj.get("sin").getAsString();
            log.info("Deleting SIN: " + sinName);
            clickActionForSin(sinName, "Delete");
            GenericDialogPage genericPage = new GenericDialogPage(driver);
            genericPage.clickConfirmYes();
        }
	}
	/**
	 * @param sinName
	 * @param actionName
	 *            - Please pass these values - Edit / Delete
	 */
	public void clickActionForSin(String sinName, String actionName) {
		ActionByLinkText.getElementInElement(driver, getRowBySinName(sinName), actionName, TIMEOUT_IN_SECS)
				.click();
	}
	
	/**
	 * Verify that SIN is missing from "Your SIN(s)" table
	 * 
	 * @param jsonArray
	 */
	public boolean doesSinExist(JsonObject sinObj) {
			String sinName = sinObj.get("sin").getAsString();
			WebElement sinRow = getRowBySinName(sinName);
			if (sinRow == null){
				log.info("Verified that the SIN: " + sinName + " is not found in the table.");
				return false;
			}
			else 
				return true;	
	}

	/**
	 * Retrieve the row element where the specified SIN exists
	 * 
	 * @param sinName
	 * @return
	 */
	public WebElement getRowBySinName(String sinName) {
		if( ActionByXpath.isDisplayed(driver, "//table[@id='yourSinList']", TIMEOUT_IN_SECS)) {
			WebElement offersTable = ActionByXpath.getElement(driver, "//table[@id='yourSinList']", TIMEOUT_IN_SECS);
			WebElement sinRow = TableElementUtils.getTableRowByCellValue(offersTable, "Name", sinName);
			return sinRow;
		} else
			 return null;
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
