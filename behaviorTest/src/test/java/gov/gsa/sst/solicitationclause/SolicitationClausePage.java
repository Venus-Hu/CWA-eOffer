package gov.gsa.sst.solicitationclause;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comment.ExecutionContext;
import comment.Page;

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.GenericDialogPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByWebElement;
import util.TableElementUtils;

/**
 * Solicitation Clause page Accessible on eOffer submission or while editing an
 * eOffer or eMod
 * 
 * @author amulay
 *
 */
public class SolicitationClausePage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(SolicitationClausePage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'SOLICITATION CLAUSES')]");
	private By respondButton = By.xpath(".//a[@title='Respond to Input']");

	private ExecutionContext executionContext;

	public SolicitationClausePage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		log.info("Loading Solicitation Clause page");
		try {
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number specified in data does not match the one on UI");
			LeftNavigationMenu.navigateTo(driver, "Solicitation Clauses");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Solicitation Clauses");
		}
		try {
			executionContext.testPageFor508("Solicitation clause");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue(isLoaded());
	}

	public void selectSolicitationClause(String templateName) {
		log.info("selectSolicitationClause for " + templateName);
		WebElement table = ActionById.getElement(driver, "tempTable", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "Template Name", templateName);
		try {
			WebElement element = row.findElement(respondButton);
			element.click();
		} catch (Exception ex) {
			row.findElement(By.xpath(".//td[3]/a[contains(.,'Edit')]")).click();
		}
	}

	/**
	 * Return true if template status is "Complete"
	 * 
	 * @param templateName text in column "Template Name" e.g. "Ordering
	 *                     Information"
	 * @return true if template name status is complete
	 */
	public boolean isTemplateComplete(String templateName) {
		WebElement table = ActionById.getElement(driver, "tempTable", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "Template Name", templateName);
		return (row.findElement(By.xpath(".//td[2]")).getText().trim()).equals("Complete");
	}

	public boolean isTemplatePresent(String templateName) {
		WebElement table = ActionById.getElement(driver, "tempTable", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "Template Name", templateName);
		if (row != null)
			return true;
		else
			return false;
	}

	/**
	 * This method will delete an existing Solicitation Clause template
	 */
	public void deleteTemplate(String templateName) {
		WebElement table = ActionById.getElement(driver, "tempTable", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "Template Name", templateName);
		try {
			row.findElement(By.partialLinkText("Delete")).click();
			;
			// row.findElement(By.xpath("./td[4]")).click();
			GenericDialogPage confirmDelete = new GenericDialogPage(driver);
			confirmDelete.clickConfirmYes();
			log.info(templateName + " has been deleted.");
		} catch (Exception e) {
			log.info("There is no template " + templateName + " to delete");
		}
	}

	public void verifyStatus(String rowName, String status) {
		By locator = By.xpath("//td[contains(.,'" + rowName + "')]/following-sibling::td[position()=1]");
		WebElement element = ActionByLocator.getElement(driver, locator, TIMEOUT_IN_SECS);
		Assert.assertTrue("Could verify that " + rowName + " status was " + status,
				ActionByWebElement.getElement(driver, element, TIMEOUT_IN_SECS).getText().contains(status));
	}

}
