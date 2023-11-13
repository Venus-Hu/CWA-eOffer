package gov.gsa.sst.cancellation;

import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionByLocator;
import util.ActionByName;


public class CancelModPage extends Page {

	private static Logger log = LoggerFactory.getLogger(CancelModPage.class);
	private ExecutionContext executionContext;
	private final int TIMEOUT_IN_SECS = 2;
	private By headerText = By.xpath("//h1[contains(text(), 'Cancel Contract')]");
	

	public CancelModPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}
	
	@Override
	protected void load() {
		try {
			LeftNavigationMenu.navigateTo(driver, "Cancellation or Termination"); 
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Cancellation or Termination"); 
		}
		try {
			executionContext.testPageFor508("Cancellation mod");
		}catch (Exception e) {
			log.warn("Section 508 exception: " +e.getMessage());
		}
		Assert.assertTrue("Could not load Cancellations page for mod", isLoaded());

	}

	@Override
	protected boolean isLoaded() {
		return ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}

	/**
	 * Add data for Cancellation mod 
	 * @param array
	 */
	public void populateForm(JsonObject cancelObj) {
		String modType = cancelObj.get("clause").getAsString();
		if(executionContext.getCurrentScenarioObj().getAsJsonArray("modTypes").size() > 1) {
			 Octo_SeleniumLibrary.clickElement(driver, By.xpath("//a[@title='Respond for " + modType + "']"));
		}
		for (Map.Entry<String, JsonElement> element : cancelObj.entrySet()) {
		
			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			switch (elementName) {
			case "modDescription":
				ActionByName.clear(driver, "ModDetailDescription", TIMEOUT_IN_SECS);
            	ActionByName.sendKeys(driver, "ModDetailDescription", elementValue, TIMEOUT_IN_SECS);
				break;
			default:
				log.info("This element is not supported by populate Cancellation mod: " + elementName);
				break;
			}
		}
		 Octo_SeleniumLibrary.clickElement(driver, By.name( "saveAndContinue"));
	}
	
}
