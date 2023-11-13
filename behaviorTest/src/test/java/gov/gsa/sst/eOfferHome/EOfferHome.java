package gov.gsa.sst.eOfferHome;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.signin.EOfferHomePage;

@Component
public class EOfferHome extends Steps {

	private EOfferHomePage eOfferHomePage;

	@Given("^I am on the eOffer home page$")
	public void i_am_on_the_eOffer_home_page() {
		if (eOfferHomePage == null) {
			eOfferHomePage = new EOfferHomePage(executionContext);
		}
		eOfferHomePage.get();
	}

	@When("^I naviagte to learn more about eOffer$")
	public void i_naviagte_to_learn_more_about_eOffer() {
		eOfferHomePage.verifyBrokenLinks(executionContext.getDriver());
	}

	@Then("^All about eOffer static page is display$")
	public void all_about_eOffer_static_page_is_display() {
		i_naviagte_to_learn_more_about_eOffer();
	}

}
