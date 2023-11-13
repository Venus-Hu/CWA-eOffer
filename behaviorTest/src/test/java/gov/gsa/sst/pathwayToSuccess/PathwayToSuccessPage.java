package gov.gsa.sst.pathwayToSuccess;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import customUtility.Octo_SeleniumLibrary;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import comment.ExecutionContext;
import comment.Page;

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.offerslist.OffersListPage;
import util.ActionByLocator;

public class PathwayToSuccessPage extends Page {

	private static Logger log = LoggerFactory.getLogger(PathwayToSuccessPage.class);
	private final int TIMEOUT_IN_SECS = 3;
	private By headerText = By
			.xpath("//h1[contains(text(),'PATHWAY TO SUCCESS AND READINESS ASSESSMENT REQUIREMENTS')]");

	private ExecutionContext executionContext;

	public PathwayToSuccessPage(ExecutionContext executionContext) {
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
		boolean isOffer = CommonUtilPage.isOffer(executionContext);
		try {
			log.info("Loading Pathway To Success page");
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number specified in data does not match the one on UI");
			LeftNavigationMenu.navigateTo(driver, "Pathway To Success");
		} catch (Exception e) {
			if (isOffer) {
				OffersListPage offerListPage = new OffersListPage(executionContext);
				String scheduleNumber = executionContext.getCurrentScenarioObj().get("scheduleNumber").getAsString();
				offerListPage.get();
				offerListPage.editOffer(scheduleNumber);
			}
			PathwayToSuccessPage pathwayToSuccessPage = new PathwayToSuccessPage(executionContext);
			pathwayToSuccessPage.get();

			LeftNavigationMenu.navigateTo(driver, "Pathway to Success");
		}
		try {
			executionContext.testPageFor508("Pathway To Success");
			executionContext.testPageFor508("Pathway to Success");
		} catch (Exception e) {
			log.warn("Section 508 exception: " + e.getMessage());
		}
		Assert.assertTrue("Pathway To Success page is not loaded", isLoaded());
	}

	public void selectpathwayAcknowledged() {

		WebElement checkBoxElement = driver.findElement(By.id("pathwayToSuccessCertAction_pathwayAcknowledged"));
		boolean isSelected = checkBoxElement.isSelected();
		if (isSelected == false) {
			Octo_SeleniumLibrary.clickElement(driver, By.id( "pathwayToSuccessCertAction_pathwayAcknowledged"));
			selectreadinessAcknowledged();
		}

	}

	public void selectreadinessAcknowledged() {

		WebElement checkBoxElement = driver.findElement(By.id("pathwayToSuccessCertAction_readinessAcknowledged"));
		boolean isSelected = checkBoxElement.isSelected();
		if (isSelected == false) {
			Octo_SeleniumLibrary.clickElement(driver, By.id( "pathwayToSuccessCertAction_readinessAcknowledged"));
		}

	}

	public void deselectreadinessAcknowledged() {

		WebElement checkBoxElement = driver.findElement(By.id("pathwayToSuccessCertAction_readinessAcknowledged"));
		boolean isSelected = checkBoxElement.isSelected();
		if (isSelected == true) {
			Octo_SeleniumLibrary.clickElement(driver, By.id("pathwayToSuccessCertAction_readinessAcknowledged"));
		}

	}

	public void selectexistingFssContractor() {
		WebElement checkBoxElement = driver.findElement(By.id("pathwayToSuccessCertAction_pathwayAcknowledged"));
		boolean isSelected = checkBoxElement.isSelected();
		if (isSelected == true) {
			Octo_SeleniumLibrary.clickElement(driver, By.id( "pathwayToSuccessCertAction_pathwayAcknowledged"));
			selectreadinessAcknowledged();
		}

		WebElement checkBoxElements = driver.findElement(By.id("pathwayToSuccessCertAction_readinessAcknowledged"));
		boolean ischeckboxSelected = checkBoxElements.isSelected();
		if (ischeckboxSelected == true) {
			Octo_SeleniumLibrary.clickElement(driver, By.id( "pathwayToSuccessCertAction_readinessAcknowledged"));
		}
		WebElement checkBoxElement2 = driver.findElement(By.id("pathwayToSuccessCertAction_existingFssContractor"));
		boolean isSelected2 = checkBoxElement2.isSelected();
		if (isSelected2 == false) {
			Octo_SeleniumLibrary.clickElement(driver, By.id( "pathwayToSuccessCertAction_existingFssContractor"));
		}

	}

	public void deselectexistingFssContractor() {
		WebElement checkBoxElement = driver.findElement(By.id("pathwayToSuccessCertAction_pathwayAcknowledged"));
		boolean isSelected = checkBoxElement.isSelected();
		if (isSelected == true) {
			Octo_SeleniumLibrary.clickElement(driver, By.id("pathwayToSuccessCertAction_pathwayAcknowledged"));
			selectreadinessAcknowledged();
		}

		WebElement checkBoxElements = driver.findElement(By.id("pathwayToSuccessCertAction_readinessAcknowledged"));
		boolean ischeckboxSelected = checkBoxElements.isSelected();
		if (ischeckboxSelected == true) {
			Octo_SeleniumLibrary.clickElement(driver, By.id("pathwayToSuccessCertAction_readinessAcknowledged")
					);
		}
		WebElement checkBoxElement2 = driver.findElement(By.id("pathwayToSuccessCertAction_existingFssContractor"));
		boolean isSelected2 = checkBoxElement2.isSelected();
		if (isSelected2 == true) {
			Octo_SeleniumLibrary.clickElement(driver, By.id("pathwayToSuccessCertAction_existingFssContractor"));
		}

	}

	public void selExistingFssContractor() {
		WebElement existingFssElement = driver.findElement(By.id("pathwayToSuccessCertAction_existingFssContractor"));
		boolean isSelected = existingFssElement.isSelected();
		if (isSelected == false) {
			Octo_SeleniumLibrary.clickElement(driver, By.id( "pathwayToSuccessCertAction_existingFssContractor"));
		}

	}

	public void submitForm() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "pathwayToSuccessCertAction_0"));
	}

}
