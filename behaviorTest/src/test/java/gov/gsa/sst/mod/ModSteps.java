package gov.gsa.sst.mod;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.When;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.util.data.Offer;

@Component
public class ModSteps extends Steps {
	private static Logger log = LoggerFactory.getLogger(ModSteps.class);
	int TIMEOUT_IN_SEC = 3;
	
	@When("^I complete all Pricing data updates for FPT contract$")
	public void i_complete_all_Pricing_updates_for_FPT_contract() throws Exception {
	   CommonUtilPage.completeModPages(executionContext);
	}
	
	@When("^I complete all Product data updates for FPT contract$")
	public void i_complete_all_Product_data_updates_for_FPT_contract() throws Throwable {
		i_complete_all_Pricing_updates_for_FPT_contract();
	}
	
	@When("^I complete all data updates for non-FPT contract$")
	public void i_complete_all_updates_for_non_FPT_contract() throws Exception {
		i_complete_all_Pricing_updates_for_FPT_contract();
	}	

	@When("^I 'Validate Product Data' OR select 'Continue' option$")
	public void i_Validate_Product_Data_OR_select_Continue_option() throws Throwable {
		i_complete_all_Pricing_updates_for_FPT_contract();
	}
	
	@When("^I provide the valid pricing document template and Capture Product Data completes successfully$")
	public void I_provide_the_valid_pricing_document_template_and_Capture_Product_Data_completes_successfully() throws Throwable {
		i_complete_all_Pricing_updates_for_FPT_contract();
	}
	
	@When("^I complete all Wizard Mgmt sub pages$")
	public void I_complete_all_Wizard_Mgmt_sub_pages() throws Exception {
		new Offer.Builder(executionContext).completeWizardSubPages().build();
	}

	@When("^all menu items are complete for eMod$")
	public void all_menu_items_are_complete_for_eMod() throws MalformedURLException, InterruptedException, IOException {
	    Map<String, Boolean> results = LeftNavigationMenu.areStepsComplete(executionContext.getDriver(), executionContext.getCurrentScenarioObj().getAsJsonArray("completedMenuItems"));
	    results.forEach((menuItem, isComplete) -> {
	    	Assert.assertTrue("Menu item '"+menuItem+"' is not complete", isComplete);
	    });
	}
	
	@When("I update the Pricing data for any new Vol/Qty Discount details at Contract Level")
	public void i_update_the_pricing_data_for_any_new_vol_qty_discount_details_at_contract_level() throws Exception {
		log.info("Update pricing data for new Vol/Qty at contract level");
		i_complete_all_Pricing_updates_for_FPT_contract();
	}
	
	@When("I update the Pricing data for any new Vol/Qty Discount details at SIN Level")
	public void i_update_the_pricing_data_for_any_new_vol_qty_discount_details_at_sin_level() throws Exception {
		log.info("Update pricing data for new Vol/Qty at SIN level");
		i_complete_all_Pricing_updates_for_FPT_contract();
	}
	
	@When("I provide Dollar discount details by Line Item in the Capture Pricing Data template")
	public void i_provide_dollar_discount_details_by_line_item_in_the_capture_pricing_data_template() throws Exception {
		log.info("Update pricing data for Line Item dollar discount in the Capture Pricing Data template");
		i_complete_all_Pricing_updates_for_FPT_contract();
	}
	
	@When("I provide Vol/Qty discount details by Line Item in the Capture Pricing Data template")
	public void i_provide_vol_qty_discount_details_by_line_item_in_the_capture_pricing_data_template() throws Exception {
		log.info("Update pricing data for Line Item volume/quantity discount in the Capture Pricing Data template");
		i_complete_all_Pricing_updates_for_FPT_contract();
	}
}
