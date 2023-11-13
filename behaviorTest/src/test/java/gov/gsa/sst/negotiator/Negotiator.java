package gov.gsa.sst.negotiator;

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
public class Negotiator extends Steps {
	NegotiatorPage negotiatorPage = null;
	private static Logger log = LoggerFactory.getLogger(Negotiator.class);
	
	public Negotiator() {
		// TODO Auto-generated constructor stub
	}

	public void init(){
		if(negotiatorPage == null)
			negotiatorPage = new NegotiatorPage(executionContext);
		negotiatorPage.get();
	}
	
	@Given("^I am on the Add Negotiator Page$")
	public void i_am_on_the_add_negotiator_page(){
		init();
	}

	@When("^I add a Negotiator with all details$")
	public void i_add_a_negotiator_with_all_details(){
		if(negotiatorPage == null)
			negotiatorPage = new NegotiatorPage(executionContext);
		negotiatorPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("negotiator"));
	}
	
	@Then("^the Negotiator is added and displayed with correct role in the Negotiator List$")
	public void the_negotiator_is_added_and_displayed_with_correct_role_in_the_Negotiator_List(){
		negotiatorPage.verifyNegotiatorAndRole(executionContext.getCurrentScenarioObj().getAsJsonArray("negotiator"));
	}
	
	@Then("^I add a Negotiator who is authorized to Sign$")
	public void i_add_a_negotiator_who_is_authorized_to_sign(){
		i_add_a_negotiator_with_all_details();
	}
	
	@Then("^I add a Negotiator who is NOT authorized to Sign$")
	public void i_add_a_negotiator_who_is_not_authorized_to_sign(){
		i_add_a_negotiator_with_all_details();
	}

	@When("^I add a Negotiator but DO NOT provide Name$")
	public void  i_add_a_negotiator_but_do_not_provide_name(){
		i_add_a_negotiator_with_all_details();
	}

	@When("^I add a Negotiator but provide an INVALID email address$")
	public void i_add_a_negotiator_but_provide_an_invalid_email_address(){
		i_add_a_negotiator_with_all_details();
	}
		
	@When("^I edit a Negotiator in the Negotiator list$")
	public void i_edit_a_negotiator_in_the_negotiator_list(){
		negotiatorPage.editNegotiator(executionContext.getCurrentScenarioObj().getAsJsonArray("editNegotiator"));
	}
	
	@When("^I update Negotiator details but provide an INVALID phone number format$")
	public void i_update_negotiator_details_but_provide_an_invalid_phone_number_format(){
		i_edit_a_negotiator_in_the_negotiator_list();
	}

	@When("^I delete a Negotiator in the Negotiator list$")
	public void i_delete_a_negotiator_in_the_negotiator_list(){
		JsonArray array = executionContext.getCurrentScenarioObj().getAsJsonArray("deleteNegotiator");
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			String name = jsonObj.get("name").getAsString();
			negotiatorPage.deleteNegotiatorByName(name);
			log.info("Deleting Negotiator: " + name);
		}
	}
	
	@Then("^the Negotiator is updated and displayed in the Negotiator List$")
	public void the_negotiator_is_updated_and_displayed_in_the_negotiator_list(){
		negotiatorPage.verifyNegotiatorAndRole(executionContext.getCurrentScenarioObj().getAsJsonArray("editNegotiator"));
	}
	
	@Then("^the Negotiator is removed from the Negotiator List$")
	public void the_negotiator_is_removed_from_the_negotiator_list(){
		init();
		JsonArray array = executionContext.getCurrentScenarioObj().getAsJsonArray("deleteNegotiator");
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			Assert.assertFalse("Deleted negotiator found in application", negotiatorPage.isNegotiatorPresent(jsonObj));
		}
	}
}
