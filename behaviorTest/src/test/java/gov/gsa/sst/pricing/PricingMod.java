package gov.gsa.sst.pricing;


import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import gov.gsa.sst.common.LeftNavigationMenu;

@Component
public class PricingMod extends Steps{
	private static Logger log = LoggerFactory.getLogger(PricingMod.class);
	PricingModPage pricingPage;

	private void init(){
		if(pricingPage == null) {
			pricingPage = new PricingModPage(executionContext);
		}
		pricingPage.get();
	}

	@Given("I am on the Pricing eMod \"([^\"]*)\" page")
	public void  i_am_on_the_Pricing_eMod_page(String modType){
		log.info("Open Pricing eMod page for sub-type '" + modType + "'");
		init();
	}
	
	@When("^I complete Temporary Price Reduction details successfully$")
	public void i_complete_pricing_mod_details_successfully(){
		pricingPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices"));
	}
	
	@When("^I complete Permanent Price Reduction-MFC details successfully$")
	public void i_complete_permanent_price_reduction_details_successfully(){
		i_complete_pricing_mod_details_successfully();
	}
	
	@When("^I complete Permanent Price Reduction-IPR details successfully$")
	public void i_complete_permanent_price_reduction_ipr_details_successfully(){
		i_complete_pricing_mod_details_successfully();
	}
	
	@When("^I complete Baseline Pricing details successfully$")
	public void i_complete_baseline_pricing_details_successfully(){
		pricingPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices"));
	}
	
	@When("^I complete EPA with CPL details successfully$")
	public void i_complete_epa_with_cpl_details_successfully(){
		pricingPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices"));
	}
	
	@When("^I complete EPA without CPL details successfully$")
	public void i_complete_epa_without_cpl_details_successfully(){
		pricingPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices"));
	}
	
	@When("^I complete Wage Determinations details successfully$")
	public void i_complete_wage_determinations_details_successfully(){
		i_complete_pricing_mod_details_successfully();
	}
	
	@When("^I add Admin POC details$")
	public void i_add_admin_poc_details(){
		JsonArray arrayDataToVerify = null;
		if (executionContext.getCurrentScenarioObj().has("verifyPOC"))
			arrayDataToVerify =executionContext.getCurrentScenarioObj().getAsJsonArray("verifyPOC");
		pricingPage.completeAdminPoc(executionContext.getCurrentScenarioObj().getAsJsonArray("pointOfContact"), arrayDataToVerify);
	}

	@When("^I complete Manage Discounts details successfully$")
	public void i_complete_Manage_Discounts_details_successfully() {
	    init();
	    pricingPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices"));
	    Assert.assertTrue("'Manage Discounts' page details are not complete", LeftNavigationMenu.isStepComplete(executionContext.getDriver(), "Pricing", 10));
	}

}
