package gov.gsa.sst.solicitationclause;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import comment.ExecutionContext;
import comment.Page;

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.GenericDialogPage;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.WebDriverUtil;
import util.ActionById;
import util.ActionByLinkText;
import util.ActionByLocator;
import util.ActionByPartialLinkText;
import util.PageUtil;
import util.TableElementUtils;

/**
 * Created by Debbie Richards on 2/21/2017.
 */
public class OrderingInfoPage extends Page {
	private static Logger log = LoggerFactory.getLogger(OrderingInfoPage.class);
	private final int TIMEOUT_IN_SECS = 3;

	private By headerText = By.xpath("//h1[contains(text(), 'ORDERING INFORMATION')]");
	private String orderInfo = "Ordering Information";
	private String title = "Ordering Information Type";
	private ExecutionContext executionContext;

	public OrderingInfoPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() throws Error {
		if(ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS) && driver.getTitle().equalsIgnoreCase("Ordering Information") )
			return CommonUtilPage.isSolicitationApt(executionContext);
		else 
			return false;
	}

	@Override
	protected void load() {
		log.info("Loading Ordering Information Type page");
		try {
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number/user specified in data does not match the one on UI");
			LeftNavigationMenu.navigateTo(driver, "Solicitation Clauses");
			SolicitationClausePage solPage = new SolicitationClausePage(executionContext);
			solPage.selectSolicitationClause(orderInfo);
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Solicitation Clauses");
			SolicitationClausePage solPage = new SolicitationClausePage(executionContext);
			solPage.selectSolicitationClause(orderInfo);
		}
		try {
			executionContext.testPageFor508("Ordering information");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Ordering Information Type page is not loaded", isLoaded());
	}

	// DLR 2/21/17 Ordering Options in list include "Ordering Receipt
	// Information" and "Remittance Address"
	public void selectOrderingInformation(String input) {
		WebDriverUtil.selectByValue(driver, "ordertype", input, TIMEOUT_IN_SECS);
		// TODO BUG in framework > ActionByName.selectByValue(driver, "ordertype", input, TIMEOUT_IN_SECS);
	}

	public void addOrderingInformation() {
		Octo_SeleniumLibrary.clickElement(driver, By.name( "orderInfoType"));
	}

	public WebElement getRowByName(String input) {
		WebElement table = ActionById.getElement(driver, "orderInfo", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "Name", input);
		return row;
	}

	public WebElement getRowByNameAndDescription(String name, String description) {
		WebElement table = ActionById.getElement(driver, "orderInfo", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValues(table, "Name", name, "Description", description);
		return row;
	}

	public void editByOrderingInfoName(String input) {
		ActionByPartialLinkText.getElementInElement(driver, getRowByName(input), "Edit", TIMEOUT_IN_SECS).click();
	}

	public void editByOrderingInfoNameAndDescription(String name, String description) {
		ActionByPartialLinkText
				.getElementInElement(driver, getRowByNameAndDescription(name, description), "Edit", TIMEOUT_IN_SECS)
				.click();
	}

	public void deleteByOrderingInfoName(String input) {
		ActionByPartialLinkText.getElementInElement(driver, getRowByName(input), "Delete", TIMEOUT_IN_SECS).click();
		GenericDialogPage deletePage = new GenericDialogPage(driver);
		deletePage.clickConfirmYes();
	}

	public void deleteByOrderingInfoNameAndDescription(String name, String description) {
		ActionByPartialLinkText
				.getElementInElement(driver, getRowByNameAndDescription(name, description), "Delete", TIMEOUT_IN_SECS)
				.click();
		GenericDialogPage deletePage = new GenericDialogPage(driver);
		deletePage.clickConfirmYes();
	}
	
	/**
	 * "orderingInformation": [{
				"orderInfo": "receiptorder",
				"receiptType": "Facsimile Transmission or",
				"facsimile": "123-456-7899",
				"name": "Tony Stark",
				"title": "Ironman",
				"address1": "Daily Planet street",
				"city": "Long Island",
				"state": "NY",
				"country": "USA",
				"zip": "10009",
				"phone": "123-456-7890",
				"fax": "123-456-7890",
				"email": "ironman@marvelcomics.com"
			}]
	 * @param array
	 */
	public void populateForm(JsonArray array) {
		if (array == null) {
			throw new RuntimeException("Data to populate the Ordering Information page is null - check test data");
		}
		
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			selectOrderingInformation(jsonObj.get("orderInfo").getAsString());
			addOrderingInformation();
			OrderingInfoDetailsPage detailsPage = new OrderingInfoDetailsPage(executionContext);
			detailsPage.populateForm(jsonObj);
		}
	}
	
	/**
	 * Adds ordering information in a JSON object as shown below:
	 * 
	 * "orderingInformation": {
     * 	 "orderingReceiptInformation": [
	 *		{
	 *			"orderInfo": "receiptorder",
	 *			"receiptType": "Facsimile Transmission or",
	 *			"facsimile": "123-456-7899"
	 *		}
	 *	],
	 *	 "remittanceAddress": [
	 *		{
	 *			"orderInfo": "remittanceorder",
	 *			"company": "ACME Corp.",
	 *			"address1": "3400 W Riverside Dr",
	 *			"city": "Death Valley",
	 *			"state": "NV",
	 *			"country": "USA",
	 *			"zip": "92328",
	 *			"phone": "123-456-7890",
	 *			"fax": "123-456-7890",
	 *			"email": "wiley.coyote@acmetest-corp.com"
	 *		}
	 *	]
	 * }
	 * 
	 * The above object would result in two entries created in the
	 * ordering information page: 'Ordering Receipt Information' and
	 * 'Remittance Address.'
	 * 
	 * @param jsonDataObject as described above
	 */
	public void populateForm(JsonObject jsonDataObject) {
		if (jsonDataObject == null) {
			throw new RuntimeException("Data to populate the Ordering Information page is null - check test data");
		}
		for (Map.Entry<String, JsonElement> element : jsonDataObject.entrySet()) {
			log.info("Adding ordering information for data object key '" + element.getKey() + "'");
			populateForm(element.getValue().getAsJsonArray());
		}
	}

	public boolean verifyTitleIs() {
		return PageUtil.getTitleTextEquals_tempFix(driver, title, TIMEOUT_IN_SECS);
	}

	public boolean isOrderingInfoPresent(String name) {
		WebElement row = getRowByName(name);
		if (row != null) {
			return true;
		}
		return false;
	}

	public boolean isOrderingInfoPresent(String name, String description) {
		WebElement row = getRowByNameAndDescription(name, description);
		if (row != null) {
			return true;
		}
		return false;
	}

	/**
	 * Determine if Ordering Information is displayed
	 * on the 'Your Ordering Information' table.
	 * 
	 * Sample expected format:
	 * 
	 * [{
	 *	"Name": "Facsimile Transmission",
	 *	"Description": "Ordering Receipt Information"
	 * },
	 * {
	 * 	"Name": "ACME Corp.",
	 * 	"Description": "Remittance Address"
     * }]
     * 
	 * @param yourOrderingInfo
	 * @return a map of names and whether they are displayed or not
	 */
	public Map<String, Boolean> isOrderingInfoPresent(JsonArray yourOrderingInfo) {
		Map<String, Boolean> results = new HashMap<>();
		yourOrderingInfo.forEach(orderInfo -> {
			JsonObject orderInfoObj = orderInfo.getAsJsonObject();
			
			String name = orderInfoObj.get("Name").getAsString();
			String description = orderInfoObj.get("Description").getAsString();
			
			results.put(name, isOrderingInfoPresent(name, description));
		});
		return results;
	}

	/**
	 * Click the button 'Back To Solicitation Clauses'
	 */
	public void clickBackToSolicitationClauses() {
		Octo_SeleniumLibrary.clickElement(driver, By.name( "backToClauseResponses"));
	}

	/**
	 * Deletes all entries in 'Your Ordering Information' table
	 */
	public void deleteExistingOrderingInformation() {
		try {
			while (true) {
				ActionByLinkText.click(driver, "Delete", 5);
				GenericDialogPage deletePage = new GenericDialogPage(driver);
				deletePage.clickConfirmYes();
			}
		} catch (TimeoutException e) {
			log.info("Deleted all existing ordering information from page");
		}
	}

	/**
	 * Deletes entries specified in JsonArray from 
	 * 'Your Ordering Information' table. Expected format is,
	 * by example:
	 * 
     * [{
     *   "Name": "Facsimile Transmission",
     *   "Description": "Ordering Receipt Information"
     * }, ... ],
	 * 
	 * @param deleteOrderingInfo an array of JsonObject to delete
	 */
	public void deleteExistingOrderInformation(JsonArray deleteOrderingInfo) {
		deleteOrderingInfo.forEach((orderingInfo) -> {
			JsonObject orderInfoObj = orderingInfo.getAsJsonObject();
			
			String name = orderInfoObj.get("Name").getAsString();
			String description = orderInfoObj.get("Description").getAsString();
			
			deleteByOrderingInfoNameAndDescription(name, description);
		});
	}
}
