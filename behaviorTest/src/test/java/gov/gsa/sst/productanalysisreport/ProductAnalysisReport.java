package gov.gsa.sst.productanalysisreport;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import gov.gsa.sst.util.data.Offer;

@Component
public class ProductAnalysisReport extends Steps {
	private static Logger log = LoggerFactory.getLogger(ProductAnalysisReport.class);
	private ProductAnalysisReportPage productAnalysisReportPage;
	
	private void init() {
		log.info("Initialize 'Product Analysis Report' page");
		if (productAnalysisReportPage == null) {
			productAnalysisReportPage = new ProductAnalysisReportPage(executionContext);
		}
		productAnalysisReportPage.get();
	}
	
	@Given("^I am on the Product Analysis Report page for an FPT Schedule$")
	public void i_am_on_the_Product_Analysis_Report_page_for_an_FPT_Schedule() throws Exception {
		createOffer();
		init();
	}

	@Given("^I generate the Product Analysis Report$")
	public void i_generate_the_Product_Analysis_Report() throws IOException {
	    init();
	    productAnalysisReportPage.generatePAR();
	}

	@When("^I check the status to verify Product Analysis Report is complete$")
	public void i_check_the_status_to_verify_Product_Analysis_Report_is_complete() {
		init();
		productAnalysisReportPage.waitForPARCompletion();
	}
	
	@When("^product data is validated successfully and message is \"([^\"]*)\"$")
	public void product_data_is_validated_successfully_and_message_is(String validationMessage) throws MalformedURLException, IOException, InterruptedException {
		init();
	    productAnalysisReportPage.validateProductData();
	    
	    Assert.assertTrue("Expected validation message was not displayed", productAnalysisReportPage.getAppMessage().contains(validationMessage));
	}
	
	private void createOffer() throws Exception {
		new Offer.Builder(executionContext)
		.downloadUploadPricingTemplate()
		.uploadPhotos()
		.build();
	}
}
