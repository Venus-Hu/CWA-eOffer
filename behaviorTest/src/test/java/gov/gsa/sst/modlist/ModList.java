package gov.gsa.sst.modlist;

import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.common.GetUEIandContract;

@Component
public class ModList extends Steps {
	private static Logger log = LoggerFactory.getLogger(ModList.class);
	protected ModListPage modListPage;

	@Given("^I am on the Modifications List page$")
	public void i_am_on_the_Modifications_List_page() throws Throwable {
		if (modListPage == null)
			modListPage = new ModListPage(executionContext);
		modListPage.get();
	}

	@When("^I navigate to Mod Types page$")
	public void i_navigate_to_mod_types_page() {
		modListPage.navigateToModTypePage();
	}

	@When("^I select mod types \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_select_mod_types_and(String modType, String arg2) throws Throwable {
		i_select_mod_types(modType);
	}

	@When("^I select mod types \"([^\"]*)\"$")
	public void i_select_mod_types(String modTypes) throws Throwable {
		String contractNumber = GetUEIandContract.getContractNumberByFilter(executionContext.getCurrentScenarioObj().get("contractNumber").getAsString());
		log.info("creating new mod for contract: " + contractNumber);
		modListPage.createNewMod(contractNumber);
	}

	@When("^I select GSA mod type: \"([^\"]*)\"$")
	public void i_select_gsa_mod_type(String modTypes) throws Throwable {
		String contractNumber = GetUEIandContract.getContractNumberByFilter(executionContext.getCurrentScenarioObj().get("contractNumber").getAsString());
		String mod = executionContext.getCurrentScenarioObj().get("modTypes").getAsJsonArray().get(0).getAsString();
		log.info("creating new mod for contract: " + contractNumber);
		modListPage.editSavedMod(contractNumber, mod);
	}

	@When("^I view GSA mod type: \"([^\"]*)\"$")
	public void i_view_gsa_mod_type(String modTypes) throws Throwable {
		String contractNumber = GetUEIandContract.getContractNumberByFilter(executionContext.getCurrentScenarioObj().get("contractNumber").getAsString());
		String mod = executionContext.getCurrentScenarioObj().get("modTypes").getAsJsonArray().get(0).getAsString();
		log.info("Viewing GSA init mod for contract: " + contractNumber);
		modListPage.viewMod(contractNumber, mod);
	}

	@Then("^\"([^\"]*)\" mod is not displayed$")
	public void mod_is_not_displayed(String modType) throws Throwable {
		modListPage.verifyModIsNotPresent(modType);
	}

	@Then("^\"([^\"]*)\" document displays specific text$")
	public void pdf_document_displays_text(String fileName) throws Exception {
		JsonArray array = executionContext.getCurrentScenarioObj().getAsJsonArray("uploadedDocuments");

		Map<String, Boolean> pdfInspectionResult = modListPage.viewDocument(array);
		pdfInspectionResult.forEach((documentKey, lineExists) -> {
			log.info("'" + documentKey + "' found in document? - " + lineExists);
			Assert.assertTrue("Line does not exist: " + documentKey, lineExists);
		});
	}
}
