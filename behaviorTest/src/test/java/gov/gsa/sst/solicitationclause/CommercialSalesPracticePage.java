package gov.gsa.sst.solicitationclause;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
import gov.gsa.sst.commonpage.SeleniumHelper;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.DataUtil;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByXpath;
import util.TableElementUtils;

public class CommercialSalesPracticePage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(CommercialSalesPracticePage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'COMMERCIAL SALES PRACTICE (CSP-1)')]");
	private ExecutionContext executionContext;
	private By cancelBtn = By.xpath("//input[@value='Cancel']");
	private By saveAddDiscountPolicyBtn = By.xpath("//input[@value='Save']");
	// On mod pages only
	private By reviseBtn = By.name("change");

	public CommercialSalesPracticePage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		log.info("Loading 'Commercial Sales Practice' page");
		try {
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number/user specified in data does not match the one on UI");
			LeftNavigationMenu.navigateTo(driver, "Solicitation Clauses");
			SolicitationClausePage solPage = new SolicitationClausePage(executionContext);
			solPage.selectSolicitationClause("Commercial Sales Practice");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Solicitation Clauses");
			SolicitationClausePage solPage = new SolicitationClausePage(executionContext);
			solPage.selectSolicitationClause("Commercial Sales Practice");
		}
		try {
			executionContext.testPageFor508("Commercial Sales practice");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("Commercial Sales Practice page is not loaded", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		if (ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS)) {
			return CommonUtilPage.isSolicitationApt(executionContext);
		} else
			return false;
	}

	public void setDollarValue(String dollarInput) {
		ActionById.clear(driver, "salesDollarValue", TIMEOUT_IN_SECS);
		ActionById.sendKeys(driver, "salesDollarValue", dollarInput, TIMEOUT_IN_SECS);
	}

	public void verifyDollarValue(String dollarVal) {
		ActionById.assertEqualsText(driver, "salesDollarValue", dollarVal, TIMEOUT_IN_SECS);
	}

	/**
	 * Set beginning of the sales period
	 *
	 * @param month
	 * @param day
	 * @param year
	 */
	public void setStartSalesPeriod(String month, String day, String year) {
		ActionByName.selectByValue(driver, "StartDateMonth", month, TIMEOUT_IN_SECS);
		ActionByName.selectByValue(driver, "StartDateDay", day, TIMEOUT_IN_SECS);
		ActionByName.selectByValue(driver, "StartDateYear", year, TIMEOUT_IN_SECS);
	}

	/**
	 * Set the end of the sales period
	 *
	 * @param month
	 * @param day
	 * @param year
	 */
	public void setEndSalesPeriod(String month, String day, String year) {
		ActionByName.selectByValue(driver, "EndDateMonth", month, TIMEOUT_IN_SECS);
		ActionByName.selectByValue(driver, "EndDateDay", day, TIMEOUT_IN_SECS);
		// ActionByName.selectByValue(driver, "EndDateYear", year,
		// TIMEOUT_IN_SECS);
		new Select(driver.findElement(By.name("EndDateYear"))).selectByVisibleText(year);
	}

	public void setDollarValueDescription(String descInput) {
		ActionById.clear(driver, "salesValueDeviation", TIMEOUT_IN_SECS);
		ActionById.type(driver, "salesValueDeviation", descInput, TIMEOUT_IN_SECS);
	}

	public void verifyDollarValueDescription(String descInput) {
		ActionById.assertEqualsText(driver, "salesValueDeviation", descInput, TIMEOUT_IN_SECS);
	}

	// #2b
	public void setSinProjectValuesDescription(String descInput) {
		ActionById.clear(driver, "sinProjectedValues", TIMEOUT_IN_SECS);
		ActionById.type(driver, "sinProjectedValues", descInput, TIMEOUT_IN_SECS);
	}

	public void verifySinProjectValuesDescription(String descInput) {
		ActionById.assertEqualsText(driver, "sinProjectedValues", descInput, TIMEOUT_IN_SECS);
	}

	// #3 radio selection
	public void selectPolicyLevel(String level) {
		if (level.equalsIgnoreCase("Offer")) {
			CommonUtilPage.selectRadioOption(driver, "discountPolicyLevel", "OFFER");
		} else
			CommonUtilPage.selectRadioOption(driver, "discountPolicyLevel", "SIN");
		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[@value='Save Level']"));
	}

	public void selectBasisLevel(String level) {
		if (level.equalsIgnoreCase("Discount")) {
			CommonUtilPage.selectRadioOption(driver, "cspBasisLevel", "discount");
		} else
			CommonUtilPage.selectRadioOption(driver, "cspBasisLevel", "MarkUp");
		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[@value='Save']"));
	}

	/**
	 * Selects Discount or Markup/Build-out for every SIN selected
	 *
	 * @param array
	 */
	public void selectBasisLevelForSin(JsonArray array) {
		log.info(" Selecting basis level i.e. Discount or Markup/Build-out for each SIN");
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement element = iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			String sin = sinObj.get("sin").getAsString();
			String level = sinObj.get("level").getAsString();
			log.info(" Selecting basis level " + level + " for SIN: " + sin);
			WebElement row = getRowBySin(sin);
			new Select(row.findElement(By.tagName("select"))).selectByVisibleText(level);
		}
		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[@value='Save']"));
		clickSaveBtn();
	}

	/**
	 * Update existing basis level for every SIN selected Performed in edit CSP
	 * operation for Offer and create mod operation for contract
	 *
	 * @param array
	 */
	public void updateBasisLevelForSin(JsonArray array) {
		log.info(" Updating basis level i.e. Discount or Markup/Build-out for each SIN");
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[@value='Update Basis']"));
		selectBasisLevelForSin(array);
	}

	/*
	 * Retrieve row using SIN from table in Item #3 on CSP page when policy level is
	 * set as SIN
	 */
	public WebElement getRowBySin(String sin) {
		// basisCsp
		WebElement table = ActionById.getElement(driver, "basisCsp", TIMEOUT_IN_SECS);
		WebElement row = TableElementUtils.getTableRowByCellValue(table, "SIN", sin);
		return row;
	}

	// #4b radio
	public void isBetterDiscountAvailable(String value) {
		ActionByName.selectByValue(driver, "deviationFromDiscountChart", value, TIMEOUT_IN_SECS);
	}

	// #4b text
	public void setDeviationExplanation(String descInput) {
		ActionById.type(driver, "deviationFromDiscount", descInput, TIMEOUT_IN_SECS);
	}

	public String getDeviationExplanation() {
		return ActionById.getText(driver, "deviationFromDiscount", TIMEOUT_IN_SECS);
	}

	public void verifyDeviationExplanation(String descInput) {
		ActionById.assertEqualsText(driver, "deviationFromDiscount", descInput, TIMEOUT_IN_SECS);
	}

	public void clickCancelBtn() {
		Octo_SeleniumLibrary.clickElement(driver, cancelBtn);
	}

	public void clickSaveBtn() {
		if (ActionByXpath.isDisplayed(driver, "//input[(@value='Save') and (@name='saveCsp')]", TIMEOUT_IN_SECS))
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[(@value='Save') and (@name='saveCsp')]"));
	}

	/**
	 * /** This method will populate all the values on CSP page except for item 3
	 * buttons (Update Level & Update Basis ) "commercialSalesPractice": {
	 * "valueOfSales": "1000", "startDate": "08/12/2016", "endDate": "08/12/2017",
	 * "salesValueDesc": "testing the sales Value Desc", "sinDesc": "SIN desc",
	 * "discountsOffered": "Yes", "isBetterDiscount": "No"}
	 *
	 * @param jsonObj
	 */
	public void populateFormDetails(JsonObject jsonObj) {
		for (Map.Entry<String, JsonElement> element : jsonObj.entrySet()) {

			String elementName = element.getKey();
			JsonElement elementValue = element.getValue();
			switch (elementName) {
			case "valueOfSales":
				setDollarValue(elementValue.getAsString());
				break;
			case "startDate":
				String startDate = elementValue.getAsString();
				if (startDate != null && !startDate.isEmpty()) {
					String[] start = startDate.split("/");
					setStartSalesPeriod(start[0], start[1], start[2]);
				} else {
					String[] start = DataUtil.getPastYearDate(2).split("/");
					setStartSalesPeriod(start[0], start[1], start[2]);
				}
				break;
			case "endDate":
				String endDate = elementValue.getAsString();
				if (endDate != null && !endDate.isEmpty()) {
					String[] end = elementValue.getAsString().split("/");
					setEndSalesPeriod(end[0], end[1], end[2]);
				} else {
					String[] end = DataUtil.getPastYearDate(1).split("/");
					setEndSalesPeriod(end[0], end[1], end[2]);
				}
				break;
			case "salesValueDesc":
				setDollarValueDescription(elementValue.getAsString());
				break;
			case "sinDesc":
				setSinProjectValuesDescription(elementValue.getAsString());
				break;
			case "isBetterDiscount":
				isBetterDiscountAvailable(elementValue.getAsString());
				break;
			case "discountsOffered":
				if (elementValue.getAsString().equalsIgnoreCase("Yes")) {
					CommonUtilPage.selectRadioOption(driver, "discountsOffered", "Yes");
				} else
					CommonUtilPage.selectRadioOption(driver, "discountsOffered", "No");
				break;
			case "discountLevel":
				if (ActionByName.isClickable(driver, "discountPolicyLevel", TIMEOUT_IN_SECS)) {
					if (elementValue.getAsString().contains("Offer")) {
						CommonUtilPage.selectRadioOption(driver, "discountPolicyLevel", "OFFER");
					} else
						CommonUtilPage.selectRadioOption(driver, "discountPolicyLevel", "SIN");
				}
				break;
			case "deviation":
				if (elementValue.getAsString().equalsIgnoreCase("Yes")) {
					CommonUtilPage.selectRadioOption(driver, "deviationFromDiscountChart", "Yes");
				} else
					CommonUtilPage.selectRadioOption(driver, "deviationFromDiscountChart", "No");
				break;
			default:
				log.warn("Unidentified data object - " + elementName);
				break;
			}
		}
		// This is for eMod
		setSinAmountForMod("10000");
		clickSaveBtn();
	}

	public void selectOfferPolicyAndDiscountBasisLevel(JsonObject jsonObj) {
		log.info(" Selecting Offer policy and Discount basis level");
		String policyLevel = jsonObj.get("discountPolicyLevel").getAsString();
		String basisLevel = jsonObj.get("basisLevel").getAsString();

		if (ActionByXpath.isDisplayed(driver, "//input[@value='Update Level']", TIMEOUT_IN_SECS)) {
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[@value='Update Level']"));
			selectPolicyLevel(policyLevel);
			selectBasisLevel(basisLevel);
		}

		if (ActionByName.isDisplayed(driver, "cspBasisLevel", TIMEOUT_IN_SECS)) {
			selectBasisLevel(basisLevel);
		}

		clickSaveBtn();
	}

	public void selectSinPolicyAndDiscountBasisLevel(JsonObject jsonObj) {
		String policyLevel = jsonObj.get("discountPolicyLevel").getAsString();
		JsonArray array = jsonObj.getAsJsonArray("basisLevel");

		selectPolicyLevel(policyLevel);
		selectBasisLevelForSin(array);
		clickSaveBtn();
	}

	public void addDiscountPolicies(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonElement jsonElement = iterator.next();
			JsonObject jsonObj = jsonElement.getAsJsonObject();
	//		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[@value='Save']", TIMEOUT_IN_SECS);
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[@value='Add Discount Policy']"));
			//Add discount policy one at a time
			addUpdateDiscountPolicy(jsonObj);
			Octo_SeleniumLibrary.clickElement(driver, saveAddDiscountPolicyBtn);
		}
	}

	public void addUpdateDiscountPolicy(JsonObject jsonObj) {

		for (Map.Entry<String, JsonElement> element : jsonObj.entrySet()) {
			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			switch (elementName) {
			case "customer":
				ActionByName.clear(driver, "customer", TIMEOUT_IN_SECS);
				ActionByName.sendKeys(driver, "customer", elementValue, TIMEOUT_IN_SECS);
				break;
			case "discount":
				ActionById.clear(driver, "discount", TIMEOUT_IN_SECS);
				ActionById.sendKeys(driver, "discount", elementValue, TIMEOUT_IN_SECS);
				break;
			case "quantity":
				ActionById.clear(driver, "quantityorvolume", TIMEOUT_IN_SECS);
				ActionById.sendKeys(driver, "quantityorvolume", elementValue, TIMEOUT_IN_SECS);
				break;
			case "concessions":
				ActionByName.clear(driver, "concessions", TIMEOUT_IN_SECS);
				ActionByName.sendKeys(driver, "concessions", elementValue, TIMEOUT_IN_SECS);
				break;
			case "fobTerm":
				new Select(driver.findElement(By.name("fob"))).selectByVisibleText(elementValue);
				break;
			case "sin":
				new Select(driver.findElement(By.name("policyIdentifier"))).selectByValue(elementValue);
				break;
			default:
				log.warn("Unidentified data object - " + elementName);
				break;
			}
		}
	}

	/**
	 * This method is for CSP page which have no data at the beginning ex. Offer
	 *
	 * @param cspObj
	 */
	public void populateForm(JsonObject cspObj) {
		populateFormDetails(cspObj);
		if (cspObj.has("policyBasisLevelForSin")) {
			selectBasisLevelForSin(cspObj.getAsJsonArray("policyBasisLevelForSin"));
		} else if (cspObj.has("policyBasisLevelForOffer")) {
			selectOfferPolicyAndDiscountBasisLevel(cspObj.getAsJsonObject("policyBasisLevelForOffer"));
		}
		if (cspObj.has("addDiscountPolicy"))
			addDiscountPolicies(cspObj.getAsJsonArray("addDiscountPolicy"));
		clickSaveBtn();
		log.info("Completed CSP page data addition");
	}

	/**
	 * This method is for CSP page that has data pre-populated in it ex. for Mods In
	 * this case you need to click on Revise button on main Add SIN page
	 *
	 * @param cspObj
	 */
	public void populateFormForMod(JsonObject cspObj) {
		log.info("Running the Mod CSP page populate method");
		populateFormDetails(cspObj);
		// TODO Add logic to check previous data on UI and compare it with current data
		// Add workflow to handle the above changes if any
		String mesg = CommonUtilPage.getAppMessage(driver);
		log.info("mesg" + mesg);
		// For now skip adding any policy level change
		if (ActionByLocator.isDisplayed(driver, reviseBtn, 1)) {
			Octo_SeleniumLibrary.clickElement(driver, reviseBtn);
			if (cspObj.has("policyBasisLevelForSin")) {
				updateBasisLevelForSin(cspObj.getAsJsonArray("policyBasisLevelForSin"));
			} else if (cspObj.has("policyBasisLevelForOffer")) {
				selectOfferPolicyAndDiscountBasisLevel(cspObj.getAsJsonObject("policyBasisLevelForOffer"));
			}
			Octo_SeleniumLibrary.clickElement(driver, reviseBtn);
			addDiscountPolicies(cspObj.getAsJsonArray("addDiscountPolicy"));
			clickSaveBtn();
			log.info("Completed CSP page data addition for a mod");
		} else if (mesg.contains("Please select the basis of your Commercial Sales Practice for each SIN.")
				|| mesg.contains("Please select the basis of your Commercial Sales Practice for the Offer/Contract.")
				|| mesg.contains("Please add at least one discount/concession policy under Section 4a")) {
			log.info("inside policy **base level**");
			if (cspObj.has("policyBasisLevelForSin")) {
				updateBasisLevelForSin(cspObj.getAsJsonArray("policyBasisLevelForSin"));
			} else if (cspObj.has("policyBasisLevelForOffer")) {
				selectOfferPolicyAndDiscountBasisLevel(cspObj.getAsJsonObject("policyBasisLevelForOffer"));
			}
			addDiscountPolicies(cspObj.getAsJsonArray("addDiscountPolicy"));
			clickSaveBtn();
			log.info("Completed CSP page data addition for a mod");
		} else
			log.warn(
					"Completed CSP page data addition for a mod but bypassed policy basis and discount policy updates");
	}

	public void setSinAmountForMod(String sinSales) {
		if (!CommonUtilPage.isOffer(executionContext) && ActionByXpath.isDisplayed(driver,
				"//input[contains(@id, 'est') and @type='text']", TIMEOUT_IN_SECS)) {
			List<WebElement> inputs = ActionByXpath.getElements(driver,
					"//input[contains(@id, 'est') and @type='text']", TIMEOUT_IN_SECS);
				inputs.forEach(input -> {
//				input.clear();
//				input.sendKeys("1000000");
				Octo_SeleniumLibrary.clear(driver, input);
				Octo_SeleniumLibrary.sendKey(driver, input, "1000000");
//				Octo_SeleniumLibrary.actionClick(driver, By.id("logo"));
			});
			
				Octo_SeleniumLibrary.actionClick(driver, By.xpath("//*[@class='columnTitleFill']/*"));

		}
	}

	public void editDiscountPolicy(JsonArray originalArray, JsonArray updatedArray) {
		Iterator<JsonElement> iterator = originalArray.iterator();
		while (iterator.hasNext()) {
			String customerName = iterator.next().getAsJsonObject().get("customer").getAsString();
			for (int i = 0; i < updatedArray.size(); i++) {
				JsonObject updatedDiscount = updatedArray.get(i).getAsJsonObject();
				if (customerName.equalsIgnoreCase(updatedDiscount.get("customer").getAsString())) {
					WebElement table = ActionByXpath.getElement(driver,
							"//table[@class='table table-striped table-bordered buttonsTable' and @aria-labelledby='csplist']",
							TIMEOUT_IN_SECS);
					WebElement row = TableElementUtils.getTableRowByCellValue(table, "Customer", updatedDiscount.get("customer").getAsString());
					row.findElement(By.xpath(".//td/a[@title='Edit Discount Policy']")).click();
					addUpdateDiscountPolicy(updatedDiscount);
					clickSaveBtn();
				}
			}
		}
	}

	public void editFormDetails(JsonObject jsonObj) {
		populateFormDetails(jsonObj);
		get();
		editDiscountPolicy(
				executionContext.getCurrentScenarioObj().getAsJsonObject("commercialSalesPractice")
						.getAsJsonArray("addDiscountPolicy"),
				executionContext.getCurrentScenarioObj().getAsJsonObject("editCommercialSalesPractice")
						.getAsJsonArray("addDiscountPolicy"));
		clickSaveBtn();
	}
}