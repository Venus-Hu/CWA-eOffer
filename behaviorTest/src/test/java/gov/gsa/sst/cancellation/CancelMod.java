package gov.gsa.sst.cancellation;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

@Component
public class CancelMod extends Steps{

	CancelModPage modPage;
	
	public void init(){
		if(modPage == null)
			modPage = new CancelModPage(executionContext);
		modPage.get();
	}
	
	@Given("^I am on the Cancellations Or Terminations eMod \"([^\"]*)\" page$")
	public void I_am_on_the_Cancellations_Or_Terminations_eMod_page(String modType){
		init();
	}
	
	@When("^I complete Cancel Contract details successfully$")
	public void i_complete_Cancel_Contract_details_successfully(){
		modPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("cancelMod"));
	}
}
