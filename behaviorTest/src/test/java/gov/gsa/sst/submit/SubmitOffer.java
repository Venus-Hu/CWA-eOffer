package gov.gsa.sst.submit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.IOException;
import java.util.Map;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comment.Steps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.offerslist.OffersListPage;
import gov.gsa.sst.util.DownloadUtil;
import gov.gsa.sst.util.LoadProperties;
import gov.gsa.sst.util.data.Offer;
import util.ActionByXpath;



@Component
public class SubmitOffer extends Steps {

	private static Logger log = LoggerFactory.getLogger(SubmitOffer.class);
	private SubmitOfferPage submitOfferPage;

	private void init() {
		log.info("Navigating to 'Submit eOffer/emod' page ...");
		if (submitOfferPage == null) {
			submitOfferPage = new SubmitOfferPage(executionContext);
		}
		submitOfferPage.get();
	}

	@Given("^I have completed all Offer sections$")
	public void i_have_completed_all_Offer_sections() throws Exception {
		offerSetup();
		init();
	}

	@Given("^I have completed all FPT Offer sections$")
	public void i_have_completed_all_fpt_Offer_sections() throws Exception {
		fptOfferSetup();
		//init(); //avoiding submit offer page load as FPT offer has refresh number update issue
	}

	@Given("^I have completed all non-FPT Offer sections$")
	public void i_have_completed_all_non_fpt_Offer_sections() throws Exception {
		fptOfferSetup();
	}

	@Given("^I have uploaded all required documents in the Uploads section$")
	public void i_have_uploaded_all_required_documents_in_the_Uploads_section() {
	    init();
	    Map<String, Boolean> results = submitOfferPage.areDocumentsUploaded(executionContext.getCurrentScenarioObj().getAsJsonArray("uploadedDocuments"));
	    results.forEach((document, isUploaded) -> {
	    	Assert.assertTrue("Document type '"+document+"' is not uploaded", isUploaded);
	    });
	}

	@When("^all menu items are complete$")
	public void all_menu_items_are_complete() {
	    Map<String, Boolean> results = LeftNavigationMenu.areStepsComplete(executionContext.getDriver(), executionContext.getCurrentScenarioObj().getAsJsonArray("completedMenuItems"));
	    results.forEach((menuItem, isComplete) -> {
	    	Assert.assertTrue("Menu item '"+menuItem+"' is not complete", isComplete);
	    });
	}

	@Then("^documents can be reviewed$")
	public void documents_can_be_reviewed() throws InvalidPasswordException, IOException {
	    init();
	    Map<String, Boolean> linkCheckResults = submitOfferPage.areDocumentsDownloadable(executionContext.getCurrentScenarioObj().getAsJsonArray("uploadedDocuments"));
	    linkCheckResults.forEach((documentType, isDownloadLinkPresent) -> {
	    	Assert.assertTrue("Download link for document type '"+documentType+"' is not present.", isDownloadLinkPresent);
	    });

	    if (!DownloadUtil.isLocalEnvironment()) {
	    	log.info("Assertions not run as environment is not local");
	    	return;
	    }

	    Map<String, Boolean> downloadResults = submitOfferPage.downloadDocuments(executionContext.getCurrentScenarioObj().getAsJsonArray("uploadedDocuments"));
	    downloadResults.forEach((documentName, isDownloaded) -> {
	    	log.info("Is document '" + documentName + "' downloaded - " + isDownloaded);
	    	Assert.assertTrue("Document name '"+documentName+"' could not be downloaded.", isDownloaded);
	    });

	    Map<String, Boolean> pdfInspectionResult = CommonUtilPage.verifyPDFDocuments(executionContext.getCurrentScenarioObj().getAsJsonArray("uploadedDocuments"));
	    pdfInspectionResult.forEach((documentKey, lineExists) -> {
	    	log.info("'" + documentKey + "' found in document? - " + lineExists);
	    	Assert.assertTrue("Line does not exist: " + documentKey, lineExists);
	    });
	}

	@Given("^I am on the Final Review page$")
	public void i_am_on_the_Final_Review_page() throws Exception {
		log.info("***Start the first step: I am on the Final Review page");
		i_have_completed_all_Offer_sections();
	}

	@When("^I complete ALL Disclaimer questions with valid responses$")
	public void i_complete_ALL_Disclaimer_questions_with_valid_responses() {
	    init();
	    submitOfferPage.answerDisclaimerQuestions(executionContext.getCurrentScenarioObj().getAsJsonArray("disclaimerQuestions"));
	}

