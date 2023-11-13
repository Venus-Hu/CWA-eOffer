package gov.gsa.sst.corporateinfo;

import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.util.DataUtil;

import org.junit.Assert;

@Component
public class CorporateInformation extends Steps {
	CorporateInformationPage corpInfoPage = null;

	public CorporateInformation() {
		// TODO Auto-generated constructor stub
	}

	@Given("^I am on the Corporate Information Page$")
	public void i_am_on_the_corporate_information_page() throws Throwable {
		if (corpInfoPage == null) {
			corpInfoPage = new CorporateInformationPage(executionContext);
		}
		corpInfoPage.get();
	}

	@When("^verify Corporate Information is correct$")
	public void verify_corporate_information_is_correct() {
		JsonObject jsonObj = executionContext.getCurrentScenarioObj();
		// DUNS is defined in the test scenario data but can be overridden
		// by the value in Application.properties if 'parallel.execution'
		// in driver.properties is set to 'true'
		corpInfoPage.verifyUEINumber(DataUtil.getUEI(executionContext));
		corpInfoPage.verifyScheduleNumber(jsonObj.get("scheduleNumber").getAsString());
	}

	@When("^Save Corporate Information data$")
	public void save_corporate_information_data() {
		corpInfoPage.submitForm();
	}

	@Then("^Corporate Information menu is complete$")
	public void menu_is_complete() {
		Assert.assertTrue("Could not verify Corporate Information menu is complete",
				LeftNavigationMenu.isStepComplete(executionContext.getDriver(), "Corporate Information"));
	}

}
