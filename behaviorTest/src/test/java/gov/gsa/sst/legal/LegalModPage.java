package gov.gsa.sst.legal;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionByLocator;
import util.ActionByName;

public class LegalModPage extends Page {

	private final int TIMEOUT_IN_SECS = 2;
	private static Logger log = LoggerFactory.getLogger(LegalModPage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'LEGAL')]");
	private ExecutionContext executionContext;

	public LegalModPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Legal Mod page");
			LeftNavigationMenu.navigateTo(driver, "Legal");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Legal");
		}
		try {
			executionContext.testPageFor508("Legal mod");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("Could not load Legal mod page", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		return ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}

	/**
	 * Respond to specific clause
	 * 
	 * @param clause
	 */
	public void selectMod(String clause) {
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//a[@title='Respond for " + clause + "']"));
	}

	public void updateNovationAgreement(JsonObject jsonObj) {
		// Do not change to switch case as order of execution is important
		if (jsonObj.has("contractWithAgency"))
			CommonUtilPage.selectRadioOption(driver, "qt1", jsonObj.get("contractWithAgency").getAsString());
		if (jsonObj.has("novatedWithAgency"))
			CommonUtilPage.selectRadioOption(driver, "qt2", jsonObj.get("novatedWithAgency").getAsString());
		if (jsonObj.has("multipleGSAContracts"))
			CommonUtilPage.selectRadioOption(driver, "qt6", jsonObj.get("multipleGSAContracts").getAsString());
		if (jsonObj.has("firstContractNovated"))
			CommonUtilPage.selectRadioOption(driver, "qt7", jsonObj.get("firstContractNovated").getAsString());

		Octo_SeleniumLibrary.clickElement(driver, By.name( "Save and Continue"));
	}

	public void populateForm(JsonObject jsonObj) {
		String modType = jsonObj.get("clause").getAsString();
		if (executionContext.getCurrentScenarioObj().getAsJsonArray("modTypes").size() > 1) {
			selectMod(modType);
		}

		String replaceUEI = jsonObj.get("replaceUEI").getAsString();
		String newDuns = jsonObj.get("updatedUEI").getAsString();
		if (replaceUEI.equalsIgnoreCase("Yes")) {

			JsonElement element = jsonObj.get("dunsPlus4");
			updateDuns(newDuns, (element == null) ? "" : element.getAsString());

			// Perform this action only for Name change mod and not Novation
			if (!modType.equalsIgnoreCase("Novation Agreement")) {
				Octo_SeleniumLibrary.clickElement(driver, By.name( "Save and Continue"));
				CorporateInformationPage corpPage = new CorporateInformationPage(executionContext);
				log.info("UEI on Corporate Information page matches updated UEI: " + corpPage.isUEIMatch(newDuns));
				corpPage.submitForm();
				 enterDetailedModDescription(jsonObj.get("modDetailDescription").getAsString());
				 Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[@name='crossValidationContinue']"));
			}
		} else
			CommonUtilPage.selectRadioOption(driver, "replaceDUNS", replaceUEI);
		if (modType.equalsIgnoreCase("Novation Agreement")) {
			updateNovationAgreement(jsonObj);
			CorporateInformationPage corpPage = new CorporateInformationPage(executionContext);
			log.info("UEI on Corporate Information page matches updated UEI: " + corpPage.isUEIMatch(newDuns));
			corpPage.submitForm();
		}
		Octo_SeleniumLibrary.clickElement(driver, By.name( "saveAndContinue"));
	}

	public void updateDuns(String newUEI, String plus) {
		log.info("Updating UEI to " + newUEI);
		CommonUtilPage.selectRadioOption(driver, "replaceDuns", "Yes");

		ActionByName.sendKeys(driver, "dunsNumber", newUEI, TIMEOUT_IN_SECS);
		//ActionByName.sendKeys(driver, "dunsPlus4", plus, TIMEOUT_IN_SECS);
		log.info("Saving updated UEI '" + newUEI + "' to executionContext");
		executionContext.getCurrentScenarioObj().addProperty("UEI", newUEI);
	}

	private void enterDetailedModDescription(String modDetailDescription) {
		ActionByName.clear(driver, "ModDetailDescription", TIMEOUT_IN_SECS);
		ActionByName.sendKeys(driver, "ModDetailDescription", modDetailDescription, TIMEOUT_IN_SECS);
		Octo_SeleniumLibrary.clickElement(driver, By.name( "saveAndContinue"));
	}

}
