package gov.gsa.sst.solicitationprovision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import comment.ExecutionContext;
import comment.Page;

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionByLocator;
import util.ActionByTagName;

public class Section889PartAPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(Section889PartAPage.class);
	private ExecutionContext executionContext;
	private By partAEquipmentEntityTextArea = By.id("scpFSS001SAM889PartAEquipmentEntity");
	private By partAEquipmentDescriptionTextArea = By.id("scpFSS001SAM889PartAEquipmentDescription");
	private By partAEquipmentUseTextArea = By.id("scpFSS001SAM889PartAEquipmentUse");
	private By partAServiceDescriptionTextArea = By.id("scpFSS001SAM889PartAServiceDescription");
	private By partAServiceUseTextArea = By.id("scpFSS001SAM889PartAServiceUse");
	private By saveAndContinueButton = By.id("saveandcontinue_for_past_performance_button");

	private By headerText = By.xpath("//h1[contains(text(), 'Technical Proposal: Prohibited Telecom (889 PART A)')]");

	public Section889PartAPage(ExecutionContext executionContext) {
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
			solProvisionPage.selectProvisionAction("Section 889 Part A");
			log.info("Loading Solicitation Provisions - Section 889 Part A page");
		} catch (Exception ex) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();
			LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
			SolicitationProvisionPage solProvisionPage = new SolicitationProvisionPage(executionContext);
			solProvisionPage.selectProvisionAction("Section 889 Part A");
		}
		try {
			executionContext.testPageFor508("Section 889 Part A");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue(isLoaded());

	}

	public void setPartARadioOption(String value) {
		CommonUtilPage.selectRadioOption(driver, "scpFSS001SAM889PartACoveragePrompt", value);
	}

	// Add field data
	public void setPartAEquipmentEntity(String partAEquipmentEntity) {
		ActionByLocator.sendKeys(driver, partAEquipmentEntityTextArea, partAEquipmentEntity, TIMEOUT_IN_SECS);
	}

	public void setPartAEquipmentDescription(String partAEquipmentDescription) {
		ActionByLocator.sendKeys(driver, partAEquipmentDescriptionTextArea, partAEquipmentDescription, TIMEOUT_IN_SECS);
	}

	public void setPartAEquipmentUse(String PartAEquipmentUse) {
		ActionByLocator.sendKeys(driver, partAEquipmentUseTextArea, PartAEquipmentUse, TIMEOUT_IN_SECS);
	}

	public void setPartAServiceDescription(String PartAServiceDescription) {
		ActionByLocator.sendKeys(driver, partAServiceDescriptionTextArea, PartAServiceDescription, TIMEOUT_IN_SECS);
	}

	public void setPartAServiceUse(String PartAServiceUse) {
		ActionByLocator.sendKeys(driver, partAServiceUseTextArea, PartAServiceUse, TIMEOUT_IN_SECS);
	}

	public void populateForm(JsonObject partA_Obj) {

//		clearAllFieldsData();
		Set<Entry<String, JsonElement>> partAInfoSet = partA_Obj.entrySet();
		Iterator<Entry<String, JsonElement>> iter = partAInfoSet.iterator();
		while (iter.hasNext()) {
			Entry<String, JsonElement> basicInfoElement = iter.next();
			String elementName = basicInfoElement.getKey();
			String elementValue = basicInfoElement.getValue().getAsString();
			switch (elementName) {
			case "PartARadioSelection":
				setPartARadioOption(elementValue);
				break;
			case "PartAEquipmentEntity":
				setPartAEquipmentEntity(elementValue);
				break;
			case "PartAEquipmentDescription":
				setPartAEquipmentDescription(elementValue);
				break;
			case "PartAEquipmentUse":
				setPartAEquipmentUse(elementValue);
				break;
			case "PartAServiceDescription":
				setPartAServiceDescription(elementValue);
				break;
			case "PartAServiceUse":
				setPartAServiceUse(elementValue);
				break;
			default:
				break;
			}
		}
		submitForm();
	}

	public void submitForm() {
		Octo_SeleniumLibrary.clickElement(driver, saveAndContinueButton);
	}

	public void clearAllFieldsData() {
		List<WebElement> txtBoxes = ActionByTagName.getElements(driver, "textarea", TIMEOUT_IN_SECS);
		for (WebElement txt : txtBoxes) {
			txt.clear();
		}
	}
}
