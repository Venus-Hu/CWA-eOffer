package gov.gsa.sst.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.gsa.sst.util.exceptions.TestDataException;

/**
 * 
 * @author amulay
 *
 */
public class ExcelUtil {
	private static Logger log = LoggerFactory.getLogger(ExcelUtil.class);
	private ArrayList<String> rowHeader;
	
	public ExcelUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method returns an array of rows  
	 * [ {"validation": "errors", "*SIN" :"123" ,...},
	 * {"validation": "errors", "*SIN" :"456", ....}]
	 * @param file
	 * @param sheetName
	 * @return
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	 */
	public JsonArray readFile(File file, String sheetName) throws Exception{
		JsonArray jArray = new JsonArray();
		
	    FileInputStream fileInput = new FileInputStream(file);
	    //Create Workbook instance holding reference to .xlsx file
        Workbook wb = WorkbookFactory.create(fileInput);

        //Get first/desired sheet from the workbook
	    Sheet sheet = wb.getSheet(sheetName);
	    if (sheet == null)
	    	throw new TestDataException("No sheet with name '"+ sheetName + "' found in file: " + file.getName() );
	    
	    Row row;
	    //Please do not use iterators as some null cells get skipped during parsing
        rowHeader = new ArrayList<String>();
      
        int rowCount; // No of rows
        rowCount = sheet.getPhysicalNumberOfRows();
        log.info("There are " + rowCount + " rows in sheet: " + sheetName);
        int columnSize = 0; // No of columns
        int tmp = 0;

        // This trick ensures that we get the data properly even if it doesn't start from first few rows
        for(int i = 0; i < rowCount; i++) {
            row = sheet.getRow(i);
            if(row != null) {
                tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                String lastCellVal = sheet.getRow(0).getCell(tmp-1).getStringCellValue();
                if(lastCellVal.isEmpty() )
                	tmp--;
                if(tmp > columnSize) 
                	columnSize = tmp;
            }
        }
        log.info("The column size is "+ columnSize);
        
        for(int rowIdx = 0; rowIdx < rowCount; rowIdx++) {
        	row = sheet.getRow(rowIdx);
        	if(row != null) {
        	     //Create JsonObject for each row
	            JsonObject rowObject = new JsonObject();
        		//For each row, go through all the columns
        		for(int colIdx = 0; colIdx <= columnSize-1; colIdx++) {
        			Cell cell = row.getCell(colIdx);
        			if(cell != null) {
	                //Check the cell type and format accordingly
        				switch (cell.getCellType())  {
	                    case Cell.CELL_TYPE_NUMERIC:
	                        if(rowIdx != 0) {
	                    		rowObject.addProperty(rowHeader.get(colIdx), cell.getNumericCellValue());
	                    	}
	                        break;
	                    case Cell.CELL_TYPE_STRING:
	                    	if(rowIdx == 0)
	                    		rowHeader.add(cell.getStringCellValue().trim() );
	                    	else {
	                    		
	                    		rowObject.addProperty(rowHeader.get(colIdx), cell.getStringCellValue().trim());
	                    	}
	                    	break;
	                    case Cell.CELL_TYPE_BLANK:
	                    	if(rowIdx != 0)
	                    		rowObject.addProperty(rowHeader.get(colIdx), "");
	                    	break;
	                	}
        			} else
        				rowObject.addProperty(rowHeader.get(colIdx), "");
        		}
	            jArray.add(rowObject);
        	}
        }
        fileInput.close();
        wb.close();
	
		return jArray;

	}
	
	public void displayData(File file, String sheetName) throws Exception{
		JsonArray jArray = readFile(file, sheetName);
		Iterator<JsonElement> iterator = jArray.iterator();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			
			Set<Entry<String, JsonElement>> qcSet = sinObj.entrySet();
			Iterator<Entry<String, JsonElement>> iter = qcSet.iterator();
			while (iter.hasNext()) {
				Entry<String, JsonElement> basicInfoElement = iter.next();
				String elementName = basicInfoElement.getKey();
				JsonElement elementValue = basicInfoElement.getValue();
				log.info(elementName + ": " + elementValue.getAsString()); 
			}
		}
	}
	
	/**
	 * This method is used to retrieve the Validation messages from DataErrors.xslx file
	 * for a specified sheet
	 * @param file
	 * @param sheetName
	 * @return list of csv errors for all failed rows 
	 * @throws Exception 
	 */
	public List<String> retrieveErrorMsgList(File file, String sheetName) throws Exception{
		JsonArray jArray = readFile(file, sheetName);
		Iterator<JsonElement> iterator = jArray.iterator();
		List<String> listMessages = new ArrayList<String>();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject sinObj = element.getAsJsonObject();
			
			Set<Entry<String, JsonElement>> qcSet = sinObj.entrySet();
			Iterator<Entry<String, JsonElement>> iter = qcSet.iterator();
			while (iter.hasNext()) {
				Entry<String, JsonElement> rowElement = iter.next();
				String elementName = rowElement.getKey();
				if(elementName.equalsIgnoreCase("Validation Message")){
					JsonElement elementValue = rowElement.getValue();
					if(!elementValue.getAsString().isEmpty()){
						listMessages.add(elementValue.getAsString());
						log.info(elementName + ": " + elementValue.getAsString());
					}
				}
			}
		}
		return listMessages;
	}
	
	/**
	 * Using the JSonArray from readFile(), filter the required rows (store as JsonObject) using scenario name as criteria
	 * @param file
	 * @param sheetName
	 * @param scenarioName
	 * @return List<JsonObject> list of JsonObject where each JsonObject corresponds to a row
	 * @throws Exception 
	 */
	public List<JsonObject> retrieveDataFromTemplate(File file, String sheetName, String scenarioName) throws Exception{
		
		JsonArray jArray = readFile(file, sheetName);
		Iterator<JsonElement> iterator = jArray.iterator();
		List<JsonObject> listScenarioObj = new ArrayList<JsonObject>();
		while (iterator.hasNext()) {
			JsonElement element = (JsonElement) iterator.next();
			JsonObject rowObj = element.getAsJsonObject();
			
			if (rowObj.has("Scenario Name") && rowObj.get("Scenario Name").getAsString().equalsIgnoreCase(scenarioName))
				listScenarioObj.add(rowObj);
		}
		return listScenarioObj;
	}
	
	public static void main(String[] arg) {

		ExcelUtil util = new ExcelUtil();
		//File file = new File("src/test/resources/InvalidPricingData.xls");
		File file = new File("src/test/resources/DataErrors.xlsx");

		List<JsonObject> listJsonObj;
		try {
		listJsonObj = util.retrieveDataFromTemplate(file, "Non Pricing", "FPT Capture Pricing - Complete ALL sheets with invalid data");
		for (JsonObject rowObj : listJsonObj) {
		Set<Entry<String, JsonElement>> qcSet = rowObj.entrySet();
		Iterator<Entry<String, JsonElement>> iter = qcSet.iterator();

		while (iter.hasNext()) {

		Entry<String, JsonElement> cellElement = iter.next();
		String elementName = cellElement.getKey();
		JsonElement elementValue = cellElement.getValue();
		log.info(elementName + " = " + elementValue.getAsString());
		}
		}
		for (JsonObject obj : listJsonObj) {
		log.info(obj.get("Error Message").getAsString() + " \n SIN:"
		+ obj.get("*Manufacturer Part Number").getAsString());
		}
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
}