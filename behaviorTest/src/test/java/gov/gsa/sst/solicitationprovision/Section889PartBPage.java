package gov.gsa.sst.solicitationprovision;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

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

public class Section889PartBPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(Section889PartBPage.class);
	private ExecutionContext executionContext;
	private By partBEquipmentEntityTextArea = By.id("scpFSS001SAM889PartBEquipmentEntity");
	private By partBEquipmentDescriptionTextArea = By.id("scpFSS001SAM889PartBEquipmentDescription");
	private By partBEquipmentUseTextArea = By.id("scpFSS001SAM889PartBEquipmentUse");
	private By partBServiceDescriptionTextArea = By.id("scpFSS001SAM889PartBServiceDescription");
	private By partBServiceUseTextArea = By.id("scpFSS001SAM889PartBServiceUse");

	private By saveAndContinueButton = By.id("saveandcontinue_for_past_performance_button");

	private By headerText = By.xpath("//h1[contains(text(), 'Technical Proposal: Prohibited Telecom (889 PART B)')]");

	public Section889PartBPage(ExecutionContext executionContext) {
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
			solProvisionPage.selectProvisionAction("Section 889 Part B");
			log.info("Loading Solicitation Provisions - Section 889 Part B page");
		} catch (Exception ex) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();
			LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
			SolicitationProvisionPage solProvisionPage = new SolicitationProvisionPage(executionContext);
			solProvisionPage.selectProvisionAction("Section 889 Part B");
		}
		try {
			executionContext.testPageFor508("Section 889 Part B");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue(isLoaded());

	}

	public void setPartBRadioOption(String value) {
		CommonUtilPage.selectRadioOption(driver, "scpFSS001SAM889PartBCoveragePrompt", value);
	}

	// Add field data
	public void setpartBEquipmentEntity(String partBEquipmentEntity) {
		ActionByLocator.sendKeys(driver, partBEquipmentEntityTextArea, partBEquipmentEntity, TIMEOUT_IN_SECS);
	}

	public void setpartBEquipmentDescription(String partBEquipmentDescription) {
		ActionByLocator.sendKeys(driver, partBEquipmentDescriptionTextArea, partBEquipmentDescription, TIMEOUT_IN_SECS);
	}

	public void setpartBEquipmentUse(String partBEquipmentUse) {
		ActionByLocator.sendKeys(driver, partBEquipmentUseTextArea, partBEquipmentUse, TIMEOUT_IN_SECS);
	}

	public void setpartBServiceDescription(String partBServiceDescription) {
		ActionByLocator.sendKeys(driver, partBServiceDescriptionTextArea, partBServiceDescription, TIMEOUT_IN_SECS);
	}

	public void setpartBServiceUse(String partBServiceUse) {
		ActionByLocator.sendKeys(driver, partBServiceUseTextArea, partBServiceUse, TIMEOUT_IN_SECS);
	}

	public void populateForm(JsonObject partB_Obj) {

//		clearAllFieldsData();
		Set<Entry<String, JsonElement>> partBInfoSet = partB_Obj.entrySet();
		Iterator<Entry<String, JsonElement>> iter = partBInfoSet.iterator();
		while (iter.hasNext()) {
			Entry<String, JsonElement> basicInfoElement = iter.next();
			String elementName = basicInfoElement.getKey();
			String elementValue = basicInfoElement.getValue().getAsString();
			switch (elementName) {
			case "PartBRadioSelection":
				setPartBRadioOption(elementValue);
				break;
			case "PartBEquipmentEntity":
				setpartBEquipmentEntity(elementValue);
				break;
			case "PartBEquipmentDescription":
				setpartBEquipmentDescription(elementValue);
				break;
			case "PartBEquipmentUse":
				setpartBEquipmentUse(elementValue);
				break;
			case "PartBServiceDescription":
				setpartBServiceDescription(elementValue);
				break;
			case "PartBServiceUse":
				setpartBServiceUse(elementValue);
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
