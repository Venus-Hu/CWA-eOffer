package gov.gsa.sst.capturepricing.downloaduploadtemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.util.DataUtil;
import gov.gsa.sst.util.DownloadUtil;
import gov.gsa.sst.util.HttpDownloadUtility;
import gov.gsa.sst.util.LoadProperties;
import gov.gsa.sst.util.WebDriverUtil;
import util.ActionByLocator;
import util.ActionByXpath;
import util.PageUtil;

public class DownloadUploadTemplateUtil {

	private final static int TIMEOUT_IN_SECS = 10;
	private static String docName;
	private static String filePath;
	private static By CHECK_DOWNLOAD_STATUS = By.name("CheckDownloadStatus");
	private static By DOWNLOAD_PRODUCT_CATALOG = By.xpath("//button[contains(.,' Download Product Catalog')]");
	private static By refreshBtn = By.name("CheckStatus");
	private static final long TIMEOUT_FOR_UPLOAD_DOCUMENT = 4 * 60;
	private static final long FILEUPLOAD_POLLING_PERIOD_SECONDS = 30;

	private static Logger log = LoggerFactory.getLogger(DownloadUploadTemplateUtil.class);

	/**
	 * This method will download PricingData.xls document Returns false if file is
	 * not downloaded successfully
	 * 
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static boolean downloadTemplateDocument(ExecutionContext executionContext)
			throws MalformedURLException, IOException {
		String docPricingFileName = "";
		WebDriver driver = executionContext.getDriver();
		JsonObject dutObj = executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate");
		docName = dutObj.get("pricingDocumentTemplateName").getAsString();

		File downloadDir = DownloadUtil.setUpDownloadLocation();
		DownloadUtil.deleteFiles(docName, downloadDir);
		if (docName.contains("zip")) {
			docPricingFileName = dutObj.get("pricingDocumentFileName").getAsString();
			DownloadUtil.deleteFiles(FilenameUtils.getBaseName(docPricingFileName), downloadDir);
		}

		if (LoadProperties.getProperty("driver.location").toLowerCase().trim().contains("remote")) {
			if (docName.contains("zip"))
				Octo_SeleniumLibrary.clickElement(driver, By.name("downloadButton"));
			else {
				log.info("Downloading '" + docName + "' through HTTP download util");
				HttpDownloadUtility.downloadFile(driver, "uploadTemplateAction.do?method=view&downloadTemplate=Yes",
						downloadDir);
			}
		} else {
			log.info("Downloading '" + docName + "' through UI");
			Octo_SeleniumLibrary.clickElement(driver, By.name("downloadButton"));
		}

		// For PCU Mod, the "Generate Product Catalog" button would have been
		// clicked in line 290 above
		// so just need to monitor "Check Status" for some time
		if (ActionByLocator.isDisplayed(driver, CHECK_DOWNLOAD_STATUS, TIMEOUT_IN_SECS)) {
			WebDriverUtil.wait(driver, CHECK_DOWNLOAD_STATUS, DOWNLOAD_PRODUCT_CATALOG, 30, 5, TimeUnit.SECONDS);
		}

		// Also for PCU Mod - if 'Download Product Catalog' link is visible
		// use it to download file
		if (ActionByLocator.isDisplayed(driver, DOWNLOAD_PRODUCT_CATALOG, 20)) {
			if (LoadProperties.getProperty("driver.location").toLowerCase().trim().contains("remote")) {
				log.info("Downloading '" + docName + "' through HTTP download util");
				HttpDownloadUtility.downloadFile(driver,
						"uploadTemplateAction.do?method=retrieveDownloadedProductCatalogTemplate", downloadDir);
			} else
				Octo_SeleniumLibrary.clickElement(driver, DOWNLOAD_PRODUCT_CATALOG);
		}

		boolean isFileDownloaded = DownloadUtil.waitForDownload(docName, downloadDir, 25);

		// this is for pcu mod where download template will be in zip format
		if (isFileDownloaded && docName.contains("zip")) {
			log.info("Unzipping the PCU mod pricing template file");
			DownloadUtil.unZipFile(docName, downloadDir);
			docName = docPricingFileName;
			log.info("document name for updating the pricing info is " + docName);
		}
		return isFileDownloaded;
	}

	/**
	 * Updates the pricing data dynamically one worksheet at a time
	 * 
	 * @throws IOException
	 */
	public static void updatePricingDocument(ExecutionContext executionContext, JsonObject worksheetDataObj,
			JsonObject pageObj) throws IOException {
		Workbook workbook = null;
		filePath = DownloadUtil.setUpDownloadLocation().getPath();
		docName = getXLFileNameToUpdate(pageObj);
		FileInputStream fsIn = new FileInputStream(filePath + File.separator + docName);
		if (docName.contains("xlsx"))
			workbook = new XSSFWorkbook(fsIn);
		else
			workbook = new HSSFWorkbook(fsIn);

		log.info("Updating the pricing document template");
		try {
			updateWorksheets(executionContext, workbook, worksheetDataObj);
		} catch (Exception e) {
			log.error(e + "Exception " + e.getMessage());
			throw new IOException("Exception updating template file: " + e.getMessage());
		} finally {
			fsIn.close();
			FileOutputStream fsOut = new FileOutputStream(new File(filePath + File.separator + docName));
			if (workbook != null) {
				workbook.write(fsOut);
				workbook.close();
			} else
				log.error("Document could not be updated.");
			fsOut.close();
		}
	}

