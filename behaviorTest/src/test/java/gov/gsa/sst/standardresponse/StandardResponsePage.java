package gov.gsa.sst.standardresponse;

import java.util.Map;

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
import gov.gsa.sst.commonpage.SeleniumHelper;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByXpath;

/**
 * This Standard Response page comes after Wizard management. Depending on the
 * option selected on this page, the SubContracting Plan Menu item will be
 * displayed or not.
 *
 * @author amulay
 */
public class StandardResponsePage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(StandardResponsePage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'STANDARD RESPONSES')]");
	private ExecutionContext executionContext;

	public StandardResponsePage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		if (ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS)) {
			return CommonUtilPage.isSolicitationApt(executionContext);
		} else
			return false;
	}

	@Override
	protected void load() {
		log.info("Loading Standard responses page");
		try {
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number specified in data does not match the one on UI");
			LeftNavigationMenu.navigateTo(driver, "Standard Responses");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();
			LeftNavigationMenu.navigateTo(driver, "Standard Responses");

		}
		try {
			executionContext.testPageFor508("Standard Responses");
		} catch (Exception e) {
			log.warn("Section 508 exception: " + e.getMessage());
		}
		Assert.assertTrue("Standard response page is not loaded", isLoaded());
	}

	public void selectDisasterRecovery(String value) {
		CommonUtilPage.selectRadioOption(driver, "DisasterRecoveryPurchasingProgram", value);
	}

	public void selectExceptionTerms(String value) {
		CommonUtilPage.selectRadioOption(driver, "ExceptionsToTermsAndConditions", value);
	}

	public void selectExceptionToCerts(String value) {
		CommonUtilPage.selectRadioOption(driver, "ExceptionsToOrcaCertsAndReps", value);
	}

	public void selectAmericanDiscovery(String value) {
		CommonUtilPage.selectRadioOption(driver, "AmericanRecoveryAndReinvestmentAct2009", value);
	}

	public void selectEmpEligibility(String value) {
		CommonUtilPage.selectRadioOption(driver, "EVerify", value);
	}

	public void selectMinOrderLimit(String text) {
		clearMinOrderLimit();
	//	ActionById.sendKeys(driver, "MinOrderLimit", text, TIMEOUT_IN_SECS);
		SeleniumHelper.sendKey_tempFix(driver, By.id("MinOrderLimit"), text);
	}

	public void clearMinOrderLimit() {
		ActionById.clear(driver, "MinOrderLimit", TIMEOUT_IN_SECS);
	}

	public void selectFastLane(String value) {
		CommonUtilPage.selectRadioOption(driver, "FastLane", value);
	}

	public void selectStartup(String value) {
		CommonUtilPage.selectRadioOption(driver, "StartupSpringBoard", value);
	}

	public void submitForm() {
//		Octo_SeleniumLibrary.clickElement(driver, By.name( "saveStandardResponse", TIMEOUT_IN_SECS);
		Octo_SeleniumLibrary.clickElement(driver, By.name( "saveStandardResponse"));
	}

	/**
	 * "standardResponse": { "disasterRecovery": "Yes", "exceptionTerms": "No",
	 * "exceptionCert": "Yes", "americanRecovery": "Yes", "empEligibility": "",
	 * "fastLane": "", "startup": "", "minOrderLimit": "", "notForProfit": "",
	 * "workInUS": "", "subContract": "No", "domestic": "", "smallBusiness": "",
	 * "locationsInUS": "", "operateInUS": "" }
	 *
	 * @param jsonObj
	 */
	public void populateForm(JsonObject jsonObj) {

		for (Map.Entry<String, JsonElement> element : jsonObj.entrySet()) {
			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			switch (elementName) {
			case "disasterRecovery":
				selectDisasterRecovery(elementValue);
				break;
			case "exceptionTerms":
				selectExceptionTerms(elementValue);
				break;
			case "exceptionCert":
				selectExceptionToCerts(elementValue);
				break;
			case "americanRecovery":
				// DO nothing. ARRA is removed.
				break;
			case "empEligibility":
				selectEmpEligibility(elementValue);
				break;
			case "minOrderLimit":
				selectMinOrderLimit(elementValue);
				break;
			case "fastLane":
				selectFastLane(elementValue);
				break;
			case "startup":
				selectStartup(elementValue);
				break;
			// Subk fields
			case "subKNonProfit":
				CommonUtilPage.selectRadioOption(driver, "subKNonProfit", elementValue);
				break;
			case "subKDomestic":
				CommonUtilPage.selectRadioOption(driver, "subKDomestic", elementValue);
				break;
			case "subKWorkPerfomed":
				CommonUtilPage.selectRadioOption(driver, "subKWorkPerfomed", elementValue);
				break;
			case "subKOpportunity":
				CommonUtilPage.selectRadioOption(driver, "subKOpportunity", elementValue);
				break;
			case "subKHasUSALocations":
				CommonUtilPage.selectRadioOption(driver, "subKHasUSALocations", elementValue);
				break;
			case "subKPrimaryOperationFromUSA":
				CommonUtilPage.selectRadioOption(driver, "subKPrimaryOperationFromUSA", elementValue);
				break;
			case "subKSmallBusinessForeignNo":
				CommonUtilPage.selectRadioOption(driver, "subKSmallBusinessForeignNo", elementValue);
				break;
			case "subKSmallBusinessForeignYes":
				CommonUtilPage.selectRadioOption(driver, "subKSmallBusinessForeignYes", elementValue);
				break;
			case "subKWorkPerfomedForeignYes":
				CommonUtilPage.selectRadioOption(driver, "subKWorkPerfomedForeignYes", elementValue);
				break;
			case "subKWorkPerfomedForeignNo":
				CommonUtilPage.selectRadioOption(driver, "subKWorkPerfomedForeignNo", elementValue);
				break;
			case "subKSmallBusinessYes":
				CommonUtilPage.selectRadioOption(driver, "subKSmallBusinessYes", elementValue);
				break;
			case "migrateContract":
				CommonUtilPage.selectRadioOption(driver, "MigrateContracts", elementValue);
				break;
			// Standard Responses SAM889 part A removed.
			// SAM 889 fields:
//			case "SAM889ProvideTelecom":
//				CommonUtilPage.selectRadioOption(driver, "SAM889ProvideTelecom", elementValue);
//				break;
//			case "SAM889OfferTelecom":
//				CommonUtilPage.selectRadioOption(driver, "SAM889OfferTelecom", elementValue);
//				break;
			default:
				throw new RuntimeException("The field passed in valid - " + elementName);
			}
		}
		submitForm();
	}

	public void confirmResponseChange(String header, String yesOrNo) {
		if (ActionByLocator.isDisplayed(driver, By.xpath("//h1[contains(text(), '" + header + "')]"),
				TIMEOUT_IN_SECS)) {
			log.info("Standard Responses page had a Confirm response page show up");
			String xpath = "//*[@name='confirmExceptionsDelete' and contains(@value,'" + yesOrNo + "')]";
			Octo_SeleniumLibrary.clickElement(driver, By.xpath(xpath));
		}
	}

	public void confirmResponseChange(String yesOrNo) {
		confirmResponseChange("Confirm Response Change", yesOrNo);
	}

	public boolean isFastlaneDisplayed() {
		if (ActionByXpath.isDisplayed(driver, "//*[@id='FastLane']", TIMEOUT_IN_SECS)) {
			return true;
		} else {
		}
		return false;
	}
}
