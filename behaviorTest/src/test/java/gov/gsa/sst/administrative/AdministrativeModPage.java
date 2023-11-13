package gov.gsa.sst.administrative;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
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
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.negotiator.NegotiatorPage;
import gov.gsa.sst.solicitationclause.PointOfContactPage;
import gov.gsa.sst.util.exceptions.TestDataException;
import gov.gsa.sst.util.exceptions.TestExecutionException;
import util.ActionByName;
import util.ActionByXpath;
import util.PageUtil;

public class AdministrativeModPage extends Page {
	private static final int TIMEOUT_IN_SECS = 5;
	private static Logger log = LoggerFactory.getLogger(AdministrativeModPage.class);
	private ExecutionContext executionContext;

	public AdministrativeModPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		// This check is for title as it can be Administrative, Point of Contacts for
		// Manufacturers, Dealers, Resellers, Agents, POINT OF CONTACT INFORMATION,
		// Legal

		try {
			if (PageUtil.getTitleTextEquals_tempFix(driver, "Administrative", TIMEOUT_IN_SECS))
				return true;
			else if (PageUtil.getTitleTextEquals_tempFix(driver,
					"Point of Contacts for Manufacturers, Dealers, Resellers, Agents", TIMEOUT_IN_SECS))
				return true;
			else if (PageUtil.getTitleTextEquals_tempFix(driver, "POINT OF CONTACT INFORMATION", TIMEOUT_IN_SECS))
				return true;
			else if (PageUtil.getTitleTextEquals_tempFix(driver, "LEGAL", TIMEOUT_IN_SECS))
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	protected void load() {
		try {
			log.info("Loading 'Administrative' page for mod");
			LeftNavigationMenu.navigateTo(driver, "Administrative", 5);
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Administrative");
		}
		try {
			executionContext.testPageFor508("Administrative mod");
		} catch (Exception e) {
			log.warn("Section 508 exception: " + e.getMessage());
		}
		Assert.assertTrue("Could not load 'Administrative' page for mod", isLoaded());
	}

	/**
	 * Entry point for Administrative mod page object. Code will execute according
	 * to the admin mod sub-type
	 * 
	 * @param administrativeModObjects
	 */
	public void populateForm(JsonArray administrativeModObjects) {
		Iterator<JsonElement> iterator = administrativeModObjects.iterator();
		while (iterator.hasNext()) {
			JsonObject adminModElement = iterator.next().getAsJsonObject();

			String adminModSubType = adminModElement.get("type").getAsString();
			switch (adminModSubType) {
			case "addressChange":
				doAddressChangeMod(adminModElement);
				break;
			case "pocManufDealears":
				doPOCsManufDealersMod(adminModElement);
				break;
			case "authorizedNegotiator":
				doNegotiatorMod(adminModElement);
				break;
			case "orderPOC":
			case "iffPOC":
				doOrderOrIFFPOCMod();
				break;
			case "pointOfContact":
			case "telephoneChange":
			case "faxChange":
			case "url":
			case "contractEndDate":
			case "emailChange":
				editBasicContractDetails(adminModElement);
				break;
			default:
				throw new TestDataException("Unknown admin mod sub-type '" + adminModSubType + "'");
			}
		}
	}

	private void doAddressChangeMod(JsonElement adminModElement) {
		String currentDUNS = executionContext.getCurrentScenarioObj().get("UEI").getAsString();
		String updatedDUNS = adminModElement.getAsJsonObject().has("updatedDUNS")
				? adminModElement.getAsJsonObject().get("updatedDUNS").getAsString()
				: "";

		log.info("Executing DUNS update workflow if needed...");
		doDUNSUpdate(currentDUNS, updatedDUNS);

		if (adminModElement.getAsJsonObject().has("modDetailDescription")) {
			if (driver.getTitle().contains("Verify Company Information for entered DUNS")) {
				log.info("Accept information on 'Corporate Information' page");
				Octo_SeleniumLibrary.clickElement(driver,
						By.xpath("//input[@name='saveAndContinue' and @value='Save and Continue']"));
			}

			log.info("Entering detailed mod description...");
			enterDetailedModDescription(adminModElement.getAsJsonObject().get("modDetailDescription").getAsString());
		}
	}

	private void doNegotiatorMod(JsonObject adminModElement) {
		String action = adminModElement.getAsJsonObject().get("action").getAsString();
		switch (action) {
		case "add":
			log.info("Opening 'Negotiator' page");
			Octo_SeleniumLibrary.clickElement(driver, By.name( "addNewNegotiator"));
			addNegotiator();
			break;
		case "edit":
			throw new TestExecutionException("'edit' action has not been implemented yet");
		// break;
		case "delete":
			throw new TestExecutionException("'delete' action has not been implemented yet");
		// break;
		default:
			throw new TestDataException("Unknown action '" + action + "' for negotiator mod");
		}
	}

	private void doPOCsManufDealersMod(JsonObject adminModElement) {
		log.info("Entering detailed mod description... ");
		enterDetailedModDescription(adminModElement.getAsJsonObject().get("modDetailDescription").getAsString());
	}

	private void doOrderOrIFFPOCMod() {
		log.info("Adding Order POC or IFF POC to contract");
		PointOfContactPage pocPage = new PointOfContactPage(executionContext);
		pocPage.populateForm(executionContext.getCurrentScenarioObj().get("pointOfContact").getAsJsonArray());
	}

	private void enterDetailedModDescription(String modDetailDescription) {
		ActionByName.clear(driver, "ModDetailDescription", TIMEOUT_IN_SECS);
		ActionByName.sendKeys(driver, "ModDetailDescription", modDetailDescription, TIMEOUT_IN_SECS);
		Octo_SeleniumLibrary.clickElement(driver, By.name( "saveAndContinue"));
	}

	private void doDUNSUpdate(String currentDUNS, String updatedDUNS) {
		if (!updatedDUNS.isEmpty() && !currentDUNS.equals(updatedDUNS)) {
			log.info("Updating DUNS from " + currentDUNS + " to " + updatedDUNS);
			CommonUtilPage.selectRadioOption(driver, "replaceDuns", "Yes");

			switch (updatedDUNS.length()) {
			case 12:
				ActionByName.sendKeys(driver, "dunsNumber", updatedDUNS, TIMEOUT_IN_SECS);
				break;
			case 13:
				ActionByName.sendKeys(driver, "dunsNumber", updatedDUNS.substring(0, 9), TIMEOUT_IN_SECS);
				ActionByName.sendKeys(driver, "dunsPlus4", updatedDUNS.substring(9, updatedDUNS.length()),
						TIMEOUT_IN_SECS);
				break;
			default:
				throw new TestDataException("Illegal value for DUNS: " + updatedDUNS);
			}
			log.info("Saving updated DUNS '" + updatedDUNS + "' to executionContext");
			executionContext.getCurrentScenarioObj().addProperty("UEI", updatedDUNS);
		} else {
			CommonUtilPage.selectRadioOption(driver, "replaceDuns", "No");
			log.info("Saving DUNS '" + currentDUNS + "' to executionContext");
			executionContext.getCurrentScenarioObj().addProperty("UEI", currentDUNS);
		}
		Octo_SeleniumLibrary.clickElement(driver, By.name( "Save and Continue"));
	}

	private void addNegotiator() {
		log.info("Add new negotiator...");
		NegotiatorPage negotiatorPage = new NegotiatorPage(executionContext);
		negotiatorPage.populateForm(executionContext.getCurrentScenarioObj().get("negotiator").getAsJsonArray());
	}

	/**
	 * Takes an array of negotiators and verified if these exist on the "Your
	 * Negotiator(s) Details" table. Example 'negotiator' array:
	 * 
	 * "negotiator": [ { "name": "Bindu M Somana", "title": "CO", "phone":
	 * "123-456-7890", "email": "bindu.somana@gsa.gov", "fax": "123-444-5678",
	 * "role": "Authorized to sign" } ],
	 * 
	 * @param negotiators
	 * @return
	 */
	public Map<String, Boolean> isNegotiatorAddedToMod(JsonArray negotiators) {
		Map<String, Boolean> negotiatorMap = new HashMap<>();
		NegotiatorPage negotiatorPage = new NegotiatorPage(executionContext);

		log.info("Extracting negotiator and role from negotiator's table");
		Iterator<JsonElement> iterator = negotiators.iterator();
		while (iterator.hasNext()) {
			JsonObject negotiator = iterator.next().getAsJsonObject();
			String name = negotiator.get("name").getAsString();
			String role = negotiator.get("role").getAsString();
			negotiatorMap.put("negotiator: " + name + ", role: " + role,
					negotiatorPage.isNegotiatorAddedToMod(negotiator));
		}
		return negotiatorMap;
	}

	private void editBasicContractDetails(JsonObject adminModElement) {
		String adminType = getStringElement(adminModElement, "type");
		String text = getStringElement(adminModElement, adminType);

		ActionByName.clear(driver, adminType, TIMEOUT_IN_SECS);
		ActionByName.sendKeys(driver, adminType, text, TIMEOUT_IN_SECS);
		Octo_SeleniumLibrary.clickElement(driver, By.name( "saveAndContinue"));
	}

	private void verifyBasicContractDetails(JsonObject verifyAdminModElement) {
		String adminType = getStringElement(verifyAdminModElement, "type");
		String text = getStringElement(verifyAdminModElement, adminType);

		String txt = ActionByName.getElement(driver, adminType, TIMEOUT_IN_SECS).getAttribute("value");
		Assert.assertTrue("Expected value: " + text + " does not equal actual: " + txt, txt.equalsIgnoreCase(text));
	}

	private String getStringElement(JsonObject jsonObject, String key) {
		if (!jsonObject.has(key)) {
			throw new TestDataException("'" + key + "' must be defined in the object: " + jsonObject);
		}
		return jsonObject.get(key).getAsString();
	}

	/**
	 * Check whether or not POCs in input array exist in the "Point of Contact
	 * Information" table. Example object:
	 * 
	 * "pointOfContact": [ { "poc": "Order POC", "description": "Order POC", "name":
	 * "Bruce Wayne" } ]
	 * 
	 * @param pocArray
	 * @return map of POCs and whether they are displayed on table
	 */
	public Map<String, Boolean> pointsOfContactExist(JsonArray pocArray) {
		Map<String, Boolean> resultsMap = new HashMap<>();
		log.info("Checking Order POC was added to mod");

		PointOfContactPage pocPage = new PointOfContactPage(executionContext);
		pocPage.verifyPocDetails(executionContext.getCurrentScenarioObj().getAsJsonArray("verifyPOC"));

		Iterator<JsonElement> iterator = pocArray.iterator();
		while (iterator.hasNext()) {
			JsonObject poc = iterator.next().getAsJsonObject();

			String name = getStringElement(poc, "name");
			String description = getStringElement(poc, "description");

			resultsMap.put(name + ", " + description, pocPage.isPOCPresent(name, description));
		}
		return resultsMap;
	}

	public void verifySpecialCharacterHandling(JsonArray verifiedData) {
		Iterator<JsonElement> iterator = verifiedData.iterator();
		while (iterator.hasNext()) {
			JsonObject adminModElement = iterator.next().getAsJsonObject();

			String adminModSubType = adminModElement.get("type").getAsString();
			switch (adminModSubType) {
			case "pointOfContact":
				verifyBasicContractDetails(adminModElement);
				break;
			default:
				throw new TestDataException("Unknown admin mod sub-type '" + adminModSubType + "'");
			}
		}
	}
}
