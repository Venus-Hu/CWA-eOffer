package gov.gsa.sst.wizardmanagement.specialfeatures;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.common.LeftNavigationMenu;

@Component
public class SpecialFeatures extends Steps{

	private static Logger log = LoggerFactory.getLogger(SpecialFeatures.class);
	private SpecialFeaturesPage specialFeaturesPage;
	
	private void init() {
		log.info("Loading 'Special Features' page...");
		if (specialFeaturesPage == null) {
			specialFeaturesPage = new SpecialFeaturesPage(executionContext);
		}
		specialFeaturesPage.get();
	}
	
	@Given("^I am on the Special Features page for an FPT Schedule$")
	public void i_am_on_the_Special_Features_page_for_an_FPT_Schedule() throws Throwable {
		init();
	}

	@When("^I identify if there are any Environmental and Special Features for each SIN$")
	public void i_identify_if_there_are_any_Environmental_and_Special_Features_for_each_SIN() throws Throwable {
		specialFeaturesPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("specialFeatures"));
	}

	@Then("^Special Features menu is complete$")
	public void special_Features_menu_is_complete() throws Throwable {
		specialFeaturesPage.clickContinueBtn();
		Assert.assertTrue("'Special Features' menu is not complete", LeftNavigationMenu.isStepComplete(executionContext.getDriver(), "Special Features"));
	}
	
	@Then("^EPA features direct vendor to correct external page$")
	public void EPA_features_direct_vendor_to_correct_external_page() throws Throwable{
		specialFeaturesPage.verifyFeatureLinks(executionContext.getCurrentScenarioObj().getAsJsonArray("specialFeatures"));
	}

}

