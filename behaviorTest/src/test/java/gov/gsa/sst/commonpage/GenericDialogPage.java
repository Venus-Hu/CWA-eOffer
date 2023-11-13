package gov.gsa.sst.commonpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByXpath;

/**
 * Generic Dialog page object, generally useful for dialog pages where "yes" or
 * "no" questions are asked by the application
 *
 */
public class GenericDialogPage extends Page {
	private final int TIMEOUT_IN_SECS = 30;
	String title;
	By confirmLocator;

	public GenericDialogPage(WebDriver driver) {
		super(driver);
		this.confirmLocator = null;
	}

	public GenericDialogPage(WebDriver driver, String title, By locator) {
		super(driver);
		this.title = title;
		this.confirmLocator = locator;
	}

	@Override
	protected boolean isLoaded() {
		return true;
	}

	@Override
	protected void load() {
		// does not require implementation at the moment
	}

	public void clickConfirmYes() {
		if (confirmLocator == null) {
			if (ActionByName.isDisplayed(driver, "confirmDeleteYes", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.name("confirmDeleteYes"));
			}
//			"subYes" Id
			if (ActionById.isDisplayed(driver, "subYes", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.id("subYes"));
			}
			if (ActionByXpath.isDisplayed(driver, "//*[@id='offerSubmitAction_savePDF' and @value='Yes']",
					TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver,
						By.xpath("//*[@id='offerSubmitAction_savePDF' and @value='Yes']"));
			}
		} else
			Octo_SeleniumLibrary.clickElement(driver, confirmLocator);

	}

	public void clickConfirmNo() {
		if (confirmLocator == null) {
			if (ActionByName.isDisplayed(driver, "confirmDeleteNo", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.name("confirmDeleteNo"));
			}
			if (ActionById.isDisplayed(driver, "subNo", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.id("subNo"));
			}
		} else
			Octo_SeleniumLibrary.clickElement(driver, confirmLocator);
	}
}
