package gov.gsa.sst.capturepricing.dataentry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import customUtility.Octo_SeleniumLibrary;
import util.ActionById;
import util.ActionByLocator;

public class FormEntryUtil {

	private final static int TIMEOUT_IN_SECS = 10;
	private static final By GET_STARTED_TAB = By.xpath("//li[@data-tab='tab-1']");
	private static final By PRICING_TAB = By.xpath("//li[@data-tab='tab-2']");
	private static final By ZONAL_PRICING_TAB = By.xpath("//li[@data-tab='tab-4']");
	private static final By DELIVERY_TAB = By.xpath("//li[@data-tab='tab-8']");
	private static final By DISCOUNTS_TAB = By.xpath("//li[@data-tab='tab-5']");
	private static final By ADDITIONAL_PHOTOS_TAB = By.xpath("//li[@data-tab='tab-3']");
	private static final By SPECIAL_CHARGES_TAB = By.xpath("//li[@data-tab='tab-7']");
	private static final By ACCESSORIES_TAB = By.xpath("//li[@data-tab='tab-6']");
	private static final By OPTIONS_TAB = By.xpath("//li[@data-tab='tab-9']");
	private static final By SPECIAL_FEATURES_TAB = By.xpath("//li[@data-tab='tab-10']");
	private static final By DIMENSIONS_AND_SHIPPING_TAB = By.xpath("//li[@data-tab='tab-11']");
	private static final By POPUP_OK_BUTTON = By.xpath("//button[contains(.,'Ok')]");
	private static final By POP_UP_DIV = By.xpath("//div[@id='dialog']/p[3]");

	private static Logger log = LoggerFactory.getLogger(FormEntryUtil.class);

	/**
	 * Parse the array to enter data one row at a time
	 * 
	 * @param driver
	 * @param tabName
	 * @param tabDataArray
	 * @param redPar
	 */
	public static void enterFormData(WebDriver driver, String tabName, JsonArray tabDataArray, boolean redPar) {
		WebElement tabLink = getTabElement(driver, tabName);
		if (tabLink != null || redPar ) {

			String handsOnTableName = getHandsOnTableName(driver, tabName);

			for (int i = 0; i < tabDataArray.size(); i++) {
				JsonObject rowObj = tabDataArray.get(i).getAsJsonObject();
				Set<Entry<String, JsonElement>> rowSet = rowObj.entrySet();
				Iterator<Entry<String, JsonElement>> iter = rowSet.iterator();
				int columnIdx = 0;
				while (iter.hasNext()) {
					Entry<String, JsonElement> element = iter.next();
					String elementName = element.getKey();
					JsonElement elementValue = element.getValue();
					switch (elementName) {
					case "*SIN":
						// Do not enter data for SIN
						break;
					case "Delete":
						// No action unless set to true
						columnIdx++;
						break;
					case "errors":
						break;
					default:
						if (!tabName.equalsIgnoreCase("Accessories")) {
							if (!elementValue.getAsString().equalsIgnoreCase("N/A"))
								setCellValue(driver, handsOnTableName, i, columnIdx, elementValue.getAsString());
							columnIdx++;
						}
						break;
					}
				}
				if (tabName.equalsIgnoreCase("Accessories")) {
					Octo_SeleniumLibrary.clickElement(driver, By
							.xpath("//table[@id='productDataTable']/tbody/tr/td[3]/input[@type='checkbox'][" + (i+1) + "]")
							);
				}
			}
		}

	}

