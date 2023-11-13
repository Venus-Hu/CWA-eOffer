package gov.gsa.sst.solicitationclause;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Component
public class PointOfContact extends Steps {
	PointOfContactPage pocPage;
	int TIMEOUT_IN_SEC = 3;

	public PointOfContact() {
		// TODO Auto-generated constructor stub
	}

	@Given("^I am on the Point of Contact Information Page$")
	public void i_am_on_the_point_of_contact_information_page() {
		if (pocPage == null) {
			pocPage = new PointOfContactPage(executionContext);
		}
		pocPage.get();
	}

	@When("^I add a Point of Contact with all details$")
	public void i_add_a_point_of_contact_with_all_details() {
		pocPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("pointOfContact"));
	}

	@Then("^the POC is added and displayed with correct description in the POC List$")
	public void the_poc_is_added_and_displayed_with_correct_description_in_the_poc_list() {
		pocPage.verifyPocExists(executionContext.getCurrentScenarioObj().getAsJsonArray("verifyPOC"));
	}

	@Given("^I edit a POC in the POC list$")
	public void i_edit_a_poc_in_the_poc_list() {
		pocPage.editPoc(executionContext.getCurrentScenarioObj().getAsJsonArray("editPointOfContact"));
	}

	@Then("^the POC is updated and displayed in the POC List$")
	public void the_poc_is_updated_and_displayed_in_the_poc_list() {
		pocPage.verifyPocExists(executionContext.getCurrentScenarioObj().getAsJsonArray("editPointOfContact"));
	}

	@When("^I delete a POC in the POC list$")
	public void i_delete_a_poc_in_the_list() {
		pocPage.deletePOC(executionContext.getCurrentScenarioObj().getAsJsonArray("pointOfContact"));
	}

	@Then("^the POC is removed from the POC List$")
	public void the_poc_is_removed_from_the_poc_list() {
		pocPage.verifyPocDoesNotExist(executionContext.getCurrentScenarioObj().getAsJsonArray("pointOfContact"));
	}

	@Then("^verify POC does not have any special characters$")
	public void verify_POC_does_not_have_any_special_characters() {
		pocPage.verifyPocDetails(executionContext.getCurrentScenarioObj().getAsJsonArray("verifyPOC"));
	}

	@When("^I add at least one Contract Admin POC$")
	public void i_add_at_least_one_contract_admin_POC() {
		pocPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("pointOfContact"));
	}

	@When("^Point of Contacts Information template is Incomplete$")
	public void poc_info_template_is_incomplete() {
		SolicitationClausePage solClausePage = new SolicitationClausePage(executionContext);
		solClausePage.get();
		Assert.assertFalse("Point of Contacts Information is complete", solClausePage.isTemplateComplete("Point of Contacts Information"));
	}

	@When("^I add a POC but DO NOT provide Address1$")
	public void i_add_poc_but_do_not_provide_address1() {
		pocPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("pointOfContact"));
	}
	
	@When("^I \"([^\"]*)\" the Point of Contacts Information template$")
	public void update_poc_template (String action) {
		pocPage.updateTemplate(action);
	}
	
	@Then("^the POCs will be displayed in the POC List for Edit/Delete$")
	public void the_template_is_deleted_and_prompts_me_to_respond () {
		pocPage.verifyPocExists(executionContext.getCurrentScenarioObj().getAsJsonArray("verifyPOC"));
	}
}
