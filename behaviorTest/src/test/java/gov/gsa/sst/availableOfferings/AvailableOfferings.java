package gov.gsa.sst.availableOfferings;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Component
public class AvailableOfferings extends Steps {

	AvailableOfferingsPage availableOfferingsPage;
	int TIMEOUT_IN_SECS = 3;

	public void init() {
		if (availableOfferingsPage == null) {
			availableOfferingsPage = new AvailableOfferingsPage(executionContext);
		}
		availableOfferingsPage.get();
	}

	@Given("^I am on the Available Offerings page$")
	public void i_am_on_the_Available_Offerings_page() throws Throwable {
		init();
	}
    //MCC-Complementary SIN
	@When("^I add one base SIN and Complementary SIN and complete Available Offerings details$")
	public void i_add_one_base_SIN_and_Complementary_SIN_and_complete_Available_Offerings_details() throws Throwable {
		i_add_at_least_one_SIN_and_complete_Available_Offerings_details();
	}

	@When("^I add at least one SIN and complete Available Offerings details$")
	public void i_add_at_least_one_SIN_and_complete_Available_Offerings_details() throws Throwable {
		JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices");
		availableOfferingsPage.populateForm(jsonObj);
	}

	@Then("^the SIN details will be displayed on Available Offerings page$")
	public void the_SIN_details_will_be_displayed_on_Available_Offerings_page() throws Throwable {
		availableOfferingsPage.verifySinExists(executionContext.getCurrentScenarioObj().get("goodsAndServices")
				.getAsJsonObject().getAsJsonArray("sinDetails"));
	}

	@Given("^I \"([^\"]*)\" SIN details on the Available Offerings page$")
	public void i_SIN_details_on_the_Available_Offerings_page(String action) throws Throwable {
		switch (action) {
		case "add":
			JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices");
			availableOfferingsPage.populateForm(jsonObj);
			break;
		case "edit":
			JsonArray array = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices")
					.getAsJsonArray("editSinDetails");
			availableOfferingsPage.editSin(array);
			break;
		default:
			break;
		}
	}

	@When("^I delete a SIN in the SIN list on Available Offerings page$")
	public void i_delete_a_SIN_in_the_SIN_list_on_Available_Offerings_page() throws Throwable {
		init();
		JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices");
		availableOfferingsPage.deleteSin(jsonObj);
	}

	@Then("^the SIN is removed from the SIN List$")
	public void the_sin_is_removed_from_the_sin_list() {
		boolean notExist = availableOfferingsPage.verifySinDoesNotExist();
		Assert.assertTrue("Sin is not deleted", notExist);
	}

	@Then("^I select preponderance of work$")
	public void i_select_preponderance_of_work() {
		JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices").getAsJsonObject("preponderance");
		availableOfferingsPage.selectPreponderance(jsonObj);
	}

	@When("^I add a SIN but provide an INVALID Estimated Sales$")
	public void i_add_a_SIN_but_provide_an_INVALID_Estimated_Sales() throws Throwable {
		init();
		i_SIN_details_on_the_Available_Offerings_page("add");
	}

	@When("^I \"([^\"]*)\" SIN details on the Available Offerings page but DO NOT provide an Estimated Sales$")
	public void i_SIN_details_on_the_Available_Offerings_page_but_DO_NOT_provide_an_Estimated_Sales(String arg1)
			throws Throwable {
		String jsonName = "editSinDetails";
		JsonArray array = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices")
				.getAsJsonArray(jsonName);
		availableOfferingsPage.editSin(array);
	}

}
