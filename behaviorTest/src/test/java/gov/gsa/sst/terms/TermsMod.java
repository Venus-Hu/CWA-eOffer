package gov.gsa.sst.terms;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comment.Steps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import util.ActionByLinkText;
import util.ActionByXpath;

@Component
public class TermsMod extends Steps {
	private TermsConditionModPage tcPage;
	private static Logger log = LoggerFactory.getLogger(TermsMod.class);
	int TIMEOUT_IN_SEC = 3;

	public void init() {
		if (tcPage == null)
			tcPage = new TermsConditionModPage(executionContext);
		tcPage.get();
	}

	@Given("^I am on the Terms and Conditions eMod \"([^\"]*)\" page$")
	public void i_am_on_the_Terms_and_Conditions_eMod_page(String modType) {
		init();
		log.info("Selecting mod type: " + modType);
	}

	@When("^I select and complete Commercial Sales Practice clause$")
	public void i_select_and_complete_clause() throws InterruptedException {
		// Commented out for OM-1786.
		// tcPage.respond();
		tcPage.populateClauses(executionContext.getCurrentScenarioObj().getAsJsonArray("reviseTerms"));
	}

	@When("^I select and complete a clause for exception$")
	public void i_select_and_complete_a_clause_for_exception() throws InterruptedException {
		// Commented out for OM-1786.
		// tcPage.respond();
		tcPage.populateExceptions(executionContext.getCurrentScenarioObj().getAsJsonArray("reviseTerms"));
	}

	@When("^I complete Re-representation of Non-Novated Merger/Acquisition details successfully$")
	public void i_complete_Rerepresentation_of_NonNovated_Merger_Acquisition_details_successfully()
			throws InterruptedException {
		i_select_and_complete_clause();
	}

	@Then("^I have uploaded ALL REQUIRED Re-Representation documents successfully$")
	public void I_have_uploaded_ALL_REQUIRED_ReRepresentation_documents_successfully() throws Exception {
		CommonUtilPage.completeMod(executionContext);
	}

	@Then("^I return to eMod Page$")
	public void i_return_to_emod_page() {
		ActionByXpath.click(executionContext.getDriver(), "//a[@title='My eMods']", TIMEOUT_IN_SEC);
		String eModHome = executionContext.getDriver().getTitle();
		//ActionByXpath.click(executionContext.getDriver(), "//h2/span[@class='servicedropSeven glyphicon glyphicon-plus-sign']", TIMEOUT_IN_SEC);
		log.info("Checking for page title: " + eModHome);
	}

	@Then("^I edit the \"([^\"]*)\"$")
	public void i_edit_the(String modType) throws Throwable {
		tcPage.editSavedMod("47QSWA23D0046", modType);
	}
	
	@Then("^I delete the \"([^\"]*)\"$")
	public void i_delete_the(String modType) throws Throwable {
		tcPage.deleteMod("47QSWA23D0046", modType);
	}

	@When("^I complete Re-representation of Business Size details successfully$")
	public void i_complete_Rerepresentation_of_business_size_details_successfully() throws InterruptedException {
		i_select_and_complete_clause();
	}

	@When("^I complete ARRA details successfully$")
	public void i_complete_ARRA_details_successfully() throws InterruptedException {
		i_select_and_complete_clause();
	}

	@When("^I complete Disaster Recovery details successfully$")
	public void i_complete_disaster_recovery_details_successfully() throws InterruptedException {
		i_select_and_complete_clause();
	}

	@When("^I complete Cooperative Purchasing details by SIN successfully$")
	public void i_complete_cooperative_purchasing_details_by_sin_successfully() throws InterruptedException {
		i_select_and_complete_clause();
	}

	@When("^I complete E-Verify details successfully$")
	public void i_complete_everify_details_successfully() throws InterruptedException {
		i_select_and_complete_clause();
	}

	@When("^I add and complete multiple Clauses for Terms and Conditions$")
	public void i_add_and_complete_multiple_Clauses_for_Terms_and_Conditions() throws InterruptedException {
		i_select_and_complete_clause();
	}

	@When("^I respond to the prompt to participate in TDR$")
	public void i_respond_to_the_prompt_to_participate_in_tdr() {
		Octo_SeleniumLibrary.clickElement(executionContext.getDriver(),By.name( "tdrOptInResponse"));
		Octo_SeleniumLibrary.clickElement(executionContext.getDriver(),By.name("saveAndContinue"));
		
	}

	@When("^I respond to the prompt to participate in TDR but do NOT save the response$")
	public void i_respond_to_the_prompt_to_participate_in_tdr_but_no_save() {
		Octo_SeleniumLibrary.clickElement(executionContext.getDriver(),By.name("tdrOptInResponse"));
	}

	@When("^I do NOT respond to the prompt to participate in TDR$")
	public void i_do_not_respond_to_the_prompt_to_participate_in_tdr() {
		Octo_SeleniumLibrary.clickElement(executionContext.getDriver(),By.name("saveAndContinue"));
	}

	@When("^I accept verification to participate in TDR$")
	public void i_accept_verification() {
		Octo_SeleniumLibrary.clickElement(executionContext.getDriver(),By.name( "crossValidationContinue"));
		LeftNavigationMenu.isStepComplete(executionContext.getDriver(), "Terms and Conditions");
	}

	@When("^I submit the Participate in TDR mod without accepting verification$")
	public void i_use_left_nav_to_submit_the_modification() {
		LeftNavigationMenu.navigateTo(executionContext.getDriver(), "Submit Modification", TIMEOUT_IN_SEC);
	}

	@When("^I create a Terms and Conditions eMod of type \"([^\"]*)\"$")
	public void i_create_a_terms_and_conditions_emod_of_type(String modType) {
		ActionByLinkText.click(executionContext.getDriver(), modType, TIMEOUT_IN_SEC);
		Octo_SeleniumLibrary.clickElement(executionContext.getDriver(), By.id( "submitOnline"));
	}
	
	@When("^I respond to the comfirm partcipation in catalog baseline$")
	public void i_respond_to_the_comfirm_partcipation_in_catalog_baseline()  {
	    
	}

	@When("^I do NOT respond to the comfirm partcipation in catalog baseline$")
	public void i_do_NOT_respond_to_the_comfirm_partcipation_in_catalog_baseline()  {
		Octo_SeleniumLibrary.clickElement(executionContext.getDriver(), By.name("saveAndContinue"));
	}

}
