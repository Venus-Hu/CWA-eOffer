package gov.gsa.sst.legal;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

@Component
public class LegalMod extends Steps {

	private LegalModPage legalPage;
	
	public void init(){
		if( legalPage == null)
			legalPage = new LegalModPage(executionContext);
		legalPage.get();
	}
	
	@Given("^I am on the Legal eMod \"([^\"]*)\" page$")
	public void i_am_on_the_Legal_eMod_page(String modType ){
		init();
	}
	
	@When("^I complete Change of Name Agreement details successfully$")
	public void I_complete_Agreement_details_successfully(){
		legalPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("legalMod"));
	}
	
	@When("^I complete Novation Agreement details successfully$")
	public void I_complete_Novation_Agreement_details_successfully(){
		I_complete_Agreement_details_successfully();
	}
}
