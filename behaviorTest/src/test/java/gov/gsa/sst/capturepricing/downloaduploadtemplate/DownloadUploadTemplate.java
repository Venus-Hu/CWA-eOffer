package gov.gsa.sst.capturepricing.downloaduploadtemplate;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.capturepricing.CaptureDataPage;



@Component
public class DownloadUploadTemplate extends Steps{
	
	DownloadUploadTemplatePage downloadUploadTemplatePage;
	private static Logger log = LoggerFactory.getLogger(DownloadUploadTemplate.class);

	@Given("^I am on the Capture Pricing Download/Upload Template page for an FPT Schedule$")
	public void i_am_on_the_Capture_Pricing_Download_Upload_Template_page_for_an_FPT_Schedule() throws Throwable {
		init();
	}

	@When("^I complete the template with pricing details for ALL SINs on my offer$")
	public void i_complete_the_template_with_pricing_details_for_ALL_SINs_on_my_offer() throws Throwable {
		init();
		log.info("Updating the pricing template document...");
		JsonObject worksheetDataObj = executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate")
				.getAsJsonObject("worksheetData");
		JsonObject pageDataObj = executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate");
		DownloadUploadTemplateUtil.updatePricingDocument(executionContext, worksheetDataObj, pageDataObj);
	}
	
	@Given("^I download the product template$")
	public void i_download_the_product_template() throws Throwable {
		init();
		log.info("Dowloading the pricing template document...");
		boolean isFileDownloaded = DownloadUploadTemplateUtil.downloadTemplateDocument(executionContext);
		Assert.assertTrue("Template file is not downloaded", isFileDownloaded);
	}

	@When("^I upload and validate the completed product template$")
	public void i_upload_and_validate_the_completed_product_template() throws Throwable {
		log.info("Uploading the updated pricing template document...");
		DownloadUploadTemplateUtil.uploadDocAndWait(executionContext);
		CaptureDataPage cpPage = new CaptureDataPage(executionContext);
		cpPage.validateData();
		
	}
	
	@When("^I try to upload the completed product template with invalid data$")
	public void i_upload_and_the_completed_product_template_with_invalid_data() throws Throwable {
		log.info("Uploading the updated  pricing template document with invalid data...");
		Assert.assertTrue("Invalid data file upload did not fail as expected",downloadUploadTemplatePage.uploadAndValidateError());
	}
	
	public void init(){
		if(downloadUploadTemplatePage == null) 
			downloadUploadTemplatePage = new DownloadUploadTemplatePage(executionContext);
		downloadUploadTemplatePage.get();
	}
	
	@When("^I provide an invalid pricing document template and status fails$")
	public void I_provide_an_invalid_pricing_document_template_and_status_fails() throws Throwable {
		log.info("Uploading the invalid pricing template document...");
		init();
		downloadUploadTemplatePage.uploadInvalidDocument();
	}	
		
	@When("^I update the template with invalid pricing details for \"([^\"]*)\" sheet$")
	public void i_update_the_template_with_invalid_pricing_details(String sheets) throws Throwable {
		init();
		log.info("Updating the pricing template document with invalid data from template file...");
		downloadUploadTemplatePage.generateDocumentFromTemplate(executionContext, executionContext.getCurrentScenarioObj().get("downloadUploadTemplate").getAsJsonObject());
	}
	
	@Then("^template document upload process fails and data errors are verified$")
	public void template_document_upload_process_fails_and_data_errors_are_verified() throws Exception{
		downloadUploadTemplatePage.readErrorFile(executionContext);
	}
	
}
