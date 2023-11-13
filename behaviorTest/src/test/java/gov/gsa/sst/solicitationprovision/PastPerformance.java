package gov.gsa.sst.solicitationprovision;

import java.net.MalformedURLException;

import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

@Component
public class PastPerformance extends Steps {
	PastPerformancePage pastPerformancePage;
	
	public void init(){
		if(pastPerformancePage == null)
			pastPerformancePage = new PastPerformancePage(executionContext);
		pastPerformancePage.get();
	}
	
	@Given("^I am on the Past Performance page$")
	public void i_am_on_the_past_performance_page(){
		init();
	}

	@When("^I complete ALL Past Performance questions with valid responses$")
	public void i_complete_all_past_performance_questions_with_valid_responses(){
		init();
		String setFeedback = executionContext.getCurrentScenarioObj().getAsJsonObject("pastPerformance").get("feedback").getAsString();
		pastPerformancePage.setFeedback(setFeedback);
	}
	
	@When("^I submit the Past Performance form$")
	public void i_submit_the_past_performance_form(){
		pastPerformancePage.submitForm();
	}
	
	@When("^I upload \"([^\"]*)\" document on Past Performance page$")
	public void i_upload_document_on_past_performance_page(String docType) throws MalformedURLException{
		init();	
		JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("pastPerformance");
		pastPerformancePage.selectTechProp(jsonObj);
		pastPerformancePage.uploadSupportingDocuments(executionContext.getCurrentScenarioObj().getAsJsonObject("pastPerformance"));
	}
	
	@When("^I edit the Past Performance$")
	public void i_edit_the_past_performance() throws MalformedURLException{
		SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
		solPage.selectProvisionAction("Past Performance");
		pastPerformancePage.populateAllFields(executionContext.getCurrentScenarioObj().getAsJsonObject("editPastPerformance"));
		pastPerformancePage.submitForm();
	}
	
}
