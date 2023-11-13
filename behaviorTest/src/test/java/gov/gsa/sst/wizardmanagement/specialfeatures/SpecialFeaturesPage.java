package gov.gsa.sst.wizardmanagement.specialfeatures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import gov.gsa.sst.wizardmanagement.WizardManagementPage;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByXpath;
import util.PageUtil;

public class SpecialFeaturesPage extends Page{

	private static Logger log = LoggerFactory.getLogger(SpecialFeaturesPage.class);
	private ExecutionContext executionContext;
	private By headerText = By.xpath("//h1[text()='Special Features']");
	private By continueBtn = By.id("btnSaveAndcontinue");
	private By checkbox = By.xpath("//input[@type='checkbox']");
	private final int TIMEOUT_IN_SECS = 10;

	public SpecialFeaturesPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		log.info("Loading 'Special features' page");
		try {
			LeftNavigationMenu.navigateTo(driver, "Special Features", TIMEOUT_IN_SECS);
			log.info("'Special Features' page is loaded");
		} catch (Exception e) {
			WizardManagementPage wizardManagent = new WizardManagementPage(executionContext);
			wizardManagent.get();

			LeftNavigationMenu.navigateTo(driver, "Special Features", TIMEOUT_IN_SECS);
		}
		try {
			executionContext.testPageFor508("Special Features");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("'Special Features' page is not loaded", isLoaded());
	}

	public void setSin(String sin) {
		ActionByName.selectByText(driver, "sinNum", sin, TIMEOUT_IN_SECS);
		clickSelectBtn();
	}

	public void verifySin(String sin) {
		ActionById.assertEqualsText(driver, "sinNum", sin, TIMEOUT_IN_SECS);
	}
	
	public void clickSelectBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "btnSelect"));
	}
	
	public void clickContinueBtn() {
		Octo_SeleniumLibrary.clickElement(driver, continueBtn);
	}
	

	/**
	 * This method will select all the checkboxes for the predefined data
	 * @param specialFeatures
	 */
	public void selectFeatures(JsonArray specialFeatures) {
		specialFeatures.forEach((feature) -> {
			if(!feature.getAsString().equalsIgnoreCase("None")){
				log.info("Select " + feature.getAsString());
				Octo_SeleniumLibrary.clickElement(driver, By.xpath("//label[contains(text(),'" + feature.getAsString() + "')]/input"));
			}
		});
	}
	
	/**
	 * EPA feature items have links and we click on check boxes to select them 
	 * 
	 * @param specialFeatures
	 */
	public void selectEpaFeatures(JsonArray specialFeatures) {
		specialFeatures.forEach((feature) -> {
			if(!feature.getAsString().equalsIgnoreCase("None")){
				Octo_SeleniumLibrary.clickElement(driver, By.xpath("//a[contains(text(),'" + feature.getAsString() + "')]/preceding-sibling::input"));
			}
		});
	}
	
	
	public void addUpdateBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "btnSave"));
	}
	
	
	/**
	 * This method will add special features for a selected SIN
	 * 
	 *	"specialFeatures": [
	 *		{
	 *			"sin": "20 130",
	 *			"spFeatures": [
	 *			"Ergonomic item",
	 *			"Scan Code 2"
	 *			],
	 *			"envFeatures": [
	 *			"Green Seal item",
	 *			"GREENGUARD Certified",
	 *			"Biodegradable"
	 *			]
	 *		},
	 *		{
	 *			"sin": "19 120",
	 *			"spFeatures": [
	 *			"None"
	 *			],
	 *			"envFeatures": [
	 *			"None"
	 *			]
	 *		}
	 *	]
	 * @param array JSON array of special features
	 */
	public void populateForm(JsonArray array){
			log.info("Populating special features form...");
			  Iterator<JsonElement> iterator = array.iterator();
			  while (iterator.hasNext()) {
			    JsonElement element = (JsonElement)iterator.next();
			    JsonObject jsonObj = element.getAsJsonObject();
			    
			    setSin(jsonObj.get("sin").getAsString());
			    clearCheckBoxes();
			    JsonArray spFeatures = jsonObj.get("spFeatures").getAsJsonArray();
			    selectFeatures(spFeatures);
			 
			    JsonArray envFeatures = jsonObj.get("envFeatures").getAsJsonArray();
			    selectFeatures(envFeatures);
			    
			    if(ActionByXpath.isDisplayed(driver, "//fieldset[contains(.,'EPA RECOMMENDED FEATURES')]", TIMEOUT_IN_SECS)){
			    	if(jsonObj.has("epaFeatures")){
			    		JsonArray epaFeatures = jsonObj.get("epaFeatures").getAsJsonArray();
				    	selectEpaFeatures(epaFeatures);
			    	} else
			    		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[@id='Noneepa']"));
			    }
			    	
			    
			    Octo_SeleniumLibrary.clickElement(driver, By.name( "saveOptions"));
			    PageUtil.checkPageLoad(driver);
			}
	}
	
	public void clearCheckBoxes() {
		List<WebElement> checkboxes = ActionByLocator.getElements(driver, checkbox, TIMEOUT_IN_SECS);
		for (WebElement checkbox : checkboxes) {
			if(checkbox.isSelected())
				checkbox.click();
		}
		Octo_SeleniumLibrary.clickElement(driver, By.id( "Noneenv")); // selecting None as default after clearing the checkboxes
		Octo_SeleniumLibrary.clickElement(driver, By.id( "Nonespf"));
		Octo_SeleniumLibrary.clickElement(driver, By.id( "Noneepa"));
		
	}
	
	public void verifyFeatureLinks(JsonArray array) {
		
		log.info("Verifying epa special features form...");
		
		  Iterator<JsonElement> iterator = array.iterator();
		  while (iterator.hasNext()) {
		    JsonElement element = (JsonElement)iterator.next();
		    JsonObject jsonObj = element.getAsJsonObject();
		    JsonObject epaObj = jsonObj.getAsJsonObject("verifyEpaFeatures");
		    
		    Set<Entry<String, JsonElement>> set = epaObj.entrySet();
	        Iterator<Entry<String, JsonElement>> iter = set.iterator();
	        while (iter.hasNext()) {
	            Entry<String, JsonElement> dataElement = iter.next();
	            String elementName = dataElement.getKey();
	            JsonElement elementValue = dataElement.getValue();
	            
	     
            	Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//a[contains(text(),'"+ elementName + "')]"));
            	ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles()); driver.switchTo().window(tabs.get(1));
            	String link = driver.getCurrentUrl().toString();
            	Assert.assertTrue( link + " did not match expected value", elementValue.toString().contains(link));
            	driver.switchTo().window(tabs.get(0));
	            }
	        }
		  }
}
