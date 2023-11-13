package gov.gsa.sst.administrative;

import java.net.MalformedURLException;
import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.upload.UploadDocumentsPage;

@Component
public class AdministrativeMod extends Steps {
	private static final int TIME_OUT_SECONDS = 5;
	private static Logger log = LoggerFactory.getLogger(AdministrativeMod.class);
	private AdministrativeModPage adminModPage;
	
	private void init() {
		if (adminModPage == null) {
			adminModPage = new AdministrativeModPage(executionContext);
		}
		adminModPage.get();
	}
	
	private void populateFormAndAssertAdministrativeStatus() {
		init();
		adminModPage.populateForm(executionContext.getCurrentScenarioObj().get("administrativeMods").getAsJsonArray());
		adminModPage.load();
		Assert.assertTrue("'Administrative' menu is not complete", LeftNavigationMenu.isStepComplete(executionContext.getDriver(), "Administrative", TIME_OUT_SECONDS));
	}
	
	@Given("^I am on the Administrative eMod \"([^\"]*)\" page$")
	public void i_am_on_the_Administrative_eMod_page(String arg1) {
	    init();
	}

	@When("^I update the UEI Number$")
	public void i_update_the_UEI_Number() {
		init();
		adminModPage.populateForm(executionContext.getCurrentScenarioObj().get("administrativeMods").getAsJsonArray());
	}

	@Then("^Corporate Information is displayed for the new UEI$")
	public void corporate_Information_is_displayed_for_the_new_UEI() {
		init();
		
		String scenarioUEI = executionContext.getCurrentScenarioObj().get("UEI").getAsString();
		log.info("Scenario UEI is " + scenarioUEI);
		
		CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
		Assert.assertTrue("UEI was not updated in Corporate Information page", corpInfoPage.isUEIMatch(scenarioUEI));
	}

	@When("^I complete Address Change details successfully$")
	public void i_complete_Address_Change_details_successfully() {
		populateFormAndAssertAdministrativeStatus();
	}

	@Given("^I am on the Final Review page in eMod for Address Change mod$")
	public void i_am_on_the_Final_Review_page_in_eMod_for_Address_Change_mod() {
		populateFormAndAssertAdministrativeStatus();
	}

	@When("^I update Point of Contact Name successfully$")
	public void i_update_Point_of_Contact_Name_successfully() {
		populateFormAndAssertAdministrativeStatus();
		adminModPage.verifySpecialCharacterHandling(executionContext.getCurrentScenarioObj().get("verifyAdministrativeMods").getAsJsonArray());
	}
	
	@When("^I complete Contract Admin POC details successfully$")
	public void i_complete_Contract_Admin_POC_details_successfully() {
		populateFormAndAssertAdministrativeStatus();	
		Map<String, Boolean> results = adminModPage.pointsOfContactExist(executionContext.getCurrentScenarioObj().get("verifyPOC").getAsJsonArray());
		results.forEach((pointOfContact, isDisplayed) -> {
			Assert.assertTrue(pointOfContact + " was not displayed", isDisplayed);});
	}

	@When("^I update Email Address successfully$")
	public void i_update_Email_Address_successfully() {
		init();
		populateFormAndAssertAdministrativeStatus();
	}

	@Given("^I am on the Final Review page in eMod for Email Address Change mod$")
	public void i_am_on_the_Final_Review_page_in_eMod_for_Email_Address_Change_mod() {
	    populateFormAndAssertAdministrativeStatus();
	}

	@When("^I update Fax number successfully$")
	public void i_update_Fax_number_successfully() {
		populateFormAndAssertAdministrativeStatus();
	}

	@When("^I complete Industrial Funding Fee POC details successfully$")
	public void i_complete_Industrial_Funding_Fee_POC_details_successfully() {
		init();
		adminModPage.populateForm(executionContext.getCurrentScenarioObj().get("administrativeMods").getAsJsonArray());
		
		Map<String, Boolean> results = adminModPage.pointsOfContactExist(executionContext.getCurrentScenarioObj().get("pointOfContact").getAsJsonArray());
		results.forEach((pointOfContact, isDisplayed) -> {
			Assert.assertTrue(pointOfContact + " was not displayed", isDisplayed);});
	}

	@When("^I complete Negotiator details successfully$")
	public void i_complete_Negotiator_details_successfully() {
	   init();
	   adminModPage.populateForm(executionContext.getCurrentScenarioObj().get("administrativeMods").getAsJsonArray());
	   
	   Map<String, Boolean> results = adminModPage.isNegotiatorAddedToMod(executionContext.getCurrentScenarioObj().get("negotiator").getAsJsonArray());
	   results.forEach((negotiator, isAdded) -> {
		   Assert.assertTrue("Negotiator '"+negotiator+"' was not added to contract", isAdded);});
	}

	@When("^I have uploaded Agent Authorization Letter documents successfully$")
	public void i_have_uploaded_Agent_Authorization_Letter_documents_successfully() throws MalformedURLException {
		log.info("Uploading document(s) for negotiator mod");
		UploadDocumentsPage uploadDocsPage = new UploadDocumentsPage(executionContext);
		uploadDocsPage.get();
		uploadDocsPage.documentAction(executionContext.getCurrentScenarioObj().get("uploadDocuments").getAsJsonArray());
		 
		Map<String, Boolean> results = uploadDocsPage.areDocumentsUploaded(executionContext.getCurrentScenarioObj().get("uploadDocuments").getAsJsonArray());
		results.forEach((documentName, isUploaded) -> {
			Assert.assertTrue("Document '"+documentName+"' was not uploaded", isUploaded);});
	}

	@When("^I complete Order POC details successfully$")
	public void i_complete_Order_POC_details_successfully() {
		init();
		adminModPage.populateForm(executionContext.getCurrentScenarioObj().get("administrativeMods").getAsJsonArray());
		
		Map<String, Boolean> results = adminModPage.pointsOfContactExist(executionContext.getCurrentScenarioObj().get("verifyPOC").getAsJsonArray());
		results.forEach((pointOfContact, isDisplayed) -> {
			Assert.assertTrue(pointOfContact + " was not displayed", isDisplayed);});
	}

	@When("^I complete Point of Contacts for Manufacturers, Dealers, Resellers, Agents details successfully$")
	public void i_complete_Point_of_Contacts_for_Manufacturers_Dealers_Resellers_Agents_details_successfully() {
	    populateFormAndAssertAdministrativeStatus();
	}

	@When("^I update Telephone number successfully$")
	public void i_update_Telephone_number_successfully() {
	    populateFormAndAssertAdministrativeStatus();
	}

	@When("^I update my website URL successfully$")
	public void i_update_my_website_URL_successfully() {
	    populateFormAndAssertAdministrativeStatus();
	}
}
