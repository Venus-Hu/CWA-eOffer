package gov.gsa.sst.wizardmanagement;

import java.net.MalformedURLException;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.common.LeftNavigationMenu;


@Component
public class WizardManagement extends Steps {
	WizardManagementPage wizardPage;
	
	public WizardManagement() {
		// TODO Auto-generated constructor stub
	}

	public void init(){
		if (wizardPage == null) {
			wizardPage = new WizardManagementPage(executionContext);
		}
		wizardPage.get();
	}

	@Given("^I am on the Wizard Management page for an FPT Schedule$")
	public void i_am_on_wizard_management_page() throws MalformedURLException, InterruptedException{
		init();
		wizardPage.verifyWizMgmtInstructionText();
	}
	
	@When("^I select Volume/Quantity or Dollar Discounts for my offer$")
	public void i_complete_wizard_details_for_my_offer(){
		wizardPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("wizardManagement"));
	}
	
	@When("^I select ALL available Accessories/Options at the Offer Level$")
	public void i_select_all_available_accessories_options_at_the_offer_level_when_available(){
		i_complete_wizard_details_for_my_offer();
	}
	
	@When("^I select NO Volume/Quantity or Dollar Discounts for my offer$")
	public void i_select_NO_Volume_Quantity_or_Dollar_Discounts_for_my_offer(){
		i_complete_wizard_details_for_my_offer();
	}
	
	@Then("^Wizard Management menu is complete$")
	public void wizard_management_menu_is_complete(){
		Assert.assertTrue("Wizard management menu is not complete", LeftNavigationMenu.isStepComplete(executionContext.getDriver(), "Wizard Management"));
	}
	
	@Then("^corresponding menu items are displayed$")
	public void corresponding_menu_items_are_displayed(){
		Assert.assertTrue("", LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), "Delivery"));
		Assert.assertTrue("Discounts menu not found", LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), "Discounts"));
		Assert.assertTrue("Zonal Pricing menu not found", LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), "Contractor Warranty"));
		Assert.assertTrue("Zonal Pricing menu not found", LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), "Zonal Pricing"));
		Assert.assertTrue("Special Charges menu not found", LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), "Special Charges"));
		Assert.assertTrue("Special Features menu not found", LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), "Special Features"));
		
	}

}
