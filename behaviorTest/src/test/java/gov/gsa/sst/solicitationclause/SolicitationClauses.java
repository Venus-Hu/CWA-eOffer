package gov.gsa.sst.solicitationclause;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.util.data.Offer;

@Component
public class SolicitationClauses extends Steps{

	SolicitationClausePage solicitationClausePage;
	
	private void init() {
		if (solicitationClausePage == null) {
			solicitationClausePage = new SolicitationClausePage(executionContext);
		}
		solicitationClausePage.get();
	}	
	
	@Given("^I am on the Solicitation Clauses Page$")
	public void i_am_on_the_Solicitation_Clauses_Page() {
	   init();
	}
	
	@When("^I have completed all Solicitation Clause templates$")
	public void i_have_completed_all_Solicitation_Clause_templates() throws Exception {
	   
		new Offer.Builder(executionContext).goodsAndServices().solClauseBasicInformation().solClauseCSP().solClausePointOfContact().solClauseOrderingInformation().build();
	}
	
	@When("^the \"([^\"]*)\" Solicitation Clause template is complete$")
	public void sol_clause_template_is_complete(String templateName) {
		init();
		Assert.assertTrue(templateName + " is not complete", solicitationClausePage.isTemplateComplete(templateName));
	}
	
	@Then("^all Solicitation Clause templates are complete$")
	public void all_Solicitation_Clause_templates_are_complete() throws Throwable {
	   init();
	   solicitationClausePage.verifyStatus("Basic Information", "Completed");
	   solicitationClausePage.verifyStatus("Commercial Sales Practice", "Completed");
	   solicitationClausePage.verifyStatus("Point of Contacts Information", "Completed");
	   solicitationClausePage.verifyStatus("Ordering Information", "Completed");
	   
	}
}
