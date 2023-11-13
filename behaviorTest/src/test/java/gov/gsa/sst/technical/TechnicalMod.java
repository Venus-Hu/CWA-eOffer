package gov.gsa.sst.technical;

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
public class TechnicalMod extends Steps {
	private TechnicalModPage technicalPage;
	private static Logger log = LoggerFactory.getLogger(TechnicalMod.class);
	
	public void init(){
		if(technicalPage == null)
			technicalPage = new TechnicalModPage(executionContext);
		technicalPage.get();
	}
	
	@Given("^I am on the Technical eMod \"([^\"]*)\" page$")
	public void i_am_on_the_technical_emod_page(String modType){
		log.info("Navigating to mod " + modType);
		init();
	}
	
	@When("^I complete Geographic Coverage details by SIN successfully$")
	public void i_complete_technical_details_successfully(){
		technicalPage.populateForm(executionContext.getCurrentScenarioObj().get("technicalMod").getAsJsonObject());
	}
	
	@When("^I complete Part Number Change details successfully$")
	public void i_complete_part_number_change_details_successfully(){
		i_complete_technical_details_successfully();
	}
	
	@When("^I complete Product Descriptive Change details successfully$")
	public void i_complete_product_descriptive_change_details_successfully(){
		i_complete_technical_details_successfully();
	}
	
	@When("^I complete Service Descriptive Change details successfully$")
	public void i_complete_service_descriptive_change_details_by_SIN_successfully(){
		i_complete_technical_details_successfully();
	}
	
	@When("^I \"([^\"]*)\" SIN to update scope on the Update SIN page$")
	public void i_SIN_to_update_scope_on_the_Update_SIN_page(String action){
		JsonObject modObject = executionContext.getCurrentScenarioObj().get("technicalMod").getAsJsonObject();
		technicalPage.selectClause(modObject.get("clause").getAsString());
		technicalPage.addSinDetails(modObject.getAsJsonArray("sinDetails"));
	}
	
	@When("^I delete a SIN in the SIN list on Technical eMod \"([^\"]*)\" page$")
	public void i_delete_a_SIN_in_the_SIN_list_on_Technical_eMod(String modType){
		JsonObject modObject = executionContext.getCurrentScenarioObj().get("technicalMod").getAsJsonObject();
		JsonArray array = modObject.getAsJsonArray("deleteSinDetails");
		//technicalPage.selectClause(modObject.get("clause").getAsString());
		technicalPage.deleteSin(array);
	}
	
	@Then("^the SIN is removed from the SIN List on Technical eMod \"([^\"]*)\" page$")
	public void the_SIN_is_removed_from_the_SIN_List_on_Technical_eMod_page(String modType){
	
		JsonArray array = executionContext.getCurrentScenarioObj().get("technicalMod").getAsJsonObject().getAsJsonArray("deleteSinDetails");
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			Assert.assertFalse("SIN found after deletion", technicalPage.doesSinExist(sinObj));
		}
	}
}
