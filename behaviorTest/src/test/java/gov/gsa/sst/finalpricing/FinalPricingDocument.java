package gov.gsa.sst.finalpricing;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.util.data.Offer;




@Component
public class FinalPricingDocument extends Steps {
	private static Logger log = LoggerFactory.getLogger(FinalPricingDocument.class);
	private FinalPricingDocumentPage finalPage;
	
	public void init(){
		if(finalPage == null)
			finalPage = new FinalPricingDocumentPage(executionContext);
		finalPage.get();
	}
	
	@Given("I am on the Pricing Document page for an FPT Schedule")
	public void i_am_on_the_pricing_document_page_for_an_fpt_schedule() throws Exception{
		new Offer.Builder(executionContext).downloadUploadPricingTemplate().productAnalysisReport().build();
		init();
	}
	
	@When("^I generate the Pricing Document$")
	public void i_generate_the_pricing_document(){
		        init();
		        log.info("Generate Pricing document and wait for upload to complete");
		        finalPage.generatePricingDocument();
		        finalPage.waitForCompletion();	        
	}

	@When("^I try to generate the Final Pricing Document$")
	public void i_try_to_generate_the_pricing_document(){
		        init();
		        log.info("Generate Pricing document");
		        finalPage.generatePricingDocument();	        
	}
	
	@Then("^I check the status to verify Final Pricing Document is complete$")
	public void i_check_the_status_to_verify_final_pricing_document_is_complete(){
		        Assert.assertTrue("Final Pricing document is not complete", finalPage.isDocumentComplete());
	}
	
	@When("^I DO NOT Generate Final Pricing Document but select Continue option$")
	public void i_DO_NOT_Generate_Final_Pricing_Document_but_select_Continue_option()  {
		        init();		         
		        finalPage.saveAndContinue();	    
	}
	
	
	@Then("^the Final Pricing Document is generated with additional column Type of Change with \"([^\"]*)\" option$")
	public void the_Final_Pricing_Document_is_generated_with_additional_column_Type_of_Change_with_option(String changeType) throws Exception {
		        init();        
		        finalPage.viewPricingDocument();	    
	}

	@Then("^I view the Final Pricing Document and verify Remove All contents$")
	public void i_view_the_Final_Pricing_Document_and_verify_Remove_All_contents() throws Throwable {
		init();
	    finalPage.viewPricingDocument();	  
	}
	
}
