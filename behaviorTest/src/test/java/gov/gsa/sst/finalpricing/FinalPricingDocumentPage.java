package gov.gsa.sst.finalpricing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.DownloadUtil;
import gov.gsa.sst.util.HttpDownloadUtility;
import gov.gsa.sst.util.LoadProperties;
import gov.gsa.sst.util.WebDriverUtil;
import util.ActionByPartialLinkText;

public class FinalPricingDocumentPage extends Page {
	private static Logger log = LoggerFactory.getLogger(FinalPricingDocumentPage.class);
	
	private ExecutionContext executionContext;
	private final int TIMEOUT_IN_SECS = 10;
	private static final long GENERATION_TIMEOUT_SECONDS = 7*60;
	private static final long GENERATION_POLLING_PERIOD_SECONDS = 30;
	private By headerText = By.xpath("//h1[contains(text(), 'PRICING DOCUMENT')]");
	private By SAVE_CONTINUE = By.id("btnSaveAndcontinue");
	private By CONTINUE = By.id("correctErrors");
 
	
	
	public FinalPricingDocumentPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		log.info("Loading Final Pricing Documents page");
		try {
			if(CommonUtilPage.isOffer(executionContext)){
				boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
				if (!flag)
					throw new Exception("The solicitation number specified in data does not match the one on UI");
			}
			LeftNavigationMenu.navigateTo(driver, "Pricing Document");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();
			LeftNavigationMenu.navigateTo(driver, "Pricing Document");
		}
		try {
			executionContext.testPageFor508("Final Pricing");
		}catch (Exception e) {
			log.warn("Section 508 exception: " +e.getMessage());
		}
		Assert.assertTrue("Final Pricing Document page is not loaded", isLoaded());
	}

 
	public void generatePricingDocument() {
        Octo_SeleniumLibrary.clickElement(driver, By.name( "generateFinalPricingDocument"));
        log.info("Generate FP document");
	}

	public boolean waitForCompletion() {
	   return WebDriverUtil.wait(driver, By.id("btnToCheck"), By.linkText("View Final Pricing Document"), GENERATION_TIMEOUT_SECONDS, GENERATION_POLLING_PERIOD_SECONDS, TimeUnit.SECONDS);
	}
	
	public boolean isDocumentComplete() {
		return ActionByPartialLinkText.isDisplayed(driver, "View Final Pricing Document", TIMEOUT_IN_SECS);
	}
	
	public void saveAndContinue() {		          
		Octo_SeleniumLibrary.clickElement(driver, SAVE_CONTINUE );
		Octo_SeleniumLibrary.clickElement(driver, CONTINUE );		          
	}
	
	
	public void validatePricingDocument()
	{
		if(executionContext.getCurrentScenarioObj().getAsJsonObject("finalPricingDocument")
				.has("validateFinalPricingDocument")){
			String validateFPDYesOrNo = executionContext.getCurrentScenarioObj().getAsJsonObject("finalPricingDocument")
					.get("validateFinalPricingDocument").getAsString();
			if(validateFPDYesOrNo.equalsIgnoreCase("Yes"))
				try {
					viewPricingDocument();
				} catch (Exception e) {
					log.error(e.getMessage());
				}
		}
	}
	
	/** 
	 * @author - Rajiv K
	 * Validate Final Pricing Document
	 * 
	 */
	
	public void viewPricingDocument() throws Exception {
		JsonObject dataObject = null; 
		log.info("Download Pricing Document");
        ActionByPartialLinkText.assertVisible(driver, "View Final Pricing Document", TIMEOUT_IN_SECS);            		       
        File downloadDir = DownloadUtil.setUpDownloadLocation();
                
        String pricingdocumentName = executionContext.getCurrentScenarioObj().get("finalPricingDocument").getAsJsonObject().get("finalPricingDocumentName").getAsString();
   		DownloadUtil.deleteFiles(pricingdocumentName, downloadDir);
         		
         		if (LoadProperties.getProperty("driver.location").toLowerCase().trim().contains("remote")) {
         			log.info("Downloading '"+pricingdocumentName+"' through HTTP download util");
         			HttpDownloadUtility.downloadFile(driver,"finalPricingDocumentAction.do?method=retrieveFinalPriceList", downloadDir);        			
         		} else {
         			log.info("Downloading '"+pricingdocumentName+"' through UI");
         			ActionByPartialLinkText.click(driver, "View Final Pricing Document", TIMEOUT_IN_SECS);
         		}
         		File file = new File(downloadDir, pricingdocumentName);
         		DownloadUtil.waitForDownload(pricingdocumentName, downloadDir, 20);
         		File zipEntryFile = null;
         		ZipFile zipFile = new ZipFile(file);
         		try {
        	    	if (zipFile.entries().hasMoreElements()) {
        	    		ZipEntry zipEntry = zipFile.entries().nextElement();
        	    		InputStream stream = zipFile.getInputStream(zipEntry);      	    		
        	    		dataObject = getWorksheetDataObjectForValidation();
        	    		if(dataObject == null)		
        	    			verifyFPDocumentContents(executionContext, stream);
        	    		else{
	        	    		Workbook xwb = new XSSFWorkbook(stream);
	        	    		validatePricingDocumentFromDataObject(xwb);
        	    		}
        	    	}
            	}catch(Exception e){
            		log.error(e.getMessage());            		
            	}
         		finally {
            		if (zipEntryFile != null) {
            			zipEntryFile.delete();
            		}
            		zipFile.close();
            		file.delete();
            	}       		
         	}
	
	/**
	 * Validate the actual and expected cell values for a
	 * given data row
	 * 
	 * @param actualRow
	 * @param expectedRow
	 * @param startColIndex starting column index (0-based)
	 */
	public void validateRowValues(Row actualRow, JsonArray expectedRow, int startColIndex) {
		for (int i = 0; i < expectedRow.size(); i++) {
			String expectedCellValue = expectedRow.get(i).getAsString().trim();
			Cell actualCell = actualRow.getCell(startColIndex);
			if (actualCell != null) {
				
				actualCell.setCellType(Cell.CELL_TYPE_STRING);
				String actualCellValue = new DataFormatter().formatCellValue(actualCell).trim().replaceAll("\\r\\n|\\r|\\n", "");
				Assert.assertEquals("Value for cell " + (startColIndex + 1), expectedCellValue, actualCellValue);
			} else {
				log.warn("Careful! Extracted cell value is null - is this expected?");
			}
			startColIndex++;
		}
	}
	   
	public void verifyFPDocumentContents(ExecutionContext executionContext, InputStream inputStream) throws IOException {
		Workbook workbook = new XSSFWorkbook(inputStream);
		try {
			JsonObject finalPricingObj = executionContext
					.getCurrentScenarioObj().get("finalPricingDocument")
					.getAsJsonObject();
			JsonArray sheetsToVerify = finalPricingObj
					.get("finalPricingDocumentContents").getAsJsonArray();
			Iterator<JsonElement> sheetsToVerifyItr = sheetsToVerify.iterator();

			while (sheetsToVerifyItr.hasNext()) {
				JsonObject expectedWorksheetData = sheetsToVerifyItr
						.next()
						.getAsJsonObject();
				String worksheetName = expectedWorksheetData
						.get("sheetName")
						.getAsString();
				Sheet actualWorksheet = workbook.getSheet(worksheetName);

				log.info("Validating sheet headers for " + worksheetName);
				Row actualHeaderData = actualWorksheet.getRow(0); // header is always first row
				JsonArray expectedHeaderData = expectedWorksheetData
						.get("sheetHeader").getAsJsonArray();
				// Column index is 0-based
				validateRowValues(actualHeaderData, expectedHeaderData, 0);

				log.info("Validating sheet data for " + worksheetName);
				JsonArray expectedSheetRows = expectedWorksheetData
						.get("sheetData").getAsJsonArray();
				Iterator<JsonElement> expectedSheetRowsItr = expectedSheetRows
						.iterator();

				// Data for final pricing doc worksheets for PCU mod starts on
				// row 3 (0-based)
				int worksheetRowIndx = 3;
				while (expectedSheetRowsItr.hasNext()) {
					JsonArray expectedRowData = expectedSheetRowsItr
							.next()
							.getAsJsonArray();
					Row actualRowData = actualWorksheet.getRow(worksheetRowIndx);
					
					log.info("Verifying worksheet data for row " + (worksheetRowIndx + 1));
					// Column index is 0-based
					validateRowValues(actualRowData, expectedRowData, 0);
					worksheetRowIndx++;
				}
			}
		} catch (Exception e) {
			log.error("Error while verifying final pricing doc");
			log.error(e.getMessage());
		} finally {
			workbook.close();
			inputStream.close();
		}
	}
	                    
	
	/**
	 * Compares document data from the data object.
	 * Data is passed in this format
	 * 
	 * 
	 *     "worksheetData": { 
	 * 				"Discounts": [
	 *				{
	 *				"*SIN": "71 98",
	 *				"*Manufacturer Part Number": "1234512",
	 *				"*Manufacturer Name": "Acer",
	 *				"*Discount Type": "Dollar Discount",
	 *				"Discount %": "",
	 *				"Dollar Value": "100",
	 *				"*Start Range": "300",
	 *				"*End Range": "399",
	 *				"Discount Condition": "Product will be eligible for discount when it falls within discount range."
	 *				}
	 *			]
	 *    )
	 * 
	 * @param book
	 * @throws IOException
	 */
	public void validatePricingDocumentFromDataObject(Workbook book) throws Exception
	{
		String sheetName;
		JsonArray dataArray = null;
		// get the data that was used to complete capture pricing template
		JsonObject worksheetDataObj = getWorksheetDataObjectForValidation();

		if(worksheetDataObj == null){
			log.error("Worksheet data is not found in the Scenario data object. Please provide the data.");
			return;
		}
		
		Row curRow = null;
		Row headerRow = null;
		int cnt  = book.getNumberOfSheets();
		//Parse through all sheets
		for(int i = 0; i < cnt; i++){
			sheetName = book.getSheetAt(i).getSheetName();
			org.apache.poi.ss.usermodel.Sheet worksheet = book.getSheetAt(i);
			headerRow = worksheet.getRow(0);
			if(worksheetDataObj.has(sheetName)){
				if(!sheetName.contains("Zonal Pricing")){
					log.info("Validating the sheet '" + sheetName + "' contents");
					dataArray = worksheetDataObj.getAsJsonArray(sheetName);
					Iterator<JsonElement> iterator = dataArray.iterator();
					//Parse through all rows in selected sheet
					for(int rowCnt = 0; rowCnt < dataArray.size(); rowCnt++) {
						curRow = validateRowFromDataObject(curRow, headerRow, worksheet, iterator);
						log.info(" Completed validation for row " + rowCnt);
					}
				}
			}
		}
	}

	/**
	 * Each row will be validated against the data object
	 * 
	 * @param cellIndex
	 * @param curRow
	 * @param headerRow
	 * @param worksheet
	 * @param iterator
	 * @return
	 * @throws Exception 
	 */
	private Row validateRowFromDataObject(Row curRow, Row headerRow,
			org.apache.poi.ss.usermodel.Sheet worksheet, Iterator<JsonElement> iterator) throws Exception {
		String sin = "";
		String manufacPartNumber = "";
		String manufacName = "";
		String optionPartNumber = null;
		int curRowIndex;
		
		int cellInitialIndex = 0;
		if (worksheet.isColumnHidden(0)) {
			cellInitialIndex = 1; // Final pricing doc for offer: first column can be hidden
		}
		
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject jsonObj = element.getAsJsonObject();
			
			if(jsonObj.has("*SIN"))
				sin = jsonObj.get("*SIN").getAsString();
			else if(jsonObj.has("SIN"))
				sin = jsonObj.get("SIN").getAsString();
			
			if(jsonObj.has("*Manufacturer Part Number"))
				manufacPartNumber = jsonObj.get("*Manufacturer Part Number").getAsString();
			else if(jsonObj.has("Manufacturer Part Number"))
				manufacPartNumber = jsonObj.get("Manufacturer Part Number").getAsString();
			else if(jsonObj.has("* Manufacturer Part Number"))
				manufacPartNumber = jsonObj.get("* Manufacturer Part Number").getAsString();
			
			if(jsonObj.has("*Manufacturer Name"))
				manufacName = jsonObj.get("*Manufacturer Name").getAsString();
			else if(jsonObj.has("Manufacturer Name"))
				manufacName = jsonObj.get("Manufacturer Name").getAsString();
			else if(jsonObj.has("* Manufacturer Name"))
				manufacName = jsonObj.get("Manufacturer Name").getAsString();
			
			if(jsonObj.has("*Option Part Number"))
				optionPartNumber = jsonObj.get("*Option Part Number").getAsString();
			Set<Entry<String, JsonElement>> set = jsonObj.entrySet();
			Iterator<Entry<String, JsonElement>> iter = set.iterator();
			curRowIndex = findRowBySinDetails(worksheet, sin, manufacPartNumber, manufacName, optionPartNumber);
			if(curRowIndex != 0){
				curRow = worksheet.getRow(curRowIndex);
				validateCellValuesFromDataObject(curRow, headerRow, iter, cellInitialIndex);
			}
			else {
				log.error("Row could not be found with values - " + sin + "-" + manufacPartNumber + "-" + manufacName);
				throw new Exception("Row could not be found with values - " + sin + "-" + manufacPartNumber + "-" + manufacName);
			}
		}
		return curRow;
	}

	/**
	 * This will validates each cell in a row against the data object
	 * 
	 * @param cellIndex
	 * @param curRow
	 * @param headerRow
	 * @param iter
	 */
	private void validateCellValuesFromDataObject(Row curRow, Row headerRow,
			Iterator<Entry<String, JsonElement>> iter, int cellIndex) {
		DataFormatter formatter = new DataFormatter();
		
		while (iter.hasNext()) {
			Cell headerCell = headerRow.getCell(cellIndex);
			Cell curCell = curRow.getCell(cellIndex);
			Entry<String, JsonElement> basicInfoElement = iter.next();
			String elementName = basicInfoElement.getKey();
			JsonElement elementValue = basicInfoElement.getValue();
			String headerText = headerCell.getStringCellValue().trim();
			log.info("Check if data header '" + elementName + "' is same as FP excel header: '" + headerText + "'");
			if(headerText.equalsIgnoreCase(elementName)) {
				log.info("The expected value is:" + elementValue + ":: And actual value is:" + curCell);
				Assert.assertEquals("Cell value",
						elementValue.getAsString(), formatter.formatCellValue(curCell));
			}
			cellIndex++;
		}
		log.info("Completed validation for?? ");
	}
	

	/**
	 *  * This method will find the row which has the matching sin, manufacPartNumber & manufacName values
	 * In case of 'Options' worksheet, option part number will be used to find the unique row along with the above values. 
	 * 
	 * @param workSheet
	 * @param sin
	 * @param manufacPartNumber
	 * @param manufacName
	 * @param optionPartNumber
	 * @return
	 */
	public int findRowBySinDetails(Sheet workSheet, String sin, String manufacPartNumber, String manufacName, String optionPartNumber)
	{
		Cell cell4 = null;
		DataFormatter formatter = new DataFormatter();
		int sinRowNumber = 0;
		for(Row row : workSheet){
			for(int i = 0; i < 4; i++){
				Cell cell1 = row.getCell(i);
				Cell cell2 = row.getCell(i+1);
				Cell cell3 = row.getCell(i+2);
				if(formatter.formatCellValue(cell1).trim().equalsIgnoreCase(sin)
					 && formatter.formatCellValue(cell2).trim().trim().equalsIgnoreCase(manufacPartNumber)	
					 && formatter.formatCellValue(cell3).trim().trim().equalsIgnoreCase(manufacName)){
						if(optionPartNumber != null){		// for 'Options' worksheet
							cell4 = row.getCell(i+3);
							if(formatter.formatCellValue(cell4).trim().trim().equalsIgnoreCase(optionPartNumber)){
								sinRowNumber = row.getRowNum();
								return sinRowNumber;
							}
						}else{
							sinRowNumber = row.getRowNum();
							return sinRowNumber;
						}
				}
			}
		}
		return sinRowNumber;
	}	

	/**
	 *Gets the Final pricing data object if it is provided to compare the actual Final pricing document data
	 * Else retrieves capture pricing data for comparison 
	 * 
	 * @return
	 */
	public JsonObject getWorksheetDataObjectForValidation()
	{
		JsonObject dataObj = null; 
		if(validateFPDDataYesNo()){
	/*		if(executionContext.getCurrentScenarioObj().has("downloadUploadTemplate") &&
					executionContext.getCurrentScenarioObj().has("finalPricingDocument")){
				if(executionContext.getCurrentScenarioObj().getAsJsonObject("finalPricingDocument").has("worksheetData"))
					dataObj = executionContext.getCurrentScenarioObj().getAsJsonObject("finalPricingDocument")
					.getAsJsonObject("worksheetData");	
				else
					dataObj = executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate")
					.getAsJsonObject("worksheetData");	
			}
			else if(executionContext.getCurrentScenarioObj().has("downloadUploadTemplate")){
				if(executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate").has("worksheetData"))
					dataObj = executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate")
					.getAsJsonObject("worksheetData");			
			}else if(executionContext.getCurrentScenarioObj().has("finalPricingDocument")){
				if(executionContext.getCurrentScenarioObj().getAsJsonObject("finalPricingDocument").has("worksheetData"))
					dataObj = executionContext.getCurrentScenarioObj().getAsJsonObject("finalPricingDocument")
					.getAsJsonObject("worksheetData");
			}*/
			
			//If Final Pricing worksheet data present use it  
			if(executionContext.getCurrentScenarioObj().has("finalPricingDocument")){
				JsonObject fpObject = executionContext.getCurrentScenarioObj().getAsJsonObject("finalPricingDocument");
				if(fpObject.has("worksheetData"))
					dataObj = fpObject.getAsJsonObject("worksheetData");
				else{
					//Use the capture pricing  
					if (executionContext.getCurrentScenarioObj().has("downloadUploadTemplate"))
						dataObj = executionContext.getCurrentScenarioObj().getAsJsonObject("downloadUploadTemplate").getAsJsonObject("worksheetData");
					else
						dataObj = executionContext.getCurrentScenarioObj().getAsJsonObject("capturePricingDataEntry").getAsJsonObject("worksheetData");			
				}
			}
		}		
		return dataObj;
	}
	
/**
 * Determines if the final pricing document needs to be validated or not
 *  
 * @return
 */
	private boolean validateFPDDataYesNo() {
		boolean validateData = false;
		if(executionContext.getCurrentScenarioObj().has("finalPricingDocument")){
			if(executionContext.getCurrentScenarioObj().getAsJsonObject("finalPricingDocument").has("validateFinalPricingDocument")){
				String validateFPD = executionContext.getCurrentScenarioObj().getAsJsonObject("finalPricingDocument")
						.get("validateFinalPricingDocument").getAsString();
				if(validateFPD.equalsIgnoreCase("Yes")){
					validateData = true;
					return validateData;
				}
			}
		}
		return validateData;
	}
	

}
