package gov.gsa.sst.additions;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import util.ActionByXpath;

@Component
public class AdditionsMod extends Steps {

	protected AddProductModPage addProductModPage;
	protected AddSinModPage addSinModPage;
	protected AddLaborCategory addLaborPage;
	protected AdditionsPage additionsPage;
	private static Logger log = LoggerFactory.getLogger(AdditionsMod.class);


	public void init() {
		String modType = executionContext.getCurrentScenarioObj().get("modTypes").getAsJsonArray().get(0).getAsString();
		switch (modType) {
		case "Add SIN":
			initSin();
			break;
		case "Add Product(s)":
			initProduct();
			break;
		case "Add Labor Category and/or Service Offerings":
			initLaborCategory();
			break;
		default:
			break;
		}
		additionsPage = new AdditionsPage(executionContext);
	}

	protected void initProduct() {
		if (addProductModPage == null)
			addProductModPage = new AddProductModPage(executionContext);
		addProductModPage.get();
	}

	protected void initSin() {
		if (addSinModPage == null)
			addSinModPage = new AddSinModPage(executionContext);
		addSinModPage.get();
	}

	protected void initLaborCategory() {
		if (addLaborPage == null)
			addLaborPage = new AddLaborCategory(executionContext);
		addLaborPage.get();
	}

	@Given("^I have the option to revise CSP for \"([^\"]*)\" mod$")
	public void I_have_the_option_to_revise_CSP_option_on_Additions_page(String modType) {
		init();
		Assert.assertTrue("Revise CSP option was not displayed", additionsPage.verifyReviseCspExists());
		if(!modType.equalsIgnoreCase("Add SIN"))
			Assert.assertTrue("Revise CSP option was not displayed", ActionByXpath.isDisplayed(executionContext.getDriver(),
				"//span[@id='cspQuest' and contains(.,'Do you want to revise your Commercial Sales Practice data?')]",
				1));
	}

	@Given("^I should not have the option to revise CSP on Additions page$")
	public void I_should_not_have_the_option_to_revise_CSP_on_Additions_page() {
		Assert.assertFalse("Revise CSP option was displayed", additionsPage.verifyReviseCspExists());
	}

	@Given("^I am on the Additions eMod \"([^\"]*)\" page$")
	public void i_am_on_the_Additions_eMod_page(String mod) throws Throwable {
		init();
	}

	@When("^I complete Add Product details successfully$")
	public void i_complete_Add_Product_details_successfully() throws Throwable {
		addProductModPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices"));
	}

	@When("^I complete Add Product details but do NOT Respond to revise CSP$")
	public void i_complete_Add_Product_details_but_no_response_to_csp() throws Throwable {
		i_complete_Add_Product_details_successfully();
	}

	@Given("I complete Add Labor Category and/or Service Offerings with revise CSP successfully")
	public void i_complete_Add_Labor_Category_and_Service_Offerings_successfully() {
		addLaborPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices"));
	}

	@Given("^I complete Add Labor Category and/or Service Offerings details without revise CSP$")
	public void I_complete_Add_Labor_Category_or_Service_Offerings_details_without_revise_CSP() {
		i_complete_Add_Labor_Category_and_Service_Offerings_successfully();
	}

	@Given("^I am on the Additions eMod \"([^\"]*)\" page for a FPT Non-TDR contract$")
	public void i_am_on_the_Additions_eMod_page_for_a_FPT_Non_TDR_contract(String arg1) throws Throwable {
		init();
	}

	@When("^I add at least one SIN as part of the mod and select Offer Category$")
	public void i_add_at_least_one_SIN_as_part_of_the_mod_and_select_Offer_Category() throws Throwable {
		JsonObject scenObj = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices");
		addSinModPage.selectAndAddSins(scenObj);
		addSinModPage.selectOfferCategory(scenObj);
	}

	@Then("^the SIN details will be displayed on eMod Add SIN page$")
	public void the_SIN_details_will_be_displayed_on_eMod_Add_SIN_page() throws Throwable {
		init();
		addSinModPage.verifySinsAdded(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices")
				.getAsJsonArray("sinDetails"));
		addSinModPage.clickSaveandContinueBtn();
	}

	@Then("^Offer Category selection will be displayed$")
	public void offer_Category_selection_will_be_displayed() throws Throwable {
		log.info("Verifying the offer category table is displayed");
		addSinModPage.verifyOfferCategoryIsDisplayed();
	}

	@When("^I Revise CSP successfully$")
	public void i_Revise_CSP_successfully() throws Throwable {
		addSinModPage.reviseCspForAddSinMod();
		addSinModPage.clickSaveandContinueBtn();
	}

//	@Given("^I am on the Additions eMod \"([^\"]*)\" page for a Non-FPT Non-TDR contract$")
//	public void i_am_on_the_Additions_eMod_page_for_a_Non_FPT_Non_TDR_contract(String arg1) throws Throwable {
//		init();
//	}

	@Given("^I am on the Additions eMod \"([^\"]*)\" page for a Non-TDR contract$")
	public void i_am_on_the_Additions_eMod_page_for_a_Non_TDR_contract(String arg1) throws Throwable {
		init();
	}

	@When("^I add at least one SIN as part of the mod$")
	public void i_add_at_least_one_SIN_as_part_of_the_mod() throws Throwable {
		addSinModPage.selectAndAddSins(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices"));
	}

	@When("^I add at least one SIN as part of the mod but do NOT respond to revise CSP$")
	public void I_add_at_least_one_SIN_as_part_of_the_mod_but_do_NOT_respond_to_revise_CSP() throws Throwable {
		completeAddSinModDetails();
	}

	@Given("^I complete Add SIN details successfully$")
	public void completeAddSinModDetails() throws Throwable {
		init();
		addSinModPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices"));
		// CommonUtilPage.completeMod(executionContext);
	}

	@Given("^I \"([^\"]*)\" TDR participation for a SIN and complete SIN addition$")
	public void I_TDR_participation_for_a_SIN_and_complete_SIN_addition(String accept) {
		init();
		addSinModPage.selectAndAddSins(executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices"));
	}

	@Then("^I should not have the option to select fastlane on Additions page$")
	public void i_should_not_have_the_option_to_select_fastlane_on_Additions_page() throws Throwable {
		Assert.assertFalse("update Fastlane option was displayed", additionsPage.isFastlaneDisplayed());
	}

	@Then("^I should have the option to select fastlane on Additions page$")
	public void i_should_have_the_option_to_select_fastlane_on_Additions_page() throws Throwable {
		Assert.assertFalse("update Fastlane option was not displayed", additionsPage.isFastlaneDisplayed());
	}
	
	@Given("^I am on the Final Review page in eMod for Add Labor Category and/or Service Offerings mod for Non-FPT contract$")
	public void i_am_on_the_Final_Review_page_in_eMod_for_Add_Labor_Category_and_or_Service_Offerings_mod_for_Non_FPT_contract() throws Throwable {
	    
	}

	@When("^I submit my eMod$")
	public void i_submit_my_eMod() throws Throwable {
	    
	}

	@Then("^eMod is submitted and message is displayed:  \"([^\"]*)\"$")
	public void emod_is_submitted_and_message_is_displayed(String arg1) throws Throwable {
	   
	}

	@Then("^Modification Id is displayed in my Submitted eMods list$")
	public void modification_Id_is_displayed_in_my_Submitted_eMods_list() throws Throwable {
	   
	}
}
