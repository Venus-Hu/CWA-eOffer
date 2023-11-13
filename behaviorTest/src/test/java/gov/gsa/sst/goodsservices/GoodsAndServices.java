package gov.gsa.sst.goodsservices;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.Steps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import customUtility.Octo_SeleniumLibrary;

@Component
public class GoodsAndServices extends Steps {
	GoodsAndServicesPage goodsServicesPage;
	int TIMEOUT_IN_SECS = 3;

	public void init() {
		if (goodsServicesPage == null) {
			goodsServicesPage = new GoodsAndServicesPage(executionContext);
		}
		goodsServicesPage.get();
	}

	@Given("^I am on the Goods and Services page$")
	public void i_am_on_the_Goods_and_Services_page() throws Throwable {
		init();
	}

	@When("^I \"([^\"]*)\" SIN details on the Goods and Services page$")
	public void i_new_sin(String arg1) {
		String jsonName = "";
		JsonArray array = null;
		switch (arg1) {
		case "add":
			jsonName = "sinDetails";
			AddSinPage sinPage = goodsServicesPage.getSinPage();
			array = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices")
					.getAsJsonArray(jsonName);
			Iterator<JsonElement> iterator = array.iterator();
			while (iterator.hasNext()) {
				JsonElement element = (JsonElement) iterator.next();
				JsonObject sinObj = element.getAsJsonObject();
				sinPage.populateForm(sinObj);
			}
			break;
		case "edit":
			jsonName = "editSinDetails";
			array = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices")
					.getAsJsonArray(jsonName);
			goodsServicesPage.editSin(array);
			break;
		default:
			break;
		}

	}

	@When("^I add at least one SIN and complete Goods and Services details$")
	public void i_add_at_least_one_sin_and_complete_goods_and_services_details() throws Throwable {
		init();
		JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices");
		goodsServicesPage.populateForm(jsonObj);
	}

	@When("^I have added at least one SIN in the Goods and Services section$")
	public void i_have_added_at_least_one_sin_in_the_goods_and_services_section() throws Throwable {
		init();
		i_new_sin("add");
	}

	@When("^I select a SIN Group and SIN with all details$")
	public void i_select_a_sin_group_and_sin_with_all_details() {
		i_new_sin("add");
	}

	@When("^I select \"([^\"]*)\" Offer Category$")
	public void i_select_offer_category(String category) {
		JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices");
		goodsServicesPage.selectNaicsCodeByText(jsonObj.get("preponderanceNaicsCode").getAsString());
		goodsServicesPage.addPreponderanceOfWorkBtn();
		goodsServicesPage.selectOfferCategory(category);
		goodsServicesPage.clickSaveAndContinueButton();
	}

	@Then("^the SIN details will be displayed on Goods and Services page$")
	public void the_sin_details_will_be_displayed_on_goods_and_services_page() {
		init();
		JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices");
		goodsServicesPage.verifySinExists(jsonObj.getAsJsonArray("sinDetails"));
	}

	@Then("^the SIN is added with associated SIN Group and displayed in the SIN List$")
	public void the_sin_is_added_with_associated_sin_group_and_displayed_in_the_sin_list() {
		init();
		JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices");
		goodsServicesPage.verifySinExists(jsonObj.getAsJsonArray("sinDetails"));
	}

	@When("^I delete a SIN in the SIN list on Goods and services page$")
	public void i_delete_a_sin_in_the_sin_list() {
		init();
		JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices");
		goodsServicesPage.deleteSin(jsonObj);
	}


	@When("^I add a SIN but DO NOT provide Commercial Delivery schedule if outside (\\d+) days$")
	public void i_add_a_SIN_but_DO_NOT_provide_Commercial_Delivery_schedule_if_outside_days(int arg1) throws Throwable {
		init();
		i_new_sin("add");
	}


	@When("^I add a TDR SIN but do not respond to the prompt$")
	public void i_add_tdr_sin_but_do_not_respond_to_prompt() {
		AddSinPage sinDetails = new AddSinPage (executionContext);
		
		String jsonName = "sinDetails";
		JsonArray array = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices").getAsJsonArray(jsonName);
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			sinDetails.populateForm(sinObj);
		}
		Octo_SeleniumLibrary.clickElement(executionContext.getDriver(),By.xpath( "//*[@id='id_tdrResponseSave' and @value='Submit Response']"));
	}
}
