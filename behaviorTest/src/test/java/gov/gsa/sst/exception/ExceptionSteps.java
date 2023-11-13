package gov.gsa.sst.exception;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

@Component
public class ExceptionSteps extends Steps {
	ExceptionPage page;
	
	@Given("^I am on the exceptions page$")
	public void i_am_on_the_exceptions_page() {
		if(page == null)
			page = new ExceptionPage(executionContext);
		page.get();
	}
	
	@When("^I select and complete a clause exception for an offer$")
	public void  I_select_and_complete_a_clause_exception_for_an_offer() {
		page.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("exception"));
	}
}
