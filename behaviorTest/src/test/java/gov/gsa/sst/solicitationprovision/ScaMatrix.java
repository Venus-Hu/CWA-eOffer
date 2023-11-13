package gov.gsa.sst.solicitationprovision;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import util.ActionByLinkText;

@Component
public class ScaMatrix extends Steps{
	
	ScaMatrixPage scaMatrixPage;

	private void init() {
		if (scaMatrixPage == null) {
			scaMatrixPage = new ScaMatrixPage(executionContext);
		}
		scaMatrixPage.get();
	}

	@Given("^I am on the SCA Matrix Page$")
	public void i_am_on_the_SCA_Matrix_Page() throws Throwable {
		init();
	}

	@When("^I add a SCA Labor Category with all details$")
	public void i_add_a_SCA_Labor_Category_with_all_details() throws Throwable {
		init();
		scaMatrixPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("scaLaborCategoryMatrix"));
	}
	
	@And("^I have completed the SCA Matrix provision$")
	public void I_have_completed_the_SCA_Matrix_provision() throws Throwable {
		 i_add_a_SCA_Labor_Category_with_all_details();
		scaMatrixPage.submitForm();
	}
	
	@And("^I add a SCA Labor Category with all details and submit form$")
	public void I_add_a_SCA_Labor_Category_with_all_details_and_submit_form() throws Throwable {
		I_have_completed_the_SCA_Matrix_provision();
	}

	@Then("^the SCA Labor Category is added and displayed with details in the SCA Labor Category Matrix$")
	public void the_SCA_Labor_Category_is_added_and_displayed_with_details_in_the_SCA_Labor_Category_Matrix() throws Throwable {
		init();
		scaMatrixPage.verifyLaborCategoriesAdded(executionContext.getCurrentScenarioObj().getAsJsonArray("scaLaborCategoryMatrix"));
	}
	
	@Then("^I submit the SCA Labor Category Matrix form$")
	public void I_submit_the_SCA_Labor_Category_Matrix_form() throws Throwable {
		init();
		scaMatrixPage.submitForm();
	}
	
	@When("^I add a SCA Labor Category but DO NOT provide \"([^\"]*)\"$")
	public void i_add_a_SCA_Labor_Category_but_DO_NOT_provide(String arg1) throws Throwable {
		i_add_a_SCA_Labor_Category_with_all_details();
	}

	@When("^I add at least one SCA Labor Category$")
	public void i_add_at_least_one_SCA_Labor_Category() throws Throwable {
		i_add_a_SCA_Labor_Category_with_all_details();
	}

	@When("^I edit the SCA Matrix$")
	public void i_edit_the_SCA_Matrix() throws Throwable {
		SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
		solPage.selectProvisionAction("SCA Matrix");
		init();
		scaMatrixPage.editMatrix(executionContext.getCurrentScenarioObj().getAsJsonArray("editScaLaborCategoryMatrix"));
	}

	@Then("^the SCA Matrix provision is updated$")
	public void the_SCA_Matrix_provision_is_updated() throws Throwable {
		scaMatrixPage.verifyLaborCategoriesAdded(executionContext.getCurrentScenarioObj().getAsJsonArray("editScaLaborCategoryMatrix"));
		scaMatrixPage.submitForm();
	}

	@When("^I edit a SCA Labor Category in the SCA Labor Category Matrix with all details$")
	public void i_edit_a_SCA_Labor_Category_in_the_SCA_Labor_Category_Matrix_with_all_details() throws Throwable {
		scaMatrixPage.editMatrix(executionContext.getCurrentScenarioObj().getAsJsonArray("editScaLaborCategoryMatrix"));
	}

	@Then("^the SCA Labor Category is updated and displayed with details in the SCA Labor Category Matrix$")
	public void the_SCA_Labor_Category_is_updated_and_displayed_with_details_in_the_SCA_Labor_Category_Matrix() throws Throwable {
		scaMatrixPage.verifyLaborCategoriesAdded(executionContext.getCurrentScenarioObj().getAsJsonArray("editScaLaborCategoryMatrix"));
	}

	@When("^I edit a SCA Labor Category in the SCA Labor Category Matrix but DO NOT provide \"([^\"]*)\"$")
	public void i_edit_a_SCA_Labor_Category_in_the_SCA_Labor_Category_Matrix_but_DO_NOT_provide(String arg1) throws Throwable {
		i_edit_the_SCA_Matrix();
	}

	@When("^I delete a SCA Labor Category in the SCA Labor Category Matrix$")
	public void i_delete_a_SCA_Labor_Category_in_the_SCA_Labor_Category_Matrix() throws Throwable {
		init();
		scaMatrixPage.deleteAllCategories();
	}

	@Then("^the SCA Labor Category is deleted and removed from the SCA Labor Category Matrix$")
	public void the_SCA_Labor_Category_is_deleted_and_removed_from_the_SCA_Labor_Category_Matrix() throws Throwable {
		Assert.assertFalse("SCA LAbor category is not deleted", ActionByLinkText.isDisplayed(executionContext.getDriver(), "Delete", 10));
		scaMatrixPage.submitForm();
	}
	
}
