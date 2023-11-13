/**
 * 
 */
package gov.gsa.sst.subcontract;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.SeleniumHelper;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByTagName;
import util.ActionByXpath;
import util.TableElementUtils;

/**
 * @author amulay
 *
 */
public class SubContractingPlanPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(SubContractingPlanPage.class);

	private By headerText = By.xpath("//h1[contains(text(), 'SUBCONTRACTING PLAN')]");

	private final By IDENTIFICATION_DATA = By.name("section1");
	private final By GOALS = By.name("section3");
	private final By PROGRAM_ADMINISTRATOR = By.name("section4");
	private final By EQUITABLE_OPPORTUNITY = By.name("section5");
	private final By ASSURANCES = By.name("section6");
	private final By SIZE_STANDARDS = By.name("section7");
	private final By REPORTING = By.name("section8");
	private final By RECORDKEEPING = By.name("section9");
	private final By STAT_REQ = By.name("section10");
	private final By GFE = By.name("section11");

	private ExecutionContext executionContext;

	public SubContractingPlanPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Subcontracting plan page");
			// Need to use driver as ActionBy* calls do not throw exceptions
			LeftNavigationMenu.navigateTo(driver, "SubContracting Plan");

		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "SubContracting Plan");
		}
		try {
			executionContext.testPageFor508("Subcontracting Plan");
		} catch (Exception e) {
			log.warn("Section 508 exception: " + e.getMessage());
		}
		Assert.assertTrue("'Sub-contracting Plan' is not loaded", isLoaded());

	}

	public void selectPlan(String newOrExist, String planType) {
		if (newOrExist.equalsIgnoreCase("New")) {
			log.info("Adding new plan of type " + planType);
			CommonUtilPage.selectRadioOption(driver, "preapproved", "No");
			CommonUtilPage.selectRadioOption(driver, "type_of_plan_2", planType.toLowerCase());
		} else {
			log.info("Adding existing plan of type " + planType);
			CommonUtilPage.selectRadioOption(driver, "preapproved", "Yes");
			CommonUtilPage.selectRadioOption(driver, "type_of_plan_1", planType.toLowerCase());
			CommonUtilPage.selectRadioOptionById(driver, "createCommercial", planType.toLowerCase());
		}
		Octo_SeleniumLibrary.clickElement(driver, By.name( "saveSelectPlan"));
	}
	
	public void selectSubContractingPlan(String newOrExist) {
		if(newOrExist.equalsIgnoreCase("New")) {
			CommonUtilPage.selectRadioOption(driver, "preapproved", "Yes");
		}
	}
	
	public void savePlanType() {
		SeleniumHelper.clickById_tempFix(driver, "savePlan", TIMEOUT_IN_SECS);
	}

	public void populateForm(JsonObject jsonObj) {
		String isNew = jsonObj.get("isNew").getAsString();
		if (isNew.equalsIgnoreCase("Yes")) {

			IdentificationDataPage idPage = new IdentificationDataPage(executionContext);
			selectSubKSection("Identification Data");
			idPage.populateForm(jsonObj.getAsJsonObject("identificationData"));

			GoalsPage goals = new GoalsPage(executionContext);
			selectSubKSection("Goals");
			goals.populateForm(jsonObj);

			ProgramAdministratorPage adminPage = new ProgramAdministratorPage(executionContext);
			selectSubKSection("Program Administrator");
			adminPage.populateForm(jsonObj.getAsJsonArray("poc"));

			// Equitable Opportunity
			addOpportunity(jsonObj.getAsJsonObject("equitableOpportunity"));

			// Clause inclusion- renamed to Assurances
			addClauseInclusion(jsonObj.getAsJsonObject("inclClause"));

			// Assignment of size standards to subcontracts
			addSizeStandards(jsonObj.getAsJsonObject("assignmentOfSizeStandard"));

			// Reporting and cooperation
			addReporting(jsonObj.getAsJsonObject("reporting"));

			// Record keeping
			addRecordKeeping(jsonObj.getAsJsonObject("recordKeeping"));

			// Statutory requirements
			addStatRequirement(jsonObj.getAsJsonObject("statutoryRequirements"));

			// GFE
			addGoodFaithEffort(jsonObj.getAsJsonObject("goodFaith"));
		} else {
			//Only for mods we will see Identification data tad for all plan types
			if (!CommonUtilPage.isOffer(executionContext) ){
				IdentificationDataPage idPage = new IdentificationDataPage(executionContext);
				selectSubKSection("Identification Data");
				idPage.populateForm(jsonObj.getAsJsonObject("identificationData"));
			}
			GoalsPage goals = new GoalsPage(executionContext);
			selectSubKSection("Goals");
			goals.populateForm(jsonObj);

			// Assignment of size standards to subcontracts
			addSizeStandards(jsonObj.getAsJsonObject("assignmentOfSizeStandard"));
		}

	}

	/**
	 * 
	 * @param jsonObj
	 * 
	 */
	public void addOpportunity(JsonObject jsonObj) {
		log.info("Adding Equitable opprotunity");

		selectSubKSection("Equitable Opportunity");

		SeleniumHelper.clickCheckBoxByName_tempFix(driver, "outreach_1", jsonObj.get("minority").getAsString());
		SeleniumHelper.clickCheckBoxByName_tempFix(driver, "outreach_2", jsonObj.get("busDev").getAsString());
		SeleniumHelper.clickCheckBoxByName_tempFix(driver, "outreach_3", jsonObj.get("sba").getAsString());
		SeleniumHelper.clickCheckBoxByName_tempFix(driver, "outreach_4", jsonObj.get("trade").getAsString());

		SeleniumHelper.clickCheckBoxByName_tempFix(driver, "internal_1", jsonObj.get("presentation").getAsString());
		SeleniumHelper.clickCheckBoxByName_tempFix(driver, "internal_2", jsonObj.get("establish").getAsString());
		SeleniumHelper.clickCheckBoxByName_tempFix(driver, "internal_3", jsonObj.get("monitor").getAsString());

		ActionByName.sendKeys(driver, "additionalEffort", jsonObj.get("effort").getAsString(), TIMEOUT_IN_SECS);
//		SeleniumHelper.sendKey_tempFix(driver, By.name("additionalEffort"), jsonObj.get("effort").getAsString());
		
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@value,'Save and Continue')]"));
	}

	public void addClauseInclusion(JsonObject jsonObj) {
		log.info("Adding Assurances");
		selectSubKSection("Assurances of Clause Inclusion and Flown Down");

		Set<Entry<String, JsonElement>> set = jsonObj.entrySet();
		Iterator<Entry<String, JsonElement>> iter = set.iterator();
		while (iter.hasNext()) {
			Entry<String, JsonElement> dataElement = iter.next();
			String elementName = dataElement.getKey();
			JsonElement elementValue = dataElement.getValue();
			SeleniumHelper.clickCheckBoxByName_tempFix(driver, elementName, elementValue.getAsString());
		}
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@value,'Save and Continue')]"));
	}

	public void addSizeStandards(JsonObject jsonObj) {
		if(jsonObj.has("agree")) {
			log.info("Adding Assignment of Size Standards to Subcontracts");
			selectSubKSection("Assignment of Size Standards to Subcontracts");
			SeleniumHelper.clickCheckBoxByName_tempFix(driver, "agree", jsonObj.get("agree").getAsString());
			Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[@name='saveButton' and contains(@value,'Save and Continue')]"));
		} else
			log.error("Missing Assignment of Size Standards to Subcontracts information");
	}

	public void addReporting(JsonObject jsonObj) {
		log.info("Adding Reporting and Cooperation");
		selectSubKSection("Reporting and Cooperation");
		SeleniumHelper.clickCheckBoxByName_tempFix(driver, "agree", jsonObj.get("agree").getAsString());
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@value,'Save and Continue')]"));
	}

	/**
	 * Good Faith Estimate tab. Check if we are on subcontracting plan page or sub
	 * page
	 * 
	 * @param jsonObj
	 */
	public void addRecordKeeping(JsonObject jsonObj) {
		log.info("Adding Record Keeping");
		selectSubKSection("Record Keeping");
		SeleniumHelper.clickCheckBoxByName_tempFix(driver, "agree", jsonObj.get("agree").getAsString());
		ActionByName.sendKeys(driver, "otherRecord", jsonObj.get("record").getAsString(), TIMEOUT_IN_SECS);
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@value,'Save and Continue')]"));
	}

	/**
	 * Statutory requirements tab. Check if we are on subcontracting plan page or
	 * sub page
	 * 
	 * @param jsonObj
	 */
	public void addStatRequirement(JsonObject jsonObj) {
		log.info("Adding Stat req");
		selectSubKSection("Statutory Requirements");
	
		SeleniumHelper.clickCheckBoxByName_tempFix(driver, "agree", jsonObj.get("agree").getAsString());
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@value,'Save and Continue')]"));
	}

	/**
	 * Good Faith Estimate tab. Check if we are on subcontracting plan page or sub
	 * page
	 * 
	 * @param jsonObj
	 */
	public void addGoodFaithEffort(JsonObject jsonObj) {
		log.info("Adding Good Faith effort");
		selectSubKSection("Description of Good Faith Effort");
		
		ActionByName.sendKeys(driver, "additionalDetails", jsonObj.get("additionalDetails").getAsString(), TIMEOUT_IN_SECS);
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@value,'Save and Continue')and @type='submit']"));
	}

	/**
	 * Select row item or tab item
	 * @param header
	 * @param name
	 * @param element
	 */
	protected void selectRowOrTab(String header, String name, By element) {
		if (ActionByXpath.isDisplayed(driver, "//*[@class='currentNo tab']/*[contains(.,'" + name + "')]",
				TIMEOUT_IN_SECS)) {
			// Select tab item
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//*[@class='currentNo tab']/*[contains(.,'" + name + "')]"));
			
		} else if (!ActionByXpath.isDisplayed(driver, "//*[@class='current tab']/*[contains(.,'" + name + "')]",
				TIMEOUT_IN_SECS)) {
			// Select row item
			WebElement div = null;
			List<WebElement> list = ActionByLocator.getElements(driver, By.className("table2"), TIMEOUT_IN_SECS);
			if(name.equalsIgnoreCase("GOOD FAITH EFFORT"))
				name = "DESCRIPTION OF GOOD FAITH EFFORT";
			for (WebElement ele : list) {
				if (ele.getText().contains(name)) {
					div = ele;
					break;
				}
			}
			WebElement table = ActionByTagName.getElementInElement(driver, div, "table", TIMEOUT_IN_SECS);
			WebElement row = TableElementUtils.getTableRowByCellValue(table, header, name);
			row.findElement(element).click();
		}
	}

	/**
	 * Select specified subsection
	 * 
	 * @param subKSection
	 */
	public void selectSubKSection(String subKSection) {

		switch (subKSection) {
		case "Identification Data":
			selectRowOrTab("Name", "IDENTIFICATION DATA", IDENTIFICATION_DATA);
			break;
		case "Goals":
			selectRowOrTab("Name", "GOALS", GOALS);
			break;
		case "Program Administrator":
			selectRowOrTab("Name", "PROGRAM ADMINISTRATOR", PROGRAM_ADMINISTRATOR);
			break;
		case "Equitable Opportunity":
			selectRowOrTab("Name", "EQUITABLE OPPORTUNITY", EQUITABLE_OPPORTUNITY);
			break;
		case "Assurances of Clause Inclusion and Flown Down":
			selectRowOrTab("Name", "ASSURANCES", ASSURANCES);
			break;
		case "Assignment of Size Standards to Subcontracts":
			selectRowOrTab("Name", "ASSIGNMENT OF SIZE STANDARDS TO SUBCONTRACTS", SIZE_STANDARDS);
			break;
		case "Reporting and Cooperation":
			selectRowOrTab("Name", "REPORTING AND COOPERATION", REPORTING);
			break;
		case "Record Keeping":
			selectRowOrTab("Name", "RECORD KEEPING", RECORDKEEPING);
			break;
		case "Statutory Requirements":
			selectRowOrTab("Name", "STATUTORY REQUIREMENTS", STAT_REQ);
			break;
		case "Description of Good Faith Effort":
			selectRowOrTab("Name", "GOOD FAITH EFFORT", GFE);
			break;
		}
	}

	public void selectAll(String option) {
		// Checking Select All button selects all options
		SeleniumHelper.clickCheckBoxByName_tempFix(driver, "selectall", "Yes");
	}

	public void deselectClearAll(String option) {

		// Un-checking Select All button clears options
		if (option.equalsIgnoreCase("Select All"))
			SeleniumHelper.clickCheckBoxByName_tempFix(driver, "selectall", "No");
		// Checking Clear All button clears options
		if (option.equalsIgnoreCase("Clear All"))
			SeleniumHelper.clickCheckBoxByName_tempFix(driver, "clearall", "Yes");
	}

	public void verifyAllEquitableOppportunityOutreach(String result) {

		if (result.equalsIgnoreCase("selected")) {
			Assert.assertTrue(driver.findElement(By.name("outreach_1")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("outreach_2")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("outreach_3")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("outreach_4")).isSelected());
		}
		if (result.equalsIgnoreCase("de-selected")) {
			Assert.assertFalse(driver.findElement(By.name("outreach_1")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("outreach_2")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("outreach_3")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("outreach_4")).isSelected());
		}
	}

	public void selectAllEquitableOppportunityInternal(String option) {
		// Checking Select All button selects all options
		SeleniumHelper.clickCheckBoxByName_tempFix(driver, "select", "Yes");
	}

	public void deselectClearAllEquitableOppportunityInternal(String option) {

		// Un-checking Select All button clears options
		if (option.equalsIgnoreCase("Select All"))
			SeleniumHelper.clickCheckBoxByName_tempFix(driver, "select", "No");
		// Checking Clear All button clears options
		if (option.equalsIgnoreCase("Clear All"))
			SeleniumHelper.clickCheckBoxByName_tempFix(driver, "clear", "Yes");
	}

	public void verifyAllEquitableOppportunityInternal(String result) {

		if (result.equalsIgnoreCase("selected")) {
			Assert.assertTrue(driver.findElement(By.name("internal_1")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("internal_2")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("internal_3")).isSelected());
		}
		if (result.equalsIgnoreCase("de-selected")) {
			Assert.assertFalse(driver.findElement(By.name("internal_1")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("internal_2")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("internal_3")).isSelected());
		}
	}

	public void verifyAllProgramAdminDuties(String result) {

		if (result.equalsIgnoreCase("selected")) {
			Assert.assertTrue(driver.findElement(By.name("duty_6")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_7")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_8")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_9")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_10")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_11")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_12")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_13")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_14")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_15")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_16")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_17")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_18")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_19")).isSelected());
			Assert.assertTrue(driver.findElement(By.name("duty_20")).isSelected());
		}
		if (result.equalsIgnoreCase("de-selected")) {
			Assert.assertFalse(driver.findElement(By.name("duty_6")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_7")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_8")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_9")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_10")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_11")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_12")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_13")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_14")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_15")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_16")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_17")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_18")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_19")).isSelected());
			Assert.assertFalse(driver.findElement(By.name("duty_20")).isSelected());
		}
	}
}
