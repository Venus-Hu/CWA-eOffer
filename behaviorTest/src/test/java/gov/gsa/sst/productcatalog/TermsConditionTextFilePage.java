package gov.gsa.sst.productcatalog;

import java.util.Iterator;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import comment.ExecutionContext;
import comment.Page;

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.exceptions.TestExecutionException;
import util.ActionById;
import util.ActionByLocator;
import util.TableElementUtils;

public class TermsConditionTextFilePage extends Page{

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(TermsConditionTextFilePage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'TERMS AND CONDITION TEXT FILE')]");
	private ExecutionContext executionContext;
	
	public TermsConditionTextFilePage(WebDriver driver) {
		super(driver);
	}
	
	public TermsConditionTextFilePage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Terms and Condition Text File mod page");
			LeftNavigationMenu.navigateTo(driver, "Product Catalog");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Product Catalog");
		}
		try {
			executionContext.testPageFor508("TC-Text File");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Could not load 'Terms and Condition Text File' page for Terms and Condition Text File mod", isLoaded());
	}

	public void populateForm()
	{
		log.info("Populating the 'Terms and Condition Text File' page data");
		String description = executionContext.getCurrentScenarioObj().getAsJsonObject("termConditionTextFileMod")
				.get("detailedDescription").getAsString();
		ActionById.sendKeys(driver, "modDesc", description, TIMEOUT_IN_SECS);
	}
	
	public void validateSins()
	{
		log.info("Verifying the sin list ...");
		JsonArray sinArray = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices").getAsJsonArray("sinDetails");
		verifySinExists(sinArray);
		log.info("Sin list validation is done");
	}
	
	public void verifySinExists(JsonArray jsonArray) {
		Iterator<JsonElement> iterator = jsonArray.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			String sinName = sinObj.get("sin").getAsString();

			WebElement sinRow = getRowBySinName(sinName);
			if (sinRow == null)
				throw new TestExecutionException("Could not find row with SIN: " + sinName);
		}
	}
	
	private WebElement getRowBySinName(String sinName) {
		WebElement offersTable = driver.findElement(By.id("modSins"));
		WebElement sinRow = TableElementUtils.getTableRowByCellValue(offersTable, "SIN", sinName);
		return sinRow;
	}
}
