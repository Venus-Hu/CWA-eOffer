package gov.gsa.sst.productcatalog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import gov.gsa.sst.commonpage.GenericDialogPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByXpath;

public class SelectComponentsModPage extends Page {

	private final int TIMEOUT_IN_SECS = 10;
	private static Logger log = LoggerFactory.getLogger(SelectComponentsModPage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'SELECT COMPONENTS')]");
	private ExecutionContext executionContext;

	private By SAVE_BTN = By.id("btnSave");
	private By SAVE_CONTINUE = By.id("saveAndContinue");
	private By CONTINUE = By.id("correctErrors");
	private By CONFIRM_SAVE = By.xpath("//span[@id='confirmSave']");
	private By CONFIRM_SAVE_YES = By.id("yesBtn");
	private By CONFIRM_SAVE_NO = By.id("noBtn");
	private By SELECT_COMPONENT_MSG = By.xpath("//span[@id='saveMessage']");
	private By MESSAGES_COLUMN = By.cssSelector("table[class*='table'] tbody tr span[id*='message']");

	public SelectComponentsModPage(ExecutionContext executionContext) {
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
			log.info("Loading Select Components page");
			LeftNavigationMenu.navigateTo(driver, "Select Components");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Select Components");
		}
		try {
			executionContext.testPageFor508("PCU-Select Components");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("Could not load 'Select Components' page for Product Catalog Update mod", isLoaded());
	}

	public void populateForm(JsonObject jsonObj) {
		selectComponents(jsonObj);
		saveComponents("Yes");
	}

	public void selectComponents(JsonObject componentsObj) {
		log.info("Selecting the components....");
		for (Map.Entry<String, JsonElement> element : componentsObj.entrySet()) {

			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			switch (elementName) {
			case "contractorWarranty":
				selectOption(elementValue, "contractorWarranty");
				break;
			case "accessories":
				selectOption(elementValue, "accessories");
				break;
			case "photos":
				selectOption(elementValue, "photos");
				break;
			case "delivery":
				selectOption(elementValue, "delivery");
				break;
			case "deliveryLevel":
				if (!elementValue.isEmpty()) {
					ActionById.selectByText(driver, "deliveryChangeLevel", "Yes", TIMEOUT_IN_SECS);
					setDeliveryLevel(elementValue);
				} else
					ActionById.selectByText(driver, "deliveryChangeLevel", "No", TIMEOUT_IN_SECS);
				break;
			case "options":
				selectOption(elementValue, "options");
				break;
			case "specialCharges":
				selectOption(elementValue, "specialCharges");
				break;
			case "specialFeatures":
				selectOption(elementValue, "specialFeatures");
				break;
			case "otherProductInformation":
				selectOption(elementValue, "OtherProductInformation");
				break;
			default:
				break;
			}
		}
	}

	public void saveComponents(String Option) {
		Octo_SeleniumLibrary.clickElement(driver, By.id("btnSave"));
		if (Option.equals("Yes")) {
			if (ActionById.isDisplayed(driver, "yesBtn", TIMEOUT_IN_SECS)) {
				GenericDialogPage confirmResponse = new GenericDialogPage(driver, "Confirm Response", By.id("yesBtn"));
				confirmResponse.clickConfirmYes();
			} else if (Option.equals("No")) {
				if (ActionById.isDisplayed(driver, "noBtn", TIMEOUT_IN_SECS)) {
					GenericDialogPage confirmResponse = new GenericDialogPage(driver, "Confirm Response",
							By.id("noBtn"));
					confirmResponse.clickConfirmYes();
				}
			}
		}
	}

	public void validateSelectedOptions() {
		log.info("Validating the Component Values ... ");
		JsonObject textObj = executionContext.getCurrentScenarioObj().getAsJsonObject("productCatalogUpdateMod")
				.getAsJsonObject("selectComponents");

		Iterator<Entry<String, JsonElement>> itr = textObj.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<String, JsonElement> entry = itr.next();
			Assert.assertEquals("Component selection value does not match", entry.getValue().getAsString(),
					ActionById.getElement(driver, entry.getKey(), TIMEOUT_IN_SECS).getAttribute("value").trim());
		}
	}

	public void validateOnConfirmationTable() {
		Octo_SeleniumLibrary.clickElement(driver, By.id("btnSave"));
		Assert.assertEquals(true, (!ActionByLocator.isEnabled(driver, SAVE_BTN, TIMEOUT_IN_SECS)));
		Assert.assertEquals(true, (!ActionByLocator.isEnabled(driver, SAVE_CONTINUE, TIMEOUT_IN_SECS)));
		log.info("Running Validations on the Confirmation Page... ");
		String readPromptText = "Are you sure you want to save the components and continue?";
		ActionByLocator.getTextEqualToVerify(driver, CONFIRM_SAVE, readPromptText, TIMEOUT_IN_SECS);
		Octo_SeleniumLibrary.clickElement(driver, CONFIRM_SAVE_YES);
		Assert.assertEquals(true, (!ActionByLocator.isEnabled(driver, CONFIRM_SAVE_YES, TIMEOUT_IN_SECS)));
		Assert.assertEquals(true, (!ActionByLocator.isEnabled(driver, CONFIRM_SAVE_NO, TIMEOUT_IN_SECS)));
		Octo_SeleniumLibrary.clickElement(driver, SAVE_CONTINUE);
		String readSelectComponentText = "SELECT COMPONENTS response is Saved.";
		ActionByLocator.getTextEqualToVerify(driver, SELECT_COMPONENT_MSG, readSelectComponentText, TIMEOUT_IN_SECS);
	}

	/**
	 * Click the "Save Changes" button in "SELECT COMPONENTS" page
	 */
	public void saveChanges() {
		Octo_SeleniumLibrary.clickElement(driver, SAVE_BTN);
	}

	/**
	 * Click the "Yes" button in the "CONFIRM PRODUCT CATALOG COMPONENT RESPONSE"
	 * page. This is a dialog page that opens after user saves changes on the
	 * "SELECT COMPONENTS" page.
	 */
	public void confirmSave() {
		Octo_SeleniumLibrary.clickElement(driver, CONFIRM_SAVE_YES);
	}

	/**
	 * Verify "Level for Delivery" message in the "CONFIRM PRODUCT CATALOG COMPONENT
	 * RESPONSE" table "Message" column
	 * 
	 * @param newDeliveryLevel      delivery level after update
	 * @param previousDeliveryLevel delivery level before update
	 * @return
	 */
	public boolean isDeliveryLevelMessage(String newDeliveryLevel, String previousDeliveryLevel) {
		String expectedMessage = "The \"Level\" for \"Delivery\" has been changed from \"" + previousDeliveryLevel
				+ "\" to \"" + newDeliveryLevel + "\", and the data in the corresponding section(s) will be removed.";
		log.info("Expected delivery message: " + expectedMessage);
		List<String> actualMessages = getSelectedComponentTableMessages();
		return actualMessages.contains(expectedMessage);
	}

	public void selectOption(String option, String elementId) {
		WebElement element = ActionById.getElement(driver, elementId, TIMEOUT_IN_SECS);
		Select select = new Select(element);
		List<WebElement> allOptions = select.getOptions();
		for (WebElement optionElement : allOptions) {
			if (optionElement.getText().contains(option)) {
				select.selectByIndex(Integer.parseInt(optionElement.getAttribute("index")));
				break;
			}
		}
	}

	public void setDeliveryLevel(String levelInput) {
		log.info("Selecting the delivery level...");
		switch (levelInput) {
		case "Contract":
			if (ActionById.isEnabled(driver, "deliveryOfferLevel", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.id("deliveryOfferLevel"));
			}
			break;
		case "SIN":
			if (ActionById.isEnabled(driver, "deliverySinLevel", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.id("deliverySinLevel"));
			}
			break;
		case "Line Item":
			if (ActionById.isEnabled(driver, "deliveryItemLevel", TIMEOUT_IN_SECS)) {
				Octo_SeleniumLibrary.clickElement(driver, By.id("deliveryItemLevel"));
			}
			break;
		}
	}

	public void clickContinueBtn() {
		Octo_SeleniumLibrary.clickElement(driver, SAVE_CONTINUE);
		Octo_SeleniumLibrary.clickElement(driver, CONTINUE);
	}

	public void validateLightningBoltText() {
		String iconText = executionContext.getCurrentScenarioObj().getAsJsonObject("productCatalogUpdateMod")
				.getAsJsonObject("selectComponents").get("lightningBoltIconText").getAsString();
		List<WebElement> boltIcons = ActionByXpath.getElements(driver, "//a/span[@class='glyphicon glyphicon-flash']",
				TIMEOUT_IN_SECS);
		for (WebElement icon : boltIcons) {
			String tooltip = icon.findElement(By.xpath("//a[./span[@class='glyphicon glyphicon-flash']]"))
					.getAttribute("data-original-title");
			Assert.assertTrue("Tooltip did not match for lightning bolt icon ", tooltip.contains(iconText));
		}
	}

	public void validateHourGlassText() {
		String iconText = executionContext.getCurrentScenarioObj().getAsJsonObject("productCatalogUpdateMod")
				.getAsJsonObject("selectComponents").get("hourglassIconText").getAsString();
		List<WebElement> hgIcons = ActionByXpath.getElements(driver, "//a/span[@class='glyphicon glyphicon-hourglass']",
				TIMEOUT_IN_SECS);
		for (WebElement icon : hgIcons) {
			String tooltip = icon.findElement(By.xpath("//a[./span[@class='glyphicon glyphicon-hourglass']]"))
					.getAttribute("data-original-title");
			Assert.assertTrue("Tooltip did not match for hourglass icon ", tooltip.contains(iconText));
		}
	}

	public void validateInformationIconText() {
		log.info("Validating the information icon text for each component");
		String tooltip;
		String labelText;
		JsonObject textObj = executionContext.getCurrentScenarioObj().getAsJsonObject("productCatalogUpdateMod")
				.getAsJsonObject("selectComponents");
		List<WebElement> infoIcons = ActionByXpath.getElements(driver,
				"//a[span[@class='glyphicon glyphicon-info-sign']]", TIMEOUT_IN_SECS);
		System.out.println(infoIcons.size());
		for (WebElement icon : infoIcons) {
			tooltip = icon.getAttribute("data-original-title");
			if (tooltip.contains("Other Product Information")) {
				labelText = icon.findElement(By.xpath("parent::span/parent::label")).getAttribute("for");
			} else {
				labelText = icon.findElement(By.xpath("preceding-sibling::label")).getAttribute("for");
			}
			switch (labelText) {
			case "contractorWarranty":
				Assert.assertTrue("contractorWarranty not found",
						tooltip.contains(textObj.get("contractorWarrantyInfoText").getAsString()));
				break;
			case "accessories":
				Assert.assertTrue("accessories not found",
						tooltip.contains(textObj.get("accessoriesInfoText").getAsString()));
				break;
			case "photos":
				Assert.assertTrue("photos not found", tooltip.contains(textObj.get("photosInfoText").getAsString()));
				break;
			case "delivery":
				Assert.assertTrue("delivery not found",
						tooltip.contains(textObj.get("deliveryInfoText").getAsString()));
				break;
			case "options":
				Assert.assertTrue("options not found", tooltip.contains(textObj.get("optionsInfoText").getAsString()));
				break;
			case "specialCharges":
				Assert.assertTrue("specialCharges not found",
						tooltip.contains(textObj.get("specialChargesInfoText").getAsString()));
				break;
			case "specialFeatures":
				Assert.assertTrue("specialFeatures not found",
						tooltip.contains(textObj.get("specialFeaturesInfoText").getAsString()));
				break;
			case "OtherProductInformation":
				Assert.assertTrue("OtherProductInformation not found",
						tooltip.contains(textObj.get("otherProductInfoText").getAsString()));
				break;
			}
		}
	}

	/**
	 * Get the contract delivery level. If delivery level cannot be determined,
	 * returns an empty string
	 * 
	 * @return "Contract", "SIN", "Line Item Level" or empty string
	 */
	public String getDeliveryLevel() {
		String deliveryLevel = "";
		String elementId = "";

		List<WebElement> radioButtons = ActionByName.getElements(driver, "deliveryLevel", TIMEOUT_IN_SECS);
		for (WebElement radioButton : radioButtons) {
			if (radioButton.isSelected()) {
				elementId = radioButton.getAttribute("id").trim();
				log.info("ID attribute is: " + elementId);

				deliveryLevel = getContractLevelFromID(elementId);
				log.info("Delivery level is: " + deliveryLevel);
			}
		}

		if (deliveryLevel.isEmpty()) {
			throw new RuntimeException(
					"Attribute '" + elementId + "' was not found for radio buttons with name='deliveryLevel'");
		}

		return deliveryLevel;
	}

	/**
	 * Maps the radio button ID to a contract delivery level. The mapped value is
	 * used for text verifications on the "CONFIRM PRODUCT CATALOG COMPONENT
	 * RESPONSE" page.
	 * 
	 * @param id the id of the delivery level radio buttons
	 * @return the contract delivery level
	 */
	private String getContractLevelFromID(String id) {
		switch (id) {
		case "deliveryOfferLevel":
			return "Contract";
		case "deliverySinLevel":
			return "SIN";
		case "deliveryItemLevel":
			return "Line Item Level";
		default:
			return "";
		}
	}

	/**
	 * Messages in "Message" column in table "CONFIRM PRODUCT CATALOG COMPONENT
	 * RESPONSE"
	 * 
	 * @return list of messages
	 */
	public List<String> getSelectedComponentTableMessages() {
		List<String> messages = new ArrayList<>();
		List<WebElement> messageCells = ActionByLocator.getElements(driver, MESSAGES_COLUMN, TIMEOUT_IN_SECS);

		for (WebElement cell : messageCells) {
			messages.add(cell.getText().trim());
		}
		return messages;
	}
}