	/**
	 * Enter cell value using the row and column index
	 * 
	 * @param driver
	 * @param handsOnTableName
	 * @param rowIndex
	 * @param fieldValue
	 * @param colIndex
	 */
	private static void setCellValue(WebDriver driver, String handsOnTableName, int rowIndex, int colIndex,
			String fieldValue) {
		log.info("Add data '" + fieldValue + "' for row" + rowIndex + " and column" + colIndex);

		((JavascriptExecutor) driver)
				.executeScript("return " + handsOnTableName + ".selectCell(" + rowIndex + ", " + colIndex + ")");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((JavascriptExecutor) driver).executeScript("return " + handsOnTableName + ".setDataAtCell(" + rowIndex + ", "
				+ colIndex + ", '" + fieldValue + "')");

		if ((((JavascriptExecutor) driver).executeScript(
				"return " + handsOnTableName + ".getDataAtCell(" + rowIndex + ", " + colIndex + ")")) == null) {
			((JavascriptExecutor) driver).executeScript("return " + handsOnTableName + ".setDataAtCell(" + rowIndex
					+ ", " + colIndex + ", '" + fieldValue + "')");
		}

		// Wait for 0.1 second to ensure that the data is saved
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param driver
	 * @param tabName
	 * @param tabDataArray
	 */
	public static void verifyErrorsOnTab(WebDriver driver, String tabName, JsonArray tabDataArray) {
		String handsOnTableName = getHandsOnTableName(driver, tabName);

		for (int i = 0; i < tabDataArray.size(); i++) {
			JsonObject rowObj = tabDataArray.get(i).getAsJsonObject();
			if (rowObj.has("errors")) {
				int colCount = rowObj.size();
				/* Need to get column count */
				if (tabName.equalsIgnoreCase("Get Started"))
					colCount = colCount - 2;
				else
					colCount = colCount - 1;
				ArrayList<String> errorList =  convertArrayToList( rowObj.getAsJsonArray("errors"));
				verifyCellErrors(driver, handsOnTableName, i, colCount, errorList);
			}
		}
		if (tabName.contains("Special Charges") || tabName.equalsIgnoreCase("Options"))
			closePopUpWindow(driver, tabName);
	}

	@SuppressWarnings("rawtypes")
	private static void verifyCellErrors(WebDriver driver, String handsOnTableName, int rowIndex, int columnCount,
			ArrayList<String> errorList) {
		int numOfErrors = errorList.size();
		int errorsFound = 0;
		for (int i = 0; i < columnCount; i++) {
			ArrayList commentFromForm = (ArrayList) ((JavascriptExecutor) driver)
					.executeScript("return " + handsOnTableName + ".getCellMeta(" + rowIndex + ", " + i + ").comment");
			if (commentFromForm != null) {
				log.info("Actual error: " + commentFromForm.get(0));
				errorsFound++;

				boolean flag = errorList.contains(((String) commentFromForm.get(0)).trim());
				log.info("Expected & actual error match?  " + flag);
				Assert.assertTrue("Could not match error: '" + commentFromForm.get(0)+"' with the expected error list", flag);
			}
			if (errorsFound >= numOfErrors) {
				log.info("Found all errors");
				break;
			}
		}
		if (errorsFound < numOfErrors) {
			// Should this be an assert
			log.error("Could not find all errors as expected");
		}

	}

	/**
	 * Convert JsonArray to an array of Strings
	 * 
	 * @param jsonArray
	 * @return
	 */
	public static ArrayList<String> convertArrayToList(JsonArray jsonArray) {
		ArrayList<String> list = new ArrayList<String>();
		jsonArray.forEach(element -> {
			log.info("Expected error: " + element.getAsString());
			list.add(element.getAsString());
		});
		return list;
	}

	// This is used to validate Offer Analysis Report Form Entry Headings
	public static void validateColumnHeadings(ExecutionContext executionContext, String formEntryHeadings,
			String handsonTableName) {
		WebDriver driver = executionContext.getDriver();
		JsonArray oarFormEntryContents = executionContext.getDataArray(formEntryHeadings).get(0).getAsJsonArray();
		for (int i = 0; i < oarFormEntryContents.size(); i++) {
			JsonObject data = oarFormEntryContents.get(i).getAsJsonObject();
			if (data.get("Tab Name") != null) {
				String tabName = data.get("Tab Name").getAsString();
				if (tabName != null) {
					log.info("Validating Tab " + tabName);
					WebElement tabLink = driver.findElement(By.xpath("//ul[@class='tabs']/li[" + (i + 1) + "]"));

					Assert.assertTrue("", tabLink.getText().contains(tabName));
					tabLink.click();
					// Wait for 5 seconds to ensure that the handson table is
					// loaded
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					JsonArray columnHeadings = data.get("Column Headings").getAsJsonArray();
					for (int j = 0; j < columnHeadings.size(); j++) {
						String columnHeading = columnHeadings.get(j).getAsString().trim();
						String columnHeadingFromPage = (String) ((JavascriptExecutor) driver)
								.executeScript("return " + handsonTableName + ".getColHeader(" + j + ")");
						log.info(" Expecting " + columnHeading + " ");
						Assert.assertEquals(columnHeading, columnHeadingFromPage);
					}
				}
			}

		}

	}

	// This is used to validate Offer Analysis Report Form Entry Data
	public static void validateFormEntryDataOnPar(ExecutionContext executionContext, String jsonFormData,
			String handsOnTableName) {
		WebDriver driver = executionContext.getDriver();
		JsonArray oarFormEntryContents = executionContext.getDataArray(jsonFormData).get(0).getAsJsonArray();
		for (int i = 0; i < oarFormEntryContents.size(); i++) {
			JsonObject data = oarFormEntryContents.get(i).getAsJsonObject();
			if (data.get("Tab Name") != null) {
				String tabName = data.get("Tab Name").getAsString();

				if (tabName != null) {
					log.info("Validating Tab " + tabName);
					WebElement tabLink = driver.findElement(By.xpath("//ul[@class='tabs']/li[" + (i + 1) + "]"));
					Assert.assertTrue(tabLink.getText().contains(tabName));
					tabLink.click();
					// Wait for 5 seconds to ensure that the handson table is
					// loaded
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					JsonArray productDataRows = data.getAsJsonArray("Product Data");
					for (int j = 0; j < productDataRows.size(); j++) {
						JsonArray productData = productDataRows.get(j).getAsJsonArray();
						for (int k = 0; k < productData.size(); k++) {
							String colDataFromJson = productData.get(k).getAsString().trim();
							String colDataFromPage = (String) ((JavascriptExecutor) driver).executeScript(
									"return " + handsOnTableName + ".getDataAtCell(" + j + "," + (k + 1) + ")");
							log.info(" Expecting " + colDataFromJson + " ");
							Assert.assertEquals(colDataFromJson, colDataFromPage);
						}
					}
				}

			}

		}
	}

	public static WebElement getTabElement(WebDriver driver, String tabName) {
		WebElement tabElement = null;
		switch (tabName) {
		case "Get Started":
			tabElement = ActionByLocator.getElement(driver, GET_STARTED_TAB, TIMEOUT_IN_SECS);
			break;
		case "Pricing":
			tabElement = ActionByLocator.getElement(driver, PRICING_TAB, TIMEOUT_IN_SECS);
			break;
		case "Delivery":
			tabElement = ActionByLocator.getElement(driver, DELIVERY_TAB, TIMEOUT_IN_SECS);
			break;
		case "Dimensions & Shipping":
			tabElement = ActionByLocator.getElement(driver, DIMENSIONS_AND_SHIPPING_TAB, TIMEOUT_IN_SECS);
			break;
		case "Zonal Offer Price":
			tabElement = ActionByLocator.getElement(driver, ZONAL_PRICING_TAB, TIMEOUT_IN_SECS);
			break;
		case "Additional Photos":
			tabElement = ActionByLocator.getElement(driver, ADDITIONAL_PHOTOS_TAB, TIMEOUT_IN_SECS);
			break;
		case "Accessories":
			tabElement = ActionByLocator.getElement(driver, ACCESSORIES_TAB, TIMEOUT_IN_SECS);
			break;
		case "Special Charges":
			tabElement = ActionByLocator.getElement(driver, SPECIAL_CHARGES_TAB, TIMEOUT_IN_SECS);
			break;
		case "Special Features":
			tabElement = ActionByLocator.getElement(driver, SPECIAL_FEATURES_TAB, TIMEOUT_IN_SECS);
			break;
		case "Options":
			tabElement = ActionByLocator.getElement(driver, OPTIONS_TAB, TIMEOUT_IN_SECS);
			break;
		case "Discounts":
			tabElement = ActionByLocator.getElement(driver, DISCOUNTS_TAB, TIMEOUT_IN_SECS);
			break;
		}

		return tabElement;
	}

	/**
	 * Get the table name using the TAb value
	 * 
	 * @param driver
	 * @param tabName
	 * @return
	 */
	public static String getHandsOnTableName(WebDriver driver, String tabName) {
		String handsOnTableName = null;

		switch (tabName) {
		case "Get Started":
			handsOnTableName = "getStartedHot";
			break;
		case "Pricing":
			handsOnTableName = "generalHot";
			break;
		case "Delivery":
			handsOnTableName = "deliveryHot";
			break;
		case "Dimensions & Shipping":
			handsOnTableName = "dimeShipHot";
			break;
		case "Zonal Offer Price":
			handsOnTableName = "zonesHot";
			break;
		case "Additional Photos":
			handsOnTableName = "photosTable";
			break;
		case "Accessories":
			handsOnTableName = "accessoriesGridTab";
			break;
		case "Special Charges":
			handsOnTableName = "specialChargesTable";
			break;
		case "Special Features":
			handsOnTableName = "spfHot";
			break;
		case "Options":
			handsOnTableName = "optionsTable";
			break;
		case "Discounts":
			handsOnTableName = "discountHot";
			break;
		default:
			handsOnTableName = "xsbStandardizationFlagHot";
			break;
		}
		if ("Special Charges".equals(tabName) || "Options".equals(tabName) || "Accessories".equals(tabName)) {
			goToPopUpWindow(driver, tabName);
			handsOnTableName = getPopupHandsOnTableName(tabName);
		}

		return handsOnTableName;
	}

	public static void validatePopupErrorMessage(ExecutionContext executionContext, String popUpErrorMessage) {
		WebDriver driver = executionContext.getDriver();
		JsonArray tabDataArray = executionContext.getDataArray(popUpErrorMessage).get(0).getAsJsonArray();
		for (int i = 0; i < tabDataArray.size(); i++) {
			String popUpMessage = tabDataArray.get(i).getAsString();
			if (popUpMessage != null) {
				String messageFromScreen = driver.findElement(POP_UP_DIV).getText();
				log.info("Popup Message From Screen " + messageFromScreen + " To be validated against " + popUpMessage);
				Assert.assertTrue(messageFromScreen.contains(popUpMessage));
			}
			// Click on Ok button to close the pop up
			driver.findElement(POPUP_OK_BUTTON).click();
		}
	}

	public static void validatePricingDataErrorMessages(ExecutionContext executionContext, String tabName,
			String formEntryDataJson) {
		WebDriver driver = executionContext.getDriver();
		WebElement tabLink = getTabElement(driver, tabName);
		if (tabLink != null) {
			navigateToTab(driver, tabName);
			String handsOnTableName = getHandsOnTableName(executionContext.getDriver(), tabName);
			if ("Special Charges".equals(tabName) || "Options".equals(tabName)) {
				goToPopUpWindow(driver, tabName);
				handsOnTableName = getPopupHandsOnTableName(tabName);
			}

			int rowIndex = 0;
			JsonArray tabDataArray = executionContext.getDataArray(formEntryDataJson).get(0).getAsJsonArray();
			for (int i = 0; i < tabDataArray.size(); i++) {
				JsonObject dataToVerify = tabDataArray.get(i).getAsJsonObject();
				log.info("Validation is " + dataToVerify.get("Comment").getAsString());
				String columnIndex = dataToVerify.get("Column Index").getAsString();
				String fieldValue = dataToVerify.get("Field Value").getAsString();
				String validationMessage = dataToVerify.get("Field Validation").getAsString();
				int colIndex = Integer.valueOf(columnIndex).intValue() - 1;
				int dependentColIndex = 0;
				setCellValue(driver, handsOnTableName, rowIndex, colIndex, fieldValue);
				if (dataToVerify.get("Dependent Column Index") != null) {
					String dependentColumnIndex = dataToVerify.get("Dependent Column Index").getAsString();
					String dependentFieldValue = dataToVerify.get("Dependent Field Value").getAsString();
					dependentColIndex = Integer.valueOf(dependentColumnIndex).intValue() - 1;
					setCellValue(driver, handsOnTableName, rowIndex, dependentColIndex, dependentFieldValue);
				}

				// If Validation Message is provided in json assert for it
				if (validationMessage != null && validationMessage.trim().length() > 0) {
					assertFormEntryCellComment(driver, handsOnTableName, rowIndex, colIndex, validationMessage);
				}

				// Check for Dependent validation
				if (dataToVerify.get("Dependent Field Validation") != null) {
					String dependentValidationMessage = dataToVerify.get("Dependent Field Validation").getAsString();
					assertFormEntryCellComment(driver, handsOnTableName, rowIndex, dependentColIndex,
							dependentValidationMessage);
				}
			}
			// Close Popup Window
			if ("Special Charges".equals(tabName) || "Options".equals(tabName)) {
				closePopUpWindow(driver, tabName);
			}

		}

	}

	@SuppressWarnings("rawtypes")
	private static void assertFormEntryCellComment(WebDriver driver, String handsOnTableName, int rowIndex,
			int colIndex, String validationMessage) {
		ArrayList commentFromForm = (ArrayList) ((JavascriptExecutor) driver).executeScript(
				"return " + handsOnTableName + ".getCellMeta(" + rowIndex + ", " + colIndex + ").comment");
		if (commentFromForm != null) {
			log.info("Comment From Form : " + commentFromForm.get(0) + " Expecting : " + validationMessage);
			Assert.assertTrue(((String) commentFromForm.get(0)).contains(validationMessage));
		}
	}

	/**
	 * Verify the data on Form entry page table
	 * @param driver
	 * @param tabName
	 * @param tabDataArray
	 */
	public static void verifyFormEntryData(WebDriver driver, String tabName,
			JsonArray tabDataArray) {
		
		WebElement tabLink = getTabElement(driver, tabName);
		if (tabLink != null) {
			navigateToTab(driver, tabName);
			String handsOnTableName = getHandsOnTableName(driver, tabName);
			if ("Special Charges".equals(tabName) || "Options".equals(tabName) || "Accessories".equals(tabName)) {
				goToPopUpWindow(driver, tabName);
				handsOnTableName = getPopupHandsOnTableName(tabName);
			}

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (!"Accessories".equals(tabName)) {
				for (int i = 0; i < tabDataArray.size(); i++) {
					JsonArray colData = tabDataArray.get(i).getAsJsonArray();
					for (int j = 0; j < colData.size(); j++) {
						String expectedData = colData.get(j).getAsString();
						if (!"N/A".equals(expectedData)) {
							((JavascriptExecutor) driver)
									.executeScript("return " + handsOnTableName + ".selectCell(" + i + ", " + j + ")");
							Object cellData = (((JavascriptExecutor) driver).executeScript(
									"return " + handsOnTableName + ".getDataAtCell(" + i + ", " + j + ")"));
							log.info("Data From Form : " + cellData + " Expecting : " + expectedData);
							if (cellData != null) {
								Assert.assertTrue(cellData.toString().contains(expectedData));
							} else {
								Assert.fail(" Expecting " + expectedData + " but no entry found in the form.");
							}
						}
					}
				}
			} else {
				for (int i = 0; i < tabDataArray.size(); i++) {
					JsonArray colData = tabDataArray.get(i).getAsJsonArray();
					WebElement row = driver
							.findElement(By.xpath("//table[@id='productDataTable']/tbody/tr[" + (i + 1) + "]"));
					List<WebElement> cells = row.findElements(By.tagName("td"));
					for (int j = 0; j < colData.size(); j++) {
						String data = colData.get(j).getAsString();
						if ("Checked".equals(data)) {
							Assert.assertTrue(cells.get(j).findElement(By.tagName("input")).isSelected());
						} else {
							Assert.assertTrue(cells.get(j).getText().trim().contains(data));
						}
					}
				}

			}

			// Close Popup Window
			if ("Special Charges".equals(tabName) || "Options".equals(tabName) || "Accessories".equals(tabName)) {
				closePopUpWindow(driver, tabName);
			}
		}
	}

	public static void validateFormEntryColumnHeadings(ExecutionContext executionContext,
			String formEntryHeadingsJson) {
		WebDriver driver = executionContext.getDriver();
		JsonArray pricingDataFormEntryHeaders = executionContext.getDataArray(formEntryHeadingsJson).get(0)
				.getAsJsonArray();
		for (int i = 0; i < pricingDataFormEntryHeaders.size(); i++) {
			JsonObject data = pricingDataFormEntryHeaders.get(i).getAsJsonObject();
			if (data.get("Tab Name") != null) {
				String tabName = data.get("Tab Name").getAsString();
				WebElement tabLink = getTabElement(driver, tabName);
				if (tabLink != null) {
					log.info("Validating Tab " + tabName);
					navigateToTab(driver, tabName);
					String handsOnTableName = getHandsOnTableName(executionContext.getDriver(), tabName);
					String headerJsonName = data.get("Header").getAsString();

					JsonArray columnHeadings = executionContext.getDataArray(headerJsonName).get(0).getAsJsonArray();
					for (int j = 0; j < columnHeadings.size(); j++) {
						String columnHeading = columnHeadings.get(j).getAsString().trim();
						String columnHeadingFromPage = (String) ((JavascriptExecutor) driver)
								.executeScript("return " + handsOnTableName + ".getColHeader(" + j + ")");
						log.info(" Expecting " + columnHeading + " **Actual Observed : " + columnHeadingFromPage);
						Assert.assertTrue(columnHeadingFromPage.contains(columnHeading));
					}
				}
			}

		}
	}

	public static void navigateToTab(WebDriver driver, String tabName) {
		WebElement tab = getTabElement(driver, tabName);
		//Check if tab is already selected and has all rows rendered
		if( !ActionByLocator.isDisplayed(driver, By.xpath("//div[@id='"+tab.getAttribute("data-tab")+"' and @class='tab-content current']"), 2)){
			Octo_SeleniumLibrary.clickElement(driver, By.xpath("//ul/li[contains(.,'" + tabName + "')]"));
		}
		String tabText = ActionByLocator.getText(driver, By.xpath("//ul/li[@class='tab-link current']"), TIMEOUT_IN_SECS);
		log.info("Ensure that selected tab '" + tabName + "' is same as active tab: " + tabText);
		//Check if all 20 rows are loaded
		boolean isLoaded = ActionByLocator.isDisplayed(driver, By.xpath("//div[@id='"+tab.getAttribute("data-tab")+"' and @class='tab-content current']//table/tbody/tr/th/div/span[text()='20']"), TIMEOUT_IN_SECS);
		log.info("Check if all 20 rows are loaded ? " + isLoaded);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void goToPopUpWindow(WebDriver driver, String tabName) {
		String popUpModalName = getPopupModalName(tabName);
		if (!ActionById.isDisplayed(driver, popUpModalName, TIMEOUT_IN_SECS)) {
			By popupWindowButton = By.xpath("//button[@data-target='#" + popUpModalName + "']");
			Octo_SeleniumLibrary.clickElement(driver, popupWindowButton);
			// Wait for 5 second to ensure that the pop up is loaded
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closePopUpWindow(WebDriver driver, String tabName) {
		By closeButtonForPopUp = By
				.xpath("//div[@id='" + getPopupModalName(tabName) + "']//button[contains(.,'Close')]");
		Octo_SeleniumLibrary.clickElement(driver, closeButtonForPopUp);
		// Wait for 3 second to ensure that the pop up is loaded
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getPopupModalName(String tabName) {
		String popUpModalName = null;
		switch (tabName) {
		case "Special Charges":
			popUpModalName = "specialChargesModal";
			break;
		case "Options":
			popUpModalName = "addviewOptions";
			break;
		case "Accessories":
			popUpModalName = "myModal";
			break;
		}

		return popUpModalName;
	}

	public static String getPopupHandsOnTableName(String tabName) {
		String handsOnTableName = null;
		switch (tabName) {
		case "Special Charges":
			handsOnTableName = "specialChargesPopUpTable";
			break;
		case "Options":
			handsOnTableName = "optionsSubTable";
			break;
		}

		return handsOnTableName;
	}
}
