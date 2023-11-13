package gov.gsa.sst.delivery;

import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
import gov.gsa.sst.util.WebDriverUtil;
import gov.gsa.sst.wizardmanagement.WizardManagementPage;
import util.ActionById;
import util.ActionByLocator;

public class DeliveryPage extends Page {

	private final int TIMEOUT_IN_SECS = 10;
	private static Logger log = LoggerFactory.getLogger(DeliveryPage.class);
	private By headerText = By.xpath("//h1[contains(text(),'Delivery')]");
	private ExecutionContext executionContext;
	private By SELECT_BTN = By.id("btnSelect");
	private By SIN_LABEL = By.xpath("//tr/td/div[2]/div[1]/label");
    private By FOB_DESTINATION_CONUS = By.id("fobDestination_conus"); 
    private By FOB_DESTINATION_ALASKA = By.id("fobDestination_alaska");
    private By FOB_DESTINATION_PURTO_RICO = By.id("fobDestination_pr");
    private By FOB_DESTINATION_HAWAII = By.id("fobDestination_HI");
   

	public DeliveryPage(WebDriver driver) {
		super(driver);
	}

	public DeliveryPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		log.info("Loading Delivery Page");
		try {
			LeftNavigationMenu.navigateTo(driver, "Delivery");
		} catch (Exception e) {
			WizardManagementPage wizardPage = new WizardManagementPage(executionContext);
			wizardPage.get();

			LeftNavigationMenu.navigateTo(driver, "Delivery");
		}
		try {
			executionContext.testPageFor508("Delivery");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Delivery page is not loaded", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
	}

	public void setSin(String sin) {
		ActionById.selectByText(driver, "sinNum", sin, TIMEOUT_IN_SECS);
		clickSelectBtn();
	}

	public void verifySin(String sin) {
		ActionById.assertEqualsText(driver, "sinNum", sin, TIMEOUT_IN_SECS);
	}

	public void clickSelectBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "btnSelect"));
	}

	/**
	 * 
	 * @param array
	 */
	public void populateForm(JsonArray array) {
		log.info("Adding Delivery Page data");
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement jsonElement = (JsonElement) iterator.next();
			JsonObject jsonObj = jsonElement.getAsJsonObject();

			for (Map.Entry<String, JsonElement> element : jsonObj.entrySet()) {
				String elementName = element.getKey();
				String elementValue = element.getValue().getAsString();
				switch (elementName) {
				case "sin": 
					setSin(elementValue);
					break;
				case "deliveryType_conus":
					CommonUtilPage.selectRadioOption(driver, "deliveryType_conus",
							convertDeliveryType(elementValue));
					break;
				case "deliveryType_alaska":
					CommonUtilPage.selectRadioOption(driver, "deliveryType_alaska",
							convertDeliveryType(elementValue));
					break;
				case "deliveryType_pr":
					CommonUtilPage.selectRadioOption(driver, "deliveryType_pr",
							convertDeliveryType(elementValue));
					break;
				case "deliveryType_HI":
					CommonUtilPage.selectRadioOption(driver, "deliveryType_HI",
							convertDeliveryType(elementValue));
					break;
				default:
					break;
				}
			}
			Octo_SeleniumLibrary.clickElement(driver, By.name( "saveOptions"));
		}

	}

	public String convertDeliveryType(String deliveryType) {
		switch (deliveryType) {
		case "FOB Destination":
			return "D";
		case "FOB Origin":
			return "O";
		case "No Delivery":
			return "ND";
		default:
			break;
		}
		return "";
	}
	
	public void validateDeliveryLevelUpdate(String delLvl) {
        if (delLvl.equals("Contract")) {
            LeftNavigationMenu.navigateTo(driver, "Delivery Emod");
            Assert.assertEquals(true, (!ActionByLocator.isVisible(driver, SELECT_BTN, TIMEOUT_IN_SECS)));            
            Assert.assertTrue("FOB Destionation CONUS is selected",  !WebDriverUtil.isSelected(driver, FOB_DESTINATION_CONUS, TIMEOUT_IN_SECS));
            Assert.assertTrue("FOB Destionation ALASKA is selected",  !WebDriverUtil.isSelected(driver, FOB_DESTINATION_ALASKA, TIMEOUT_IN_SECS));
            Assert.assertTrue("FOB Destionation PURTO RICO is selected",  !WebDriverUtil.isSelected(driver, FOB_DESTINATION_PURTO_RICO, TIMEOUT_IN_SECS));
            Assert.assertTrue("FOB Destionation HAWAII is selected",  !WebDriverUtil.isSelected(driver, FOB_DESTINATION_HAWAII, TIMEOUT_IN_SECS));       
            } else if (delLvl.equals("SIN")) {        	       
        	          LeftNavigationMenu.navigateTo(driver, "Delivery Emod");  
        	          Assert.assertEquals(true, ActionByLocator.isVisible(driver, SELECT_BTN, TIMEOUT_IN_SECS));
        	          Assert.assertEquals("SIN *", ActionByLocator.getElement(driver, SIN_LABEL, TIMEOUT_IN_SECS)
          			      .getText().trim());       
        	          Assert.assertEquals(true, WebDriverUtil.isSelected(driver, FOB_DESTINATION_CONUS, TIMEOUT_IN_SECS));
        	          Assert.assertEquals(true, WebDriverUtil.isSelected(driver, FOB_DESTINATION_ALASKA, TIMEOUT_IN_SECS));
        	          Assert.assertEquals(true, WebDriverUtil.isSelected(driver, FOB_DESTINATION_PURTO_RICO, TIMEOUT_IN_SECS));
        	          Assert.assertEquals(true, WebDriverUtil.isSelected(driver, FOB_DESTINATION_HAWAII, TIMEOUT_IN_SECS));       	          
                      } else { 
            	              log.info("Delivery Level not defined...");
         } 
    }

}
