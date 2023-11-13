package gov.gsa.sst.solicitationclause;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.GenericDialogPage;
import gov.gsa.sst.commonpage.SeleniumHelper;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByXpath;
import util.TableElementUtils;

public class ListClausesPage extends Page {

	private By clauseRespondLink = By.partialLinkText("Respond");
	private By clauseEditLink = By.partialLinkText("Edit");
	private By clauseDeleteLink = By.partialLinkText("Delete");
	private By headerText = By.xpath("//h1[contains(text(), 'SOLICITATION CLAUSES')]");

	private String listOfClauseTitle = "List of Clauses";
	private int TIMEOUT_IN_SECS = 3;
	private Logger log = LoggerFactory.getLogger(ListClausesPage.class);
	private ExecutionContext executionContext;

	public ListClausesPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		if (ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS)
				&& driver.getTitle().contains(listOfClauseTitle)) {
			return CommonUtilPage.isSolicitationApt(executionContext);
		} else
			return false;
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Solicitation clauses - Basic info page");
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number specified in data does not match the one on UI");
			SolicitationClausePage solPage = new SolicitationClausePage(executionContext);
			solPage.selectSolicitationClause("Basic Information");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Solicitation Clauses");
			SolicitationClausePage solPage = new SolicitationClausePage(executionContext);
			solPage.selectSolicitationClause("Basic Information");
		}
		try {
			executionContext.testPageFor508("List of Clauses");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("List of Clauses page is not loaded for Basic info", isLoaded());
	}

	public void responseClauseList(JsonObject offerObj) throws InterruptedException {
		JsonArray clauseResponseArray = offerObj.get("clauseResponse").getAsJsonArray();
		for (int i = 0; i < clauseResponseArray.size(); i++) {
			JsonObject clauseObj = clauseResponseArray.get(i).getAsJsonObject();

			for (Map.Entry<String, JsonElement> element : clauseObj.entrySet()) {
				String elementName = element.getKey();
				JsonElement elementValue = element.getValue();

				switch (elementName) {
				case "clauseNumber":
					ListClausesPage listPage = new ListClausesPage(executionContext);
					try {
						listPage.respondClauseResponse(elementValue.getAsString());
					} catch (Exception e) {
						log.error("Failure while adding clause info for " + elementName);
						log.error("Exceptoin message: " + e.getLocalizedMessage());
					}
					break;
				case "Table":
					JsonArray tableElementArray = elementValue.getAsJsonArray();
					JsonObject tableObj = null;
					for (int j = 0; j < tableElementArray.size(); j++) {
						SeleniumHelper.scrollDown(driver, 400);
						Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[@name='Save' and @value='Add']"));
						tableObj = tableElementArray.get(j).getAsJsonObject();
						Set<Entry<String, JsonElement>> tableInfoSet = tableObj.entrySet();
						Iterator<Entry<String, JsonElement>> tableiter = tableInfoSet.iterator();
						while (tableiter.hasNext()) {
							Entry<String, JsonElement> tableInfoElement = tableiter.next();

							String tableElementName = tableInfoElement.getKey();
							JsonElement tableElementValue = tableInfoElement.getValue();
//							PerformActionUtils.performActionByLocatorName(driver, tableElementName,tableElementValue.getAsString());
							SeleniumHelper.performActionByLocatorName(driver, tableElementName, tableElementValue.getAsString());

						}
						Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[@name='Save' and @value='Save']"));

					}
					break;
				default:
					log.info("Selecting default action for " + elementName + " and value: "
							+ elementValue.getAsString());
				if(ActionById.isClickable(driver, "addrow_Hazardous_Mat_ID", TIMEOUT_IN_SECS))
					Octo_SeleniumLibrary.clickElement(driver, By.id( "addrow_Hazardous_Mat_ID"));
//					PerformActionUtils.performActionByLocatorName(driver, elementName, elementValue.getAsString());
					SeleniumHelper.performActionByLocatorName(driver, elementName, elementValue.getAsString());
					break;
				}
			}
			Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[@name='Save' and @value='Save']"));
			if(ActionByXpath.isClickable(driver, "//input[@id='save' and @value='Save']", TIMEOUT_IN_SECS))
				Octo_SeleniumLibrary.clickElement(driver, By.id( "save"));
		}
		if (ActionByName.isDisplayed(driver, "backToClauseResponses", TIMEOUT_IN_SECS))
			Octo_SeleniumLibrary.clickElement(driver, By.name( "backToClauseResponses"));
	}

	public boolean checkClauseStatus(String clauseName, String status) {
		// TODO fix this method
		WebElement table = ActionByName.getElement(driver, "clauseList", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValues(table, "Clause Number", clauseName, "Status",
				status);
		if (row != null)
			return true;
		else
			return false;
	}

	public boolean checkAllClauseStatus(String status) {
		// TODO fix this method
		WebElement table = ActionByName.getElement(driver, "clauseList", TIMEOUT_IN_SECS);
		List<WebElement> tableRow = table.findElements(By.tagName("tr"));
		for (int i = 1; i < tableRow.size(); i++) {
			if (!tableRow.get(i).findElements(By.tagName("td")).get(2).getText().equalsIgnoreCase(status)) {
				return false;
			}
		}
		return true;
	}

	public void deleteAllClauseResponse() {
		WebElement table = ActionByName.getElement(driver, "clauseList", TIMEOUT_IN_SECS);
		List<WebElement> deleteLinks = table.findElements(clauseDeleteLink);
		for (int i = 0; i < deleteLinks.size(); i++) {
			driver.findElement(clauseDeleteLink).click();
			GenericDialogPage confirmRecord = new GenericDialogPage(driver, "CONFIRM DELETE", By.name("confirmAction"));
			confirmRecord.clickConfirmYes();
			isListOfClausesPage();
			// TODO Check for error messages
		}
	}

	public void deleteClauseResponse(String clauseNumber) {
		WebElement table = ActionByName.getElement(driver, "clauseList", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "Clause Number", clauseNumber);
		row.findElement(clauseDeleteLink).click();
		GenericDialogPage confirmRecord = new GenericDialogPage(executionContext.getDriver(), "CONFIRM DELETE",
				By.name("confirmAction"));
		confirmRecord.clickConfirmYes();
		isListOfClausesPage();
	}

	public void respondClauseResponse(String clauseNumber) {
		WebElement table = ActionByName.getElement(driver, "clauseList", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "Clause Number", clauseNumber);
		try {
			SeleniumHelper.scrollDown(driver, 400);
			row.findElement(clauseRespondLink).click();
		} catch (Exception e) {
			row.findElement(clauseEditLink).click();
		}
	}

	public void isListOfClausesPage() {
		driver.getTitle().contains(listOfClauseTitle);
	}

}
