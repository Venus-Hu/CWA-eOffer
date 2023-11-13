package util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class TableElementUtils {
	
	public static WebElement identifyTableWithID(WebDriver driver, String divID) {
		WebElement tableElem = null;
	
		List<WebElement> divObjects =  driver.findElements(By.id(divID));
		for(WebElement divObject : divObjects) {
			List<WebElement> tables =  divObject.findElements(By.tagName("table"));
	
			for(WebElement table : tables) {
				if(tableElem == null) {
					tableElem = table;
				}
			}
		}
		
		return tableElem;
	}
	
	public static WebElement identifyTableWithID_Caption(WebDriver driver, String divID, String caption) {
		WebElement tableElem = null;
	
		List<WebElement> divObjects =  driver.findElements(By.id(divID));
		for(WebElement divObject : divObjects) {

			List<WebElement> tables =  divObject.findElements(By.tagName("table"));
	
			for(WebElement table : tables) {
				if(tableElem == null) {
					try {
						WebElement c = table.findElement(By.tagName("caption"));
						if(c != null && c.getText().contains(caption)) {
							tableElem = table;
						}
					} catch(NoSuchElementException e) {
					}
				}
			}
		}
		
		return tableElem;
	}
	
	public static WebElement identifyTableWithCaption(WebDriver driver, String caption) {
		WebElement tableElem = null;
		
		List<WebElement> tables =  driver.findElements(By.tagName("table"));
		
		for(WebElement table : tables) {
			if(tableElem == null) {
				try {
					WebElement c = table.findElement(By.tagName("caption"));
					if(c != null && c.getText().contains(caption)) {
						tableElem = table;
					}
				} catch(NoSuchElementException e) {
				}
			}
		}
		
		return tableElem;
	}
	
	public static int getTableRowCount(WebElement table) {
		List<WebElement> allRows = table.findElements(By.tagName("tr"));
		return allRows.size() - 1;
	}
	
	public static WebElement getNthTableRow(WebElement table, int n) {
		List<WebElement> allRows = table.findElements(By.tagName("tr"));
		return allRows.get(n);
	}
	
	public static WebElement getTableRowByCellValue(WebElement table, String cellHead, String cellValue) {
		WebElement result = null;
		int requiredCellIndex = -1;
		
		//find cell index
		List<WebElement> allRows = table.findElements(By.tagName("tr"));
		for(int i = 0; i < allRows.size(); i++) {
			if(requiredCellIndex < 0) {
				List<WebElement> cells = allRows.get(i).findElements(By.tagName("th"));
				if(cells == null || cells.isEmpty()) {
					cells = allRows.get(i).findElements(By.tagName("td"));
				}
				
				for(int j = 0; j < cells.size(); j++) {
					WebElement cell = cells.get(j);
					String cellText = cell.getText();
					if(cellHead.equalsIgnoreCase(cellText.trim()))
						requiredCellIndex = j;
				}
			}
		}
		
		if(requiredCellIndex > -1) {
			for(int i = 1; i < allRows.size(); i++) {
				WebElement row = allRows.get(i);
				List<WebElement> cells = row.findElements(By.tagName("td"));
				if(cells.size() > requiredCellIndex && cells.get(requiredCellIndex).getText().trim().contains(cellValue)) {
					result = row;
				}
			}
		}
		
		return result;
	}
	
/**
 * @author skumar
 * Getting table row by combination of 2 cells value
 * @param table (input as table)
 * @param FirstHeader
 * @param Value of the first Header
 * @param Second Header of the same rown
 * @param Value of the second header
 * @return
 */
	public  static WebElement getTableRowByCellValues(WebElement table, String cellHead1, String cellValue1,String cellHead2, String cellValue2) {
		WebElement result = null;
		int requiredCellIndex1 = -1;
		int requiredCellIndex2 = -1;
		
		//find cell index
		List<WebElement> allRows = table.findElements(By.tagName("tr"));
		for(int i = 0; i < allRows.size(); i++) {
			if(requiredCellIndex1 < 0) {
				List<WebElement> cells1 = allRows.get(i).findElements(By.tagName("th"));
				if(cells1 == null || cells1.isEmpty()) {
					cells1 = allRows.get(i).findElements(By.tagName("td"));
				}
				
				for(int j = 0; j < cells1.size(); j++) {
					WebElement cell = cells1.get(j);
					String cellText = cell.getText();
					if(cellHead1.equalsIgnoreCase(cellText.trim()))
						requiredCellIndex1 = j;
				}
			}
			if(requiredCellIndex2 < 0) {
				List<WebElement> cells2 = allRows.get(i).findElements(By.tagName("th"));
				if(cells2 == null || cells2.isEmpty()) {
					cells2 = allRows.get(i).findElements(By.tagName("td"));
				}
				
				for(int k = 0; k < cells2.size(); k++) {
					WebElement cell2 = cells2.get(k);
					String cellText2 = cell2.getText();
					if(cellHead2.equalsIgnoreCase(cellText2.trim()))
						requiredCellIndex2 = k;
				}
			}
		}
		
		if(requiredCellIndex1 > -1) {
			for(int i = 1; i < allRows.size(); i++) {
				WebElement row = allRows.get(i);
				List<WebElement> cells = row.findElements(By.tagName("td"));
				if((cells.size() > requiredCellIndex1 && cells.get(requiredCellIndex1).getText().trim().contains(cellValue1)) &&
				(cells.size() > requiredCellIndex2 && cells.get(requiredCellIndex2).getText().trim().contains(cellValue2))){
					result = row;
				}
			}
		}
		
		return result;
	}

	/**
	 * Getting table row by combination of 3 cells value
	 * @author skumar
	 * @param table (input as table)
	 * @param FirstHeader
	 * @param Value of the first Header
	 * @param Second Header of the same row
	 * @param Value of the second header
	 * @param Third Header of the same row
	 * @param Third Value of the same row
	 * @return - Returns the particular row index
	 */
	public  static WebElement getTableRowByCellValues(WebElement table, String cellHead1, String cellValue1,String cellHead2, String cellValue2,String cellHead3, String cellValue3) {
		WebElement result = null;
		int requiredCellIndex1 = -1;
		int requiredCellIndex2 = -1;
		int requiredCellIndex3 = -1;
		
		//find cell index
		List<WebElement> allRows = table.findElements(By.tagName("tr"));
		for(int i = 0; i < allRows.size(); i++) {
			if(requiredCellIndex1 < 0) {
				List<WebElement> cells1 = allRows.get(i).findElements(By.tagName("th"));
				if(cells1 == null || cells1.isEmpty()) {
					cells1 = allRows.get(i).findElements(By.tagName("td"));
				}
				
				for(int j = 0; j < cells1.size(); j++) {
					WebElement cell = cells1.get(j);
					String cellText = cell.getText();
					if(cellHead1.equalsIgnoreCase(cellText.trim()))
						requiredCellIndex1 = j;
				}
			}
			if(requiredCellIndex2 < 0) {
				List<WebElement> cells2 = allRows.get(i).findElements(By.tagName("th"));
				if(cells2 == null || cells2.isEmpty()) {
					cells2 = allRows.get(i).findElements(By.tagName("td"));
				}
				
				for(int k = 0; k < cells2.size(); k++) {
					WebElement cell2 = cells2.get(k);
					String cellText2 = cell2.getText();
					if(cellHead2.equalsIgnoreCase(cellText2.trim()))
						requiredCellIndex2 = k;
				}
			}
			if(requiredCellIndex3 < 0) {
				List<WebElement> cells3 = allRows.get(i).findElements(By.tagName("th"));
				if(cells3 == null || cells3.isEmpty()) {
					cells3 = allRows.get(i).findElements(By.tagName("td"));
				}
				
				for(int l = 0; l < cells3.size(); l++) {
					WebElement cell3 = cells3.get(l);
					String cellText3 = cell3.getText();
					if(cellHead3.equalsIgnoreCase(cellText3.trim()))
						requiredCellIndex3 = l;
				}
			}
		}
		
		if(requiredCellIndex1 > -1) {
			for(int i = 1; i < allRows.size(); i++) {
				WebElement row = allRows.get(i);
				List<WebElement> cells = row.findElements(By.tagName("td"));
				if((cells.size() > requiredCellIndex1 && cells.get(requiredCellIndex1).getText().trim().contains(cellValue1)) &&
				(cells.size() > requiredCellIndex2 && cells.get(requiredCellIndex2).getText().trim().contains(cellValue2)) &&
				(cells.size() > requiredCellIndex3 && cells.get(requiredCellIndex3).getText().trim().contains(cellValue3))){
					result = row;
				}
			}
		}
		
		return result;
	}

	/**
	 * Removing attribute as attribute as checked and value as also checked
	 * @param1 - pass the webdriver object
	 * @param2 - find element by id,name or tag in javascript
	 * @param3 - value of the element
	 */
	public static void clearRadioElement(WebDriver driver,String radioBtnBy,String value)
	{
		if(radioBtnBy.equalsIgnoreCase("id"))
		{
			((JavascriptExecutor) driver).executeScript(
						"var inputs = document.getElementsById('"+value+"');"
				        + "for(var i = 0; i < inputs.length; i++)"
				        + "{"
				        + "inputs[i].removeAttribute('checked','checked');"
				        + "}");
		}
		else if(radioBtnBy.equalsIgnoreCase("name"))
		{
			((JavascriptExecutor) driver).executeScript(
						"var inputs = document.getElementsByName('"+value+"');"
				        + "for(var i = 0; i < inputs.length; i++)"
				        + "{"
				        + "inputs[i].removeAttribute('checked','checked');"
				        + "}");
		}
		else if(radioBtnBy.equalsIgnoreCase("tag"))
		{
			((JavascriptExecutor) driver).executeScript(
						"var inputs = document.getElementsByTagName('"+value+"');"
				        + "for(var i = 0; i < inputs.length; i++)"
				        + "{"
				        + "inputs[i].removeAttribute('checked','checked');"
				        + "}");
		}
		
	}
	
	
	public static List<String> getTableDataAsCsv(WebElement table) {
		List<WebElement> allRows = table.findElements(By.tagName("tr"));
		
		List<String> dataAsCsv = new ArrayList<String>();
		for(int i = 0; i < allRows.size(); i++) {
			List<WebElement> cells = allRows.get(i).findElements(By.tagName("th"));
			cells.addAll(allRows.get(i).findElements(By.tagName("td")));
			/*if(cells == null || cells.size() <= 0) {
				cells = allRows.get(i).findElements(By.tagName("td"));
			}*/
			String rowData = "";
			for(int j = 0; j < cells.size(); j++) {
				WebElement cell = cells.get(j);
				rowData += cell.getText() + ",";
			}
			
			if(rowData.contains(",")) 
				rowData = rowData.substring(0, rowData.lastIndexOf(","));

			dataAsCsv.add(rowData);
		}
		
		return dataAsCsv;
	}
	
	public static WebElement getLinkElementFromTableRow(WebElement tableRow, String elementValue)
	{
		WebElement result = null;
		result = tableRow.findElement(By.partialLinkText(elementValue));
		return result;
	}

	public static WebElement getElementFromTableRow(WebElement tableRow, String tagNameForElement, String elementValue) {
		WebElement result = null;
		
		List<WebElement> cells = tableRow.findElements(By.tagName("td"));
		for(WebElement cell : cells) {
			List<WebElement> children = cell.findElements(By.tagName(tagNameForElement));
			if(children != null && children.size() > 0) {
				for(WebElement child : children) {
					String value = child.getAttribute("value");
					if(value != null && elementValue.equalsIgnoreCase(value.trim())) {
						result = child;
					}
				}
			}
		}
		
		return result;
	}
	
	public static WebElement getElementFromTableRowByText(WebElement tableRow, String tagNameForElement, String text) {
		WebElement result = null;
		
		List<WebElement> cells = tableRow.findElements(By.tagName("td"));
		for(WebElement cell : cells) {
			List<WebElement> children = cell.findElements(By.tagName(tagNameForElement));
			if(children != null && children.size() > 0) {
				for(WebElement child : children) {
					if(text.equalsIgnoreCase(child.getText())) {
						result = child;
					}
				}
			}
		}
		
		return result;
	}
}
