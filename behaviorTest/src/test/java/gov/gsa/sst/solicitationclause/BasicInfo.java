package gov.gsa.sst.solicitationclause;

import java.util.Iterator;

import org.junit.Assert;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import util.ActionByXpath;

@Component
public class BasicInfo  extends Steps{
	BasicInfoPage basicInfoPage;
	ListClausesPage listPage;
	int TIMEOUT_IN_SEC = 3;
	public BasicInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public void initBasicInfo(){
		if(basicInfoPage == null)
			basicInfoPage = new BasicInfoPage(executionContext);
		basicInfoPage.get();
	}
	
	public void init(){
		if(listPage == null)
			listPage = new ListClausesPage(executionContext);
	//	listPage.get();
	}

	@Given("^I am on the Basic Information Solicitation Clauses page$")
	public void i_am_on_the_basic_information_solicitation_clauses_page(){
		initBasicInfo();
	}

	@Given("^I am on the Basic Information page for non formatted vendor response$")
	public void i_am_on_the_basic_information_page_for_non_formatted_vendor_response(){
		initBasicInfo();
	}
	
	@When("^I have responded to ALL Basic Info Solicitation Clauses successfully$")
	public void i_have_responded_to_all_basic_info_solicitation_clauses_successfully() throws InterruptedException{
		init();
		listPage.responseClauseList(executionContext.getCurrentScenarioObj().getAsJsonObject("basicInfo"));
	}
	
	@Then("^Basic Information template is Complete$")
	public void basic_information_template_is_complete(){
		SolicitationClausePage solicitationClausePage = new SolicitationClausePage(executionContext);
	    solicitationClausePage.get();
	    Assert.assertTrue("Basic Information template is not completed", solicitationClausePage.isTemplateComplete("Basic Information"));
	}
	
	@When("^I delete a Solicitation Clause in the Clause List$")
	public void i_delete_a_solicitation_clause_in_the_clause_list(){
		init();
		JsonArray array = executionContext.getCurrentScenarioObj().getAsJsonObject("basicInfo").getAsJsonArray("deleteClause");
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject clauseObj = element.getAsJsonObject();
			listPage.deleteClauseResponse(clauseObj.get("clauseNumber").getAsString());
		}
	}
	
	@Then("^the Clause status is Incomplete$")
	public void the_clause_status_is_incomplete(){
		init();
		JsonArray array = executionContext.getCurrentScenarioObj().getAsJsonObject("basicInfo").getAsJsonArray("deleteClause");
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject clauseObj = element.getAsJsonObject();
			listPage.checkClauseStatus(clauseObj.get("clauseNumber").getAsString(), "Incomplete");
		}
		
	}
	
	@When("^I respond to a Basic information Clause and provide valid responses for ALL questions$")
	public void i_respond_to_a_basic_information_clause_and_provide_valid_responses_for_all_questions() throws InterruptedException{
		initBasicInfo();
		basicInfoPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("basicInfo"));
	}
	
	@When("^I \"([^\"]*)\" the Basic Information template$")
	public void i_the_basic_info_template(String arg1) {
		basicInfoPage.editDeleteTemplate(arg1,executionContext.getCurrentScenarioObj().getAsJsonObject("editBasicInfo"));
	}
	
	@Then("^the Solicitation Clause List will be displayed for Edit/Delete$")
	public void the_sol_clause_list_will_be_displayed() {
		Assert.assertTrue(ActionByXpath.getText(executionContext.getDriver(), "(//table[@id='tempTable']//tr/td)[1]", TIMEOUT_IN_SEC).toUpperCase().contains("BASIC INFORMATION"));
	}
	
	@Then("^the Basic Information template will be deleted and the page will prompt me to Respond$")
	public void the_basic_information_template_will_be_deleted_and_the_page_wil_prompt_me_to_respond() {
		SolicitationClausePage solClausePage = new SolicitationClausePage(executionContext);
		solClausePage.get();
		Assert.assertFalse("Basic Information is complete", solClausePage.isTemplateComplete("Basic Information"));
	}
	

}
