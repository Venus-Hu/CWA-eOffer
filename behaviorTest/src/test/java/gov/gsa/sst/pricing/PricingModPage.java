package gov.gsa.sst.pricing;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import comment.ExecutionContext;

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.mod.SelectSins;
import gov.gsa.sst.solicitationclause.PointOfContactPage;
import util.ActionByXpath;

public class PricingModPage extends SelectSins {

	private static Logger log = LoggerFactory.getLogger(PricingModPage.class);
	//private String headerText = "//h1[contains(text(), '#MOD_NAME')]";

	public PricingModPage(ExecutionContext executionContext) {
		super(executionContext);
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {		
		String modType = executionContext.getCurrentScenarioObj().get("modTypes").getAsJsonArray().get(0).getAsString();
		log.info("Loading Pricing page for "+ modType +" mod");
		/*switch (modType) {
		case "Temporary Price Reduction":
			return getHeader().contains(modType.toLowerCase());
		case "Permanent Price Reduction (Based on Most Favored Customer)":
			return ActionByLocator.isDisplayed(driver, By.xpath(headerText.replace("#MOD_NAME", "PERMANENT PRICE REDUCTION (BASED ON MOST FAVORED CUSTOMER)")), TIMEOUT_IN_SECS);
		case "Permanent Price Reduction (Industry Partner requested)":
			return ActionByLocator.isDisplayed(driver, By.xpath(headerText.replace("#MOD_NAME", "PERMANENT PRICE REDUCTION (INDUSTRY PARTNER REQUESTED)")), TIMEOUT_IN_SECS);
		case "Capture Formatted Pricing":
			return ActionByLocator.isDisplayed(driver, By.xpath(headerText.replace("#MOD_NAME", "BASELINE")), TIMEOUT_IN_SECS);
		case "Economic Price Adjustments (EPA) with Commercial Price List (increase)":
			return ActionByLocator.isDisplayed(driver, By.xpath(headerText.replace("#MOD_NAME", "ECONOMIC PRICE ADJUSTMENTS (EPA) WITH COMMERCIAL PRICE LIST")), TIMEOUT_IN_SECS);
		default:
			break;
		}*/
		if(modType.equalsIgnoreCase("Capture Formatted Pricing"))
			return getHeader().contains("baseline");
		else if(modType.contains("Economic Price Adjustments"))
			return modType.toLowerCase().contains(getHeader());
		else
			return getHeader().contains(modType.toLowerCase());
	}
	
	private String getHeader(){
		String headerText = ActionByXpath.getText(driver, "//h1", 2).toLowerCase();
		log.info("Header on UI: " + headerText);
		return headerText;
	}

	@Override
	protected void load() {
		try {
			
			LeftNavigationMenu.navigateTo(driver, "Pricing"); 
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Pricing"); 
		}
		try {
			executionContext.testPageFor508("Pricing mod");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Could not load Pricing page for mod", isLoaded());
	}

	/**
	 * This method is required for Baseline mod
	 * @param array
	 */
	public void completeAdminPoc(JsonArray array, JsonArray dataToVerify){
		log.info("Adding data for Admin POC");
		LeftNavigationMenu.navigateTo(driver, "Administrative");
		
		PointOfContactPage pocPage = new PointOfContactPage(executionContext);
		pocPage.populateForm(array);
		if(dataToVerify != null)
			pocPage.verifyPocDetails(dataToVerify);
	}
}
