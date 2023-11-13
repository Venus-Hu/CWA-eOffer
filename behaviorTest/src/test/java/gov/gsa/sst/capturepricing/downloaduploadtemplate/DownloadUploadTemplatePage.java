package gov.gsa.sst.capturepricing.downloaduploadtemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import gov.gsa.sst.capturepricing.CaptureDataPage;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.DownloadUtil;
import gov.gsa.sst.util.ExcelUtil;
import gov.gsa.sst.util.WebDriverUtil;
import gov.gsa.sst.wizardmanagement.WizardManagementPage;
import util.ActionByLocator;
import util.ActionByXpath;

public class DownloadUploadTemplatePage extends Page {

	private final int TIMEOUT_IN_SECS = 10;
	private By headerText = By.xpath("//h1[contains(text(), 'DOWNLOAD/UPLOAD')]");
	private ExecutionContext executionContext;
	private static Logger log = LoggerFactory.getLogger(DownloadUploadTemplatePage.class);
	private List<String> wizMgmtSelectedOptions = new ArrayList<String>();
	private By refreshBtn = By.name("CheckStatus");
	private static final long TIMEOUT_FOR_UPLOAD_DOCUMENT = 4 * 60;
	private static final long FILEUPLOAD_POLLING_PERIOD_SECONDS = 30;

	// Maintain error map
	private Map<String, List<String>> errorMap;