	/**
	 * Pricing document is updated for different mods This method SUPPORTS ONLY when
	 * a single mod type is selected
	 * 
	 * @param executionContext
	 * @param wb
	 * @param worksheetDataObj
	 * @throws IOException
	 */
	public static void updateWorksheets(ExecutionContext executionContext, Workbook wb, JsonObject worksheetDataObj)
			throws IOException {
		JsonObject scenarioObj = executionContext.getCurrentScenarioObj();
		populateWorksheetFromDataObject(scenarioObj, wb, worksheetDataObj);
	}

	/**
	 * This method populates the worksheet data from data objects. Following is the
	 * data example for Photos & Accessories worksheets.
	 * 
	 * "downloadUploadTemplate": { "worksheetData": { "Photos": [ { "*SIN": "426
	 * 4D", "*Manufacturer Part Number": "12345", "*Manufacturer Name": "Lenovo",
	 * "Additional Photo 1": "Flower00.jpg", "Additional Photo 2": "flower01.jpg",
	 * "Additional Photo 3": "flower02.jpg" } ], "Accessories": [ { "*SIN": "426
	 * 4D", "*Manufacturer Part Number": "12345", "*Manufacturer Name": "Lenovo",
	 * "*Accessory Part Number": "344545", "*Accessory Manufacturer": "Lenovo Comp"
	 * } ] } }
	 * 
	 * @param book
	 * @throws IOException
	 */
	public static void populateWorksheetFromDataObject(JsonObject scenarioObj, Workbook book,
			JsonObject worksheetDataObj) throws IOException {
		String sheetName;
		JsonArray dataArray = null;
		int startingRowNum;
		boolean isProductCatalogMod = scenarioObj.has("productCatalogUpdateMod");
		boolean isFlaggedPAR = false;
		if (scenarioObj.has("productAnalysisReport")) {
			if (scenarioObj.getAsJsonObject("productAnalysisReport").has("pricingDocumentFileName"))
				isFlaggedPAR = true;
		}

		Row curRow = null;
		Row headerRow = null;
		int cnt = book.getNumberOfSheets();

		for (int i = 0; i < cnt; i++) {
			sheetName = book.getSheetAt(i).getSheetName();
			Sheet worksheet = book.getSheetAt(i);
			headerRow = worksheet.getRow(0);
			if (worksheetDataObj.has(sheetName)) {
				if (isFlaggedPAR)
					startingRowNum = 1;
				else
					startingRowNum = 2;
				log.info("Populating the sheet " + sheetName);
				dataArray = worksheetDataObj.getAsJsonArray(sheetName);
				Iterator<JsonElement> iterator = dataArray.iterator();
				while (iterator.hasNext()) {
					JsonElement element = (JsonElement) iterator.next();
					JsonObject rowObj = element.getAsJsonObject();
					if (!isProductCatalogMod)
						curRow = worksheet.createRow((short) startingRowNum);
					else {
						curRow = getTargetRow(worksheetDataObj, worksheet, rowObj); // for
																					// 'product
																					// catalog
																					// mod',
																					// the
																					// worksheet
																					// data
																					// is
																					// already
																					// pre-populated
						if (curRow == null)
							return;
					}

					populateCellsFromDataObject(curRow, headerRow, rowObj);
					startingRowNum++;
				}
			}
			if (!isProductCatalogMod)
				startingRowNum = 2;
		}
	}

