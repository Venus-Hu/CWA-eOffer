package gov.gsa.sst.subcontract;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

import comment.Steps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import customUtility.Octo_SeleniumLibrary;
import util.ActionByName;

@Component
public class SubContractPlan extends Steps {
	SubContractingPlanPage subkPage;
	
	private void init(){
		if (subkPage == null)
			subkPage = new SubContractingPlanPage(executionContext);
		subkPage.get();
	}
	
	@Given("^I am on the SubContracting Plan Selection page$")
	public void I_am_on_the_subContracting_plan_selection_page(){
		init();
	}

	@When("^I select a \"([^\"]*)\" for subcontracting$")
	public void i_select_a_for_subcontracting(String plan){
		String [] arr = plan.split(" ");
		if(subkPage == null)
			subkPage = new SubContractingPlanPage(executionContext);
		subkPage.selectPlan( arr[0], arr[1]);	
	}

	@When("^I select an \"([^\"]*)\" that has been approved$")
	public void i_select_an_that_has_been_approved(String plan){
		String [] arr = plan.split(" ");
		subkPage.selectPlan( arr[0], arr[1]);
	}
	
	@When("^I select an \"([^\"]*)\" that has been approved for the contract$")
	public void i_select_an_that_has_been_approved_for_the_contract(String plan){
		if(ActionByName.isDisplayed(executionContext.getDriver(), "change", 1))
			Octo_SeleniumLibrary.clickElement(executionContext.getDriver(), By.name("change"));
		if (subkPage == null)
			subkPage = new SubContractingPlanPage(executionContext);
		String [] arr = plan.split(" ");
		subkPage.selectPlan( arr[0], arr[1]);
	}
	
	@Then("^I am required to complete all ten sections of SubContracting Plan information$")
	public void i_am_required_to_complete_all_ten_sections_of_subContracting_plan_information(){
		subkPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("subkPlan"));
	}

	@When("^I am only required to provide Goals and size standards information for the SubContracting Plan$")
	public void i_am_only_required_to_provide_goals_information_for_the_subContracting_plan(){
		i_am_required_to_complete_all_ten_sections_of_subContracting_plan_information();
	}

	@Given("^I am on the SubContracting Plan \"([^\"]*)\" page for a \"([^\"]*)\"$")
	public void i_am_on_the_SubContracting_Plan_page_for_a(String subKSection, String plan) throws Throwable {
		init();
		String [] arr = plan.split(" ");
		subkPage.selectPlan( arr[0], arr[1]);
		subkPage.selectSubKSection(subKSection);
	}

	@When("^I choose the \"([^\"]*)\" option to select ALL Outreach Efforts options$")
	public void i_choose_the_option_to_select_ALL_Outreach_Efforts_options(String option) throws Throwable {
	    subkPage.selectAll(option);
	}
	
	@When("^I choose the \"([^\"]*)\" option to de-select ALL Outreach Efforts options$")
	public void i_choose_the_option_to_de_select_ALL_Outreach_Efforts_options(String option) throws Throwable {
	    subkPage.deselectClearAll(option);
	}

	@Then("^all of the Outreach Efforts options will be \"([^\"]*)\"$")
	public void all_of_the_Outreach_Efforts_options_will_be(String result) throws Throwable {
		subkPage.verifyAllEquitableOppportunityOutreach(result);
	}

	@When("^I choose the \"([^\"]*)\" option to select ALL Internal Efforts options$")
	public void i_choose_the_option_to_select_ALL_Internal_Efforts_options(String option) throws Throwable {
		subkPage.selectAllEquitableOppportunityInternal(option);
	}

	@When("^I choose the \"([^\"]*)\" option to de-select ALL Internal Efforts options$")
	public void i_choose_the_option_to_de_select_ALL_Internal_Efforts_options(String option) throws Throwable {
	    subkPage.deselectClearAllEquitableOppportunityInternal(option);
	}

	@Then("^all of the Internal Efforts options will be \"([^\"]*)\"$")
	public void all_of_the_Internal_Efforts_options_will_be(String result) throws Throwable {
		subkPage.verifyAllEquitableOppportunityInternal(result);
	}

	@When("^I choose the \"([^\"]*)\" option to select ALL Program Administrator Duties$")
	public void i_choose_the_option_to_select_ALL_Program_Administrator_Duties(String option) throws Throwable {
		subkPage.selectAll(option);;
	}

	@Then("^all of the Duties options will be \"([^\"]*)\"$")
	public void all_of_the_Duties_options_will_be(String result) throws Throwable {
		subkPage.verifyAllProgramAdminDuties(result);
	}

	@When("^I de-select the \"([^\"]*)\" option to de-select ALL Program Administrator Duties$")
	public void i_de_select_the_option_to_de_select_ALL_Program_Administrator_Duties(String option) throws Throwable {
		subkPage.deselectClearAll(option);
	}	
	
	@When("^I select a \"([^\"]*)\" but do not provide a plan type$")
	public void i_select_a_but_do_not_provide_a_plan_type(String plan) throws Throwable {
		String [] arr = plan.split(" ");
		if(subkPage == null)
			subkPage = new SubContractingPlanPage(executionContext);
		subkPage.selectSubContractingPlan(arr[0]);
		subkPage.savePlanType();
	}

	

	@When("^I do NOT add required data for \"([^\"]*)\" in subcontracting plan$")
	public void  i_do_NOT_add_data_for_in_subcontracting_plan(String tabName) {
		JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("subkPlan");
		switch (tabName) {
		case "Identification":
			IdentificationDataPage idPage = new IdentificationDataPage(executionContext);
			subkPage.selectSubKSection("Identification Data");
			idPage.populateForm(jsonObj.getAsJsonObject("identificationData"));	
			break;
		case "Assignment of Size Standards to Subcontracts":
			subkPage.addSizeStandards(jsonObj.getAsJsonObject("assignmentOfSizeStandard"));
			break;
		case "Assurances":
			subkPage.addClauseInclusion(jsonObj.getAsJsonObject("inclClause"));
			break;
		default:
			break;
		}
	}
}
