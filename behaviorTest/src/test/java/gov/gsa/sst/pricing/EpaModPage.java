package gov.gsa.sst.pricing;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.solicitationclause.CommercialSalesPracticePage;
import util.ActionByLocator;
import util.ActionByName;


public class EpaModPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private By saveAndContinueBtn = By.name("saveAndContinue");
	private static Logger log = LoggerFactory.getLogger(EpaModPage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'ECONOMIC PRICE ADJUSTMENTS')]");
	private ExecutionContext executionContext;
	
	public EpaModPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}
	
	@Override
	protected boolean isLoaded() {
		return ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Pricing page for EPA mod");
			LeftNavigationMenu.navigateTo(driver, "Pricing"); 
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Pricing"); 
		}
		try {
			executionContext.testPageFor508("EPA mod");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Could not load Pricing page for EPA mod", isLoaded());
	}

	public void populateForm(JsonObject jsonObj) {
		log.info("Populating details for EPA mod");
		//TODO save sIN
		
		ActionByName.sendKeys(driver, "percentage", jsonObj.get("percentage").getAsString(), TIMEOUT_IN_SECS);
		ActionByName.sendKeys(driver, "modDesc", jsonObj.get("modRequestDesc").getAsString(), TIMEOUT_IN_SECS);
		Octo_SeleniumLibrary.clickElement(driver, saveAndContinueBtn);
		String isCspRequired = jsonObj.get("reviseCSP").getAsString();
		CommonUtilPage.selectRadioOption(driver, "cspRequired", jsonObj.get("reviseCSP").getAsString());
		if(isCspRequired.equalsIgnoreCase("Yes")){
			CommercialSalesPracticePage cspPage = new CommercialSalesPracticePage(executionContext);
			JsonObject cspObj = executionContext.getCurrentScenarioObj().getAsJsonObject("commercialSalesPractice");
			cspPage.populateForm(cspObj);
			//TODO
		}
	}
}
