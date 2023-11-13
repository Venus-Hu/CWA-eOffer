package gov.gsa.sst.subcontract;

import java.util.Iterator;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import comment.ExecutionContext;

import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionByName;

public class ProgramAdministratorPage extends SubContractingPlanPage{
	private final int TIMEOUT_IN_SECS = 30;
	private static Logger log = LoggerFactory.getLogger(ProgramAdministratorPage.class);
	private By menuItem = By.cssSelector("a[title=\"SCA MAtrix\"] > img");
	private ExecutionContext executionContext;
	
	public ProgramAdministratorPage(ExecutionContext executionContext) {
		super(executionContext);
		this.executionContext = executionContext;
	}
	
	@Override
	protected void load() {
		log.info("Loading Program Admin page");
		try {
			Octo_SeleniumLibrary.clickElement(driver, menuItem);
		} catch(Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();
			
			Octo_SeleniumLibrary.clickElement(driver, menuItem);
		}
		try {
			executionContext.testPageFor508("SubK-Program Administrator");
		}catch (Exception e) {
			log.warn("Section 508 exception: " +e.getMessage());
		}
		Assert.assertTrue("'Program Admin' page was not loaded", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		return false;
	}

	public void setName(String input) {
		ActionByName.sendKeys(driver, "Name", input, TIMEOUT_IN_SECS);
	}
	
	public void setTitle(String input) {
		ActionByName.sendKeys(driver, "Title", input, TIMEOUT_IN_SECS);
	}
	
	public void setAddress1(String input) {
		ActionByName.sendKeys(driver, "Address1", input, TIMEOUT_IN_SECS);
	}
	
	public void setCity(String input) {
		ActionByName.sendKeys(driver, "City", input, TIMEOUT_IN_SECS);
	}

	public void selectStateUS(String input) {
		ActionByName.selectByValue(driver, "State", input, TIMEOUT_IN_SECS);
	}

	public void setZip(String input) {
		ActionByName.sendKeys(driver, "Zip", input, TIMEOUT_IN_SECS);
	}
	
	public void setStateIntl(String input) {
		ActionByName.sendKeys(driver, "Istate", input, TIMEOUT_IN_SECS);
	}
	
	public void selectCountry(String input) {
		ActionByName.selectByValue(driver, "Country", input, TIMEOUT_IN_SECS);
	}
	
	public void setPhone(String input) {
		ActionByName.sendKeys(driver, "Phone", input, TIMEOUT_IN_SECS);
	}

	public void setFax(String input) {
		ActionByName.sendKeys(driver, "Fax", input, TIMEOUT_IN_SECS);
	}

	public void setEmail(String input) {
		ActionByName.sendKeys(driver, "Email", input, TIMEOUT_IN_SECS);
	}

	public void populateForm(JsonArray array){
		 Iterator<JsonElement> iterator = array.iterator();
		 int i = 0;
		  while (iterator.hasNext()) {
		    JsonElement element = (JsonElement)iterator.next();
		    JsonObject jsonObj = element.getAsJsonObject();
		    if( i == 0 ){
		    	Octo_SeleniumLibrary.clickElement(driver, By.name( "addPocButton"));
		    }
		    else
		    	Octo_SeleniumLibrary.clickElement(driver, By.name( "addAltPocButton"));
		 
			setName(jsonObj.get("name").getAsString());
			setTitle(jsonObj.get("title").getAsString());
			setAddress1(jsonObj.get("address1").getAsString());
			setCity(jsonObj.get("city").getAsString());
		    if(jsonObj.has("state")){
				selectStateUS(jsonObj.get("state").getAsString());
			} else {
				setStateIntl(jsonObj.get("stateIntl").getAsString());
			}
		    setZip(jsonObj.get("zip").getAsString());
		    selectCountry(jsonObj.get("country").getAsString());
		    setPhone(jsonObj.get("phone").getAsString());
			setFax(jsonObj.get("fax").getAsString());
			setEmail(jsonObj.get("email").getAsString());
			Octo_SeleniumLibrary.clickElement(driver, By.name( "savePocDetails"));
		    i++;
		  }
			Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@value,'Save and Continue')]"));
	}
}
