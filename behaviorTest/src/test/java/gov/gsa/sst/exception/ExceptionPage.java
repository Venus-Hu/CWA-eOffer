/**
 * 
 */
package gov.gsa.sst.exception;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionByLocator;
import util.ActionByName;
import util.PageUtil;
import util.TableElementUtils;

/**
 * @author amulay
 *
 */
public class ExceptionPage extends Page {

	private static Logger log = LoggerFactory.getLogger(ExceptionPage.class);

	private final int TIMEOUT_IN_SECS = 3;
	private ExecutionContext executionContext;
	List<String> listExceptions;
	//private By menuItem = By.cssSelector("a[title=\"Solicitation Regulation Exceptions\"] > img");
	private By pageHeader = By
			.xpath("//h2[contains(text(),'Available list of Contract clauses for taking exception(s)')]");

	
	public ExceptionPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		log.info("Exception page is being loaded");
		try {
			LeftNavigationMenu.navigateTo(driver, "Exceptions");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();
			
			LeftNavigationMenu.navigateTo(driver, "Exceptions");
		}
		try {
			executionContext.testPageFor508("Exception Page");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Exceptions page is not loaded", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		return ActionByLocator.isDisplayed(driver, pageHeader, TIMEOUT_IN_SECS);
	}

	public void populateForm(JsonArray array) {
		//Select all clauses at once and complete them one by one 
		selectRegulations(array);
		
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			
			// Ensure that data file has clauses in the same order as shown in
			// UI
			String clause = jsonObj.get("clauseNumber").getAsString();
			
			Assert.assertTrue(ActionByLocator.isDisplayed(driver, By.xpath("//a[contains(@title,'" + clause + "')]"),
					TIMEOUT_IN_SECS));
			
			String clauseExcerpt = jsonObj.get("excerptFromClause").getAsString();
			String exceptionText = jsonObj.get("exceptionText").getAsString();

			ActionByName.sendKeys(driver, "excerptFromClause", clauseExcerpt, TIMEOUT_IN_SECS);
			Octo_SeleniumLibrary.clickElement(driver, By.name( "copyExceptionText"));
			driver.switchTo().alert().accept();

			if (!exceptionText.isEmpty()) {
				ActionByName.sendKeys(driver, "exceptionText", exceptionText, TIMEOUT_IN_SECS);
			}
			String buttonValue = jsonObj.get("buttonAction").getAsString();
			Octo_SeleniumLibrary.clickElement(driver, By.xpath("//*[@value='" + buttonValue + "']"));
		}
	}

	/**
	 * select multiple clauses from "Available list of contract clauses for taking exception(s)" page
	 * @param array
	 */
	public void selectRegulations(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			String title = jsonObj.get("title").getAsString();
			String clauseNumber = jsonObj.get("clauseNumber").getAsString();

			selectClause(clauseNumber, title);
		}
		PageUtil.clickSubmitButtonByVal(driver, "Select Regulation(s)");
	}
	
	/**
	 * Select single clause from "Available list of contract clauses for taking exception(s)" page
	 * @param clause
	 * @param title
	 */
	public void selectClause(String clause, String title){
		WebElement table = TableElementUtils.identifyTableWithID(driver, "table1");
		if (table != null) {
			WebElement tableRow = TableElementUtils.getTableRowByCellValues(table, "Clause Number", clause,
					"Title", title);
			if (tableRow != null && clause.length() > 0) {
				tableRow.findElement(By.tagName("input")).click();
			}
		}	
	}

}