	/**
	 * This method retrieves the header row and
	 * 
	 * @param worksheetDataObj
	 * @param worksheet
	 * @param jsonObj
	 * @return
	 */
	private static Row getTargetRow(JsonObject worksheetDataObj, Sheet worksheet, JsonObject jsonObj) {
		String sin;
		String manufacPartNum;
		String manufacName;
		String optPartNumber = null;
		Row curRow;
		int currentRowNum = 0;
		// String optPartNumber = null;
		sin = jsonObj.get("*SIN").getAsString();
		manufacPartNum = jsonObj.get("*Manufacturer Part Number").getAsString();
		manufacName = jsonObj.get("*Manufacturer Name").getAsString();
		if (worksheet.getSheetName().equalsIgnoreCase("Options"))
			optPartNumber = jsonObj.get("*Option Part Number").getAsString();
		currentRowNum = findRowBySinDetails(worksheet, sin, manufacPartNum, manufacName, optPartNumber);
		if (currentRowNum == 0) {
			log.error("Target row could not be found with values - " + sin + " " + manufacName + " " + manufacPartNum);
			return null;
		}
		curRow = worksheet.getRow(currentRowNum);
		return curRow;
	}

	/**
	 * * This method will find the row which has the matching sin, manufacPartNumber
	 * & manufacName values In case of 'Options' worksheet, option part number will
	 * be used to find the unique row along with the above values.
	 * 
	 * @param workSheet
	 * @param sin
	 * @param manufacPartNumber
	 * @param manufacName
	 * @param optionPartNumber
	 * @return
	 */
	private static int findRowBySinDetails(Sheet workSheet, String sin, String manufacPartNumber, String manufacName,
			String optionPartNumber) {
		Cell cell4 = null;
		DataFormatter formatter = new DataFormatter();
		int sinRowNumber = 0;
		for (Row row : workSheet) {
			for (int i = 0; i < 4; i++) {
				Cell cell1 = row.getCell(i);
				Cell cell2 = row.getCell(i + 1);
				Cell cell3 = row.getCell(i + 2);
				if (formatter.formatCellValue(cell1).trim().equalsIgnoreCase(sin)
						&& formatter.formatCellValue(cell2).trim().trim().equals(manufacPartNumber)
						&& formatter.formatCellValue(cell3).trim().trim().equals(manufacName)) {
					cell4 = row.getCell(i + 3);
					if (optionPartNumber != null && cell4.toString() != "") { // for
																				// 'Options'
																				// worksheet
						if (formatter.formatCellValue(cell4).trim().trim().equals(optionPartNumber)) {
							sinRowNumber = row.getRowNum();
							return sinRowNumber;
						}
					} else {
						sinRowNumber = row.getRowNum();
						return sinRowNumber;
					}
				}
			}
		}
		return sinRowNumber;
	}

	/**
	 * This will populate each cell in a row with data from data file obtained by
	 * the rowObj It ensures that data is added to cell with correct column header
	 * 
	 * @param cellIndex
	 * @param curRow
	 * @param headerRow
	 * @param iter
	 */
	private static void populateCellsFromDataObject(Row curRow, Row headerRow, JsonObject rowObj) {
		int cellCount = headerRow.getLastCellNum();
		for (int i = 0; i < cellCount; i++) {
			Cell headerCell = headerRow.getCell(i);
			Cell curCell = curRow.createCell(i);
			String headerText = headerCell.getStringCellValue().trim();
			if (rowObj.has(headerText)) {
				if (headerText.equalsIgnoreCase("*Start Date (MM/DD/YYYY)")
						|| headerText.equalsIgnoreCase("*End Date (MM/DD/YYYY)")) {
					String date = rowObj.get(headerText).getAsString().isEmpty() ? DataUtil.getCurrentDate(headerText)
							: rowObj.get(headerText).getAsString();
					curCell.setCellValue(date);

				} else {
					if (rowObj.get(headerText).getAsString() != "N/A")
						curCell.setCellValue(rowObj.get(headerText).getAsString());
				}
			}
		}
	}

