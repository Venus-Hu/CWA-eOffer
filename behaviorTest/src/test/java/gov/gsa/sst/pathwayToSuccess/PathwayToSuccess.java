package gov.gsa.sst.pathwayToSuccess;

import gov.gsa.sst.common.LeftNavigationMenu;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Component
public class PathwayToSuccess extends Steps {

	PathwayToSuccessPage pathwayToSuccessPage = null;

	public PathwayToSuccess() {

	}

	@Given("^I am on the Pathway to Success Page$")
	public void i_am_on_the_Pathway_to_Success_Page() {

		if (pathwayToSuccessPage == null) {
			pathwayToSuccessPage = new PathwayToSuccessPage(executionContext);
		}
		pathwayToSuccessPage.get();
	}

	@When("^Vendor complete section one$")
	public void vendor_complete_section_one() {
		pathwayToSuccessPage.selectpathwayAcknowledged();
	}

	@When("^Vendor Save and Continue$")
	public void vendor_Save_and_Continue() {
		pathwayToSuccessPage.submitForm();
	}

	@Then("^Pathway to Success menu is complete$")
	public void pathway_to_Success_menu_is_complete() {
		Assert.assertTrue("Could not verify Pathway to Success menu is complete",
				LeftNavigationMenu.isStepComplete(executionContext.getDriver(), "Pathway to Success"));
	}

	@When("^Vendor complete section two$")
	public void vendor_complete_section_two() {
		pathwayToSuccessPage.selectexistingFssContractor();
	}

	@When("^Vendor complete section one and two$")
	public void vendor_complete_section_one_and_two() {
		pathwayToSuccessPage.selectpathwayAcknowledged();
		pathwayToSuccessPage.selExistingFssContractor();
	}

	@When("^Vendor did not complete section one Or two$")
	public void vendor_did_not_complete_section_one_Or_two() {
		pathwayToSuccessPage.deselectexistingFssContractor();
		vendor_Save_and_Continue();
	}

	@When("^Vendor did not complete section one$")
	public void vendor_did_not_complete_section_one() {
		pathwayToSuccessPage.selectpathwayAcknowledged();
		pathwayToSuccessPage.deselectreadinessAcknowledged();
		vendor_Save_and_Continue();

	}

}
