package gov.gsa.sst.wizardmanagement.zonalpricing;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.commonpage.GenericDialogPage;

@Component
public class ZonalPricing extends Steps{

	ZonalPricingPage zonalPricingPage;
	
	public void init(){
		if (zonalPricingPage == null) {
			zonalPricingPage = new ZonalPricingPage(executionContext);
		}
		zonalPricingPage.get();
	}
	
	@Given("^I am on the Zonal Pricing page for an FPT Schedule$")
	public void gotoPricingpage() throws Throwable {
		init();
		zonalPricingPage.verifyZonalPricingInstructionText();
	}

	@Then("^the Zone details will be displayed in the Zones list$")
	public void the_Zone_details_will_be_displayed_in_the_Zones_list() throws Throwable {
		zonalPricingPage.verifyAddedZones(executionContext.getCurrentScenarioObj().getAsJsonArray("zonalPricing"));
		zonalPricingPage.clickContinueBtn();
		GenericDialogPage page = new GenericDialogPage(executionContext.getDriver(), "Zonal Pricing", By.name("continueBtn"));
		page.clickConfirmYes();
	}

	@When("^I assign a Zone Number to ALL states/territories$")
	public void i_add_a_CONUS_and_Non_CONUS_zones_to_select_all_the_states() throws Throwable {
		JsonArray array = executionContext.getCurrentScenarioObj().getAsJsonArray("zonalPricing");
		zonalPricingPage.populateForm(array);
	}
}
