package gov.gsa.sst.solicitationclause;

import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import comment.ExecutionContext;
import comment.Page;

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.WebDriverUtil;
import util.ActionByLocator;
import util.ActionByName;
import util.PageUtil;

/**
 * Created by Debbie Richards on 2/8/2017.
 */
public class OrderingInfoDetailsPage extends Page {
	private static Logger log = LoggerFactory.getLogger(OrderingInfoDetailsPage.class);
	private final int TIMEOUT_IN_SECS = 30;
	private By headerText = By.xpath("//h1[contains(text(), 'ORDERING INFORMATION')]");
	private String pageTitle = "Ordering Information Details";
	private ExecutionContext executionContext;
	private String orderInfo = "Ordering Information";
	
	public OrderingInfoDetailsPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() throws Error {
		return ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		log.info("Loading Ordering Information Details page");
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
			executionContext.testPageFor508("Ordering information details");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Ordering Information Details page is not loaded", isLoaded());
	}

	// DLR 2/21/17 - Not sure if this is how to handle radio buttons
	/**
	 * 
	 * @param input
	 *            Options are "Computer-to-Computer Electronic Data Interchange
	 *            (EDI)" or "Facsimile Transmission or"
	 */
	public void setReceiptType(String input) {
		CommonUtilPage.selectRadioOption(driver, "ReceiptType", input);
	}

	public void verifyReceiptType(String text) {
		ActionByName.assertEqualsText(driver, "ReceiptType", text, TIMEOUT_IN_SECS);
	}

	public void setFacsimile(String input) {
		clear("FacsimileFax");
		ActionByName.sendKeys(driver, "FacsimileFax", input, TIMEOUT_IN_SECS);
	}

	public String getFacsimile() {
		return ActionByName.getText(driver, "FacsimileFax", TIMEOUT_IN_SECS);
	}

	public void verifyFacsimile(String text) {
		ActionByName.assertEqualsText(driver, "FacsimileFax", text, TIMEOUT_IN_SECS);
	}

	public void setName(String input) {
		clear("Name");
		ActionByName.sendKeys(driver, "Name", input, TIMEOUT_IN_SECS);
	}

	public String getName() {
		return ActionByName.getText(driver, "Name", TIMEOUT_IN_SECS);
	}

	public void verifyName(String text) {
		ActionByName.assertEqualsText(driver, "Name", text, TIMEOUT_IN_SECS);
	}

	public void setTitle(String input) {
		clear("Title");
		ActionByName.sendKeys(driver, "Title", input, TIMEOUT_IN_SECS);
	}

	public String getTitle() {
		return ActionByName.getText(driver, "Title", TIMEOUT_IN_SECS);
	}

	public void verifyTitle(String text) {
		ActionByName.assertEqualsText(driver, "Title", text, TIMEOUT_IN_SECS);
	}

	public void setAddress1(String input) {
		clear("Address1");
		ActionByName.sendKeys(driver, "Address1", input, TIMEOUT_IN_SECS);
	}

	public String getAddress1() {
		return ActionByName.getText(driver, "Address1", TIMEOUT_IN_SECS);
	}

	public void verifyAddress1(String text) {
		ActionByName.assertEqualsText(driver, "Address1", text, TIMEOUT_IN_SECS);
	}

	public void setAddress2(String input) {
		ActionByName.sendKeys(driver, "Address2", input, TIMEOUT_IN_SECS);
	}

	public String getAddress2() {
		return ActionByName.getText(driver, "Address2", TIMEOUT_IN_SECS);
	}

	public void verifyAddress2(String text) {
		ActionByName.assertEqualsText(driver, "Address2", text, TIMEOUT_IN_SECS);
	}

	public void setCity(String input) {
		clear("City");
		ActionByName.sendKeys(driver, "City", input, TIMEOUT_IN_SECS);
	}

	public String getCity() {
		return ActionByName.getText(driver, "City", TIMEOUT_IN_SECS);
	}

	public void verifyCity(String text) {
		ActionByName.assertEqualsText(driver, "City", text, TIMEOUT_IN_SECS);
	}

	public void selectStateUS(String input) {
		WebDriverUtil.selectByValue(driver, "State", input, TIMEOUT_IN_SECS);
	}

	public void getStateUS() {
		ActionByName.getText(driver, "State", TIMEOUT_IN_SECS);
	}

	public void verifyStateUS(String text) {
		ActionByName.assertEqualsText(driver, "State", text, TIMEOUT_IN_SECS);
	}

	public void setStateIntl(String input) {
		clear("Istate");
		ActionByName.sendKeys(driver, "Istate", input, TIMEOUT_IN_SECS);
	}

	public String getStateIntl() {
		return ActionByName.getText(driver, "Istate", TIMEOUT_IN_SECS);
	}

