package gov.gsa.sst.delivery;

import java.net.MalformedURLException;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.commonpage.CommonUtilPage;

@Component
public class Delivery extends Steps {
	DeliveryPage deliveryPage;
	
	public Delivery() {
		// TODO Auto-generated constructor stub
	}

	public void init(){
		if(deliveryPage == null) 
			deliveryPage = new DeliveryPage(executionContext);
		deliveryPage.get();
	}
	
	@Given("^I selected Delivery at Offer level and am on the Delivery page$")
	public void  i_am_on_the_delivery_page_for_an_fpt_schedule() throws MalformedURLException, InterruptedException{
		init();
	}
	
	@Given("^I selected Delivery at SIN level and am on the Delivery page$")
	public void  i_selected_delivery_at_sin_level_and_am_on_the_delivery_page() throws MalformedURLException, InterruptedException{
		i_am_on_the_delivery_page_for_an_fpt_schedule();
	}
	
	@When("^I select delivery types for ALL delivery areas$")
	public void i_select_delivery_types_for_all_delivery_areas(){
		deliveryPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("delivery"));
	}
	
	@Then("^the Delivery option is updated and message is displayed: \"([^\"]*)\"$")
	public void the_delivery_option_is_updated_and_message_is_displayed(String msg) throws Throwable {
		CommonUtilPage.verifyAppMessage(msg, executionContext.getDriver());	
	}
}
