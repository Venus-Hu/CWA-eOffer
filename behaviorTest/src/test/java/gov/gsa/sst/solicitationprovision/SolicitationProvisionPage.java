package gov.gsa.sst.solicitationprovision;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionByLocator;
import util.ActionByWebElement;

/**
 * This page comes after Solicitation Clauses and has at least 3 sub pages
 *
 * @author amulay Depending on schedule, different technical proposals are
 *         mandatory. Ex. Schedule 70: SCA and Relevant Exp are mandatory
 *         Schedule 75: Corp Exp, Past performance and QC are mandatory fields.
 */
public class SolicitationProvisionPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(SolicitationProvisionPage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'Solicitation Provisions')]");
	private ExecutionContext executionContext;

	public SolicitationProvisionPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		log.info("Loading Solicitation Provision page");
		try {
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number/user specified in data does not match the one on UI");
			LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
		}
		try {
			executionContext.testPageFor508("Solicitation Provision");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("Solicitation provision page is not loaded", isLoaded());
	}

	public void selectProvisionAction(String provisionName) {
		By respondLocator = By.xpath("//*[text()[contains(.,'" + provisionName
				+ "')]]/following-sibling::td/a[text()[contains(.,'Respond')]]");
		By editLocator = By.xpath(
				"//*[text()[contains(.,'" + provisionName + "')]]/following-sibling::td/a[text()[contains(.,'Edit')]]");
		if (ActionByLocator.isDisplayed(driver, respondLocator, TIMEOUT_IN_SECS)) {
			Octo_SeleniumLibrary.clickElement(driver, respondLocator);
		} else
			Octo_SeleniumLibrary.clickElement(driver, editLocator);
	}

	/**
	 * Verify the status of sub-pages
	 *
	 * @param rowName Can be Corporate Experience or quality control etc.
	 * @param status  Incomplete, Completed
	 */
	public void verifyStatus(String rowName, String status) {
		By locator = By.xpath("//td[contains(.,'" + rowName + "')]/following-sibling::td[position()=1]");
		WebElement element = ActionByLocator.getElement(driver, locator, TIMEOUT_IN_SECS);
		Assert.assertTrue("Could verify that " + rowName + " status was " + status,
				ActionByWebElement.getElement(driver, element, TIMEOUT_IN_SECS).getText().contains(status));

	}

	public void completeTAA(JsonObject tradeObj) {
		selectProvisionAction("Trade Agreements Act (TAA) Compliance");
		String value = tradeObj.get("proposedSIN").getAsString();
		CommonUtilPage.selectRadioOption(driver, "scpfss001tradeagreementsactManufOrDealer", value);
		// Octo_SeleniumLibrary.clickElement(driver, By.id(
		// "scpfss001tradeagreementsactAcknowledgementI
		// acknowledge the above statement to be true.", TIMEOUT_IN_SECS);
		// TODO change the locator
		selectTAA();
		// Octo_SeleniumLibrary.clickElement(driver, By.id(
		// "saveandcontinue_for_past_performance_button",
		// TIMEOUT_IN_SECS);
	}

	public void selectTAA() {
		By acknowledge = By.xpath("//input[@value='I acknowledge the above statement to be true.']");
		if (ActionByLocator.isSelected(driver, acknowledge, TIMEOUT_IN_SECS)) {
			Octo_SeleniumLibrary.clickElement(driver, acknowledge);
		}
		Octo_SeleniumLibrary.clickElement(driver, By.id("saveandcontinue_for_past_performance_button"));
	}
}