	@When("^I submit my eOffer$")
	public void i_submit_my_eOffer() {
		String environment = LoadProperties.getProperty("environment");
		if (environment == null) {
			throw new RuntimeException("'environment' property is not defined");
		}
		if (!environment.contains("gsa")) {
			throw new RuntimeException("Offer can only be submitted in GSA environments");
		}
		init();
	    submitOfferPage.clickContinue();
	    submitOfferPage.clickFinalSubmit();
	}

//	@Then("^offer is submitted and message is displayed:  \"([^\"]*)\"$")
//	public void offer_is_submitted_and_message_is_displayed(String submissionMessage) {
//		// no need to init as page is a generic message page
//		String message = CommonUtilPage.getMessage(executionContext.getDriver(), 30);
//	    assertThat("Offer post-offer submission message", message, containsString(submissionMessage));
//	}

	@Then("^offer is submitted and message is displayed: \"([^\"]*)\"$")
	public void offer_is_submitted_and_message_is_displayed(String submissionMessage) throws Throwable {
		// no need to init as page is a generic message page
				String message = CommonUtilPage.getMessage(executionContext.getDriver(), 30);
			    assertThat("Offer post-offer submission message", message, containsString(submissionMessage));
	}

	@Then("^Offer Id is displayed in my Submitted eOffers list$")
	public void offer_Id_is_displayed_in_my_Submitted_eOffers_list() {
	    OffersListPage offersListPage = new OffersListPage(executionContext);
	    offersListPage.get();
	    if(ActionByXpath.isDisplayed(executionContext.getDriver(), "//h2[@id='submittedmods']/span[contains(@class, 'glyphicon-plus-sign')]", 1))
	    	Octo_SeleniumLibrary.clickElement(executionContext.getDriver(),By.xpath( "//h2[@id='submittedmods']/span[contains(@class, 'glyphicon-plus-sign')]"));

	    Assert.assertTrue("Solicitation is not submitted",
	    		offersListPage.isSolicitationInTable("submittedeofferstable", executionContext.getCurrentScenarioObj().get("scheduleNumber").getAsString()));
	}

	private void offerSetup() throws Exception {
		// Test offer for scenario will be created unless "execute.before"
		// is false or does not exist
		if (!LoadProperties.executeBeforeSteps()) {
			return;
		}
		new Offer.Builder(executionContext)
		.negotiators()
		.goodsAndServices()
		.standardResponses()
		.solClauseBasicInformation()
		.solClausePointOfContact()
		.solClauseOrderingInformation()
		.solClauseCSP()
		.solProvisionCorporateExperience()
		.solProvisionPastPerformance()
		.solProvisionQualityControl()
		.solProvisionRelevantExperience()
		.solProvisionTradeAct()
		.solProvisionSectionPartA()
		.solProvisionSectionPartB()
		.solProvisionSCAMatrix()
		.exception()
		.subContractingPlan()
		.uploadDocuments()
		.build();
	}

	private void fptOfferSetup() throws Exception {
		// Test offer for scenario will be created unless "execute.before"
		// is false or does not exist
		if (!LoadProperties.executeBeforeSteps()) {
			return;
		}
		new Offer.Builder(executionContext)
		.negotiators()
		.goodsAndServices()
		.wizardManagement()
		.completeWizardSubPages()
		.downloadUploadPricingTemplate()
		.uploadPhotos()
		.dataEntryOption()
		.ediOption()
		.productAnalysisReport()
		.finalPricingDocument()
		.standardResponses()
		.solClauseBasicInformation()
		.solClausePointOfContact()
		.solClauseOrderingInformation()
		.solClauseCSP()
		.solProvisionCorporateExperience()
		.solProvisionPastPerformance()
		.solProvisionQualityControl()
		.solProvisionRelevantExperience()
		.solProvisionTradeAct()
		.solProvisionSCAMatrix()
		.uploadDocuments()
		.build();
	}

	@Then("^I can perform Final Review of my eMod$")
	public void i_can_perform_Final_Review_of_my_eMod() throws Throwable {
		init();
		i_have_uploaded_all_required_documents_in_the_Uploads_section();
//		documents_can_be_reviewed();
	}

	@Then("^on mod completion, the mod response document displays contract is TDR$")
	public void on_mod_completion_the_mod_response_document_displays_contract_is_TDR() throws Throwable{
		i_can_perform_Final_Review_of_my_eMod();
	}
}
