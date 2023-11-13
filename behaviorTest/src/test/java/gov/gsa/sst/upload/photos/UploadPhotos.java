package gov.gsa.sst.upload.photos;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.util.data.Offer;

@Component
public class UploadPhotos extends Steps {
	UploadPhotosPage photosPage;

	public void init(){
		if(photosPage == null){
			photosPage = new UploadPhotosPage(executionContext);
		}
		photosPage.get();
	}
	
	@Given("^I am on the Upload Photos page for a FPT Schedule$")
	public void i_am_on_the_upload_photos_page_for_a_FPT_Schedule() throws Exception{
		new Offer.Builder(executionContext).downloadUploadPricingTemplate().build();
		init();
	}

	@When("^I upload ALL photos in valid format that were identified in the product template$")
	public void  i_upload_all_photos_in_valid_format_that_were_identified_in_the_product_template() throws IOException {
		init();
		photosPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("uploadPhotos"));
		photosPage.validatePhotos();
	}
	
	@Then("^uploaded photos are validated successfully and message is \"([^\"]*)\"$")
	public void  uploaded_photos_are_validated_successfully_and_message_is(String message){
		CommonUtilPage.verifyAppMessage(message, executionContext.getDriver());
	}
	

	@Then("^I try to validate photos without uploading any photos$")
	public void  i_try_to_validate_photos_without_uploading_any_photos(){
		init();
		photosPage.validatePhotos();
	}
	
	@Given("^the vendor has uploaded a photo with the same name as an already existing photo for a contract$")
	public void the_vendor_uploads_photo_for_a_contract() {
		init();
		photosPage.verifyPhotos(executionContext.getCurrentScenarioObj().get("uploadPhotos").getAsJsonArray());
	}
	
	@When("^the vendor deletes the uploaded photo file$")
	public void the_vendor_deletes_the_uploaded_photo_file() {
		init();
		photosPage.deletePhotos(executionContext.getCurrentScenarioObj().get("deletePhotos").getAsJsonArray());
	}
	
	@Given("^the vendor has uploaded a photo with a unique name$")
	public void the_vendor_has_uploaded_a_photo_with_a_unique_name() {
		the_vendor_uploads_photo_for_a_contract();
	}
	
	@Then("^the user receives message: \"([^\"]*)\"$")
	public void the_user_receives_message(String message) {
		uploaded_photos_are_validated_successfully_and_message_is(message);
	}
	
	@Then("^the photo is deleted from the mod$")
	public void the_photo_is_deleted_from_the_mod() {
		init();
			
		Map<String, Boolean> results = photosPage.arePhotosUploaded(executionContext.getCurrentScenarioObj().get("deletePhotos").getAsJsonArray());
		results.forEach((photoFileName, isUploaded) -> {
			Assert.assertFalse("Photo "+photoFileName+" not deleted from 'PHOTOS' table", isUploaded);
		});
	}
}
