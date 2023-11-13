package gov.gsa.sst.solicitationprovision;

import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;


@Component
public class CorporateExperience extends Steps{

	CorporateExperiencePage corporateExperiencePage;
	
	@Given("^I am on the Corporate Experience Page$")
	public void i_am_on_the_Corporate_Experience_Page() throws Throwable {
		init();
	}
	
	@When("^I respond to Corporate Experience questions but DO NOT answer \"([^\"]*)\" question$")
	public void i_respond_to_Corporate_Experience_questions_but_DO_NOT_answer_question(String arg1) throws Throwable {
		init();
		corporateExperiencePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("corporateExperience"));
	}	
	
	@When("^I complete ALL Corporate Experience questions with valid responses$")
	public void i_complete_ALL_Corporate_Experience_questions_with_valid_responses() throws Throwable {
		init();
		corporateExperiencePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("corporateExperience"));
	}	

	@And("^I add ALL Corporate Experience questions with valid responses$")
	public void i_add_ALL_Corporate_Experience_questions_with_valid_responses() throws Throwable {
		SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
		solPage.selectProvisionAction("Corporate Experience");
		init();
		corporateExperiencePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("addCorporateExperience"));
	}	
	
	@When("^I edit the Corporate Experience$")
	public void i_edit_the_Corporate_Experience() throws Throwable {
		SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
		solPage.selectProvisionAction("Corporate Experience");
		init();
		corporateExperiencePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("editCorporateExperience"));
	}


	@When("^I edit the Corporate Experience but DO NOT answer \"([^\"]*)\" question$")
	public void i_edit_the_Corporate_Experience_but_DO_NOT_answer_question(String arg1) throws Throwable {
		init();
		corporateExperiencePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("editCorporateExperience"));
	}	

	private void init() {
		if (corporateExperiencePage == null) {
			corporateExperiencePage = new CorporateExperiencePage(executionContext);
		}
		corporateExperiencePage.get();
	}
}
