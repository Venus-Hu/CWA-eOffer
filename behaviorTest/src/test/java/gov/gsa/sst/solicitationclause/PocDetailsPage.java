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

import gov.gsa.sst.commonpage.CommonUtilPage;
import customUtility.Octo_SeleniumLibrary;import util.ActionByLocator;
import util.ActionByName;
import util.PageUtil;	

/**
 * @author amulay
 */
public class PocDetailsPage extends Page {

	String pageTitle = "Point of Contact Information";
	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(PocDetailsPage.class);
	private ExecutionContext executionContext;
	
	public PocDetailsPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}
	
	@Override
	protected boolean isLoaded() throws Error {
		if(driver.getTitle().equalsIgnoreCase("Point of Contact Information")){
			return CommonUtilPage.isSolicitationApt(executionContext);		
		} else
			return false;
	}

	@Override
	protected void load() {
		log.info("Loading POC details page");
		try {
			executionContext.testPageFor508("POC details");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
	}

	public void setName(String input) {
		clearName(input);
		ActionByName.sendKeys(driver, "Name", input, TIMEOUT_IN_SECS);
	}

	public void verifyName(String text) {
		String txt = ActionByName.getElement(driver, "Name",  TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
	}

	public void setTitle(String input) {
		ActionByName.sendKeys(driver, "Title", input, TIMEOUT_IN_SECS);
	}


	public void verifyTitle(String text) {
		String txt = ActionByName.getElement(driver, "Title",  TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
	}

	public void setAddress1(String input) {
		clearAddress1(input);
		ActionByName.sendKeys(driver, "Address1", input, TIMEOUT_IN_SECS);
	}

	public void verifyAddress1(String text) {
		String txt = ActionByName.getElement(driver, "Address1",  TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
	}

	public void setAddress2(String input) {
		clearAddress2(input);
		ActionByName.sendKeys(driver, "Address2", input, TIMEOUT_IN_SECS);
	}

	public void verifyAddress2(String text) {
		String txt = ActionByName.getElement(driver, "Address2",  TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
	}

	public void setCity(String input) {
		clearCity(input);
		ActionByName.sendKeys(driver, "City", input, TIMEOUT_IN_SECS);
	}

	public void verifyCity(String text) {
		String txt = ActionByName.getElement(driver, "City",  TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
	}

	public void selectStateUS(String input) {
		ActionByName.selectByValue(driver, "State", input, TIMEOUT_IN_SECS);
	}

	public void verifyStateUS(String text) {
		String txt = ActionByLocator.getElement(driver, By.xpath("//*[@name='State']/option[@selected='true']"),  TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
	}

	public void setStateIntl(String input) {
		clearStateIntl(input);
		ActionByName.sendKeys(driver, "Istate", input, TIMEOUT_IN_SECS);
	}

	public void verifyStateIntl(String text) {
		ActionByName.assertEqualsText(driver, "Istate", text, TIMEOUT_IN_SECS);
	}

	public void setZip(String input) {
		clearZip(input);
		ActionByName.sendKeys(driver, "Zip", input, TIMEOUT_IN_SECS);
	}


	public void verifyZip(String text) {
		String txt = ActionByName.getElement(driver, "Zip",  TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
	}

	public void selectCountry(String input) {
		ActionByName.selectByValue(driver, "Country", input, TIMEOUT_IN_SECS);
	}

	public void verifyCountry(String text) {
		String txt = ActionByLocator.getElement(driver, By.xpath("//*[@name='Country']/option[@selected='true']"),  TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
	}

	public void setPhone(String input) {
		clearPhone(input);
		ActionByName.sendKeys(driver, "Phone", input, TIMEOUT_IN_SECS);
	}

	public void verifyPhone(String text) {
	
		String txt = ActionByName.getElement(driver, "Phone",  TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
	}

	public void setFax(String input) {
		clearFax(input);
		ActionByName.sendKeys(driver, "Fax", input, TIMEOUT_IN_SECS);
	}

	public void verifyFax(String text) {
		String txt = ActionByName.getElement(driver, "Fax",  TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
	}

	public void setEmail(String input) {
		clearEmail(input);
		ActionByName.sendKeys(driver, "Email", input, TIMEOUT_IN_SECS);
	}
	
	public void setUEI(String input) {
		clearUEI(input);
		ActionByName.sendKeys(driver, "DunsNumber", input, TIMEOUT_IN_SECS);
	}
	
	public void verifyUEI(String text) {
		String txt = ActionByName.getElement(driver, "DunsNumber", TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
	}

	public void verifyEmail(String text) {
		String txt = ActionByName.getElement(driver, "Email",  TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
	}

//	public void setDunsNumber(String input) {
//		clearDunsNumber(input);
//		ActionByName.sendKeys(driver, "DunsNumber", input, TIMEOUT_IN_SECS);
//	}
//
//	public void verifyDunsNumber(String text) {
//		String txt = ActionByName.getElement(driver, "DunsNumber",  TIMEOUT_IN_SECS).getAttribute("value");
//		Assert.assertTrue("Expected value: "+ text + " does not equal actual: " + txt , txt.equalsIgnoreCase(text));
//	}

	public void clearName(String input) {
		ActionByName.clear(driver, "Name", TIMEOUT_IN_SECS);
	}

	public void clearTitle(String input) {
		ActionByName.clear(driver, "Title", TIMEOUT_IN_SECS);
	}

	public void clearAddress1(String input) {
		ActionByName.clear(driver, "Address1", TIMEOUT_IN_SECS);
	}

	public void clearAddress2(String input) {
		ActionByName.clear(driver, "Address2", TIMEOUT_IN_SECS);
	}

	public void clearCity(String input) {
		ActionByName.clear(driver, "City", TIMEOUT_IN_SECS);
	}

	public void clearStateUS(String input) {
		ActionByName.clear(driver, "State", TIMEOUT_IN_SECS);
	}

	public void clearStateIntl(String input) {
		ActionByName.clear(driver, "Istate", TIMEOUT_IN_SECS);
	}

	public void clearZip(String input) {
		ActionByName.clear(driver, "Zip", TIMEOUT_IN_SECS);
	}

	public void clearCountry(String input) {
		ActionByName.clear(driver, "Country", TIMEOUT_IN_SECS);
	}

	public void clearPhone(String input) {
		ActionByName.clear(driver, "Phone", TIMEOUT_IN_SECS);
	}

	public void clearFax(String input) {
		ActionByName.clear(driver, "Fax", TIMEOUT_IN_SECS);
	}

	public void clearEmail(String input) {
		ActionByName.clear(driver, "Email", TIMEOUT_IN_SECS);
	}
	
	public void clearUEI(String intput) {
		ActionByName.clear(driver, "DunsNumber", TIMEOUT_IN_SECS);
	}

//	public void clearDunsNumber(String input) {
//		ActionByName.clear(driver, "DunsNumber", TIMEOUT_IN_SECS);
//	}

	public void submitBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.name( "pointOfContact"));
	}

	public void populateForm(JsonObject jsonObj){
		for (Map.Entry<String, JsonElement> element : jsonObj.entrySet()) {
			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			switch (elementName) {
			case "name":
				setName(elementValue);
				break;
			case "title":
				setTitle(elementValue);
				break;
			case "address1":
				setAddress1(elementValue);
				break;
			case "city":
				setCity(elementValue);
				break;
			case "state":
				selectStateUS(elementValue);
				break;
			case "stateIntl":
				setStateIntl(elementValue);
				break;
			case "zip":
				setZip(elementValue);
				break;
			case "country":
				selectCountry(elementValue);
				break;
			case "phone":
				setPhone(elementValue);
				break;
			case "fax":
				setFax(elementValue);
				break;
			case "email":
				setEmail(elementValue);
				break;
			case "uei":
				setUEI(elementValue);
				break;
			default:
				break;
			}
		}
		submitBtn();
	}

	public void verifyPOC(JsonObject jsonObj){
		for (Map.Entry<String, JsonElement> element : jsonObj.entrySet()) {
			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			switch (elementName) {
			case "name":
				verifyName(elementValue);
				break;
			case "title":
				verifyTitle(elementValue);
				break;
			case "address1":
				verifyAddress1(elementValue);
				break;
			case "city":
				verifyCity(elementValue);
				break;
			case "state":
				verifyStateUS(elementValue);
				break;
			case "stateIntl":
				verifyStateIntl(elementValue);
				break;
			case "zip":
				verifyZip(elementValue);
				break;
			case "country":
				verifyCountry(elementValue);
				break;
			case "phone":
				verifyPhone(elementValue);
				break;
			case "fax":
				verifyFax(elementValue);
				break;
			case "email":
				verifyEmail(elementValue);
				break;
			default:
				break;
			}
		}
		submitBtn();
	}

	public void verifyTitle() {
		PageUtil.verifyTitleIs(driver, pageTitle, TIMEOUT_IN_SECS);
	}
	
}
