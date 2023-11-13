package gov.gsa.sst.dunspage;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.offerslist.OffersListPage;

@Component
public class Duns extends Steps {

	private DunsPage dunsPage;

	@Given("^I am on the DUNS page$")
	public void selectDunsNumber() throws Throwable {
		if (dunsPage == null) {
			dunsPage = new DunsPage(executionContext);
		}
		dunsPage.get();
	}

	@Then("^I provide a valid DUNS Number$")
	public void i_provide_a_valid_duns_number() {
		dunsPage.populateUEI();
	}

	@Then("^I provide a empty DUNS Number$")
	public void i_provide_a_empty_duns_number() {
		dunsPage.populateUEI(executionContext.getCurrentScenarioObj().get("UEI").getAsString().trim());
	}

	@Then("^I provide an invalid DUNS Number$")
	public void i_provide_an_invalid_duns_number() {
		dunsPage.populateUEI(executionContext.getCurrentScenarioObj().get("UEI").getAsString().trim());
	}

	@When("^My Offers page displays my saved and submitted offers$")
	public void my_offers_page_displays_my_saved_and_submitted_offers() {
		OffersListPage offerPage = new OffersListPage(executionContext);
		offerPage.get();
		// Did not add any validation as page.get() will assert page is loaded
	}

	@When("^Error message \"([^\"]*)\"  is displayed on DUNS page$")
	public void error_message_is_displayed_on_duns_page(String errMsg) {
		CommonUtilPage.verifyErrorOnPage(executionContext.getDriver(), errMsg);
	}
	
	@Given("^I am on the UEI page$")
	public void i_am_on_the_UEI_page() {
		if (dunsPage == null) {
			dunsPage = new DunsPage(executionContext);
		}
		dunsPage.get();
	}

	@When("^I provide a valid UEI Number$")
	public void i_provide_a_valid_UEI_Number(){
	   dunsPage.populateUEI();
	}
	
	@When("^I provide an valid UEI Number$")
	public void i_provide_an_valid_UEI_Number(){
	   dunsPage.populateUEI();
	}

	@When("^I provide a empty UEI Number$")
	public void i_provide_a_empty_UEI_Number() {
	   dunsPage.populateUEI(executionContext.getCurrentScenarioObj().get("UEI").getAsString().trim());
	}

	@Then("^Error message \"([^\"]*)\"  is displayed on UEI page$")
	public void error_message_is_displayed_on_UEI_page(String arg1) {
		CommonUtilPage.verifyErrorOnPage(executionContext.getDriver(), arg1);
	}

	@When("^I provide an invalid UEI Number$")
	public void i_provide_an_invalid_UEI_Number(){
		dunsPage.populateUEI(executionContext.getCurrentScenarioObj().get("UEI").getAsString().trim());
	}
}
