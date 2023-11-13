package gov.gsa.sst.option;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.When;

@Component
public class OptionMod extends Steps {
	OptionModPage optionPage;
	


	@When("I \"([^\"]*)\" TDR participation for the contract")
	public void  I_TDR_participation_for_the_contract(String action){
		if(optionPage == null){
			optionPage = new OptionModPage(executionContext);
		}
		optionPage.get();
		optionPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("optionMod"));
	}
	
}
