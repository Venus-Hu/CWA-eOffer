package gov.gsa.sst.capturepricing.edi;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

@Component
public class EdiOption extends Steps {

	private EdiOptionPage ediPage;

	public void init(){
		if(ediPage == null)
			ediPage = new EdiOptionPage(executionContext.getDriver());
		ediPage.get();
	}
	
	@Given("^I am on the Capture Pricing EDI page for an FPT Schedule$")
	public void i_am_on_the_Capture_Pricing_EDI_page_for_an_FPT_Schedule(){
		init();
	}
	
	
	@When("^I generate a transaction key using EDI$")
	public void i_generate_a_transaction_key_using_EDI(){
		ediPage.generateKey();
	}
	

}
