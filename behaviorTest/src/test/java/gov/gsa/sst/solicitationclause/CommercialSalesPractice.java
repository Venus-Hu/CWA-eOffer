package gov.gsa.sst.solicitationclause;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import comment.Steps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import customUtility.Octo_SeleniumLibrary;
import util.ActionByXpath;

@Component
public class CommercialSalesPractice extends Steps {

	CommercialSalesPracticePage commercialSalesPracticePage;
	int TIMEOUT_IN_SEC = 3;

	private void init() {
		if (commercialSalesPracticePage == null) {
			commercialSalesPracticePage = new CommercialSalesPracticePage(executionContext);
		}
		commercialSalesPracticePage.get();
	}

	@When("^I respond to Commercial Sales Practice and provide valid responses for ALL questions$")
	public void i_respond_to_Commercial_Sales_Practice_and_provide_valid_responses_for_ALL_questions()
			throws Throwable {
		init();
		commercialSalesPracticePage.populateFormDetails(
				executionContext.getCurrentScenarioObj().getAsJsonObject("commercialSalesPractice"));
	}

	@When("^I select Offer/Contract Level for Discount/Concession Policy Level and I select Discount Basis Level$")
	public void updatePolicyLevelAndBasis() throws Throwable {
		commercialSalesPracticePage.selectOfferPolicyAndDiscountBasisLevel(executionContext.getCurrentScenarioObj()
				.getAsJsonObject("commercialSalesPractice").getAsJsonObject("policyBasisLevelForOffer"));
	}

	@When("^I select Offer/Contract Level for Discount/Concession Policy Level$")
	public void i_select_Offer_Contract_Level_for_Discount_Concession_Policy_Level() throws Throwable {
		updatePolicyLevelAndBasis();
	}

	@When("^I add a Discount Policy$")
	public void i_add_a_Discount_Policy() throws Throwable {
		commercialSalesPracticePage.addDiscountPolicies(executionContext.getCurrentScenarioObj()
				.getAsJsonObject("commercialSalesPractice").getAsJsonArray("addDiscountPolicy"));
	}

	@Then("^Commercial Sales Practice template is Complete$")
	public void commercial_Sales_Practice_template_is_Complete() throws Throwable {
		SolicitationClausePage clausePage = new SolicitationClausePage(executionContext);
		clausePage.get();
		Assert.assertTrue("CSP is incomplete", clausePage.isTemplateComplete("Commercial Sales Practice"));
	}
	
	@Then("^Commercial Sales Practice template is Incomplete$")
	public void commercial_Sales_Practice_template_is_Incomplete() throws Throwable {
		SolicitationClausePage clausePage = new SolicitationClausePage(executionContext);
		clausePage.get();
		Assert.assertFalse("CSP is complete", clausePage.isTemplateComplete("Commercial Sales Practice"));
	}
	
	@When("^I select SIN for Discount/Concession Policy Level and select Discount Basis Level$")
	public void i_select_SIN_for_Discount_Concession_Policy_Level_and_select_Discount_Basis_Level() throws Throwable {
		commercialSalesPracticePage.selectBasisLevelForSin(executionContext.getCurrentScenarioObj()
				.getAsJsonObject("commercialSalesPractice").getAsJsonArray("policyBasisLevelForSin"));
	}

	@When("^I add a Discount Policy for each SIN$")
	public void i_add_a_Discount_Policy_for_each_SIN() throws Throwable {
		commercialSalesPracticePage.addDiscountPolicies(executionContext.getCurrentScenarioObj()
				.getAsJsonObject("commercialSalesPractice").getAsJsonArray("addDiscountPolicy"));
	}


	@When("^I edit the Commercial Sales Practice template which is complete$")
	public void i_edit_the_Commercial_Sales_Practice_template_which_is_complete() throws Throwable {
		Octo_SeleniumLibrary.clickElement(executionContext.getDriver(), By.xpath("//a[@title='Edit Commercial Sales Practice']"));
		commercialSalesPracticePage.editFormDetails(
				executionContext.getCurrentScenarioObj().getAsJsonObject("editCommercialSalesPractice"));
	}

	@Then("^the Commercial Sales Practice template is updated and message is displayed:  \"([^\"]*)\"$")
	public void the_Commercial_Sales_Practice_template_is_updated_and_message_is_displayed(String arg1) throws Throwable {
		ActionByXpath.getText(executionContext.getDriver(), "//li[@class='alert alert-success']", TIMEOUT_IN_SEC).contains(arg1);
	}

	@When("^I delete the Commercial Sales Practice template which is complete$")
	public void i_delete_the_Commercial_Sales_Practice_template_which_is_complete() throws Throwable {
		SolicitationClausePage solClausePage = new SolicitationClausePage(executionContext);
		solClausePage.get();
		solClausePage.deleteTemplate("Commercial Sales Practice");
	}

	@Then("^Commercial Sales Practice details are required in Solicitation Clauses$")
	public void commercial_Sales_Practice_details_are_required_in_Solicitation_Clauses() {
		// TODO
		// Validate that CSP action item is present on the page
		SolicitationClausePage clausePage = new SolicitationClausePage(executionContext);
		clausePage.get();
		Assert.assertTrue("CSP clause is not found", clausePage.isTemplatePresent("Commercial Sales Practice"));
	}

	@Then("^Commercial Sales Practice details are not required in Solicitation Clauses$")
	public void commercial_Sales_Practice_details_are_not_required_in_Solicitation_Clauses() {
		// Validate that CSP action item is not present on the page
		SolicitationClausePage clausePage = new SolicitationClausePage(executionContext);
		clausePage.get();
		Assert.assertFalse("CSP clause is found", clausePage.isTemplatePresent("Commercial Sales Practice"));
	}

	@When("^I respond to Commercial Sales Practice but do not provide Sales Dollar Value$")
	public void i_respond_to_csp_but_do_not_provides_sales_dollar_value() throws Throwable {
		i_respond_to_Commercial_Sales_Practice_and_provide_valid_responses_for_ALL_questions();
	}

	@When("^I respond to Commercial Sales Practice but the sales period is not one year apart$")
	public void i_respond_to_csp_but_sales_period_is_not_one_year() throws Throwable {
		i_respond_to_Commercial_Sales_Practice_and_provide_valid_responses_for_ALL_questions();
	}

	@When("^I respond to Commercial Sales Practice but do not explain the reason for the deviation$")
	public void i_respond_to_csp_but_deviation_explation_is_not_provided() throws Throwable {
		i_respond_to_Commercial_Sales_Practice_and_provide_valid_responses_for_ALL_questions();
	}

	@When("^I respond to Commercial Sales Practice but do not provide a Discount Policy$")
	public void i_respond_to_csp_but_do_not_provide_a_discount_policy() throws Throwable {
		i_respond_to_Commercial_Sales_Practice_and_provide_valid_responses_for_ALL_questions();
		commercialSalesPracticePage.selectOfferPolicyAndDiscountBasisLevel(executionContext.getCurrentScenarioObj().getAsJsonObject("commercialSalesPractice").getAsJsonObject("policyBasisLevelForOffer"));
		commercialSalesPracticePage.clickSaveBtn();
	}
	
	@Given("^I successfully respond to the Commercial Sales Practice$")
	public void i_successfully_respond_to_csp() {
		init();
		commercialSalesPracticePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("commercialSalesPractice"));
		SolicitationClausePage clausePage = new SolicitationClausePage(executionContext);
		clausePage.get();
		Assert.assertTrue("CSP is not complete", clausePage.isTemplateComplete("Commercial Sales Practice"));
	}
	
}