	public void verifyStateIntl(String text) {
		ActionByName.assertEqualsText(driver, "Istate", text, TIMEOUT_IN_SECS);
	}

	public void setZip(String input) {
		clear("Zip");
		ActionByName.sendKeys(driver, "Zip", input, TIMEOUT_IN_SECS);
	}

	public String getZip() {
		return ActionByName.getText(driver, "Zip", TIMEOUT_IN_SECS);
	}

	public void verifyZip(String text) {
		ActionByName.assertEqualsText(driver, "Zip", text, TIMEOUT_IN_SECS);
	}

	public void selectCountry(String input) {		
		WebDriverUtil.selectByValue(driver, "Country", input, TIMEOUT_IN_SECS);
		// TODO Bug in framework > ActionByName.selectByValue(driver, "Country", input, TIMEOUT_IN_SECS);
	}

	public void getCountry() {
		ActionByName.getText(driver, "Country", TIMEOUT_IN_SECS);
	}

	public void verifyCountry(String text) {
		ActionByName.assertEqualsText(driver, "Country", text, TIMEOUT_IN_SECS);
	}

	public void setPhone(String input) {
		clear("Phone");
		ActionByName.sendKeys(driver, "Phone", input, TIMEOUT_IN_SECS);
	}

	public String getPhone() {
		return ActionByName.getText(driver, "Phone", TIMEOUT_IN_SECS);
	}

	public void verifyPhone(String text) {
		ActionByName.assertEqualsText(driver, "Phone", text, TIMEOUT_IN_SECS);
	}

	public void setFax(String input) {
		clear("Fax");
		ActionByName.sendKeys(driver, "Fax", input, TIMEOUT_IN_SECS);
	}

	public String getFax() {
		return ActionByName.getText(driver, "Fax", TIMEOUT_IN_SECS);
	}

	public void verifyFax(String text) {
		ActionByName.assertEqualsText(driver, "Fax", text, TIMEOUT_IN_SECS);
	}

	public void setEmail(String input) {
		clear("Email");
		ActionByName.sendKeys(driver, "Email", input, TIMEOUT_IN_SECS);
	}

	public String getEmail() {
		return ActionByName.getText(driver, "Email", TIMEOUT_IN_SECS);
	}

	public void verifyEmail(String text) {
		ActionByName.assertEqualsText(driver, "Email", text, TIMEOUT_IN_SECS);
	}

	public void setCompanyName(String input) {
		clear("CompanyName");
		ActionByName.sendKeys(driver, "CompanyName", input, TIMEOUT_IN_SECS);
	}

	private void clear(String name) {
		ActionByName.clear(driver, name, TIMEOUT_IN_SECS);
	}

	public String getCompanyName() {
		return ActionByName.getText(driver, "CompanyName", TIMEOUT_IN_SECS);
	}

	public void verifyCompanyName(String text) {
		ActionByName.assertEqualsText(driver, "CompanyName", text, TIMEOUT_IN_SECS);
	}

	public void setAttention(String input) {
		ActionByName.sendKeys(driver, "Attention", input, TIMEOUT_IN_SECS);
	}

	public String getAttention() {
		return ActionByName.getText(driver, "Attention", TIMEOUT_IN_SECS);
	}
	
	public void submitBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.name( "saveOrder"));
	}

	public void populateForm(JsonObject jsonObj) {
		for(Map.Entry<String, JsonElement> element: jsonObj.entrySet()) {
			
			String elementName = element.getKey();
			JsonElement elementValue = element.getValue();
			switch (elementName) {
			case "receiptType":
				setReceiptType(elementValue.getAsString());
				break;
			case "facsimile":
				setFacsimile(elementValue.getAsString());
				break;
			case "name":
				setName(jsonObj.get("name").getAsString());
				break;
			case "title":
				setTitle(jsonObj.get("title").getAsString());
				break;
			case "address1":
				setAddress1(jsonObj.get("address1").getAsString());
				break;
			case "city":
				setCity(jsonObj.get("city").getAsString());
				break;
			case "state":
				selectStateUS(jsonObj.get("state").getAsString());
				break;
			case "sateIntl":
				setStateIntl(jsonObj.get("stateIntl").getAsString());
				break;
			case "zip":	
				setZip(jsonObj.get("zip").getAsString());
				break;
			case "country":
				selectCountry(jsonObj.get("country").getAsString());
				break;
			case "phone":
				setPhone(jsonObj.get("phone").getAsString());
				break;
			case "fax":
				setFax(jsonObj.get("fax").getAsString());
				break;
			case "email":
				setEmail(jsonObj.get("email").getAsString());
				break;
			case "company":
				setCompanyName(jsonObj.get("company").getAsString());
				break;
			default:
				break;
			}
		}
		submitBtn();
	}

	public void verifyTitle() {
		PageUtil.verifyTitleIs(driver, pageTitle, TIMEOUT_IN_SECS);
	};
}