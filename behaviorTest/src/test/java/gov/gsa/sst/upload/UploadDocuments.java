package gov.gsa.sst.upload;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.util.DownloadUtil;
import util.ActionByXpath;

@Component
public class UploadDocuments extends Steps {
	private static Logger log = LoggerFactory.getLogger(UploadDocuments.class);

	private UploadDocumentsPage uploadDocumentsPage;

	private void init() {
		log.info("Instantiate Upload Documents page...");
		if (uploadDocumentsPage == null) {
			uploadDocumentsPage = new UploadDocumentsPage(executionContext);
		}
		log.info("Navigate to Upload Documents page...");
		uploadDocumentsPage.get();
	}

	@Given("^I am on the Upload Documents page$")
	public void i_am_on_the_Upload_Documents_page() {
		init();
	}

	@When("^I upload a document in valid format and provide document details$")
	public void i_upload_a_document_in_valid_format_and_provide_document_details() throws MalformedURLException {
		init();
		uploadDocumentsPage.documentAction(executionContext.getCurrentScenarioObj().getAsJsonArray("uploadDocuments"));
	}

	@Then("^the document\\(s\\) are uploaded and displayed with document type and name in the Documents List$")
	public void the_document_s_are_uploaded_and_displayed_with_document_type_and_name_in_the_Documents_List() {
		init();
		uploadDocumentsPage.get();

		Map<String, Boolean> results = uploadDocumentsPage
				.areDocumentsUploaded(executionContext.getCurrentScenarioObj().getAsJsonArray("uploadDocuments"));
		results.forEach((documentName, isUploaded) -> {
			log.info("Document '" + documentName);
			log.info("isUploaded '" + isUploaded);
			Assert.assertTrue("Document '" + documentName + "' is not uploaded", isUploaded);
		});
	}

	@When("^I upload a document but DO NOT select document from computer$")
	public void i_upload_a_document_but_DO_NOT_select_document_from_computer() throws MalformedURLException {
		i_upload_a_document_in_valid_format_and_provide_document_details();
	}

	@When("^I upload a document with an INVALID file format$")
	public void i_upload_a_document_with_an_INVALID_file_format() throws MalformedURLException {
		i_upload_a_document_in_valid_format_and_provide_document_details();
	}

	@When("^I upload a document with a file size greater than the required size$")
	public void i_upload_a_document_with_a_file_size_greater_than_the_required_size() throws MalformedURLException {
		i_upload_a_document_in_valid_format_and_provide_document_details();
	}

	@When("^I have uploaded ALL REQUIRED documents successfully$")
	public void i_have_uploaded_ALL_REQUIRED_documents_successfully() throws MalformedURLException {
		i_upload_a_document_in_valid_format_and_provide_document_details();
	}

	@When("^I have uploaded ALL REQUIRED Supporting Documentation successfully$")
	public void i_have_uploaded_ALL_REQUIRED_Supporting_documents_successfully() throws MalformedURLException {
		i_have_uploaded_ALL_REQUIRED_documents_successfully();
	}

	@When("^I have uploaded ALL REQUIRED Legal documents successfully$")
	public void i_have_uploaded_ALL_REQUIRED_Legal_documents_successfully() throws MalformedURLException {
		i_have_uploaded_ALL_REQUIRED_documents_successfully();
	}

	@When("^I have uploaded ALL REQUIRED Wage Determination documents successfully$")
	public void i_have_uploaded_ALL_REQUIRED_wage_determinations_documents_successfully() throws MalformedURLException {
		i_have_uploaded_ALL_REQUIRED_documents_successfully();
	}

	@When("^I have uploaded ALL REQUIRED Labor Category documents successfully$")
	public void i_have_uploaded_ALL_REQUIRED_Labor_Category_documents_successfully() throws MalformedURLException {
		i_have_uploaded_ALL_REQUIRED_documents_successfully();
	}

	@When("^I delete a document in the Documents List$")
	public void i_delete_a_document_in_the_Documents_List() throws MalformedURLException {
		init();
		uploadDocumentsPage
				.documentAction(executionContext.getCurrentScenarioObj().getAsJsonArray("uploadDocumentsToDelete"));
	}

	@Then("^the document is removed from the Documents List$")
	public void the_document_is_removed_from_the_Documents_List() {
		init();

		Map<String, Boolean> results = uploadDocumentsPage.areDocumentsUploaded(
				executionContext.getCurrentScenarioObj().getAsJsonArray("uploadDocumentsToDelete"));
		results.forEach((documentName, isUploaded) -> {
			Assert.assertFalse("Document '" + documentName + "' was not deleted", isUploaded);
		});
	}

	@When("^I view a document in the Documents List$")
	public void i_view_a_document_in_the_Documents_List() throws MalformedURLException {
		init();
		uploadDocumentsPage
				.documentAction(executionContext.getCurrentScenarioObj().getAsJsonArray("uploadDocumentsToView"));
	}

	@Then("^the document is downloaded successfully$")
	public void the_document_is_downloaded_successfully() {
		init();

		if (!DownloadUtil.isLocalEnvironment()) {
			log.info("Assertions not run as environment is not local");
			return;
		}

		Map<String, Boolean> results = uploadDocumentsPage.areDocumentsDownloaded(
				executionContext.getCurrentScenarioObj().getAsJsonArray("uploadDocumentsToView"));
		results.forEach((documentName, isDownloaded) -> {
			Assert.assertTrue("Document '" + documentName + "' was not downloaded", isDownloaded);
		});
	}

	@When("^I upload an additional document with same document type$")
	public void i_upload_an_additional_document_with_same_document_type() throws MalformedURLException {
		init();
		uploadDocumentsPage
				.documentAction(executionContext.getCurrentScenarioObj().getAsJsonArray("uploadDocumentsToAdd"));

		Map<String, List<String>> uploadedDocs = uploadDocumentsPage
				.getUploadedDocuments(executionContext.getCurrentScenarioObj().getAsJsonArray("documentTypes"));
		executionContext.getCurrentScenarioObj().getAsJsonArray("uploadDocumentsToAdd").forEach(document -> {
			String documentType = document.getAsJsonObject().get("type").getAsString();
			String documentName = document.getAsJsonObject().get("documentName").getAsString();

			Assert.assertTrue(
					"Document type '" + documentType + "' does not have a file of name '" + documentName + "'",
					uploadedDocs.get(documentType).contains(documentName));
		});
	}

	@When("^I am required to upload \"([^\"]*)\" document in the Uploads section$")
	public void i_am_required_to_upload_document_in_the_uploads_section(String docName) {
		init();
		Assert.assertTrue("Uploading " + docName + " is not manadatory!",
				uploadDocumentsPage.isDocumentMandatory(docName));
	}

	// div/p[text()='Selected file to upload is greater than 100.0 MB. Only
	// files of size less than or equal to 100.0 MB can be uploaded.']
	@Then("^Message is displayed: \"([^\"]*)\"$")
	public void message_is_displayed(String message) {
		boolean isFound = ActionByXpath.isDisplayed(executionContext.getDriver(),
				"//div/p[text()='" + message + "']",
				1);
		Assert.assertTrue("Could not find the error message: " + message, isFound);
	}
	
	@Then("^the documents are uploaded and displayed with document type and name in the Documents List$")
	public void docs_are_uploaded_and_displayed() {
		uploadDocumentsPage.areDocumentsUploaded(executionContext.getCurrentScenarioObj().getAsJsonArray("uploadDocuments"));
	}
}
