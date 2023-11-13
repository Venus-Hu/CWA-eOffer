package gov.gsa.sst.solicitationprovision;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.commonpage.CommonUtilPage;

@Component
public class RelevantExperience extends Steps {
	Sch70RelevantExperiencePage page;
	
	public void init(){
		if (page == null )
			page = new Sch70RelevantExperiencePage(executionContext);
		page.get();
	}
	
	@Given("^I am on the Relevant Project Experience page$")
	public void i_am_on_the_relevant_project_experience_page(){
		init();
	}
	
	@When("^I add at least three relevant projects for each SIN$")
	public void i_add_at_least_three_relevant_projects_for_each_sin(){
		page.get();
		page.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("relevantExperience"));
	}
	
	@When("^I have already added a project for a SIN$")
	public void i_have_already_added_a_project_for_a_sin(){
		init();
		page.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("relevantExperience"));
	}
	
	@When("^I edit a project from the Relevant Project Experience list for that SIN$")
	public void i_edit_a_project_from_the_relevant_project_experience_list_for_that_sin(){
		SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
		solPage.selectProvisionAction("Relevant Project Experience");
		page.updateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("editRelevantExperience"));
	}
	
	@Then("^the project is updated and message is displayed: \"([^\"]*)\"$")
	public void the_project_is_updated_and_message_is_displayed(String successMsg){
		CommonUtilPage.verifyAppMessage(successMsg, executionContext.getDriver());
	}
	
	@When("^I delete a project from the Relevant Project Experience list for that SIN$")
	public void i_delete_a_project_from_the_relevant_project_experience_list_for_that_sin(){
		SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
		solPage.selectProvisionAction("Relevant Project Experience");
		page.deleteProjects(executionContext.getCurrentScenarioObj().getAsJsonArray("relevantExperience"));	
	}
	
	@Then("^the project is removed from the Relevant Project Experience list for that SIN$")
	public void the_project_is_removed_from_the_relevant_project_experience_list_for_that_sin(){
		Assert.assertTrue("Not all projects were deleted", page.areAllProjectsDeleted());
	}
}
