package gov.gsa.sst.productcatalog;

import java.util.Iterator;

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

import gov.gsa.sst.common.LeftNavigationMenu;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.exceptions.TestExecutionException;
import util.ActionById;
import util.ActionByLocator;
import util.TableElementUtils;

public class ProductCatalogUpdateModPage extends Page{

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(ProductCatalogUpdateModPage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'PRODUCT CATALOG UPDATE')]");
	private ExecutionContext executionContext;
	private By beginDateText = By.xpath("//p[@class='mg-top-10 clearBoth']");
	
	
	public ProductCatalogUpdateModPage(ExecutionContext executionContext) {
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
			log.info("Loading Product Catalog Update page");
			LeftNavigationMenu.navigateTo(driver, "Product Catalog");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Product Catalog");
		}
		try {
			executionContext.testPageFor508("PCU mod");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Could not load 'Product Catalog Update' page for Product Catalog Update mod", isLoaded());
	}
	
	public void validateContractBeginDate()
	{
		String expectedBeginDate = executionContext.getCurrentScenarioObj().getAsJsonObject("productCatalogUpdateMod")
				.get("contractBeginDate").getAsString();
		String contractDateText = ActionByLocator.getText(driver, beginDateText, TIMEOUT_IN_SECS);
		contractDateText = contractDateText.substring(contractDateText.indexOf(":") + 1).trim();
		Assert.assertTrue("Contract begin date from UI " + contractDateText + " date did not match the expected " + expectedBeginDate, 
				expectedBeginDate.contentEquals(contractDateText));
	}
	
	public void validateSins()
	{
		log.info("Verifying the sin list ...");
		JsonArray sinArray = executionContext.getCurrentScenarioObj().getAsJsonObject("goodsAndServices").getAsJsonArray("sinDetails");
		verifySinExists(sinArray);
		log.info("Sin list validation is done");
	}
	
	public WebElement getRowBySinName(String sinName) {
		WebElement offersTable = driver.findElement(By.id("modSins"));
		WebElement sinRow = TableElementUtils.getTableRowByCellValue(offersTable, "SIN", sinName);
		return sinRow;
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
	
	public void clickSaveAndContinue()
	{
		Octo_SeleniumLibrary.clickElement(driver, By.id( "saveAndContinue"));
	}
	
	public void setDetailedDescription()
	{
		String description = executionContext.getCurrentScenarioObj().getAsJsonObject("productCatalogUpdateMod")
				.get("detailedDescription").getAsString();
		ActionById.sendKeys(driver, "modDesc", description, TIMEOUT_IN_SECS);
	}
	
	public void populateForm()
	{
		log.info("Populating the 'Product Catalog Update' page data");
		setDetailedDescription();
		clickSaveAndContinue();
	}
	
	
	
}
