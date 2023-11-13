package gov.gsa.sst.standardresponse;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.common.LeftNavigationMenu;

@Component
public class StandardResponses extends Steps {
	StandardResponsePage standardResponsePage;

	public void init() {
		if (standardResponsePage == null) {
			standardResponsePage = new StandardResponsePage(executionContext);
		}
		standardResponsePage.get();
	}

	@Given("^I am on the Standard Responses page$")
	public void i_am_on_the_standard_responses_page() {
		init();
	}

	@When("^I complete ALL questions with valid responses$")
	public void i_complete_all_questions_with_valid_responses() {
		standardResponsePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("standardResponse"));
	}

	@Then("^Standard Responses menu is complete$")
	public void menu_is_complete() {
		Assert.assertTrue("Could not verify Standard Responses menu is complete",
				LeftNavigationMenu.isStepComplete(executionContext.getDriver(), "Standard Responses"));
	}

	@When("^we are for profit, domestic, not a small business and there ARE opportunities for subcontracting work$")
	public void we_are_for_profit_domestic_not_a_small_business_and_there_are_subcontracting_work(){
		standardResponsePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("standardResponse"));
	}

	@When("^we are for profit, domestic, not a small business and there are NO opportunities for subcontracting work$")
	public void we_are_for_profit_domestic_not_a_small_business_and_there_are_no_subcontracting_work(){
		standardResponsePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("standardResponse"));
		standardResponsePage.confirmResponseChange("Yes");
	}

	@When("^I take exception to any of the Terms and Conditions$")
	public void i_take_exception_to_any_of_the_terms_and_conditions() {
		standardResponsePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("standardResponse"));
	}

	@When("^I DO NOT take exception to any of the Terms and Conditions$")
	public void i_do_not_take_exception_to_any_of_the_terms_and_conditions(){
		standardResponsePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("standardResponse"));
		standardResponsePage.confirmResponseChange("Yes");
	}

	@Then("^Exceptions are required and Exceptions menu is displayed$")
	public void exceptions_menu_is_displayed() {
		Assert.assertTrue("Could not verify Standard Responses menu is complete",
				LeftNavigationMenu.isStepComplete(executionContext.getDriver(), "Standard Responses"));
		Assert.assertTrue("Could not verify Exceptions menu is present",
				LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), "Exceptions"));
	}

	@Then("^SubContracting Plan is required and SubK menu is displayed$")
	public void subk_menu_is_displayed() {
		Assert.assertTrue("Could not verify Standard Responses menu is complete",
				LeftNavigationMenu.isStepComplete(executionContext.getDriver(), "Standard Responses"));
		Assert.assertTrue("Could not verify SubContracting Plan menu is present",
				LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), "SubContracting Plan"));
	}

	@When("^I respond to questions but DO NOT answer \"([^\"]*)\" question$")
	public void i_respond_to_questions_but_do_not_answer_question(String question) {
		// TODO For negative scenario, delete offer and create new one
		switch (question) {
		case "Minimum Order Limit":
			standardResponsePage.clearMinOrderLimit();
			break;

		default:
			break;
		}
		standardResponsePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("standardResponse"));

	}

	@Then("^SubContracting Plan is NOT required and SubK menu is NOT displayed$")
	public void subk_menu_is_not_displayed() {
		Assert.assertTrue("Could not verify Standard Responses menu is complete", LeftNavigationMenu.isStepComplete(executionContext.getDriver(), "Standard Responses"));
		Assert.assertFalse("SubContracting Plan is present on the left hand menu", LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), "SubContracting Plan"));
	}

	@Then("^Exceptions are NOT required and Exceptions menu is NOT displayed$")
	public void exceptions_menu_is_not_displayed() {
		Assert.assertTrue("Could not verify Standard Responses menu is complete",
				LeftNavigationMenu.isStepComplete(executionContext.getDriver(), "Standard Responses"));
		Assert.assertFalse("Exception menu is present",
				LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), "Exception"));
	}

	@When("^we are for profit, foreign, have locations, and operations in US, not a small business and there ARE opportunities for subcontracting work$")
	public void we_are_for_profit_foreign_have_locations_and_operations_in_US_not_a_small_business_and_there_ARE_opportunities_for_subcontracting_work() throws Throwable {
		standardResponsePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("standardResponse"));
		standardResponsePage.confirmResponseChange("Yes");
	}

	@When("^we are not-for-profit, and no work on the contract will be performed in the US$")
	public void we_are_not_for_profit_and_no_work_on_the_contract_will_be_performed_in_the_US() throws Throwable {
		standardResponsePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("standardResponse"));
		standardResponsePage.confirmResponseChange("Yes");
	}

	@Then("^I should have the option to select fastlane on Standard Responses page$")
	public void i_should_have_the_option_to_select_fastlane_on_Standard_Responses_page() throws Throwable {
		Assert.assertTrue("update Fastlane option was not displayed", standardResponsePage.isFastlaneDisplayed());
	}

	@Then("^I should not have the option to select fastlane on Standard Responses page$")
	public void i_should_not_have_the_option_to_select_fastlane_on_Standard_Responses_page() throws Throwable {
		Assert.assertFalse("update Fastlane option was displayed", standardResponsePage.isFastlaneDisplayed());
	}
}
