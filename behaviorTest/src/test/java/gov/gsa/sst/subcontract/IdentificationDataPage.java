package gov.gsa.sst.subcontract;

import java.util.Map;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.SeleniumHelper;
import util.ActionById;
import util.ActionByName;

public class IdentificationDataPage extends SubContractingPlanPage {
	private final int TIMEOUT_IN_SECS = 10;
	private static Logger log = LoggerFactory.getLogger(IdentificationDataPage.class);
	ExecutionContext executionContext;

	public IdentificationDataPage(ExecutionContext executionContext) {
		super(executionContext);
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		log.info("Loading identification page");
		try {
			executionContext.testPageFor508("Identification page");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
	}

	@Override
	protected boolean isLoaded() {
		return false;
	}

	public void populateForm(JsonObject jsonObj) {

		for (Map.Entry<String, JsonElement> element : jsonObj.entrySet()) {

			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			switch (elementName) {
			
			case "uei":
				ActionByName.click(driver, "addDunsUeiButton", TIMEOUT_IN_SECS);
				ActionByName.sendKeys(driver, "dunsUeiNumber", elementValue, TIMEOUT_IN_SECS);
				driver.findElement(By.xpath("//input[@name = 'isContractDunsUei' and @value = 'Yes']")).click();
				ActionById.click(driver, "subYes", TIMEOUT_IN_SECS);
				break;
			case "suppliesDesc":
				ActionByName.sendKeys(driver, "descOfSupplier", elementValue, TIMEOUT_IN_SECS);
				break;
			case "placeOfPerformance":
				ActionByName.sendKeys(driver, "placeOfPerform", elementValue, TIMEOUT_IN_SECS);
				break;
			case "basePeriod":
			//	SeleniumHelper.sendKeyById_tempFix(driver, "Base", elementValue);
				ActionById.sendKeys(driver, "Base", elementValue, TIMEOUT_IN_SECS);
				break;
			case "period1":
			//	SeleniumHelper.sendKeyById_tempFix(driver, "OptionPeriod1", elementValue);
				ActionById.sendKeys(driver, "OptionPeriod1", elementValue, TIMEOUT_IN_SECS);
				break;
			case "period2":
			//	SeleniumHelper.sendKeyById_tempFix(driver, "OptionPeriod2", elementValue);
				ActionById.sendKeys(driver, "OptionPeriod2", elementValue, TIMEOUT_IN_SECS);
				break;
			case "period3":
				SeleniumHelper.sendKeyById_tempFix(driver, "OptionPeriod3", elementValue);
			//	ActionById.sendKeys(driver, "OptionPeriod3", elementValue, TIMEOUT_IN_SECS);
				break;
			case "subkStartDate":
				ActionById.sendKeys(driver, "subkStartDate", elementValue, TIMEOUT_IN_SECS);
				break;
			case "startDate":
				setStartDate(CommonUtilPage.getStartDate());
				break;
			case "endDate":
				setEndDate(CommonUtilPage.getEndDate());
				break;
			case "annualSales":
			//	ActionById.sendKeys(driver, "annualSales", elementValue, TIMEOUT_IN_SECS);
				SeleniumHelper.sendKeyById_tempFix(driver, "annualSales", elementValue);
				break;
			default:
				break;
			}
		}
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[contains(@value,'Save and Continue')]"));
	}

	public void setStartDate(String date) {
		ActionByName.sendKeys(driver, "startDate", date, TIMEOUT_IN_SECS);
	}

	public void setEndDate(String date) {
		ActionByName.sendKeys(driver, "endDate", date, TIMEOUT_IN_SECS);
	}
}
