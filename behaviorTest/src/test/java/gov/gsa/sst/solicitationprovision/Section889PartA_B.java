package gov.gsa.sst.solicitationprovision;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Component
public class Section889PartA_B extends Steps {
	SolicitationProvisionPage solPage;
	Section889PartAPage partApage;
	Section889PartBPage partBpage;

	public void init(String partPageText) {
		if (partApage == null && partPageText.contains("partA")) {
			partApage = new Section889PartAPage(executionContext);
		}
		if (partBpage == null && partPageText.contains("partB")) {
			partBpage = new Section889PartBPage(executionContext);
		}
		if (solPage == null) {
			solPage = new SolicitationProvisionPage(executionContext);
		}

		if (partPageText.contains("partA")) {
			partApage.get();
		} else {
			partBpage.get();
		}
	}

	@Given("^I am on the Section SAM part A page$")
	public void i_am_on_the_Section_SAM_part_A_page() {
		init("partA");
	}

	@Given("^I am on the Section SAM(\\d+) part B page$")
	public void i_am_on_the_Section_SAM_part_B_page() {
		init("partB");
	}

	@When("^I complete All Section SAM part A questions with valid responses$")
	public void i_complete_All_Section_SAM_part_A_questions_with_valid_responses() {
		init("partA");
		partApage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("section889PartA"));
	}

	@When("^I complete All Section SAM part B questions with valid responses$")
	public void i_complete_All_Section_SAM_part_B_questions_with_valid_responses() {
		init("partB");
		partBpage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("section889PartB"));
	}

	@Given("^I complete ALL Section SAM(\\d+) part A questions with valid responses$")
	public void i_complete_ALL_Section_SAM_part_A_questions_with_valid_responses() {
		i_complete_All_Section_SAM_part_A_questions_with_valid_responses();
	}

	@Given("^I complete ALL Section SAM(\\d+) part B questions with valid responses$")
	public void i_complete_ALL_Section_SAM_part_B_questions_with_valid_responses() {
		i_complete_All_Section_SAM_part_B_questions_with_valid_responses();
	}

	@When("^I edit the Section SAM(\\d+) part A$")
	public void i_edit_the_Section_SAM_part_A(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^I edit the Section SAM(\\d+) part B$")
	public void i_edit_the_Section_SAM_part_B(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^the Section SAM(\\d+) part A is updated and message is displayed:  \"([^\"]*)\"$")
	public void the_Section_SAM_part_A_is_updated_and_message_is_displayed(int arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^the Section SAM(\\d+) part B is updated and message is displayed:  \"([^\"]*)\"$")
	public void the_Section_SAM_part_B_is_updated_and_message_is_displayed(int arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^Section SAM part A is complete$")
	public void section_SAM_part_A_is_complete() {
		solPage.verifyStatus("Section 889 Part A", "Completed");
	}

	@Then("^Section SAM part B is complete$")
	public void section_SAM_part_B_is_complete() {
		solPage.verifyStatus("Section 889 Part B", "Completed");
	}

	@When("^I edit the Section SAM(\\d+) part A but DO NOT answer \"([^\"]*)\" question$")
	public void i_edit_the_Section_SAM_part_A_but_DO_NOT_answer_question(int arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^I edit the Section SAM(\\d+) part B but DO NOT answer \"([^\"]*)\" question$")
	public void i_edit_the_Section_SAM_part_B_but_DO_NOT_answer_question(int arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^Error message is displayed as: \"([^\"]*)\"$")
	public void error_message_is_displayed_as(String errorMessage) throws Throwable {
		errorMessage = errorMessage.replace("'", "\"");

	}

}
