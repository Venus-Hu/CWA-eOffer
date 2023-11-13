package gov.gsa.sst.solicitationprovision;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

@Component
public class QualityControl extends Steps{
	
	QualityControlPage qualityControlPage;
	
	@Given("^I am on the Quality Control page$")
	public void i_am_on_the_Quality_Control_Page() throws Throwable {
		init();
	}

	@When("^I complete ALL Quality Control questions with valid responses$")
	public void i_complete_ALL_Quality_Control_questions_with_valid_responses() throws Throwable {
		init();
		qualityControlPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("qualityControl"));
	}

	@And("^I add ALL Quality Control questions with valid responses$")
	public void i_add_ALL_Quality_Control_questions_with_valid_responses() throws Throwable {
		SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
		solPage.selectProvisionAction("Quality Control");
		init();
		qualityControlPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("addQualityControl"));
	}	
	
	@When("^I respond to Quality Control questions but DO NOT answer \"([^\"]*)\" question$")
	public void i_respond_to_Quality_Control_questions_but_DO_NOT_answer_question(String arg1) throws Throwable {
		init();
		qualityControlPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("qualityControl"));
	}

	@When("^I edit the Quality Control$")
	public void i_edit_the_Quality_Control() throws Throwable {
		SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
		solPage.selectProvisionAction("Quality Control");
		init();
		qualityControlPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("editQualityControl"));
	}

	@When("^I edit the Quality Control but DO NOT answer \"([^\"]*)\" question$")
	public void i_edit_the_Quality_Control_but_DO_NOT_answer_question(String arg1) throws Throwable {
		SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
		solPage.selectProvisionAction("Quality Control");
		init();
		qualityControlPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("editQualityControl"));
	}
	
	private void init() {
		if (qualityControlPage == null) {
			qualityControlPage = new QualityControlPage(executionContext);
		}
		qualityControlPage.get();
	}
}
