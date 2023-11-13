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
import util.ActionByLocator;
import util.ActionByTagName;

/**
 * 
 * @author amulay
 *
 */
public class QualityControlPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(QualityControlPage.class);
	private By internalReviewTextArea = By.name("scpFSS001QualityControlInternalReviewNarrative");
	private By personnelTextArea = By.name("scpFSS001QualityControlPersonnelNarrative");
	private By subcontractorTextArea = By.name("scpFSS001QualityControlSubkNarrative");
	private By problemTextArea = By.name("scpFSS001QualityControlProblemNarrative");
	private By performanceTextArea = By.name("scpFSS001QualityControlPerformanceNarrative");
	private By multipleAgenciesTextArea = By.name("scpFSS001QualityControlMultipleAgenciesNarrative");

	private By saveAndContinueButton = By.xpath("//input[contains(@value,'Save and Continue')]");
	private ExecutionContext executionContext;
	private By headerText = By.xpath("//h1[contains(text(), 'Technical Proposal: Quality Control')]");

	
	public QualityControlPage(ExecutionContext executionContext) {
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
			LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
			SolicitationProvisionPage solProvisionPage = new SolicitationProvisionPage(executionContext);
			solProvisionPage.selectProvisionAction("Quality Control");
			log.info("Loading Solicitation Provisions - Quality Control page");				
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();
			
			LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
			SolicitationProvisionPage solProvisionPage = new SolicitationProvisionPage(executionContext);
			solProvisionPage.selectProvisionAction("Quality Control");
		}
		try {
			executionContext.testPageFor508("Quality Control");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue(isLoaded());
	}
	
	/* Add field data */  
	public void setInternalReview(String internalReview) {
		ActionByLocator.sendKeys(driver, internalReviewTextArea, internalReview, TIMEOUT_IN_SECS);
	}

	public void setPersonnel(String personnelText) {
		ActionByLocator.sendKeys(driver, personnelTextArea, personnelText, TIMEOUT_IN_SECS);
	}
	
	public void setSubcontractor(String subcontractorText) {
		ActionByLocator.sendKeys(driver, subcontractorTextArea, subcontractorText, TIMEOUT_IN_SECS);
	}
	
	public void setProblemInfo(String problemText) {
		ActionByLocator.sendKeys(driver, problemTextArea, problemText, TIMEOUT_IN_SECS);
	}
	
	public void setPerformanceInfo(String performanceText) {
		ActionByLocator.sendKeys(driver, performanceTextArea, performanceText, TIMEOUT_IN_SECS);
	}
	
	public void setMultipleAgencies(String multipleAgenciesText) {
		ActionByLocator.sendKeys(driver, multipleAgenciesTextArea, multipleAgenciesText, TIMEOUT_IN_SECS);
	}
	
	public void submitForm() {
		Octo_SeleniumLibrary.clickElement(driver, saveAndContinueButton);
	}

	/**
	 * "qualityControl": {
			"internalReview": "review",
			"personnel": "narrative personnel",
			"subk": "sub contracting",
			"problem": "problem",
			"performance": "KPI",
			"multiProject": "multiple projects"
		}
	 * @param jsonObj
	 */
	public void populateForm(JsonObject jsonObj){
		clearAllFieldsData();
		Set<Entry<String, JsonElement>> qcSet = jsonObj.entrySet();
		Iterator<Entry<String, JsonElement>> iter = qcSet.iterator();
		while (iter.hasNext()) {
			Entry<String, JsonElement> basicInfoElement = iter.next();
			String elementName = basicInfoElement.getKey();
			JsonElement elementValue = basicInfoElement.getValue();
			switch (elementName) {
			case "internalReview":
				setInternalReview(elementValue.getAsString());
				break;
			case "individualIdentification":
				setPersonnel(elementValue.getAsString());
				break;
			case "subKPerformance":
				setSubcontractor(elementValue.getAsString());
				break;
			case "potentialProblems":
				setMultipleAgencies(elementValue.getAsString());
				break;
			case "procedureForQuality":
				setProblemInfo(elementValue.getAsString());
				break;
			case "qcManagement":
				setPerformanceInfo(elementValue.getAsString());
				break;
			default:
				break;
			}
		}
		submitForm();
	}
	
	public void clearAllFieldsData(){
		List <WebElement> txtBoxes = ActionByTagName.getElements(driver, "textarea", TIMEOUT_IN_SECS);
		for(WebElement txt : txtBoxes){
			txt.clear();
		}
	}

}
