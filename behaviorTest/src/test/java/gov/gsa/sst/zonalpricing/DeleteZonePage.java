package gov.gsa.sst.zonalpricing;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import comment.Page;

import customUtility.Octo_SeleniumLibrary;
import util.ActionByLocator;

/**
 * @author sjayatirtha
 *
 */
public class DeleteZonePage extends Page {

	private final int TIME_OUT_SECS = 30;
	private By viewWizSummaryLink = By.partialLinkText("View Wizard Summary");
	private By backBtn = By.id("btnBackDelete");
	private By deleteZoneBtn = By.id("btnDelete");
	private By anyChkBox = By.xpath("//input[@type='checkbox'] and [@name='states']");

	public DeleteZonePage(WebDriver driver) {
		super(driver);
	}

	@Override
	protected boolean isLoaded() throws Error {
		return driver.getTitle().equalsIgnoreCase("Delete Zonal Pricing");
	}

	@Override
	protected void load() {

	}

	public void clickViewWizSummaryLink() {
		Octo_SeleniumLibrary.clickElement(driver, viewWizSummaryLink);
	}

	public void clickBackBtn() {
		Octo_SeleniumLibrary.clickElement(driver, backBtn);
	}

	public void deleteZoneBtn() {
		Octo_SeleniumLibrary.clickElement(driver, deleteZoneBtn);
	}

	public boolean checkIfAllStatesChkBoxesAreDisabled() {
		boolean disabled = true;
		List<WebElement> statesChkBoxes = ActionByLocator.getElements(driver, anyChkBox, TIME_OUT_SECS);
		for (WebElement chkBox : statesChkBoxes) {
			Assert.assertFalse(chkBox.isEnabled());
		}

		return disabled;
	}

}
