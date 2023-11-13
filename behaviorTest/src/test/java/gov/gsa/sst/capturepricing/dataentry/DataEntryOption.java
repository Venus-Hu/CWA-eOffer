package gov.gsa.sst.capturepricing.dataentry;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.capturepricing.CaptureDataPage;
import gov.gsa.sst.commonpage.CommonUtilPage;

@Component
public class DataEntryOption extends Steps {
	DataEntryOptionPage page;

	public void init(){
		if(page == null)
			page = new DataEntryOptionPage(executionContext);
		page.get();
	}
	
	@Given("^I am on the Capture Pricing Data entry page for an FPT Schedule$")
	public void I_am_on_the_Capture_Pricing_Data_entry_page_for_an_FPT_Schedule(){
		init();
	}
	
	@When("^I complete the data entry with pricing details for ALL SINs on my offer$")
	public void I_complete_the_data_entry_with_pricing_details_for_ALL_SINs_on_my_offer(){
		page.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("capturePricingDataEntry").getAsJsonObject("worksheetData"));
		page.backToCapturePricing();
	}
	
	@When("^I validate the completed product data$")
	public void i_upload_and_validate_the_completed_product_template() throws Throwable {
		CaptureDataPage cpPage = new CaptureDataPage(executionContext);
		cpPage.validateData();
	}
	
	@When("^I complete the data entry with invalid data for ALL SINs on my offer$")
	public void I_complete_the_data_entry_with_invalid_data_for_ALL_SINs_on_my_offer(){
		page.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("capturePricingDataEntry").getAsJsonObject("worksheetData"), false);
		page.backToCapturePricing();
	}
	
	@When("^I validate the errors in completed product data on Form entry page$")
	public void i_validate_the_errors_in_completed_product_data(){
		CaptureDataPage cpPage = new CaptureDataPage(executionContext);
		cpPage.validateData();
		CommonUtilPage.verifyErrorMessage(executionContext.getDriver(),
				executionContext.getCurrentScenarioObj().getAsJsonObject("capturePricingDataEntry").getAsJsonArray("errors"));
	}
	
	@When("^I remove the photo details for ALL SINs on my offer using data entry$")
	public void i_update_the_details_for_ALL_SINs_on_my_offer_using_data_entry(){
		init();
		page.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("capturePricingDataEntry").getAsJsonObject("worksheetData"));
		page.backToCapturePricing();
	}
	
	@Then("^I verify the data on Form entry page$")
	public void I_verify_the_data_on_Form_entry_page(){
		init();
		page.validateFormEntryData(executionContext.getCurrentScenarioObj().getAsJsonObject("capturePricingDataEntry").getAsJsonObject("worksheetData"));
	}
	
	@And("^I complete the data entry with pricing details for the mod$")
	public void I_complete_the_data_entry_with_pricing_details_for_the_mod(){
		I_complete_the_data_entry_with_pricing_details_for_ALL_SINs_on_my_offer();
	}
}
