package gov.gsa.sst.solicitationclause;

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
import util.ActionByName;
import util.ActionByXpath;

/**
 * Basic Information page. It is a dynamic page and for automation purposes
 * following fields are coded. Delivery Prices Ordering Information Scope of
 * Contract Post Award SBPRep under Contract number under NAICS code authorised
 * signer Representation for the Multiple Award Schedule Program
 *
 * @author amulay
 *
 */
public class BasicInfoPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(BasicInfoPage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'BASIC INFORMATION')]");
	private String clauseNumber = "//th[contains(.,'Clause Number')]";
	private ExecutionContext executionContext;

	public BasicInfoPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		if (ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS)
				&& driver.getTitle().contains("Basic Information")) {
			return CommonUtilPage.isSolicitationApt(executionContext);
		} else if (ActionByXpath.isDisplayed(driver, clauseNumber, TIMEOUT_IN_SECS)) {
			return CommonUtilPage.isSolicitationApt(executionContext);
		} else
			return false;
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Solicitation clauses - Basic info page");
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number/user specified in data does not match the one on UI");
			LeftNavigationMenu.navigateTo(driver, "Solicitation Clauses");
			SolicitationClausePage solPage = new SolicitationClausePage(executionContext);
			solPage.selectSolicitationClause("Basic Information");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Solicitation Clauses");
			SolicitationClausePage solPage = new SolicitationClausePage(executionContext);
			solPage.selectSolicitationClause("Basic Information");
		}
		try {
			executionContext.testPageFor508("Basic Info page");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("Could not load Basic Info page", isLoaded());
	}

	public void populateForm(JsonObject basicInfoObj) throws InterruptedException {
		if (ActionByXpath.isDisplayed(driver, clauseNumber, TIMEOUT_IN_SECS)) {
			ListClausesPage listPage = new ListClausesPage(executionContext);
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//a[@class='btn btn-primary btn-sm']"));
			listPage.responseClauseList(basicInfoObj);
		} else {
			for (Map.Entry<String, JsonElement> element : basicInfoObj.entrySet()) {

				String elementName = element.getKey();
				JsonElement elementValue = element.getValue();
//				PerformActionUtils.performActionByLocatorName(driver, elementName, elementValue.getAsString());
				SeleniumHelper.performActionByLocatorName(driver, elementName, elementValue.getAsString());

			}
		}
		submitForm();
	}

	public void submitForm() {
		if (ActionByName.isDisplayed(driver, "saveBasicInfo", TIMEOUT_IN_SECS))
			Octo_SeleniumLibrary.clickElement(driver, By.name( "saveBasicInfo"));
		else if (ActionById.isDisplayed(driver, "backtoSolClauses", TIMEOUT_IN_SECS))
			Octo_SeleniumLibrary.clickElement(driver, By.id( "backtoSolClauses"));
		else if (ActionById.isDisplayed(driver, "save", TIMEOUT_IN_SECS))
			Octo_SeleniumLibrary.clickElement(driver, By.id( "save"));

		if (ActionById.isDisplayed(driver, "save", TIMEOUT_IN_SECS)) {
			Octo_SeleniumLibrary.clickElement(driver, By.id( "save"));
			Octo_SeleniumLibrary.clickElement(driver, By.id( "backtoSolClauses"));
		}
	}

	public void editDeleteTemplate(String arg, JsonObject jsonObject) {
		LeftNavigationMenu.navigateTo(driver, "Solicitation Clauses");
		switch (arg) {
		case "Edit":
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//a[@title='Edit Basic Information']"));
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//a[@title='Edit DELIVERY PRICES (APR 1984)']"));
			Octo_SeleniumLibrary.clickElement(driver, By.id( "editrow_Direct_Delivery_consignee1"));
			try {
				populateForm(jsonObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "Delete":
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//a[@title='Edit Basic Information']"));
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//a[@title='Delete DELIVERY PRICES (APR 1984)']"));
			Octo_SeleniumLibrary.clickElement(driver, By.id( "subYes"));
			break;
		default:
			// TODO THROW EXCEPTION
		}
	}

}
