package gov.gsa.sst.solicitationprovision;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.util.data.Offer;

@Component
public class SolicitationProvision extends Steps {
	SolicitationProvisionPage solPage;
	CorporateExperiencePage corporateExperiencePage;
	PastPerformancePage pastPerformancePage;
	QualityControlPage qualityControlPage;

	public void init() {
		if (solPage == null) {
			solPage = new SolicitationProvisionPage(executionContext);
		}
		solPage.get();
	}

	@Given("^I am on the Solicitation Provisions page$")
	public void i_am_on_the_solicitation_provisions_page() throws Throwable {
		init();
	}

	@Then("^Corporate Experience provision is complete$")
	public void corporate_Experience_provision_is_complete() throws Throwable {
		init();
		solPage.verifyStatus("Corporate Experience", "Completed");
	}

	@Then("^Quality Control provision is complete$")
	public void Quality_Control_provision_is_complete() throws Throwable {
		init();
		solPage.verifyStatus("Quality Control", "Completed");
	}

	@Then("^Relevant Project Experience provision is complete$")
	public void relevant_project_experience_provision_is_complete() {
		init();
		solPage.verifyStatus("Relevant Project Experience", "Completed");
	}

	@Then("^the Corporate Experience provision is updated and message is displayed:  \"([^\"]*)\"$")
	public void the_Corporate_Experience_provision_is_updated_and_message_is_displayed(String arg1) throws Throwable {
		CommonUtilPage.verifyAppMessage(arg1, executionContext.getDriver());
	}

	@Then("^the Past Performance provision is updated and message is displayed: \"([^\"]*)\"$")
	public void the_past_performance_provision_is_updated_and_message_is_displayed(String arg1) throws Throwable {
		CommonUtilPage.verifyAppMessage(arg1, executionContext.getDriver());
	}

	@Then("^the Quality Control provision is updated and message is displayed:  \"([^\"]*)\"$")
	public void the_Quality_Control_provision_is_updated_and_message_is_displayed(String arg1) throws Throwable {
		CommonUtilPage.verifyAppMessage(arg1, executionContext.getDriver());
	}

	@Then("^SCA Matrix provision is complete$")
	public void sca_Matrix_provision_is_complete() throws Throwable {
		init();
		solPage.verifyStatus("SCA Matrix", "Completed");
	}

	@When("^Past Performance provision is complete$")
	public void past_performance_provision_is_complete() {
		init();
		solPage.verifyStatus("Past Performance", "Completed");
	}

	@When("^I have completed all Solicitation Provisions$")
	public void i_have_completed_all_Solicitation_Provisions() throws Throwable {

		new Offer.Builder(executionContext).goodsAndServices().solProvisionCorporateExperience()
				.solProvisionPastPerformance().solProvisionQualityControl().solProvisionRelevantExperience()
				.solProvisionTradeAct().solProvisionSectionPartA().solProvisionSectionPartB().solProvisionSCAMatrix()
				.build();

	}

	@Then("^all Solicitation Provisions are complete$")
	public void all_Solicitation_Provisions_are_complete() throws Throwable {
		init();
		solPage.verifyStatus("Corporate Experience", "Completed");
		solPage.verifyStatus("Past Performance", "Completed");
		solPage.verifyStatus("Quality Control", "Completed");
		solPage.verifyStatus("Relevant Project Experience", "Completed");
		solPage.verifyStatus("Trade Agreements Act (TAA) Compliance", "Completed");
		solPage.verifyStatus("Section 889 Part A", "Completed");
		solPage.verifyStatus("Section 889 Part B", "Completed");
		solPage.verifyStatus("SCA Matrix", "Completed");

	}

}
