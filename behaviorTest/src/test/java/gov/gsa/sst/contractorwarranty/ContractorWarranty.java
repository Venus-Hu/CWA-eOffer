package gov.gsa.sst.contractorwarranty;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Component
public class ContractorWarranty extends Steps {

	ContractorWarrantyPage contractorPage;
	private static Logger log = LoggerFactory.getLogger(ContractorWarranty.class);
	
	public ContractorWarranty() {
		// TODO Auto-generated constructor stub
	}

	public void init(){
		if(contractorPage == null)
			contractorPage = new ContractorWarrantyPage(executionContext);
		contractorPage.get();
	}

	@Given("^I am on the Contractor Warranty page for a FPT Schedule$")
	public void i_am_on_the_contractor_warranty_page_for_a_fpt_schedule() throws MalformedURLException, InterruptedException{
		init();	
	}
	
	@When("^I select the appropriate warranty details$")
	public void i_select_the_appropriate_warranty_details(){
		init();	
		log.info("Adding contractor warranty details");
		contractorPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("contractorWarranty"));
	}	
	
	@Then("^Contractor warranty details are displayed$")
	public void contractor_warranty_details_are_displayed(){
		init();	
		contractorPage.verifyWarrantyDetails(executionContext.getCurrentScenarioObj().getAsJsonArray("contractorWarranty"));
	}
}