	/**
	 * Upload the updated pricing document and wait for process to complete the data
	 * 
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */
	public static void uploadDocAndWait(ExecutionContext executionContext)
			throws MalformedURLException, InterruptedException {
		WebDriver driver = executionContext.getDriver();
		uploadPricingDocument(driver, docName);
		if (waitForCapturePricingDocumentToUpload(driver)) {
			if (ActionByXpath.isDisplayed(driver, "//span[@class='btn btn-danger special']", TIMEOUT_IN_SECS)) {
				log.error("Pricing document upload failed. Please refer to DataErrors document.");
				return;
			}
			log.info("Upload Pricing document successful");
			Octo_SeleniumLibrary.clickElement(driver, By.id("goToGeneratePricingBtn"));
		} else {
			try {
				if (driver.findElement(
						By.xpath("//span[@class='btn btn-danger special']/following-sibling::a")) != null) {
					DownloadUploadTemplatePage page = new DownloadUploadTemplatePage(executionContext);
					page.displayErrorFileContents(executionContext);
				}
			} catch (Exception e) {
				log.info("Exception reading DataError file: " + e.getMessage());
				e.printStackTrace();
			}
			log.error("Pricing document upload failed.");
		}
	}

	/**
	 * Upload the updated pricing document
	 * 
	 * @param fileName
	 * @throws MalformedURLException
	 */
	public static void uploadPricingDocument(WebDriver driver, String fileName) throws MalformedURLException {
		String filePath = DownloadUtil.setUpDownloadLocation().getPath();
		String docPath = filePath + File.separator + fileName;
		log.info(" Upload file: " + docPath);
		String browser = LoadProperties.getProperty("driver.browser");
		String location = LoadProperties.getProperty("driver.location");

		File file = new File(filePath + File.separator + fileName);
		if (!file.exists()) {
			log.error(file.getAbsolutePath() + " - Pricing document does not exist");
			return;
		}

		if (browser.equalsIgnoreCase("chrome")) {
			driver.findElement(By.xpath("//input[@type='file']")).sendKeys(file.getAbsolutePath());
		} else if (browser.equalsIgnoreCase("firefox")) {
			if (location.equalsIgnoreCase("remote")) {
				ActionByLocator.type(driver, By.xpath("//input[@type='file']"), filePath, TIMEOUT_IN_SECS);
			} else if (location.equalsIgnoreCase("local")) {
				driver.findElement(By.xpath("//input[@type='file']")).sendKeys(file.getAbsolutePath());
			}
		}

		Octo_SeleniumLibrary.clickElement(driver,
				By.xpath("//input[@name='uploadTemplate' and @value='Start Upload']"));
		PageUtil.checkPageLoad(driver);
	}

	/**
	 * Wait for pricing document to upload completely
	 * 
	 * @return
	 */
	public static boolean waitForCapturePricingDocumentToUpload(WebDriver driver) throws InterruptedException {
		if (LoadProperties.getProperty("environment").equalsIgnoreCase("cloud"))
			return WebDriverUtil.wait(driver, refreshBtn, By.xpath("//span[@class='btn btn-success special']"),
					TIMEOUT_FOR_UPLOAD_DOCUMENT, FILEUPLOAD_POLLING_PERIOD_SECONDS, TimeUnit.SECONDS);
		else { // increase time for document to upload to 8min in GSA test env
			return WebDriverUtil.wait(driver, refreshBtn, By.xpath("//span[@class='btn btn-success special']"), 490,
					FILEUPLOAD_POLLING_PERIOD_SECONDS, TimeUnit.SECONDS);
		}
	}

	private static String getXLFileNameToUpdate(JsonObject pageObj) {
		String xlFileName = null;
		if (pageObj.has("pricingDocumentFileName")) {
			xlFileName = pageObj.get("pricingDocumentFileName").getAsString();
			log.info("Document to be updated is " + xlFileName);
		} else if (pageObj.has("pricingDocumentTemplateName")) {
			xlFileName = pageObj.get("pricingDocumentTemplateName").getAsString();
			log.info("Document to be updated is " + xlFileName);
		}
		return xlFileName;
	}

}
