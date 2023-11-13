package gov.gsa.sst.dunspage;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.GetUEIandContract;
import gov.gsa.sst.common.SignIn;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.SeleniumHelper;
import gov.gsa.sst.signin.EOfferHomePage;
import gov.gsa.sst.util.DataUtil;
import gov.gsa.sst.util.LoadProperties;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;

/**
 * DUNS page requesting DUNS number
 * 
 * @author amulay
 *
 */
public class DunsPage extends Page {
	private static Logger log = LoggerFactory.getLogger(DunsPage.class);
	private final int TIMEOUT_IN_SECS = 3;
	private By headerSignin = By.xpath("//h1[contains(text(), 'SIGN IN')]");
	private ExecutionContext executionContext;

	/**
	 * Initialize the DUNS selection page
	 * 
	 * @param driver
	 */
	public DunsPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		if (LoadProperties.getProperty("environment").isEmpty()
				|| LoadProperties.getProperty("environment").equalsIgnoreCase("cloud")) {
			String userName = executionContext.getCurrentScenarioObj().getAsJsonObject("SignIn").get("name")
					.getAsString();
			if (ActionByLocator.isDisplayed(driver, By.xpath("//div[@id='control' and contains(.,'" + userName + "')]"),
					TIMEOUT_IN_SECS))
				return ActionByLocator.isDisplayed(driver, headerSignin, TIMEOUT_IN_SECS);
			else {
				log.info("Username " + userName + " does not match the one on UI");
				return false;
			}
		} else
			return ActionByLocator.isDisplayed(driver, headerSignin, TIMEOUT_IN_SECS);
	}

	@Override
	public void load() {
		log.info("Loading the DUNS page");
		try {
			if (CommonUtilPage.isOffer(executionContext)) {
				if (!CommonUtilPage.isSolicitationApt(executionContext))
					throw new Exception("The solicitation number specified in data does not match the one on UI");
			}
		} catch (Exception e) {
			log.error("Error on DUNS page: " + e.getMessage());
		} finally {
			EOfferHomePage home = new EOfferHomePage(executionContext);
			home.get();
			SignIn.login(driver, executionContext.getCurrentScenarioObj().get("SignIn").getAsJsonObject());
		}
		try {
			executionContext.testPageFor508("UEI");
		} catch (Exception e) {
			log.warn("Section 508 exception: " + e.getMessage());
		}
		Assert.assertTrue("DUNS page is not loaded", isLoaded());
	}

	public String getSelectedDunsFromList() {
		return ActionByName.getText(driver, "existingDuns", TIMEOUT_IN_SECS);
	}

	public void selectDunsFromList(String dunsNumber) {
		ActionByName.selectByValue(driver, "existingDuns", dunsNumber, TIMEOUT_IN_SECS);
	}

	public void enterDunsNumber(String dunsNumber) {
		if(ActionById.isDisplayed(driver, "ackButton", TIMEOUT_IN_SECS)) {
			Octo_SeleniumLibrary.clickElement(driver, By.id( "ackButton"));
		}
		ActionByName.clear(driver, "dunsNumber", TIMEOUT_IN_SECS);
		ActionByName.sendKeys(driver, "dunsNumber", dunsNumber, TIMEOUT_IN_SECS);
	}

	public void enterUEINumber(String ueiNumber) {

		// get comment UEI for eOffer only when we have commentUEI in JSON file
		if (ueiNumber.equalsIgnoreCase("commentUEI")) {
			ueiNumber = LoadProperties.getProperty("comment.UEI");
		}
		ueiNumber = GetUEIandContract.getUEIByFilter(ueiNumber);
		
		if(ActionById.isDisplayed(driver, "ackButton", TIMEOUT_IN_SECS)) {
			Octo_SeleniumLibrary.clickElement(driver, By.id( "ackButton"));
		}
		ActionById.clear(driver, "inputDunsFormAlt", TIMEOUT_IN_SECS);
		ActionById.sendKeys(driver, "inputDunsFormAlt", ueiNumber, TIMEOUT_IN_SECS);
	}

	public String getDunsNumber() {
		return ActionByName.getText(driver, "dunsNumber", TIMEOUT_IN_SECS);
	}

	public String getUeiNumber() {
		return ActionById.getText(driver, "inputDunsFormAlt", TIMEOUT_IN_SECS);
	}

	public void enterDunsPlus4(String duns4Number) {
		ActionByName.sendKeys(driver, "dunsPlus4", duns4Number, TIMEOUT_IN_SECS);
	}

	public String getDunsPlus4() {
		return ActionByName.getText(driver, "dunsPlus4", TIMEOUT_IN_SECS);
	}

	public void submitForm() {
		Octo_SeleniumLibrary.clickElement(driver, By.id("btnSubmit"));
	}

	public void populateForm() {
		enterDunsNumber(DataUtil.getUEI(executionContext));
		submitForm();
	}

	public void populateUEI() {
		enterUEINumber(DataUtil.getUEI(executionContext));
		submitForm();
	}

	/**
	 * For use in scenarios where the data scenario DUNS *must* be used, regardless
	 * of parallel execution setup, e.g. when passing an invalid or empty DUNS.
	 * 
	 * @param duns
	 */
	public void populateForm(String duns) {
		enterDunsNumber(duns);
		submitForm();
	}

	public void populateUEI(String uei) {
		enterUEINumber(uei);
		submitForm();
	}
}
