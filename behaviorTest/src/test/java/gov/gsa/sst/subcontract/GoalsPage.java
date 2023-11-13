package gov.gsa.sst.subcontract;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.GenericDialogPage;
import gov.gsa.sst.commonpage.SeleniumHelper;
import util.ActionById;
import util.ActionByName;

public class GoalsPage extends SubContractingPlanPage {

	private final int TIMEOUT_IN_SECS = 30;
	private static Logger log = LoggerFactory.getLogger(GoalsPage.class);
	private ExecutionContext executionContext;
	
	public GoalsPage( ExecutionContext executionContext) {
		super(executionContext);
		this.executionContext = executionContext;
	}

	
	@Override
	protected void load() {
		log.info("Loading Goals page");
		try {
			executionContext.testPageFor508("Goals page");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
	}

	@Override
	protected boolean isLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	public void populateForm(JsonObject jsonObj) {
		if(jsonObj.has("goals")) {
			JsonArray goals = jsonObj.getAsJsonArray("goals");
			String planType = jsonObj.get("planType").getAsString();
			addGoals(goals, planType);
	
			// Add supplies and services if plan is New
			if (jsonObj.has("supplies")) {
				addSupplies(jsonObj.getAsJsonArray("supplies"));
				developGoals(jsonObj.get("develop").getAsString());
				listOfSources(jsonObj.get("sources").getAsString());
				selectIndirectCosts(jsonObj.get("indirectCost").getAsString(), jsonObj.get("indirectText").getAsString());
				Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@value,'Save and Continue')]"));
			}
		} else
			log.error("There was no goal information in test data");
	}

	/**
	 * 
	 * @param devText
	 */
	protected void developGoals(String devText) {
		ActionByName.sendKeys(driver, "methodToDevelop", devText, TIMEOUT_IN_SECS);
	}

	/**
	 * 
	 * @param srcList
	 */
	protected void listOfSources(String srcList) {
		ActionByName.sendKeys(driver, "sourceList", srcList, TIMEOUT_IN_SECS);
	}

	/**
	 * 
	 * @param inclCost
	 */
	protected void selectIndirectCosts(String inclCost, String desc) {
		CommonUtilPage.selectRadioOption(driver, "haveIndirectRadio", inclCost);
		ActionByName.sendKeys(driver, "haveIndirectText", desc, TIMEOUT_IN_SECS);
	}

	/**
	 * 
	 * @param jsonArr
	 */
	protected void addGoals(JsonArray jsonArr, String planType) {
		Iterator<JsonElement> iterator = jsonArr.iterator();
		int index = 0;
		String[] headerArr = { "Base *", "1st Option", "2nd Option", "3rd Option" };
		while (iterator.hasNext()) {

			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			if (planType.equals("Individual") || planType.equals("Master") ) {
				log.info("Adding goals: " + index);
				if (index == 0)
					selectRowOrTab("Period", headerArr[index], By.partialLinkText("Add"));
				else
					selectRowOrTab("Period", headerArr[index], By.xpath("./td/a"));
				index++;
			} else {
				// For new Commercial plan
				selectRowOrTab("Period", "Annual Sales", By.partialLinkText("Add"));
			}
			addGoalInDollars(jsonObj);
		}
	}

	/**
	 * Add Base Period Subcontracting Goals in Dollars "goals": [ { "total":
	 * "10000", "largeBusiness": "1000", "smallBusiness": "9000", "veteran":
	 * "200", "serviceDisabled": "300", "hubZone": "400", "sdb": "500", "women":
	 * "600" } ]
	 * 
	 * @param jsonObj
	 */
	protected void addGoalInDollars(JsonObject jsonObj) {
		ActionById.sendKeys(driver, "total_d", jsonObj.get("total").getAsString(), TIMEOUT_IN_SECS);
		String largeBusiness = jsonObj.get("largeBusiness").getAsString();
		if (largeBusiness != null && !largeBusiness.isEmpty()) {
			ActionById.sendKeys(driver, "large_d", largeBusiness, TIMEOUT_IN_SECS);
		} else {
			ActionById.sendKeys(driver, "small_d", jsonObj.get("smallBusiness").getAsString(), TIMEOUT_IN_SECS);
		}
//		ActionById.sendKeys(driver, "vosb_d", jsonObj.get("veteran").getAsString(), TIMEOUT_IN_SECS);
//		ActionById.sendKeys(driver, "sdvosb_d", jsonObj.get("serviceDisabled").getAsString(), TIMEOUT_IN_SECS);
//		ActionById.sendKeys(driver, "hub_d", jsonObj.get("hubZone").getAsString(), TIMEOUT_IN_SECS);
//		ActionById.sendKeys(driver, "sdb_d", jsonObj.get("sdb").getAsString(), TIMEOUT_IN_SECS);
//		ActionById.sendKeys(driver, "wosb_d", jsonObj.get("women").getAsString(), TIMEOUT_IN_SECS);
		
		SeleniumHelper.sendKeyById_tempFix(driver, "vosb_d", jsonObj.get("veteran").getAsString());
		SeleniumHelper.sendKeyById_tempFix(driver, "sdvosb_d", jsonObj.get("serviceDisabled").getAsString());
		SeleniumHelper.sendKeyById_tempFix(driver, "hub_d", jsonObj.get("hubZone").getAsString());
		SeleniumHelper.sendKeyById_tempFix(driver, "sdb_d", jsonObj.get("sdb").getAsString());
		SeleniumHelper.sendKeyById_tempFix(driver, "wosb_d", jsonObj.get("women").getAsString());
		
		GenericDialogPage savePage = new GenericDialogPage(driver);
		savePage.clickConfirmYes();
	}

	protected void addSupplies(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();

		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			Octo_SeleniumLibrary.clickElement(driver, By.name( "addServicesButton"));

			ActionByName.sendKeys(driver, "supplierName", jsonObj.get("name").getAsString(), TIMEOUT_IN_SECS);
			ActionByName.sendKeys(driver, "naicsCode", jsonObj.get("naics").getAsString(), TIMEOUT_IN_SECS);
			SeleniumHelper.clickCheckBoxByName_tempFix(driver, "large_p", jsonObj.get("large").getAsString());
			SeleniumHelper.clickCheckBoxByName_tempFix(driver, "small_p", jsonObj.get("small").getAsString());
			SeleniumHelper.clickCheckBoxByName_tempFix(driver, "vosb_p", jsonObj.get("vosb").getAsString());
			SeleniumHelper.clickCheckBoxByName_tempFix(driver, "sdvosb_p", jsonObj.get("sdvosb").getAsString());
			SeleniumHelper.clickCheckBoxByName_tempFix(driver, "hub_p", jsonObj.get("hub").getAsString());
			SeleniumHelper.clickCheckBoxByName_tempFix(driver, "sdb_p", jsonObj.get("sdb").getAsString());
			Octo_SeleniumLibrary.clickElement(driver, By.name( "wosb_p"));
			
			//SeleniumHelper.clickCheckBoxByName_tempFix(driver, "wosb_p", jsonObj.get("wosb").getAsString());
			GenericDialogPage savePage = new GenericDialogPage(driver);
			savePage.clickConfirmYes();
		}
	}
}
