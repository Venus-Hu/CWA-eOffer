package gov.gsa.sst.wizardmanagement.specialcharges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Component
public class SpecialCharges extends Steps {
	private static Logger log = LoggerFactory.getLogger(SpecialCharges.class);
	private SpecialChargesPage specialChargesPage;
	
	private void init() {
		log.info("Loading 'Special Charges' page...");
		if (specialChargesPage == null) {
			specialChargesPage = new SpecialChargesPage(executionContext);
		}
		specialChargesPage.get();
	}
	
	@Given("^I am on the Special Charges page for an FPT Schedule$")
	public void i_am_on_the_Special_Charges_page_for_an_FPT_Schedule() throws InterruptedException, IOException {
	    init();
	}

	@When("^I add ALL available Special Charges for a SIN$")
	public void i_add_ALL_available_Special_Charges_for_a_SIN() {
	    init();
	    specialChargesPage.populateForm(executionContext.getCurrentScenarioObj().get("specialCharges").getAsJsonArray());
	 //   specialChargesPage.addUpdateSpecialChargesBtn();
	    specialChargesPage.clickContinueBtn();
	}

	@Then("^the Special Charges are added and displayed in the Special Charges list$")
	public void the_Special_Charges_are_added_and_displayed_in_the_Special_Charges_list() {
	   init();
	   JsonArray specialCharges = executionContext.getCurrentScenarioObj().get("specialCharges").getAsJsonArray();
	   Map<String, List<String>> expectedSpecialCharges = getSpecialChargesMap(specialCharges);
	   Map<String, List<String>> actualSpecialCharges = specialChargesPage.getSpecialChargesForSIN(specialCharges);
	   Assert.assertTrue("Actual and expected 'Special Charges' not the same", expectedSpecialCharges.equals(actualSpecialCharges));
	}

	@When("^I add at least one Special Charge for each SIN$")
	public void i_add_at_least_one_Special_Charge_for_each_SIN() {
		// The step below can be reused in this case because
		// the selection of special charges is data-driven
		i_add_ALL_available_Special_Charges_for_a_SIN();
	}
	
	/**
	 * Converts JSON array data for special charges into
	 * a map.
	 * 
	 * @param jsonArray
	 * @return
	 */
	private Map<String, List<String>> getSpecialChargesMap(JsonArray jsonArray) {
		Map<String, List<String>> map = new HashMap<>();
		jsonArray.forEach((element) -> {
			List<String> charges = new ArrayList<>();
			
			JsonObject specialCharge = element.getAsJsonObject();
			String sinNumber = specialCharge.get("sin").getAsString();
			
			specialCharge.get("charges").getAsJsonArray().forEach((charge) -> {
				charges.add(charge.getAsString());
			});
			charges.sort((c1,c2) -> c1.compareTo(c2));
			
			map.put(sinNumber, charges);
		});
		return map;
	}
}
