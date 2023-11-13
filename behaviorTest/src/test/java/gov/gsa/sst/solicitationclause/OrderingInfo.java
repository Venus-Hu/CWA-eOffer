package gov.gsa.sst.solicitationclause;

import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Component
public class OrderingInfo extends Steps {
	private static Logger log = LoggerFactory.getLogger(OrderingInfo.class);
	private OrderingInfoPage orderingInfoPage;

	private void init() {
		log.info("Navigate to Ordering Information Type page...");
		if (orderingInfoPage == null) {
			orderingInfoPage = new OrderingInfoPage(executionContext);
		}
		orderingInfoPage.get();
	}
	
	@Given("^I am on the Ordering Information Page$")
	public void i_am_on_the_Ordering_Information_Page() {
	    init();
	}

	@When("^I add an Ordering Information entry with all details$")
	public void i_add_an_Ordering_Information_entry_with_all_details() {
		i_add_at_least_one_Ordering_Receipt_Information_entry();
	}

	@Then("^the Ordering Information is added and displayed with correct description in the Ordering Information List$")
	public void the_Ordering_Information_is_added_and_displayed_with_correct_description_in_the_Ordering_Information_List() {
		init();
		
		Map<String, Boolean> results = orderingInfoPage.isOrderingInfoPresent(executionContext.getCurrentScenarioObj().getAsJsonArray("yourOrderingInformation"));
		results.forEach((name, isDisplayed) -> {
			Assert.assertTrue("'" + name +"' ordering information is not displayed", isDisplayed);
		});
	}

	@When("^I add at least one Ordering Receipt Information entry$")
	public void i_add_at_least_one_Ordering_Receipt_Information_entry() {
	   init();
	   orderingInfoPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("orderingReceiptInformation"));
	}

	@When("^I add at least one Remittance Address entry$")
	public void i_add_at_least_one_Remittance_Address_entry() {
		init();
		orderingInfoPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("remittanceAddress"));
	}

	@Then("^Ordering Information template is complete$")
	public void ordering_Information_template_is_complete() {
		init();
		orderingInfoPage.clickBackToSolicitationClauses();
		
	    SolicitationClausePage solicitationClausePage = new SolicitationClausePage(executionContext);
	    solicitationClausePage.get();
	    Assert.assertTrue("'Ordering Information' template is not complete", solicitationClausePage.isTemplateComplete("Ordering Information"));
	}

	@Then("^Ordering Information template is Incomplete$")
	public void ordering_Information_template_is_Incomplete() {
		init();
		orderingInfoPage.clickBackToSolicitationClauses();
		
	    SolicitationClausePage solicitationClausePage = new SolicitationClausePage(executionContext);
	    solicitationClausePage.get();
	    Assert.assertFalse("'Ordering Information' template is complete but should not be", solicitationClausePage.isTemplateComplete("Ordering Information"));
	}

	@When("^I add an Ordering Receipt Information entry with Fax transmission but DO NOT provide Fax Number$")
	public void i_add_an_Ordering_Receipt_Information_entry_with_Fax_transmission_but_DO_NOT_provide_Fax_Number() {
		i_add_at_least_one_Ordering_Receipt_Information_entry();
	}

	@When("^I add an Remittance Address entry but provide an Invalid Zip Code for US Address$")
	public void i_add_an_Remittance_Address_entry_but_provide_an_Invalid_Zip_Code_for_US_Address() {
		i_add_at_least_one_Remittance_Address_entry();
	}

	@When("^I edit an Ordering Information entry in the Ordering Information List$")
	public void i_edit_an_Ordering_Information_entry_in_the_Ordering_Information_List() {
		// add ordering information entry first
		i_add_at_least_one_Ordering_Receipt_Information_entry();
		i_add_at_least_one_Remittance_Address_entry();
		
		// Now edit them
		orderingInfoPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("editedOrderingReceiptInformation"));
		orderingInfoPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("editedRemittanceAddress"));
	}

	@Then("^the Ordering Information is updated and displayed in the Ordering Information List$")
	public void the_Ordering_Information_is_updated_and_displayed_in_the_Ordering_Information_List() {
		the_Ordering_Information_is_added_and_displayed_with_correct_description_in_the_Ordering_Information_List();
	}

	@When("^I update an Ordering Receipt Information with EDI transmission but DO NOT provide Email$")
	public void i_update_an_Ordering_Receipt_Information_with_EDI_transmission_but_DO_NOT_provide_Email() {
	    init();
	    orderingInfoPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("updatedOrderingReceiptInformation"));;
	}

	@When("^I delete an Ordering Information entry from the Ordering Information List$")
	public void i_delete_an_Ordering_Information_entry_from_the_Ordering_Information_List() {
	    init();
	    orderingInfoPage.deleteExistingOrderInformation(executionContext.getCurrentScenarioObj().getAsJsonArray("orderingInformationToDelete"));
	}

	@Then("^the Ordering Information entry is removed from the Ordering Information List$")
	public void the_Ordering_Information_entry_is_removed_from_the_Ordering_Information_List() {
		init();
		Map<String, Boolean> results = orderingInfoPage.isOrderingInfoPresent(executionContext.getCurrentScenarioObj().getAsJsonArray("orderingInformationToDelete"));
		
		results.forEach((name, isDisplayed) -> {
			Assert.assertFalse("'" + name +"' ordering information is still displayed", isDisplayed);
		});
	}
}
