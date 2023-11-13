package gov.gsa.sst.productcatalog;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import comment.Steps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
@Component
public class TermsConditionTextFile extends Steps{

	protected TermsConditionTextFilePage termsConditionTextFilePage;
	
	public void initTermsConditionTextFile()
	{
		if(termsConditionTextFilePage == null)
			termsConditionTextFilePage = new TermsConditionTextFilePage(executionContext);
		termsConditionTextFilePage.get();
	}
	
	@When("^I create \"([^\"]*)\" mod$")
	public void I_create_mod(String arg1) {
		initTermsConditionTextFile();
	}
	
	@Then("^Terms and Condition Text File mod is created successfully$")
	public void terms_and_Condition_Text_File_mod_is_created_successfully() throws Throwable {
		LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), "Product Catalog");
	}
		
	@Given("^I am on the Terms and Condition Text File eMod \"([^\"]*)\" page$")
	public void i_am_on_the_Terms_and_Condition_Text_File_eMod_page(String arg1) throws Throwable {
		initTermsConditionTextFile();
	}

	@When("^I validate sin list associated with the contract$")
	public void i_validate_sin_list_associated_with_the_contract() throws Throwable {
		initTermsConditionTextFile();
		termsConditionTextFilePage.validateSins();
	}

	@When("^I add description of the modification request$")
	public void i_add_description_of_the_modification_request() throws Throwable {
		initTermsConditionTextFile();
		termsConditionTextFilePage.populateForm();
	}

	@When("^I complete Terms and Condition Text File page successfully$")
	public void i_complete_Terms_and_Condition_Text_File_page_successfully() throws Throwable {
		termsConditionTextFilePage.populateForm();
		Octo_SeleniumLibrary.clickElement(executionContext.getDriver(), By.id("saveAndContinue"));
		
	}

}
