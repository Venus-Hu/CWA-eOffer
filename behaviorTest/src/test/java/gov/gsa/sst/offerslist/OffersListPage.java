package gov.gsa.sst.offerslist;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.GenericDialogPage;
import gov.gsa.sst.commonpage.SeleniumHelper;
import gov.gsa.sst.dunspage.DunsPage;
import gov.gsa.sst.util.DataUtil;
import gov.gsa.sst.util.exceptions.TestDataException;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByPartialLinkText;
import util.ActionByXpath;
import util.TableElementUtils;

/**
 * @author sjayatirtha
 */
public class OffersListPage extends Page {

	private static Logger log = LoggerFactory.getLogger(OffersListPage.class);
	private final int TIMEOUT_IN_SECS = 20;
	private ExecutionContext executionContext;

	private By lockIcon = By.xpath("//img[@src='/images/small_lock.gif')]");
	private By unlockIcon = By.xpath("//img[@src='/images/small_unlock.gif')]");
	private String legendForLocked = "Offer is Locked. You can only update Negotiators and POC.";
	private String legendForUnLocked = "Offer is Unlocked. You can edit entire Offer.";
	private String legendForExpress = "Express Offer.";

	public OffersListPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		if (CommonUtilPage.isUserCorrect(executionContext)) {
			return driver.getTitle().equalsIgnoreCase("My eOffers");
		} else
			return false;
	}

	@Override
	protected void load() {
		// we try to click on the my eoffers
		try {
			log.info("Loading Offer List page");
			if (CommonUtilPage.isUEICorrect(executionContext)) {
				if (CommonUtilPage.isUserCorrect(executionContext)) {
					ActionByPartialLinkText.click(driver, "My eOffers", 2);
				} else
					throw new Exception(
							"The solicitation number or user specified in data does not match the one on UI");
			} else
				throw new Exception("The DUNS/UEI number in data does not match the one on UI");
		} catch (Exception ex) {
			log.info("Relogging again as solictation or user on UI does not match scenario data");
			DunsPage dunsPage = new DunsPage(executionContext);
			dunsPage.get();
			// dunsPage.enterDunsNumber(getDuns());
			dunsPage.enterUEINumber(getUEI());
			dunsPage.submitForm();
		}
		try {
			executionContext.testPageFor508("Offer List");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("'Offers List' page did not load", isLoaded());
	}

	/**
	 * Get the DUNS value to run test scenario.
	 * 
	 * The DUNS from Application.properties 'eoffer.duns' is used if
	 * 'parallel.execution' property in driver.properties is set to 'true'
	 * 
	 * @return DUNS for test scenario execution
	 */
//	private String getDuns() {
//		return DataUtil.getDUNS(executionContext);
//	}

	private String getUEI() {
		return DataUtil.getUEI(executionContext);
	}

	public void selectSolicitation(String scheduleNumber) {
		boolean solNumberFound = false;
		WebElement element = ActionByName.getElement(driver, "solicitationNumber", TIMEOUT_IN_SECS);
		Select select = new Select(element);
		List<WebElement> allOptions = select.getOptions();
		for (WebElement optionElement : allOptions) {
			String schNumberDropdown = optionElement.getText().split("-")[0].trim();
			if (schNumberDropdown.equalsIgnoreCase(scheduleNumber)) {
				String optionIndex = optionElement.getAttribute("index");
				select.selectByIndex(Integer.parseInt(optionIndex));
				solNumberFound = true;
				break;
			}
		}

		if (!solNumberFound) {
			throw new TestDataException("Schedule number '" + scheduleNumber
					+ "' is not a valid option. Check test data or application status");
		}
	}

	public String getSolicitation() {
		return ActionByName.getText(driver, "solicitationNumber", TIMEOUT_IN_SECS);
	}

	public void verifyRole(String solNumber) {
		ActionByName.assertEqualsText(driver, "solicitationNumber", solNumber, TIMEOUT_IN_SECS);
	}

	public void submitForm() {
		Octo_SeleniumLibrary.clickElement(driver, By.name( "submitOnline"));
		// Update to bypass pathway for all other script

		SeleniumHelper.clickCheckBoxById_tempFix(driver, "pathwayToSuccessCertAction_pathwayAcknowledged", "yes");
		SeleniumHelper.clickCheckBoxById_tempFix(driver, "pathwayToSuccessCertAction_readinessAcknowledged", "yes");
		SeleniumHelper.clickCheckBoxById_tempFix(driver, "pathwayToSuccessCertAction_existingFssContractor", "no");
		Octo_SeleniumLibrary.clickElement(driver, By.id( "pathwayToSuccessCertAction_0"));
		Octo_SeleniumLibrary.clickElement(driver, By.name( "crossValidationContinue"));
	}

	public void submitFormWithoutPathWay() {
		Octo_SeleniumLibrary.clickElement(driver, By.name( "submitOnline"));
	}

	public void clickOfferIdLink(String offerId) {
		ActionByPartialLinkText.click(driver, offerId, TIMEOUT_IN_SECS);
	}

	public void editSavedOffer(String offerId) {
		log.info("Edit saved offer");
		WebElement row = getRowBySolicitation(offerId, "savedeofferstable");
		log.info("Selecting offer to edit with details: " + row.getText());
		if (!row.getText().contains("Expired")) {
			ActionByLocator.getElementInElement(driver, row, By.xpath(".//input[@value='Edit Offer']"), TIMEOUT_IN_SECS)
					.click();
		}
	}

	public void deleteSavedOfferBySolicitation(String scheduleNumber) {
		// WebElement offersTable = driver.findElement(By.id("savedeofferstable"));

		WebElement offersTable = ActionById.getElement(driver, "savedeofferstable", TIMEOUT_IN_SECS);
		WebElement offerRow = TableElementUtils.getTableRowByCellValue(offersTable, "Schedule\nClick to sort column",
				scheduleNumber);
		SeleniumHelper.highLightElement(driver, offerRow);
		log.info("Get all cell values in the row to be deleted :" + offerRow.getText());
		if (offerRow.getText().contains("Incomplete")) {
			SeleniumHelper.highLightElement(driver,
					offerRow.findElement(By.xpath("./td/form/*[@value='Delete Offer']")));
			offerRow.findElement(By.xpath("./td/form/*[@value='Delete Offer']")).click();
			GenericDialogPage deletePage = new GenericDialogPage(driver);
			deletePage.clickConfirmYes();
		}
	}

	public void viewSubmittedOffer(String offerId) {
		ActionByPartialLinkText.getElementInElement(driver, getRowByOfferId(offerId, "submittedeofferstable"),
				"View Offer", TIMEOUT_IN_SECS).click();
	}

	public void withdrawSubmittedOffer(String offerId) {
		ActionByPartialLinkText.getElementInElement(driver, getRowByOfferId(offerId, "submittedeofferstable"),
				"Withdraw Offer", TIMEOUT_IN_SECS).click();
	}

	public void viewSignedOffer(String offerId) {
		ActionByPartialLinkText.getElementInElement(driver, getRowByOfferId(offerId, "signedofferstable"), "View Offer",
				TIMEOUT_IN_SECS).click();
	}

	public void viewPackage(String offerId) {
		ActionByPartialLinkText.getElementInElement(driver, getRowBySolicitation(offerId, "signedofferstable"),
				"View Package", TIMEOUT_IN_SECS).click();
	}

	public void downloadPackage(String offerId) {
		ActionByPartialLinkText.getElementInElement(driver, getRowByOfferId(offerId, "signedofferstable"),
				"Download Package", TIMEOUT_IN_SECS).click();
	}

	public void createOffer(String solicitationNumber) {
		// to check whether offer exist or not if exist delete it
		WebElement element = getRowBySolicitation(solicitationNumber, "savedeofferstable");
		if (element != null) {
			deleteSavedOfferBySolicitation(solicitationNumber);
		}
		String scheduleNumber = executionContext.getCurrentScenarioObj().get("scheduleNumber").getAsString();
		log.info("Schedule number is " + scheduleNumber);
		selectSolicitation(scheduleNumber);
		submitForm();
	}

	public void editOffer(String scheduleNumber) {
		// to check whether offer exists. If exist edit it else create it
		WebElement element = getRowBySolicitation(scheduleNumber, "savedeofferstable");
		if (element != null) {
			editSavedOffer(scheduleNumber);
		} else {
			log.info("Create offer for schedule number " + scheduleNumber);
			selectSolicitation(scheduleNumber);
			submitForm();
		}
	}

	public void deleteOffer(String scheduleNumber) {
		// to check whether offer exists. If exist delete it else create it
		WebElement element = getRowBySolicitation(scheduleNumber, "savedeofferstable");
		if (element != null) {
			deleteSavedOfferBySolicitation(scheduleNumber);
		} else {
			log.info("Create offer for schedule number " + scheduleNumber);
			selectSolicitation(scheduleNumber);
			submitForm();
		}
	}

	public void validateLegendContent() {
		Assert.assertTrue(ActionByXpath.getTextEqualToVerify(driver, "//fieldset/table/tbody/tr[0]", legendForLocked,
				TIMEOUT_IN_SECS));
		Assert.assertTrue(ActionByLocator.isDisplayed(driver, lockIcon, 5));
		Assert.assertTrue(ActionByXpath.getTextEqualToVerify(driver, "//fieldset/table/tbody/tr[1]", legendForUnLocked,
				TIMEOUT_IN_SECS));
		Assert.assertTrue(ActionByLocator.isDisplayed(driver, unlockIcon, 5));
		Assert.assertTrue(ActionByXpath.getTextEqualToVerify(driver, "//fieldset/table/tbody/tr[2]", legendForExpress,
				TIMEOUT_IN_SECS));
	}

	public WebElement getRowByOfferIdAndSolicitation(String solicitation, String offerId, String tableId) {
		WebElement offersTable = ActionById.getElement(driver, tableId, TIMEOUT_IN_SECS);
		WebElement offerRow = TableElementUtils.getTableRowByCellValues(offersTable, "ID", offerId, "Solicitation",
				solicitation);
		return offerRow;
	}

	public WebElement getRowBySolicitation(String solicitation, String tableId) {
		// Temp fix for eOffer expand bootstrap button issue:
		// Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//*[@id='headingOne']//a", 3);
		WebElement offersTable = ActionById.getElement(driver, tableId, TIMEOUT_IN_SECS);
		WebElement offerRow = TableElementUtils.getTableRowByCellValue(offersTable, "Schedule\nClick to sort column",
				solicitation);
		if (offerRow != null && offerRow.getText().contains("Expired")) {
			log.error("The solicitation you are looking for " + solicitation + " has expired");
			offerRow = null;
		}
		return offerRow;
	}

	@Deprecated
	public WebElement getRowByOfferId(String offerId, String tableId) {
		WebElement offersTable = ActionById.getElement(driver, tableId, TIMEOUT_IN_SECS);
		WebElement offerRow = TableElementUtils.getTableRowByCellValue(offersTable, "ID", offerId);
		return offerRow;
	}

	/**
	 * Check a solicitation number is visible in the table specified by the caption
	 * 
	 * @param caption        e.g. "Saved eOffers"
	 * @param scheduleNumber e.g. "70"
	 * @return true if solicitation number is displayed in the table
	 */
	public boolean isSolicitationInTable(String caption, String scheduleNumber) {
		// Temp fix for eOffer expand bootstrap button issue:
		// Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//*[@id='headingOne']//a", 3);
		WebElement row = getRowBySolicitation(scheduleNumber, caption);
		if (row != null)
			return true;
		else
			return false;
	}

}
