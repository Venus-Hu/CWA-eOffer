package gov.gsa.sst.wizardmanagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.GenericDialogPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByTagName;
import util.ActionByWebElements;
import util.ActionByXpath;

/**
 * @author sjayatirtha
 *
 */

public class WizardManagementPage extends Page {

	private ExecutionContext executionContext;
	private static Logger log = LoggerFactory.getLogger(WizardManagementPage.class);
	private final int TIMEOUT_IN_SECS = 10;
	private By headerText = By.xpath("//h1[contains(text(), 'WIZARD MANAGEMENT')]");
	
	private By instructionsDivElement = By.xpath("//div[@class='panel-body']");
	private String instructionsLine1 = "Select your options as applicable to your business.";
	private String instructionsLine2 = "Click \"Save Responses\" to save your data.";
	private String instructionsNote = "Note: Selections made on this screen will be visible on the left panel under Wizard Management as well as in your Capture Pricing document. The Accessories/Options you select will not be visible on the left panel, however, they will be captured within your final template.";
	private By instructionsNoteLocator = By.xpath("//div[@class='panel-body']/span");
	private By wizOptionsTable = By.xpath("//table[@role='presentation']");

	
	public WizardManagementPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}


	@Override
	protected boolean isLoaded() {
		return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
	}
	
	@Override
	protected void load() {
		log.info("Loading Wizard management page");
		try {
			// Need to use driver as ActionBy* calls do not throw exceptions
			LeftNavigationMenu.navigateTo(driver, "Wizard Management", 10);
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();
			LeftNavigationMenu.navigateTo(driver, "Wizard Management", 60);
		}
		try {
			executionContext.testPageFor508("Wizard Management");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("'Wizard Management' page is not loaded", isLoaded());
	}

	/**
	 * @param wizOptionLocator
	 *            - Use the drop down locators defined above
	 * @param yesornoVal
	 */
	public void setWizardOptions(By wizOptionLocator, String yesornoVal) {
		if (ActionByLocator.isEnabled(driver, wizOptionLocator, TIMEOUT_IN_SECS)) {
			//ActionByLocator.selectByText(driver, wizOptionLocator, yesornoVal, TIMEOUT_IN_SECS);
		}
	}

	/**
	 * @param wizOptionLocator
	 *            - Pass the drop down locators defined above
	 * @return
	 */
	public String getSelectedWizardOption(By wizOptionLocator) {
		return ActionByLocator.getText(driver, wizOptionLocator, TIMEOUT_IN_SECS);
	}

	public void selectDiscount(String optionValue){
		ActionById.selectByText(driver, "discounts", optionValue, TIMEOUT_IN_SECS); 
	}
	
	/**
	 * Set the 'Volume/Quantity or Dollar Discounts' option
	 * 
	 * @param levelInput - Input values like 'Offer' or 'Contract' / 'SIN' / 'Line Item'
	 */
	public void setDiscountsLevel( String levelInput) {
		switch (levelInput) {
		case "Contract":
		case "Offer":
			if (ActionById.isEnabled(driver, "discountsOfferLevel", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.id( "discountsOfferLevel"));
			}
			break;
		case "SIN":
			if (ActionById.isEnabled(driver, "discountsSinLevel", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.id( "discountsSinLevel"));
			}
			break;
		case "Line Item":
			if (ActionById.isEnabled(driver, "discountsItemLevel", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.id( "discountsItemLevel"));
			}
			break;
		}
	}

	/**
	 * Select the delivery level
	 * @param option
	 * @param levelInput
	 *            - Pass input values like Offer / SIN / Line Item
	 */
	public void setDeliveryLevel(String levelInput) {
		switch (levelInput) {
		case "Offer":
			if (ActionById.isEnabled(driver, "deliveryOfferLevel", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.id( "deliveryOfferLevel"));
			}
			break;
		case "SIN":
			if (ActionById.isEnabled(driver, "deliverySinLevel", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.id( "deliverySinLevel"));
			}
			break;
		case "Line Item":
			if (ActionById.isEnabled(driver, "deliveryItemLevel", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.id( "deliveryItemLevel"));
			}
			break;
		}
	}

	public String getSelectedDiscountsLevel() {
		String discountLevel = "";
		if (ActionById.isSelected(driver, "discountsItemLevel", TIMEOUT_IN_SECS)) {
			discountLevel = "Item Level";
		} else if (ActionById.isSelected(driver, "discountsOfferLevel", TIMEOUT_IN_SECS)) {
			discountLevel = "Offer Level";
		} else {
			discountLevel = "SIN Level";
		}
		return discountLevel;
	}

	public String getSelectedDeliveryLevel() {
		String deliveryLevel = "";
		if (ActionById.isSelected(driver, "deliveryItemLevel", TIMEOUT_IN_SECS)) {
			deliveryLevel = "Item Level";
		} else if (ActionById.isSelected(driver, "deliveryOfferLevel", TIMEOUT_IN_SECS)) {
			deliveryLevel = "Offer Level";
		} else {
			deliveryLevel = "SIN Level";
		}
		return deliveryLevel;
	}

	public void clickBackBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "btnBacktoList"));
	}

	public void clickSaveResponsesBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "btnSave"));
	}

	public void clickContinueBtn() {		
		Octo_SeleniumLibrary.clickElement(driver, By.id( "saveAndContinue"));
	}

	/**
	 * This method updates the wizard management fields as specified in data file
	 * "wizardManagement":{ 
     *  	"selectDollarDiscount": "Yes",
     *  	"discountLevel": "Offer",
     *   	"selectDelivery": "Yes",
     *   	"deliveryLevel": "Line Item",
	 *      "contractorWarranty": "No"
	 *        }
	 * @param jsonObj
	 */
	public void populateForm(JsonObject jsonObj){	
		log.info("Adding Wizard Management data - populateForm");
		Set<Entry<String, JsonElement>> set = jsonObj.entrySet();
		Iterator<Entry<String, JsonElement>> iter = set.iterator();
		while (iter.hasNext()) {
			Entry<String, JsonElement> basicInfoElement = iter.next();
			String elementName = basicInfoElement.getKey();
			JsonElement elementValue = basicInfoElement.getValue();
			switch (elementName) {
			case "selectDollarDiscount":
				selectDiscount(elementValue.getAsString());
				break;
			case "discountLevel":
				setDiscountsLevel(elementValue.getAsString());
				break;
			case "selectDelivery":
				ActionById.selectByText(driver, "delivery", elementValue.getAsString(), TIMEOUT_IN_SECS);
				break;
			case "deliveryLevel":
				setDeliveryLevel(elementValue.getAsString());
				break;
			case "contractorWarranty":
				ActionById.selectByText(driver, "contractorWarranty", elementValue.getAsString(), TIMEOUT_IN_SECS);					
				break;
			case "zonalPricing":
				ActionById.selectByText(driver, "zones", elementValue.getAsString(), TIMEOUT_IN_SECS);
				break;
			case "specialCharges":
				ActionById.selectByText(driver, "specialCharges", elementValue.getAsString(), TIMEOUT_IN_SECS);
				break;
			case "specialFeatures":
				ActionById.selectByText(driver, "specialFeatures", elementValue.getAsString(), TIMEOUT_IN_SECS);
				break;
			case "accessories":
				ActionById.selectByText(driver, "accessories", elementValue.getAsString(), TIMEOUT_IN_SECS);
				break;
			case "options":
				ActionById.selectByText(driver, "options", elementValue.getAsString(), TIMEOUT_IN_SECS);
				break;
			case "photos":
				ActionById.selectByText(driver, "photos", elementValue.getAsString(), TIMEOUT_IN_SECS);
			default:
				break;
			}
		}
		clickSaveResponsesBtn();
		if(ActionByName.isDisplayed(executionContext.getDriver(), "confirmResponseChange", TIMEOUT_IN_SECS)){
			GenericDialogPage page = new GenericDialogPage(executionContext.getDriver());
			page.clickConfirmYes();
		}
	}
	
	public void verifyWizMgmtInstructionText() {
		WebElement divElement = ActionByLocator.getElement(driver, instructionsDivElement, TIMEOUT_IN_SECS);
		List<WebElement> list = ActionByTagName.getElementsInElement(driver, divElement, "li", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, list, 0, instructionsLine1, TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, list, 1, instructionsLine2, TIMEOUT_IN_SECS);

		ActionByLocator.assertTextContainsTrue(driver, instructionsNoteLocator, instructionsNote, TIMEOUT_IN_SECS); // instructions
																													// Note
	}

	public void validateOptionsText() {
		WebElement wizMgmtTable = ActionByLocator.getElement(driver, wizOptionsTable, TIMEOUT_IN_SECS);
		List<WebElement> labels = ActionByTagName.getElementsInElement(driver, wizMgmtTable, "label", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, labels, 0, "Volume/Quantity or Dollar Discounts",
				TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, labels, 1, "Delivery", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, labels, 2, "Contractor Warranty", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, labels, 3, "Zonal Pricing", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, labels, 4, "Special Charges", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, labels, 5, "Special Features", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, labels, 6, "Accessories ", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, labels, 7, "Options", TIMEOUT_IN_SECS);
		ActionByWebElements.assertTextContainsTrue(driver, labels, 8, "Photos", TIMEOUT_IN_SECS);
	}
	
	public List<String> getSelectedWizMgmtOptions()
	{
		List<String> selectedWizOptions = new ArrayList<>();
		List<WebElement> options = new ArrayList<>();
		options = ActionByXpath.getElements(driver, "//select[@class='questions-dropdown form-control']", TIMEOUT_IN_SECS);
		options.forEach((option) -> {
			Select select = new Select(option);
			String response = select.getFirstSelectedOption().getText();
            if(response.equalsIgnoreCase("Yes")){
				 selectedWizOptions.add(option.getAttribute("id"));}
		});		
		return selectedWizOptions;
	}
	
	public void resetWizMgmtOptions()
	{
		log.info("Reseting Wizard management options..");
		List<WebElement> options = new ArrayList<>();
		options = ActionByXpath.getElements(driver, "//select[@class='questions-dropdown form-control']", TIMEOUT_IN_SECS);
		for(int i = 0; i < options.size(); i++) {
			Select select = new Select(options.get(i));
			select.selectByVisibleText("No");		
		}		
		clickSaveResponsesBtn();
		if(ActionByName.isDisplayed(executionContext.getDriver(), "confirmResponseChange", TIMEOUT_IN_SECS)){
			GenericDialogPage page = new GenericDialogPage(executionContext.getDriver(), "Wizard Management", By.name("confirmResponseChange"));
			page.clickConfirmYes();
		}
	}
	

}
