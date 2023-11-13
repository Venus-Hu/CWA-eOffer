package gov.gsa.sst.solicitationprovision;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

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

import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.GenericDialogPage;
import customUtility.Octo_SeleniumLibrary;import util.ActionByClassName;
import util.ActionById;
import util.ActionByName;
import util.ActionByTagName;
import util.TableElementUtils;

/**
 * This page is a sub page of Solicitation Provision also called as Technical
 * Proposal. This page has 3 projects per SIN that need to be populated. This is
 * a placeholder for now.
 * 
 * @author amulay
 *
 */
public class Sch70RelevantExperiencePage extends Page {

	private final int TIMEOUT_IN_SECS = 10;
	private static Logger log = LoggerFactory.getLogger(Sch70RelevantExperiencePage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'Technical Proposal: Relevant Project Experience')]");
	private By saveAndContinueButton = By.xpath("//input[contains(@value,'Save and Continue')]");
	private ExecutionContext executionContext;

	public Sch70RelevantExperiencePage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Relevant Project Experience page");

			SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
			solPage.selectProvisionAction("Relevant Project Experience");
		} catch (Exception e) {
			SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
			solPage.get();
			solPage.selectProvisionAction("Relevant Project Experience");
		}
		try {
			executionContext.testPageFor508("Relevant Experience");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("Solicitation Provisions- Relevant experience page is not loaded", isLoaded());
	}

	/**
	 * Perform Add, Edit delete action
	 * 
	 * @param action
	 */
	public void selectProjectAction(String action, String value, String sinVal) {// projTable132_4
		WebElement div = ActionByClassName.getElement(driver, "table2", TIMEOUT_IN_SECS);
		WebElement table = div.findElement(By.tagName("table"));
		WebElement row = null;
		String sin = "";
		if (!sinVal.isEmpty())
			sin = "and contains(@href, '"+sinVal+"')";
//			sin = "and contains(@href, 'sin=" + sinVal + "')";

		switch (action) {
		case "add":
			Octo_SeleniumLibrary.clickElement(driver, By.xpath("//a[contains(text(),'Add Project') " + sin + "]"));
			break;
		case "edit":
			row = TableElementUtils.getTableRowByCellValues(table, "Edit", "Edit Project", "Project", value);
			row.findElement(By.xpath(".//a[contains(text(),'Edit Project') " + sin + "]")).click();
			break;
		case "delete":
			row = TableElementUtils.getTableRowByCellValue(table, "Delete", "Delete Project");
			row.findElement(By.xpath(".//a[contains(text(),'Delete Project') " + sin + "]")).click();
			break;
		default:
			break;
		}
	}

	/**
	 * Add data to all text fields
	 * 
	 * @param array
	 */
	public void populateForm(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {

			JsonElement jsonElement = (JsonElement) iterator.next();
			JsonObject jsonObj = jsonElement.getAsJsonObject();
		
			// Find out the Xpath/Locator for the given SIN in data
			String sinValue = "";
			if (jsonObj.has("sin")) {
//				sinValue = jsonObj.get("sin").getAsString().replace(" ", "_");
				sinValue = jsonObj.get("sin").getAsString().split(" ")[0];
				log.info("SIN value updated for xpath is: " + sinValue);
			}
			selectProjectAction("add", "", sinValue);
			
			
			
			clearAllFields();
			Set<Entry<String, JsonElement>> set = jsonObj.entrySet();
			Iterator<Entry<String, JsonElement>> iter = set.iterator();
	
			// submit data for the single SIN 
			while (iter.hasNext()) {
				Entry<String, JsonElement> element = iter.next();
				String elementName = element.getKey();
				JsonElement elementValue = element.getValue();
				if (elementName.equalsIgnoreCase("customerDetails")) {
					submitForm();
					addCustomerDetails(jsonObj, "customerDetails");
					// Add substitution details
					addSubstitutionDetails(jsonObj, "substitution");
					break;
				}
				if (elementName.equalsIgnoreCase("sin")) {
					// Do nothing
				} else
					ActionByName.sendKeys(driver, elementName, elementValue.getAsString(), TIMEOUT_IN_SECS);
			}
			submitForm();
		}
	}

	private void addCustomerDetails(JsonObject jsonObj, String name) {

		JsonObject customer = jsonObj.getAsJsonObject(name);
		Set<Entry<String, JsonElement>> set = customer.entrySet();
		Iterator<Entry<String, JsonElement>> iter = set.iterator();
		while (iter.hasNext()) {
			Entry<String, JsonElement> element = iter.next();
			String elementName = element.getKey();
			JsonElement elementValue = element.getValue();
			ActionById.sendKeys(driver, elementName, elementValue.getAsString(), TIMEOUT_IN_SECS);
		}
		submitForm();
	}

	private void addSubstitutionDetails(JsonObject jsonObj, String name) {

		JsonObject customer = jsonObj.getAsJsonObject(name);
		Set<Entry<String, JsonElement>> set = customer.entrySet();
		Iterator<Entry<String, JsonElement>> iter = set.iterator();
		while (iter.hasNext()) {
			Entry<String, JsonElement> element = iter.next();
			String elementName = element.getKey();
			JsonElement elementValue = element.getValue();
			ActionById.sendKeys(driver, elementName, elementValue.getAsString(), TIMEOUT_IN_SECS);
		}
		submitForm();
	}

	/**
	 * Edit Project
	 * 
	 * @param array
	 */
	public void updateForm(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		int index = 1;
		while (iterator.hasNext()) {
			JsonElement jsonElement = (JsonElement) iterator.next();
			JsonObject jsonObj = jsonElement.getAsJsonObject();

			selectProjectAction("edit", "Project " + index, "");
			index++;
			clearAllFields();

			Set<Entry<String, JsonElement>> set = jsonObj.entrySet();
			Iterator<Entry<String, JsonElement>> iter = set.iterator();
			while (iter.hasNext()) {
				Entry<String, JsonElement> element = iter.next();
				String elementName = element.getKey();
				
				JsonElement elementValue = element.getValue();
				if (elementName.equalsIgnoreCase("customerDetails")) {
					submitForm();
					clearAllFields();
					addCustomerDetails(jsonObj, "customerDetails");
					// Add substitution details
					addSubstitutionDetails(jsonObj, "substitution");
					break;
				}
				if (elementName.equalsIgnoreCase("sin")) {
					// Do nothing
				} else
				ActionByName.sendKeys(driver, elementName, elementValue.getAsString(), TIMEOUT_IN_SECS); 
			}
			submitForm();
		}
	}

	public void deleteProjects(JsonArray array) {
		int rowSize = array.size();
		for (int i = 0; i < rowSize; i++) {
			selectProjectAction("delete", "", "");
			GenericDialogPage deletePage = new GenericDialogPage(executionContext.getDriver());
			deletePage.clickConfirmYes();
		}
	}

	public void submitForm() {
		Octo_SeleniumLibrary.clickElement(driver, saveAndContinueButton);
	}

	public void clearAllFields() {
		List<WebElement> txtBoxes = ActionByTagName.getElements(driver, "textarea", TIMEOUT_IN_SECS);
		for (WebElement txt : txtBoxes) {
			txt.clear();
		}
	}

	public boolean areAllProjectsDeleted() {
		WebElement div = ActionByClassName.getElement(driver, "table2", TIMEOUT_IN_SECS);
		WebElement table = div.findElement(By.tagName("table"));
		int rowCount = TableElementUtils.getTableRowCount(table);
		// There is a header row and empty row by default
		if (rowCount <= 2) {
			WebElement row = TableElementUtils.getTableRowByCellValue(table, "delete", "Delete Project");
			if (row == null)
				return true;
			else
				return false;
		} else
			return false;
	}
}