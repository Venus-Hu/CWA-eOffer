package gov.gsa.sst.discounts;

import java.util.Iterator;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


@Component
public class Discounts extends Steps{

	DiscountsPage discountsPage;
	private static Logger log = LoggerFactory.getLogger(Discounts.class);
	
	private void init()
	{
		if (discountsPage == null) {
			discountsPage = new DiscountsPage(executionContext);
		}
		discountsPage.get();
	}
	
	@Given("^I am on the Discounts page for an FPT Schedule$")
	public void i_am_on_the_Discounts_page_for_an_FPT_Schedule() {
		init();
	}

	@When("^I add Discounts details for an Offer$")
	public void i_add_Discounts_details_for_an_Offer() {
		discountsPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("discounts"));
	}

	@Then("^the Discounts are added and displayed in the Volume/Quantity Discounts list$")
	public void the_Discounts_are_added_and_displayed_in_the_Volume_Quantity_Discounts_list() {
		log.info("Verify Discounts list...");
		discountsPage.verifyDiscountsList(executionContext.getCurrentScenarioObj().getAsJsonArray("discounts"));
	}

	@When("^I add Discounts details for available SINs$")
	public void i_add_Discounts_details_for_available_SINs() {
		discountsPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("discounts"));
	}
	
	@When("^I edit a Discount from the list$")
	public void i_edit_a_Discount_from_the_list(){
		discountsPage.editDiscounts(executionContext.getCurrentScenarioObj().getAsJsonArray("editDiscounts"));
	}
	
	@Then("^the Discount is updated and displayed in the Volume/Quantity Discounts list$")
	public void the_Discount_is_updated_and_displayed_in_the_Volume_Quantity_Discounts_list() {
		the_Discounts_are_added_and_displayed_in_the_Volume_Quantity_Discounts_list();
	}
	
	@When("^I delete a Discount from the list$")
	public void i_delete_a_Discount_from_the_list(){
		discountsPage.deleteDiscounts(executionContext.getCurrentScenarioObj().getAsJsonArray("discounts"));
	}
	
	@Then("^the Discount is deleted and removed from the Volume/Quantity Discounts list$")
	public void the_Discount_is_deleted_and_removed_from_the_Volume_Quantity_Discounts_list() {
		JsonArray array = executionContext.getCurrentScenarioObj().getAsJsonArray("discounts");
		Iterator<JsonElement> ite = array.iterator();
		while(ite.hasNext()){
			JsonElement element = (JsonElement) ite.next();
	        JsonObject jsonObj = element.getAsJsonObject();
			String startRange = jsonObj.get("startRange").getAsString();
	    	log.info("Verifying the discount row with start range value " + startRange);
		    Assert.assertFalse("Discount row is not found in the list", discountsPage.isDiscountPresentByStartRange(startRange));
		}
	}
	
	@When("^I add Discounts details BUT DO NOT provide \"([^\"]*)\"$")
	public void i_add_Discounts_details_BUT_DO_NOT_provide(String elementName) {
		init();
		log.info("Adding discount without " + elementName);
		discountsPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("discounts"));
	}
	
	@When("^I add Discounts details BUT provide a larger Start Range$")
	public void i_add_Discounts_details_BUT_provide_a_larger_Start_Range() {
		init();
		log.info("Add discount with start range > end range");
		discountsPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("discounts"));
	}
	
	@When("^I add Discounts details BUT provide an INVALID \"([^\"]*)\"$")
	public void i_add_Discounts_details_BUT_provide_an_INVALID(String elementName) {
		init();
		log.info("Adding discount with invalid " + elementName);
		discountsPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("discounts"));
	}
	
	@When("^I add overlapping discounts details for an Offer$")
	public void i_add_overlapping_discounts_details_for_an_Offer() {
		init();
		log.info("Adding discounts with start and end ranges that overlap");
		discountsPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("discounts"));
	}
}