	public DownloadUploadTemplatePage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		if (errorMap == null)
			errorMap = new HashMap<String, List<String>>();
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		log.info("Loading Upload/Download Template page");
		try {
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
			if (!flag)
				throw new Exception("The solicitation number specified in data does not match the one on UI");
			// retrieve Wiz options
			getWizardOptions();

			CaptureDataPage cpPage = new CaptureDataPage(executionContext);
			cpPage.get();
			cpPage.selectTemplateOption();
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			getWizardOptions();
			CaptureDataPage cpPage = new CaptureDataPage(executionContext);
			cpPage.get();
			cpPage.selectTemplateOption();
		}
		try {
			executionContext.testPageFor508("Capture Pricing Upload");
		} catch (Exception e) {
			log.warn("Section 508 exception: " + e.getMessage());
		}
		Assert.assertTrue("Capture Pricing page is not loaded", isLoaded());
	}

	/**
	 * Call method to get Wiz options from UI for all Offers and mods except Delete
	 * Product
	 */
	public void getWizardOptions() {
		// Do not retrieve Wiz options from UI for Delete Product mod as Wiz
		// page is absent.
		// This code works when only 1 mod type is selected
		// Also this code should work for all offers
		if (CommonUtilPage.isOffer(executionContext))
			getWizMgmtSelectedOptions();
		else if (!CommonUtilPage.retrieveModTypes(executionContext).contains("Delete Product(s)"))
			getWizMgmtSelectedOptions();
	}

	@Override
	protected boolean isLoaded() {
		if (ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS)) {
			if (CommonUtilPage.isOffer(executionContext))
				return CommonUtilPage.isSolicitationApt(executionContext);
			else
				return true;
		} else
			return false;
	}

	/**
	 * Get the list of wiz mgmt options that have been selected as Yes
	 */
	public void getWizMgmtSelectedOptions() {
		WizardManagementPage wizardManagent = new WizardManagementPage(executionContext);
		wizardManagent.get();
		wizMgmtSelectedOptions = wizardManagent.getSelectedWizMgmtOptions(); // needed offers
	}

	/**
	 * Downloads the pricing document template, updates it and uploads the document
	 * 
	 * @throws Exception
	 */
	public void populateForm() throws Exception {
		DownloadUploadTemplateUtil.downloadTemplateDocument(executionContext);
		getDataToUpdateWorksheet();
		DownloadUploadTemplateUtil.uploadDocAndWait(executionContext);
		log.info("Validating the pricing document contents ....");
		CaptureDataPage cpPage = new CaptureDataPage(executionContext);
		cpPage.validateData();
		log.info("Successfully validated Pricing document.");
	}

	/**
	 * Updates the pricing data dynamically one worksheet at a time
	 * 
	 * @throws IOException
	 */
	public void getDataToUpdateWorksheet() throws IOException {
		// for error validations, we do not want to update the pricing document
		JsonObject pageDataObj = executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate");
		if (pageDataObj.has("updateDocument")) {
			String updateDocument = pageDataObj.get("updateDocument").getAsString();

			if (!updateDocument.equalsIgnoreCase("No")) {
				JsonObject worksheetDataObj = pageDataObj.getAsJsonObject("worksheetData");
				DownloadUploadTemplateUtil.updatePricingDocument(executionContext, worksheetDataObj, pageDataObj);
			}
		} else {
			JsonObject worksheetDataObj = pageDataObj.getAsJsonObject("worksheetData");
			DownloadUploadTemplateUtil.updatePricingDocument(executionContext, worksheetDataObj, pageDataObj);
		}
	}

	/**
	 * This method generates the PricingData.xls file using data from the
	 * InvalidPricingData.xls
	 * 
	 * @param cptObj
	 * @throws IOException
	 */
	public void generateDocumentFromTemplate(ExecutionContext executionContext, JsonObject cptObj) throws Exception {
		String scenarioName = executionContext.getCurrentScenarioObj().get("scenarioName").getAsString();
		log.info("Creating the pricing document");

		String docName = cptObj.get("pricingDocumentTemplateName").getAsString();
		if (!docName.contains("xls")) {
			docName = cptObj.get("pricingDocumentFileName").getAsString();
		}
		String filePath = DownloadUtil.setUpDownloadLocation().getPath();

		ExcelUtil util = new ExcelUtil();
		String datafile = cptObj.get("dataFile").getAsString();
		File file = new File("src/test/resources/data/ExcelData/" + datafile);

		Workbook workbook = null;
		FileInputStream fsIn = new FileInputStream(filePath + File.separator + docName);
		if (docName.contains("xlsx"))
			workbook = new XSSFWorkbook(fsIn);
		else
			workbook = new HSSFWorkbook(fsIn);

		// HSSFWorkbook workbook = new Workbook(fsIn);
		try {
			// Retrieve sheet names that are needed for this scenario and loop
			// through them
			JsonArray sheetsArray = cptObj.getAsJsonArray("sheets");
			Iterator<JsonElement> iterator = sheetsArray.iterator();
			while (iterator.hasNext()) {
				JsonElement element = (JsonElement) iterator.next();
				String sheetName = element.getAsString();
				List<JsonObject> list = util.retrieveDataFromTemplate(file, sheetName, scenarioName);

				log.info("Populate " + sheetName + " with data from template file");
				populateWorksheet(list, workbook, sheetName);
			}
		} finally {
			fsIn.close();
			FileOutputStream fsOut = new FileOutputStream(new File(filePath + File.separator + docName));
			workbook.write(fsOut);
			fsOut.close();
			workbook.close();
		}
	}

	/**
	 * Use list of rows from template file to create worksheet
	 * 
	 * @param templateRowList
	 * @param sheetName
	 * @throws IOException
	 */
	private void populateWorksheet(List<JsonObject> templateRowList, Workbook workbook, String sheetName)
			throws IOException {
		// This worksheet is from the file to be updated ex.PricingData.xls
		Sheet worksheet = null;
		worksheet = workbook.getSheet(sheetName);

		int startingRowNum = worksheet.getLastRowNum() + 1;
		Row curRow = null;
		// Retrieve the header row from the document being updated and cell
		// count for header row
		Row headerRow = worksheet.getRow(0);
		int headerCellCount = headerRow.getLastCellNum();

		List<String> errorList = new ArrayList<String>();
		for (JsonObject rowObj : templateRowList) {
			curRow = worksheet.createRow((short) startingRowNum);

			Set<Entry<String, JsonElement>> qcSet = rowObj.entrySet();
			Iterator<Entry<String, JsonElement>> iter = qcSet.iterator();
			int index = 0;
			while (iter.hasNext() && index < headerCellCount) {
				Cell curCell = curRow.getCell(index, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				Entry<String, JsonElement> cellElement = iter.next();
				String elementName = cellElement.getKey();
				JsonElement elementValue = cellElement.getValue();
				switch (elementName) {
				case "Scenario Name":
					// Do nothing
					index = -1;
					break;
				case "Error Message":
					errorList.add(elementValue.getAsString());
					index = -1;
					break;
				default:
					// Check if template header and doc headers match before
					// adding cell value
					String headerText = headerRow.getCell(index).getStringCellValue().trim();
					// log.info("Header:" + headerText + " = " +
					// elementValue.getAsString()) ;
					if (headerText.equalsIgnoreCase(elementName.trim())) {
						curCell.setCellValue(elementValue.getAsString());
					}
					break;
				}
				index++;
			}
		}
		if (errorList != null)
			errorMap.put(sheetName, errorList);
	}

	/**
	 * Use this method for negative scenarios where you want the file upload to fail
	 * 
	 * @return returns true if uploaded file showed Data error failure
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */
	public boolean uploadAndValidateError() throws MalformedURLException, InterruptedException {
		String docName = executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate")
				.get("pricingDocumentTemplateName").getAsString();
		if (docName.contains("zip")) {
			docName = executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate")
					.get("pricingDocumentFileName").getAsString();
		}
		DownloadUploadTemplateUtil.uploadPricingDocument(driver, docName);
		boolean flag = waitForCapturePricingDocumentToFail();
		log.info("Upload Pricing document failed? " + flag);
		return flag;
	}

	public boolean waitForCapturePricingDocumentToFail() throws InterruptedException {
		return WebDriverUtil.wait(driver, refreshBtn, By.xpath("//span[@class='btn btn-danger special']"),
				TIMEOUT_FOR_UPLOAD_DOCUMENT, FILEUPLOAD_POLLING_PERIOD_SECONDS, TimeUnit.SECONDS);
	}

	// Try using uploadAndValidateError() instead of this as it reads the error
	// messages too
	public void uploadInvalidDocument() throws Exception {
		String docName = executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate")
				.get("invalidPricingFileName").getAsString();
		docName = this.getClass().getResource("/data/") + docName;
		DownloadUploadTemplateUtil.uploadPricingDocument(driver, docName);
		if (waitForCapturePricingDocumentToFail()) {
			Assert.assertTrue("File upload did not fail",
					ActionByXpath.isDisplayed(driver, "//span[@class='btn btn-danger special']", TIMEOUT_IN_SECS));
		}
	}

	/**
	 * Read the data error file and display it on log
	 * 
	 * @throws Exception
	 */
	public void displayErrorFileContents(ExecutionContext executionContext) throws Exception {
		DownloadUtil.deleteFiles("DataErrors");
		WebDriver driver = executionContext.getDriver();
		log.info("Is error file download link found " + driver
				.findElement(By.xpath("//span[@class='btn btn-danger special']/following-sibling::a")).getText());
		Octo_SeleniumLibrary.clickElement(driver,
				By.xpath("//span[@class='btn btn-danger special']/following-sibling::a"));
		DownloadUtil.waitForDownload("DataErrors.xlsx", TIMEOUT_IN_SECS);

		if (DownloadUtil.isFileDownloaded("DataErrors.xlsx")) {
			File file = new File(DownloadUtil.setUpDownloadLocation().getPath() + File.separator + "DataErrors.xlsx");
			JsonArray sheetsArray = executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate")
					.getAsJsonArray("sheets");
			Iterator<JsonElement> iterator = sheetsArray.iterator();
			while (iterator.hasNext()) {
				JsonElement element = (JsonElement) iterator.next();
				String sheetName = element.getAsString();
				ExcelUtil util = new ExcelUtil();
				List<String> actualMsgList = util.retrieveErrorMsgList(file, sheetName);

				log.info("Listing errors for sheet: " + sheetName);
				for (String actualError : actualMsgList) {
					log.info("Actual Error: " + actualError);
				}
			}
		}
	}

	/**
	 * Read the DAtaerrors file and match errors in it with expected error list
	 * 
	 * @throws Exception
	 */
	public void readErrorFile(ExecutionContext executionContext) throws Exception {
		DownloadUtil.deleteFiles("DataErrors");
		WebDriver driver = executionContext.getDriver();
		driver.findElement(By.xpath("//span[@class='btn btn-danger special']/following-sibling::a")).click();
		DownloadUtil.waitForDownload("DataErrors.xlsx", TIMEOUT_IN_SECS);

		if (DownloadUtil.isFileDownloaded("DataErrors.xlsx")) {
			File file = new File(DownloadUtil.setUpDownloadLocation().getPath() + File.separator + "DataErrors.xlsx");
			JsonArray sheetsArray = executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate")
					.getAsJsonArray("sheets");
			Iterator<JsonElement> iterator = sheetsArray.iterator();
			while (iterator.hasNext()) {
				JsonElement element = (JsonElement) iterator.next();
				String sheetName = element.getAsString();
				ExcelUtil util = new ExcelUtil();
				List<String> actualMsgList = util.retrieveErrorMsgList(file, sheetName);
				List<String> expectedMsgList = errorMap.get(sheetName);

				// TODO parse both list and ensure that expected and actual
				// errors match
				log.info("Verifying errors for sheet: " + sheetName);
				for (String actualError : actualMsgList) {
					boolean matchFound = false;
					for (String expectedError : expectedMsgList) {
						log.info("Actual Error: " + actualError + "\n Expected Error: " + expectedError);
						if (actualError.equals(expectedError)) {
							matchFound = true;
							log.info("Match found? " + matchFound);
							break;
						}
					}
					Assert.assertTrue("Unexpected error found in DataErrors.xslx - " + actualError, matchFound);
				}
			}
		}
	}

}