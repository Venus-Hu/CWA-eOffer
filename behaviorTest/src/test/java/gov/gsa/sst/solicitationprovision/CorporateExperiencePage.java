package gov.gsa.sst.solicitationprovision;

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

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionByName;
import util.ActionByTagName;

/**
 * This page is part of Solicitation Provision (Technical Proposal)
 * 
 * @author amulay
 *
 */
public class CorporateExperiencePage extends Page {

	private ExecutionContext executionContext;
	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(CorporateExperiencePage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'Technical Proposal: Corporate Experience')]");
	private By saveAndContinueButton = By.xpath("//input[contains(@value,'Save and Continue')]");
	
	
	public CorporateExperiencePage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		try {
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number/user specified in data does not match the one on UI");
			LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
			SolicitationProvisionPage solProvisionPage = new SolicitationProvisionPage(executionContext);
			solProvisionPage.selectProvisionAction("Corporate Experience");
	
			log.info("Loading Solicitation Provisions - Corporate Experience page");				
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();
			
			LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
			SolicitationProvisionPage solProvisionPage = new SolicitationProvisionPage(executionContext);
			solProvisionPage.selectProvisionAction("Corporate Experience");
		}
		try {
			executionContext.testPageFor508("Corporate Experience");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Corporate Experience page is not loaded", isLoaded());
	}
	
	/* Add field data */  
	public void setNumberOfYears(String numberOfYearsText) {
		ActionByName.sendKeys(driver, "scpFSS001CorporateExperienceNoOfYearsNarrative", numberOfYearsText, TIMEOUT_IN_SECS);
	}

	public void setOrganization(String orgText) {
		ActionByName.sendKeys(driver, "scpFSS001CorporateExperienceOrganizationNarrative", orgText, TIMEOUT_IN_SECS);
	}
	
	public void setKnowledge(String knowledgeText) {
		ActionByName.sendKeys(driver, "scpFSS001CorporateExperienceKnowledgeNarrative", knowledgeText, TIMEOUT_IN_SECS);
	}
	
	public void setAccounting(String accountingText) {
		ActionByName.sendKeys(driver, "scpFSS001CorporateExperienceAccountingNarrative", accountingText, TIMEOUT_IN_SECS);
	}
	
	public void setPersonnel(String personnelText) {
		ActionByName.sendKeys(driver, "scpFSS001CorporateExperiencePersonnelNarrative", personnelText, TIMEOUT_IN_SECS);
	}
	
	public void setMarketing(String marketingText) {
		ActionByName.sendKeys(driver, "scpFSS001CorporateExperienceMarketingNarrative", marketingText, TIMEOUT_IN_SECS);
	}
	
	public void setSubcontractor(String subcontractorText) {
		ActionByName.sendKeys(driver, "scpFSS001CorporateExperienceSubcontractorNarrative", subcontractorText, TIMEOUT_IN_SECS);
	}
	
	public void submitForm() {
		Octo_SeleniumLibrary.clickElement(driver, saveAndContinueButton);
	}
	
	/* Clear all field data */
	public void clearAllFieldsData(){
		ActionByName.clear(driver, "scpFSS001CorporateExperienceNoOfYearsNarrative", TIMEOUT_IN_SECS);
		List <WebElement> txtBoxes = ActionByTagName.getElements(driver, "textarea", TIMEOUT_IN_SECS);
		for(WebElement txt : txtBoxes){
			txt.clear();
		}
	}

	
	/**
	 * "corporateExperience": {
	 *	"AnumberOfYears": "2-3 years o ",
	 *	"BorgEmployeesInfo": "",
	 * 	"CbriefHistory": "know",
	 *	"Dinfo": "accounts ",
	 *	"EdesciptionOfResources": "persons",
	 *	"FproductMarketing": "dsf",
	 *	"GuseOfSubKs": "sub contract"
	 *	}
	 * @param corporateExpObj
	 */
	public void populateForm(JsonObject corporateExpObj){
		clearAllFieldsData();
		Set<Entry<String, JsonElement>> corpInfoSet = corporateExpObj.entrySet();
		Iterator<Entry<String, JsonElement>> iter = corpInfoSet.iterator();
		while (iter.hasNext()) {
			Entry<String, JsonElement> basicInfoElement = iter.next();
			String elementName = basicInfoElement.getKey();
			String elementValue = basicInfoElement.getValue().getAsString();
			switch (elementName) {
			case "AnumberOfYears":
				setNumberOfYears(elementValue);
				break;
			case "BorgEmployeesInfo":
				setOrganization(elementValue);
				break;
			case "CbriefHistory":
				setKnowledge(elementValue);
				break;
			case "Dinfo":
				setAccounting(elementValue);
				break;
			case "EdesciptionOfResources":
				setPersonnel(elementValue);
				break;
			case "FproductMarketing":
				setMarketing(elementValue);
				break;
			case "GuseOfSubKs":
				setSubcontractor(elementValue);
				break;
			default:
				break;
			}
		}
		submitForm();
	}
	
}
