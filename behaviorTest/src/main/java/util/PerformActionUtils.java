 package util;


 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 import org.openqa.selenium.*;
 import org.openqa.selenium.interactions.Actions;
 import org.openqa.selenium.support.ui.ExpectedConditions;
 import org.openqa.selenium.support.ui.Select;
 import org.openqa.selenium.support.ui.WebDriverWait;

 import java.util.Iterator;
 import java.util.List;
 import java.util.Map.Entry;
 import java.util.Set;
 import java.util.concurrent.TimeUnit;

public class PerformActionUtils {

	/**
     * Attempts to click on an element multiple times (to avoid stale element
     * exceptions caused by rapid DOM refreshes)
     *
     * @param d
     *            The WebDriver
     * @param by
     *            By element locator
     */
    public static void dependableClick(WebDriver d, By by)
    {
        final int MAXIMUM_WAIT_TIME = 10;
        final int MAX_STALE_ELEMENT_RETRIES = 5;

        WebDriverWait wait = new WebDriverWait(d, MAXIMUM_WAIT_TIME);
        int retries = 0;
        while (true)
        {
            try
            {
                wait.until(ExpectedConditions.elementToBeClickable(by)).click();

                return;
            }
            catch (StaleElementReferenceException e)
            {
                if (retries < MAX_STALE_ELEMENT_RETRIES)
                {
                    retries++;
                    continue;
                }
                else
                {
                    throw e;
                }
            }
        }
    }
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
					System.out.println("celText"+cellText+"-"+"cell head"+cellHead1+"cell text trim"+cellText.trim());
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
					System.out.println("celText"+cellText2+"-"+"cell head"+cellHead2+"cell text trim"+cellText2.trim());

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
					System.out.println("Row found"+result.getText());
					return result;
				}
			}
		}
		
		return result;
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
					System.out.println("Row found"+row.getText());
					return result;
				}
			}
		}
		
		return result;
	}
	
	
	public static  void performActionByLocator(WebDriver driver,JsonObject solClauseObj)  throws InterruptedException
	{
		Set<Entry<String, JsonElement>> basicInfoSet = solClauseObj.entrySet();

        Iterator<Entry<String, JsonElement>> iter = basicInfoSet.iterator();
        while ( iter.hasNext() )
        {
        	Entry<String, JsonElement> basicInfoElement = iter.next();
        	
        	String elementName = basicInfoElement.getKey();
        	JsonElement elementValue = basicInfoElement.getValue();
        	
        	String tagName = "";
        		try{
        			tagName = driver.findElement(By.name(elementName)).getTagName();
        		}catch(NoSuchElementException e)
        		{
        			tagName = driver.findElement(By.id(elementName)).getTagName();
        		}
        	switch(tagName)
        	{
        	case "select" : 
        		try{
        		new Select(driver.findElement(By.name(elementName))).selectByVisibleText(elementValue.getAsString());
        		}catch(NoSuchElementException e)
        		{
            		new Select(driver.findElement(By.id(elementName))).selectByVisibleText(elementValue.getAsString());
        		}
        		break;
        	case "input" :
        		String type = "";
        		try{
        		 type = driver.findElement(By.name(elementName)).getAttribute("type");
        		}
        		catch(NoSuchElementException e)
        		{
            		 type = driver.findElement(By.id(elementName)).getAttribute("type");
        		}
        		if(type.equalsIgnoreCase("text"))
        		{	        		
        			try{
        				driver.findElement(By.name(elementName)).sendKeys(elementValue.getAsString());
        			}catch(NoSuchElementException e)
        			{
            			driver.findElement(By.id(elementName)).sendKeys(elementValue.getAsString());	
        			}
        		}else
            	if(type.equalsIgnoreCase("radio"))
            	{
            		if(elementValue.getAsString().equalsIgnoreCase("Yes"))
            		{
            			try{
            				driver.findElement(By.name(elementName)).click();
            			}catch(NoSuchElementException e)
            			{
            				driver.findElement(By.id(elementName)).click();
            			}
            		}else if(elementValue.getAsString().equalsIgnoreCase("No"))
            		{
            			try{
            			TableElementUtils.clearRadioElement(driver, "name", elementValue.getAsString());
            			}catch(NoSuchElementException e)
            			{
                			TableElementUtils.clearRadioElement(driver, "id", elementValue.getAsString());
            			}
            		}
            	}
            	else if(type.equalsIgnoreCase("checkbox"))
            	{
            		try{
            			driver.findElement(By.name(elementName));
            			if(elementValue.getAsString().equalsIgnoreCase("Yes"))
            			{
            				if(!driver.findElement(By.name(elementName)).isSelected())
            				{
            					driver.findElement(By.name(elementName)).click();
            				}
            			}else if(elementValue.getAsString().equalsIgnoreCase("No"))
            			{
            				if(driver.findElement(By.name(elementName)).isSelected())
            				{
            					driver.findElement(By.name(elementName)).click();
            				}
            			}
            		}catch(NoSuchElementException e)
            		{
            			driver.findElement(By.id(elementName));
            			if(elementValue.getAsString().equalsIgnoreCase("Yes"))
            			{
            				if(!driver.findElement(By.id(elementName)).isSelected())
            				{
            					driver.findElement(By.id(elementName)).click();
            				}
            			}else if(elementValue.getAsString().equalsIgnoreCase("No"))
            			{
            				if(driver.findElement(By.id(elementName)).isSelected())
            				{
            					driver.findElement(By.id(elementName)).click();
            				}
            			}
            			
            		}
            	}else if(type.equalsIgnoreCase("button"))
            	{
            		try{
            		driver.findElement(By.name(elementName)).click();
            		}catch(NoSuchElementException e)
            		{
            			driver.findElement(By.id(elementName)).click();
            		}
            	}else if(type.equalsIgnoreCase("a"))
            	{
            		
            	}

        		break;
        	case "textarea" : 
        		try{
        			driver.findElement(By.name(elementName)).sendKeys(elementValue.getAsString());
        		}catch(NoSuchElementException e)
        		{
        			driver.findElement(By.id(elementName)).sendKeys(elementValue.getAsString());
        		}
        		break;
       
        	}
        	
        	
        }
        return;
	}
	
	public static  void performActionByLocator(WebDriver driver,By locator,String input)
	{
		
		String tagName = "";
		new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(locator));
    	tagName = driver.findElement(locator).getTagName().toLowerCase();
    	switch(tagName)
    	{
    	case "select" :
    		try{
    		new Select(driver.findElement(locator)).selectByVisibleText(input);
    		}catch(Exception e)
    		{
        		new Select(driver.findElement(locator)).selectByValue(input);
    		}
    		break;
    	case "input" :
    		String type = "";
   		    type = driver.findElement(locator).getAttribute("type");

   		    if(type.equalsIgnoreCase("text"))
   		    {
   				driver.findElement(locator).clear();
   				driver.findElement(locator).sendKeys(input);
   		    }else if(type.equalsIgnoreCase("radio"))
   		    {
    			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(locator));
   		    	driver.findElement(locator).click();
   		    }
   		    else if(type.equalsIgnoreCase("checkbox"))
   		    {
    			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(locator));
   		    	driver.findElement(locator).click();
   		    }else if(type.equalsIgnoreCase("button"))
       	    {
    			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(locator));
   		    	driver.findElement(locator).click();	
       	    }else if(type.equalsIgnoreCase("a"))
       	    {
    			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(locator));
       	    	driver.findElement(locator).click();
       	    }
   		    break;
    	case "textarea" : 
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(input);
		break;
		   default:
   			   break;
	}
    	
	}
	
	
	public static  void performActionByLocator(WebDriver driver,By locator)
	{
		
		String tagName = "";
		new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(locator));
    	tagName = driver.findElement(locator).getTagName().toLowerCase();
    	switch(tagName)
    	{
    	case "input" :
    		String type = "";
   		    type = driver.findElement(locator).getAttribute("type");
   		    if(type.equalsIgnoreCase("radio"))
   		    {
    			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(locator));
   		    	driver.findElement(locator).click();
   		    }
   		    else if(type.equalsIgnoreCase("checkbox"))
   		    {
    			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(locator));
   		    	driver.findElement(locator).click();
   		    }else if(type.equalsIgnoreCase("button"))
       	    {
    			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(locator));
   		    	driver.findElement(locator).click();	
       	    }else if(type.equalsIgnoreCase("a"))
       	    {
    			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(locator));
       	    	driver.findElement(locator).click();
       	    }
   		    break;
   		   default:
   			   break;
    	}
    	
	}
	
	public static  void performActionByLocator(WebDriver driver,By locator,String input,int timeoutInSecs)
	{
		
		String tagName = "";
		new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(locator));
    	tagName = driver.findElement(locator).getTagName().toLowerCase();
    	switch(tagName)
    	{
    	case "select" :
    		try{
    		new Select(driver.findElement(locator)).selectByVisibleText(input);
    		}catch(Exception e)
    		{
        		new Select(driver.findElement(locator)).selectByValue(input);
    		}
    		break;
    	case "input" :
    		String type = "";
   		    type = driver.findElement(locator).getAttribute("type");

   		    if(type.equalsIgnoreCase("text"))
   		    {
   				driver.findElement(locator).clear();
   				driver.findElement(locator).sendKeys(input);
   		    }else if(type.equalsIgnoreCase("radio"))
   		    {
    			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
   		    	driver.findElement(locator).click();
   		    }
   		    else if(type.equalsIgnoreCase("checkbox"))
   		    {
    			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
   		    	driver.findElement(locator).click();
   		    }else if(type.equalsIgnoreCase("button"))
       	    {
    			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
   		    	driver.findElement(locator).click();	
       	    }else if(type.equalsIgnoreCase("a"))
       	    {
    			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
       	    	driver.findElement(locator).click();
       	    }
   		    break;
    	case "textarea" : 
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(input);
		break;
		   default:
   			   break;
	}
    	
	}
	
	public static enum performAction {
		click,type,clearAndtype,selectByText,selectByValue,selectByIndex
	}
	

	public static  void performActionByLocator(WebDriver driver,By locator,String input,performAction action,int timeoutInSecs)
	{
		switch(action)
		{
		case click:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
			driver.findElement(locator).click();
			break;
		case type:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(locator));
			driver.findElement(locator).sendKeys(input);
			break;
		case clearAndtype:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(locator));
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(input);
			break;
		case selectByText:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
			new Select(driver.findElement(locator)).selectByVisibleText(input);
			break;
		case selectByValue:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
			new Select(driver.findElement(locator)).selectByValue(input);
			break;
		case selectByIndex:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
			new Select(driver.findElement(locator)).selectByIndex(Integer.parseInt(input));
			break;
		default:
			break;
		}
	}

	public static  void performActionByLocatorForAngularApp(WebDriver driver,By locator,String input,performAction action,int timeoutInSecs)
	{
		WebElement elem = null;

		switch(action)
		{
		case click:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
			elem = driver.findElement(locator);
			elem.click();
			break;
		case type:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			PageLoad.checkPageLoadAngular(driver, timeoutInSecs);			
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(locator));
			elem = driver.findElement(locator);
			elem.sendKeys(input);
			break;
		case clearAndtype:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(locator));
			elem = driver.findElement(locator);
	        elem.clear();
	        elem.sendKeys(input);
			break;
		case selectByText:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			PageLoad.checkPageLoadAngular(driver, timeoutInSecs);			
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
			elem = driver.findElement(locator);
			new Select(elem).selectByVisibleText(input);
			break;
		case selectByValue:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			PageLoad.checkPageLoadAngular(driver, timeoutInSecs);			
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
			elem = driver.findElement(locator);
			new Select(elem).selectByValue(input);
			break;
		case selectByIndex:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
			elem = driver.findElement(locator);
			new Select(elem).selectByIndex(Integer.parseInt(input));
			break;
		default:
			break;
		}
	}
	
	public static  void performActionByLocator(WebDriver driver,WebElement element,String input,performAction action,int timeoutInSecs)
	{
		switch(action)
		{
		case click:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			break;
		case type:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(input);
			break;
		case clearAndtype:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOf(element));
			element.clear();
			element.sendKeys(input);
			break;
		case selectByText:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(element));
			new Select(element).selectByVisibleText(input);
			break;
		case selectByValue:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(element));
			new Select(element).selectByValue(input);
			break;
		case selectByIndex:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(element));
			new Select(element).selectByIndex(Integer.parseInt(input));
			break;
		default:
			break;
		}
	}
	
	public static  void performActionByLocatorForAngularApp(WebDriver driver,WebElement element,String input,performAction action,int timeoutInSecs)
	{

		switch(action)
		{
		case click:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			break;
		case type:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			PageLoad.checkPageLoadAngular(driver, timeoutInSecs);			
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(input);
			break;
		case clearAndtype:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOf(element));
			element.clear();
			element.sendKeys(input);
			break;
		case selectByText:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			PageLoad.checkPageLoadAngular(driver, timeoutInSecs);			
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(element));
			new Select(element).selectByVisibleText(input);
			break;
		case selectByValue:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			PageLoad.checkPageLoadAngular(driver, timeoutInSecs);			
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(element));
			new Select(element).selectByValue(input);
			break;
		case selectByIndex:
			PageLoad.checkPageLoad(driver, timeoutInSecs);
			PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(element));
			new Select(element).selectByIndex(Integer.parseInt(input));
			break;
		default:
			break;
		}
	}
	
	public static  void performActionByLocator(WebDriver driver,By locator,int timeoutInSecs)
	{
		
		String tagName = "";
		new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(locator));
    	tagName = driver.findElement(locator).getTagName().toLowerCase();
    	switch(tagName)
    	{
    	case "input" :
    		String type = "";
   		    type = driver.findElement(locator).getAttribute("type");
   		    if(type.equalsIgnoreCase("radio"))
   		    {
    			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
   		    	driver.findElement(locator).click();
   		    }
   		    else if(type.equalsIgnoreCase("checkbox"))
   		    {
    			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
   		    	driver.findElement(locator).click();
   		    }else if(type.equalsIgnoreCase("button"))
       	    {
    			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
   		    	driver.findElement(locator).click();	
       	    }else if(type.equalsIgnoreCase("a"))
       	    {
    			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
       	    	driver.findElement(locator).click();
       	    }
   		    break;
   		   default:
   			   break;
    	}
    	
	}
	
	public static  void performActionByLocatorName(WebDriver driver,String elementName,String elementValue)  throws InterruptedException
	{
        	
        	String tagName = "";
			PageUtil.checkPageLoad(driver);
			new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.name(elementName)));
        	tagName = driver.findElement(By.name(elementName)).getTagName().toLowerCase();
        	switch(tagName)
        	{
        	case "select" : 
        		try{
        		PageUtil.checkPageLoad(driver);
        		new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.name(elementName)));
        		new Select(driver.findElement(By.name(elementName))).selectByVisibleText(elementValue);
        		}catch(Exception e){
        			
            		new Select(driver.findElement(By.name(elementName))).selectByValue(elementValue);
        		}
        		break;
        	case "input" :
        		String type = "";
    			PageUtil.checkPageLoad(driver);
    			new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.name(elementName)));
        		 type = driver.findElement(By.name(elementName)).getAttribute("type");

        		if(type.equalsIgnoreCase("text"))
        		{	        		

        				driver.findElement(By.name(elementName)).clear();
        				driver.findElement(By.name(elementName)).sendKeys(elementValue);
        		}else
            	if(type.equalsIgnoreCase("radio"))
            	{
        			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.name(elementName)));
            		if(elementValue.equalsIgnoreCase("Yes"))
            		{
            				driver.findElement(By.name(elementName)).click();
            		}else if(elementValue.equalsIgnoreCase("No"))
            		{
            			TableElementUtils.clearRadioElement(driver, "name", elementValue);
            			
            		}
            	}
            	else if(type.equalsIgnoreCase("checkbox"))
            	{
        			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.name(elementName)));
            			if(elementValue.equalsIgnoreCase("Yes"))
            			{
            				if(!driver.findElement(By.name(elementName)).isSelected())
            				{
            					driver.findElement(By.name(elementName)).click();
            				}
            			}else if(elementValue.equalsIgnoreCase("No"))
            			{
            				if(driver.findElement(By.name(elementName)).isSelected())
            				{
            					driver.findElement(By.name(elementName)).click();
            				}
            			}          
            	}else if(type.equalsIgnoreCase("button"))
            	{
        			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.name(elementName)));
            		driver.findElement(By.name(elementName)).click();	
            	}else if(type.equalsIgnoreCase("a"))
            	{
            		
            	}
        		break;
        	case "textarea" : 
        			driver.findElement(By.name(elementName)).clear();
        			driver.findElement(By.name(elementName)).sendKeys(elementValue);
        		break;
        	}
        	return;
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param elementValue
	 * @throws InterruptedException
	 */
	public static  void performActionByLocatorValue(WebDriver driver,By locator,String elementValue)  throws InterruptedException
	{
        	
        	String tagName = "";
        	tagName = driver.findElement(locator).getTagName();
        	switch(tagName)
        	{
        	case "select" : 
        		new Select(driver.findElement(locator)).selectByVisibleText(elementValue);
        		break;
        	case "input" :
        		String type = "";

        		 type = driver.findElement(locator).getAttribute("type");

            		 type = driver.findElement(locator).getAttribute("type");

        		if(type.equalsIgnoreCase("text"))
        		{	        		

        				driver.findElement(locator).clear();
        				driver.findElement(locator).sendKeys(elementValue);
        		}else
            	if(type.equalsIgnoreCase("radio"))
            	{
            		if(elementValue.equalsIgnoreCase("Yes"))
            		{
            				driver.findElement(locator).click();
            		}else if(elementValue.equalsIgnoreCase("No"))
            		{
            			//TableElementUtils.clearRadioElement(driver, "name", elementValue);
            			
            		}
            	}
            	else if(type.equalsIgnoreCase("checkbox"))
            	{
            			driver.findElement(locator);
            			if(elementValue.equalsIgnoreCase("Yes"))
            			{
            				if(!driver.findElement(locator).isSelected())
            				{
            					driver.findElement(locator).click();
            				}
            			}else if(elementValue.equalsIgnoreCase("No"))
            			{
            				if(driver.findElement(locator).isSelected())
            				{
            					driver.findElement(locator).click();
            				}
            			}          
            	}else if(type.equalsIgnoreCase("button"))
            	{
            		driver.findElement(locator).click();	
            	}else if(type.equalsIgnoreCase("a"))
            	{
            		
            	}
        		break;
        	case "textarea" : 
        			driver.findElement(locator).clear();
        			driver.findElement(locator).sendKeys(elementValue);
        		break;
        	}
        	return;
	}
	public static  void performActionByLocatorId(WebDriver driver,String elementName,String elementValue)  throws InterruptedException
	{
        	
        	String tagName = "";
			new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.id(elementName)));
        	tagName = driver.findElement(By.id(elementName)).getTagName().toLowerCase();
        	switch(tagName)
        	{
        	case "select" : 
        		new Select(driver.findElement(By.id(elementName))).selectByVisibleText(elementValue);
        		break;
        	case "input" :
        		String type = "";

        		 type = driver.findElement(By.id(elementName)).getAttribute("type");

            		 type = driver.findElement(By.id(elementName)).getAttribute("type");

        		if(type.equalsIgnoreCase("text"))
        		{	        		

        				driver.findElement(By.id(elementName)).clear();
        				driver.findElement(By.id(elementName)).sendKeys(elementValue);
        		}else
            	if(type.equalsIgnoreCase("radio"))
            	{
        			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.id(elementName)));
            		if(elementValue.equalsIgnoreCase("Yes"))
            		{
            				driver.findElement(By.id(elementName)).click();
            		}else if(elementValue.equalsIgnoreCase("No"))
            		{
            			TableElementUtils.clearRadioElement(driver, "name", elementValue);
            			
            		}
            	}
            	else if(type.equalsIgnoreCase("checkbox"))
            	{
        			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.id(elementName)));
            			if(elementValue.equalsIgnoreCase("Yes"))
            			{
            				if(!driver.findElement(By.id(elementName)).isSelected())
            				{
            					driver.findElement(By.id(elementName)).click();
            				}
            			}else if(elementValue.equalsIgnoreCase("No"))
            			{
            				if(driver.findElement(By.id(elementName)).isSelected())
            				{
            					driver.findElement(By.id(elementName)).click();
            				}
            			}          
            	}else if(type.equalsIgnoreCase("button"))
            	{
        			new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.id(elementName)));
            		driver.findElement(By.id(elementName)).click();	
            	}else if(type.equalsIgnoreCase("a"))
            	{
            		
            	}
        		break;
        	case "textarea" : 
        			driver.findElement(By.id(elementName)).clear();
        			driver.findElement(By.id(elementName)).sendKeys(elementValue);
        		break;
        	}
        	return;
	}
	public static  void performActionByLocatorCss(WebDriver driver,String elementName,String elementValue)  throws InterruptedException
	{
        	
        	String tagName = "";
        	tagName = driver.findElement(By.cssSelector(elementName)).getTagName();
        	switch(tagName)
        	{
        	case "select" : 
        		new Select(driver.findElement(By.cssSelector(elementName))).selectByVisibleText(elementValue);
        		break;
        	case "input" :
        		String type = "";

        		 type = driver.findElement(By.cssSelector(elementName)).getAttribute("type");

            		 type = driver.findElement(By.cssSelector(elementName)).getAttribute("type");

        		if(type.equalsIgnoreCase("text"))
        		{	        		

        				driver.findElement(By.cssSelector(elementName)).clear();
        				driver.findElement(By.cssSelector(elementName)).sendKeys(elementValue);
        		}else
            	if(type.equalsIgnoreCase("radio"))
            	{
            		if(elementValue.equalsIgnoreCase("Yes"))
            		{
            				driver.findElement(By.cssSelector(elementName)).click();
            		}else if(elementValue.equalsIgnoreCase("No"))
            		{
            			TableElementUtils.clearRadioElement(driver, "name", elementValue);
            			
            		}
            	}
            	else if(type.equalsIgnoreCase("checkbox"))
            	{
            			driver.findElement(By.cssSelector(elementName));
            			if(elementValue.equalsIgnoreCase("Yes"))
            			{
            				if(!driver.findElement(By.cssSelector(elementName)).isSelected())
            				{
            					driver.findElement(By.cssSelector(elementName)).click();
            				}
            			}else if(elementValue.equalsIgnoreCase("No"))
            			{
            				if(driver.findElement(By.cssSelector(elementName)).isSelected())
            				{
            					driver.findElement(By.cssSelector(elementName)).click();
            				}
            			}          
            	}else if(type.equalsIgnoreCase("button"))
            	{
            		driver.findElement(By.cssSelector(elementName)).click();	
            	}else if(type.equalsIgnoreCase("a"))
            	{
            		
            	}
        		break;
        	case "textarea" : 
        			driver.findElement(By.cssSelector(elementName)).clear();
        			driver.findElement(By.cssSelector(elementName)).sendKeys(elementValue);
        		break;
        	}
        	return;
	}
	
	public static  void performActionByLocatorclassName(WebDriver driver,String elementName,String elementValue)  throws InterruptedException
	{
        	
        	String tagName = "";
        	tagName = driver.findElement(By.className(elementName)).getTagName();
        	tagName = tagName.toLowerCase();
        	switch(tagName)
        	{
        	case "select" : 
        		new Select(driver.findElement(By.className(elementName))).selectByVisibleText(elementValue);
        		break;
        	case "input" :
        		String type = "";

        		 type = driver.findElement(By.className(elementName)).getAttribute("type");

            		 type = driver.findElement(By.className(elementName)).getAttribute("type");

        		if(type.equalsIgnoreCase("text"))
        		{	        		

        				driver.findElement(By.className(elementName)).clear();
        				driver.findElement(By.className(elementName)).sendKeys(elementValue);
        		}else
            	if(type.equalsIgnoreCase("radio"))
            	{
            		if(elementValue.equalsIgnoreCase("Yes"))
            		{
            				driver.findElement(By.className(elementName)).click();
            		}else if(elementValue.equalsIgnoreCase("No"))
            		{
            			TableElementUtils.clearRadioElement(driver, "name", elementValue);
            			
            		}
            	}
            	else if(type.equalsIgnoreCase("checkbox"))
            	{
            			driver.findElement(By.className(elementName));
            			if(elementValue.equalsIgnoreCase("Yes"))
            			{
            				if(!driver.findElement(By.className(elementName)).isSelected())
            				{
            					driver.findElement(By.className(elementName)).click();
            				}
            			}else if(elementValue.equalsIgnoreCase("No"))
            			{
            				if(driver.findElement(By.className(elementName)).isSelected())
            				{
            					driver.findElement(By.className(elementName)).click();
            				}
            			}          
            	}else if(type.equalsIgnoreCase("button"))
            	{
            		driver.findElement(By.className(elementName)).click();	
            	}else if(type.equalsIgnoreCase("a"))
            	{
            		
            	}
        		break;
        	case "textarea" : 
        			driver.findElement(By.className(elementName)).clear();
        			driver.findElement(By.className(elementName)).sendKeys(elementValue);
        		break;
        	}
        	return;
	}
	
	public static  void performActionBy(WebDriver driver,By by,String elementValue)  throws InterruptedException
	{
        	
        	String tagName = "";
        	tagName = driver.findElement(by).getTagName();
        	switch(tagName)
        	{
        	case "select" : 
        		new Select(driver.findElement(by)).selectByVisibleText(elementValue);
        		break;
        	case "input" :
        		String type = "";

        		 type = driver.findElement(by).getAttribute("type");

            		 type = driver.findElement(by).getAttribute("type");

        		if(type.equalsIgnoreCase("text"))
        		{	        		

        				driver.findElement(by).clear();
        				driver.findElement(by).sendKeys(elementValue);
        		}else
            	if(type.equalsIgnoreCase("radio"))
            	{
            		if(elementValue.equalsIgnoreCase("Yes")||elementValue.equalsIgnoreCase("Y"))
            		{
            				driver.findElement(by).click();
            		}else if(elementValue.equalsIgnoreCase("No")||elementValue.equalsIgnoreCase("N"))
            		{
            			TableElementUtils.clearRadioElement(driver, "name", elementValue);
            			
            		}
            	}
            	else if(type.equalsIgnoreCase("checkbox"))
            	{
            			driver.findElement(by);
            			if(elementValue.equalsIgnoreCase("Yes")||elementValue.equalsIgnoreCase("Y"))
            			{
            				if(!driver.findElement(by).isSelected())
            				{
            					driver.findElement(by).click();
            				}
            			}else if(elementValue.equalsIgnoreCase("No")||elementValue.equalsIgnoreCase("N"))
            			{
            				if(driver.findElement(by).isSelected())
            				{
            					driver.findElement(by).click();
            				}
            			}          
            	}else if(type.equalsIgnoreCase("button"))
            	{
            		driver.findElement(by).click();	
            	}else if(type.equalsIgnoreCase("file"))
            	{
            		driver.findElement(by).sendKeys(elementValue);
            	}
        		break;
        	case "textarea" : 
        			driver.findElement(by).clear();
        			driver.findElement(by).sendKeys(elementValue);
        		break;
        	case "a":
        			driver.findElement(by).click();
        		break;
        	}
        	return;
	}
	public enum Action {
		click,type,upload,select,clear,clearAndType
	}
	public enum Locator{
		name,id,xpath,css,classname,link,partiallink,tagname
	}
	public static void performAct(WebDriver driver,Locator locator,Action action,String element,String elementValue)
	{
		switch(action)
		{
		case click:
			String type = driver.findElement(By.name(element)).getAttribute("type");
				switch(locator)
				{							
				case name: 
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(driver.findElement(By.name(element)).isSelected())
			            				{
			            					driver.findElement(By.name(element)).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(driver.findElement(By.name(element)).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "name", element);
			            		}
			            	}
			        
							driver.findElement(By.name(element)).click();
							break;
				case id:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(driver.findElement(By.id(element)).isSelected())
			            				{
			            					driver.findElement(By.id(element)).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(driver.findElement(By.id(element)).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "id", element);
			            		}
			            	}
							driver.findElement(By.id(element)).click();
							break;
				case xpath:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(driver.findElement(By.xpath(element)).isSelected())
			            				{
			            					driver.findElement(By.xpath(element)).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(driver.findElement(By.xpath(element)).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "xpath", element);
			            		}
			            	}
							driver.findElement(By.xpath(element)).click();
							break;
				case css:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(driver.findElement(By.cssSelector(element)).isSelected())
			            				{
			            					driver.findElement(By.cssSelector(element)).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(driver.findElement(By.cssSelector(element)).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "cssSelector", element);
			            		}
			            	}
							driver.findElement(By.cssSelector(element)).click();
							break;
				case classname:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(driver.findElement(By.className(element)).isSelected())
			            				{
			            					driver.findElement(By.className(element)).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(driver.findElement(By.className(element)).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "className", element);
			            		}
			            	}
							driver.findElement(By.className(element)).click();
							break;
				case link:
							driver.findElement(By.linkText(element)).click();
							break;
				case partiallink:
							driver.findElement(By.partialLinkText(element)).click();
							break;
				case tagname:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(driver.findElement(By.tagName(element)).isSelected())
			            				{
			            					driver.findElement(By.tagName(element)).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(driver.findElement(By.tagName(element)).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "tagName", element);
			            		}
			            	}
							driver.findElement(By.tagName(element)).click();
							break;
				default:
					break;	
				}
				break;
		case type:
			switch(locator)
			{
				case name: 
				driver.findElement(By.name(element)).sendKeys(elementValue);
				break;
				case id:
				driver.findElement(By.id(element)).sendKeys(elementValue);
				break;
				case xpath:
				driver.findElement(By.xpath(element)).sendKeys(elementValue);
				break;
				case css:
				driver.findElement(By.cssSelector(element)).sendKeys(elementValue);
				break;
				case classname: 
				driver.findElement(By.className(element)).sendKeys(elementValue);
				break;
				case tagname:
				driver.findElement(By.tagName(element)).sendKeys(elementValue);
				break;
				default:
				break;
			}
			break;
		case clearAndType:
			switch(locator)
			{
				case name: 
				driver.findElement(By.name(element)).clear();
				driver.findElement(By.name(element)).sendKeys(elementValue);
				break;
				case id:
				driver.findElement(By.name(element)).clear();	
				driver.findElement(By.id(element)).sendKeys(elementValue);
				break;
				case xpath:
				driver.findElement(By.name(element)).clear();	
				driver.findElement(By.xpath(element)).sendKeys(elementValue);
				break;
				case css:
				driver.findElement(By.name(element)).clear();		
				driver.findElement(By.cssSelector(element)).sendKeys(elementValue);
				break;
				case classname: 
				driver.findElement(By.name(element)).clear();							
				driver.findElement(By.className(element)).sendKeys(elementValue);
				break;
				case tagname:
				driver.findElement(By.name(element)).clear();							
				driver.findElement(By.tagName(element)).sendKeys(elementValue);
				break;
				default:
				break;
			}
			break;
		case select:
			switch(locator)
			{
			case name:
				new Select(driver.findElement(By.name(element))).selectByVisibleText(elementValue);
				break;
			case id:
				new Select(driver.findElement(By.id(element))).selectByVisibleText(elementValue);
				break;
			case xpath:
				new Select(driver.findElement(By.xpath(element))).selectByVisibleText(elementValue);
				break;
			case css:
				new Select(driver.findElement(By.cssSelector(element))).selectByVisibleText(elementValue);
				break;
			case classname:
				new Select(driver.findElement(By.className(element))).selectByVisibleText(elementValue);
				break;
			case tagname:
				new Select(driver.findElement(By.tagName(element))).selectByVisibleText(elementValue);
				break;				
			default:
				break;
			}
			break;
		case clear:
			String attribute = driver.findElement(By.name(element)).getAttribute("type");
			switch(locator)
			{
			case name:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(driver.findElement(By.name(element)).isSelected())
            				{
            					driver.findElement(By.name(element)).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(driver.findElement(By.name(element)).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "name", element);
            		}
            	}else
            	{
            		driver.findElement(By.name(element)).clear();
            	}
				break;
			case id:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(driver.findElement(By.id(element)).isSelected())
            				{
            					driver.findElement(By.id(element)).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(driver.findElement(By.id(element)).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "id", element);
            		}
            	}else
            	{
            		driver.findElement(By.id(element)).clear();
            	}
				break;
			case xpath:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(driver.findElement(By.xpath(element)).isSelected())
            				{
            					driver.findElement(By.xpath(element)).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(driver.findElement(By.xpath(element)).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "xpath", element);
            		}
            	}else
            	{
            		driver.findElement(By.xpath(element)).clear();
            	}
				break;
			case classname:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(driver.findElement(By.className(element)).isSelected())
            				{
            					driver.findElement(By.className(element)).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(driver.findElement(By.className(element)).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "className", element);
            		}
            	}else
            	{
            		driver.findElement(By.className(element)).clear();
            	}
				break;
			case css:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(driver.findElement(By.cssSelector(element)).isSelected())
            				{
            					driver.findElement(By.cssSelector(element)).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(driver.findElement(By.cssSelector(element)).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "cssSelector", element);
            		}
            	}else
            	{
            		driver.findElement(By.cssSelector(element)).clear();
            	}
            	break;
			case tagname:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(driver.findElement(By.tagName(element)).isSelected())
            				{
            					driver.findElement(By.tagName(element)).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(driver.findElement(By.tagName(element)).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "tagName", element);
            		}
            	}else
            	{
            		driver.findElement(By.tagName(element)).clear();
            	}
				break;
			default:
				break;
			}
			break;
		default:
			break;
		
		}
		return;
	}
	
	public static void performAct(WebDriver driver,Locator locator,WebElement webElement,Action action,String element,String elementValue)
	{
		switch(action)
		{
		case click:
			String type = webElement.findElement(By.name(element)).getAttribute("type");
				switch(locator)
				{							
				case name: 
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(webElement.findElement(By.name(element)).isSelected())
			            				{
			            					webElement.findElement(By.name(element)).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(webElement.findElement(By.name(element)).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "name", element);
			            		}
			            	}
			        
							webElement.findElement(By.name(element)).click();
							break;
				case id:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(webElement.findElement(By.id(element)).isSelected())
			            				{
			            					webElement.findElement(By.id(element)).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(webElement.findElement(By.id(element)).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "id", element);
			            		}
			            	}
							webElement.findElement(By.id(element)).click();
							break;
				case xpath:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(webElement.findElement(By.xpath(element)).isSelected())
			            				{
			            					webElement.findElement(By.xpath(element)).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(webElement.findElement(By.xpath(element)).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "xpath", element);
			            		}
			            	}
							webElement.findElement(By.xpath(element)).click();
							break;
				case css:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(webElement.findElement(By.cssSelector(element)).isSelected())
			            				{
			            					webElement.findElement(By.cssSelector(element)).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(webElement.findElement(By.cssSelector(element)).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "cssSelector", element);
			            		}
			            	}
							webElement.findElement(By.cssSelector(element)).click();
							break;
				case classname:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(webElement.findElement(By.className(element)).isSelected())
			            				{
			            					webElement.findElement(By.className(element)).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(webElement.findElement(By.className(element)).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "className", element);
			            		}
			            	}
							webElement.findElement(By.className(element)).click();
							break;
				case link:
							webElement.findElement(By.linkText(element)).click();
							break;
				case partiallink:
							webElement.findElement(By.partialLinkText(element)).click();
							break;
				case tagname:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(webElement.findElement(By.tagName(element)).isSelected())
			            				{
			            					webElement.findElement(By.tagName(element)).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(webElement.findElement(By.tagName(element)).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "tagName", element);
			            		}
			            	}
							webElement.findElement(By.tagName(element)).click();
							break;
				default:
					break;	
				}
				break;
		case type:
			switch(locator)
			{
				case name: 
				webElement.findElement(By.name(element)).sendKeys(elementValue);
				break;
				case id:
				webElement.findElement(By.id(element)).sendKeys(elementValue);
				break;
				case xpath:
				webElement.findElement(By.xpath(element)).sendKeys(elementValue);
				break;
				case css:
				webElement.findElement(By.cssSelector(element)).sendKeys(elementValue);
				break;
				case classname: 
				webElement.findElement(By.className(element)).sendKeys(elementValue);
				break;
				case tagname:
				webElement.findElement(By.tagName(element)).sendKeys(elementValue);
				break;
				default:
				break;
			}
			break;
		case clearAndType:
			switch(locator)
			{
				case name: 
				webElement.findElement(By.name(element)).clear();
				webElement.findElement(By.name(element)).sendKeys(elementValue);
				break;
				case id:
				webElement.findElement(By.name(element)).clear();	
				webElement.findElement(By.id(element)).sendKeys(elementValue);
				break;
				case xpath:
				webElement.findElement(By.name(element)).clear();	
				webElement.findElement(By.xpath(element)).sendKeys(elementValue);
				break;
				case css:
				webElement.findElement(By.name(element)).clear();		
				webElement.findElement(By.cssSelector(element)).sendKeys(elementValue);
				break;
				case classname: 
				webElement.findElement(By.name(element)).clear();							
				webElement.findElement(By.className(element)).sendKeys(elementValue);
				break;
				case tagname:
				webElement.findElement(By.name(element)).clear();							
				webElement.findElement(By.tagName(element)).sendKeys(elementValue);
				break;
				default:
				break;
			}
			break;
		case select:
			switch(locator)
			{
			case name:
				new Select(webElement.findElement(By.name(element))).selectByVisibleText(elementValue);
				break;
			case id:
				new Select(webElement.findElement(By.id(element))).selectByVisibleText(elementValue);
				break;
			case xpath:
				new Select(webElement.findElement(By.xpath(element))).selectByVisibleText(elementValue);
				break;
			case css:
				new Select(webElement.findElement(By.cssSelector(element))).selectByVisibleText(elementValue);
				break;
			case classname:
				new Select(webElement.findElement(By.className(element))).selectByVisibleText(elementValue);
				break;
			case tagname:
				new Select(webElement.findElement(By.tagName(element))).selectByVisibleText(elementValue);
				break;				
			default:
				break;
			}
			break;
		case clear:
			String attribute = webElement.findElement(By.name(element)).getAttribute("type");
			switch(locator)
			{
			case name:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(webElement.findElement(By.name(element)).isSelected())
            				{
            					webElement.findElement(By.name(element)).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(webElement.findElement(By.name(element)).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "name", element);
            		}
            	}else
            	{
            		webElement.findElement(By.name(element)).clear();
            	}
				break;
			case id:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(webElement.findElement(By.id(element)).isSelected())
            				{
            					webElement.findElement(By.id(element)).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(webElement.findElement(By.id(element)).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "id", element);
            		}
            	}else
            	{
            		webElement.findElement(By.id(element)).clear();
            	}
				break;
			case xpath:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(webElement.findElement(By.xpath(element)).isSelected())
            				{
            					webElement.findElement(By.xpath(element)).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(webElement.findElement(By.xpath(element)).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "xpath", element);
            		}
            	}else
            	{
            		webElement.findElement(By.xpath(element)).clear();
            	}
				break;
			case classname:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(webElement.findElement(By.className(element)).isSelected())
            				{
            					webElement.findElement(By.className(element)).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(webElement.findElement(By.className(element)).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "className", element);
            		}
            	}else
            	{
            		webElement.findElement(By.className(element)).clear();
            	}
				break;
			case css:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(webElement.findElement(By.cssSelector(element)).isSelected())
            				{
            					webElement.findElement(By.cssSelector(element)).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(webElement.findElement(By.cssSelector(element)).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "cssSelector", element);
            		}
            	}else
            	{
            		webElement.findElement(By.cssSelector(element)).clear();
            	}
            	break;
			case tagname:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(webElement.findElement(By.tagName(element)).isSelected())
            				{
            					webElement.findElement(By.tagName(element)).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(webElement.findElement(By.tagName(element)).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "tagName", element);
            		}
            	}else
            	{
            		webElement.findElement(By.tagName(element)).clear();
            	}
				break;
			default:
				break;
			}
			break;
		default:
			break;
		
		}
		return;
	}

	public static void performAct(WebDriver driver,Locator locator,int index,Action action,String element,String elementValue)
	{
		switch(action)
		{
		case click:
			String type = driver.findElements(By.name(element)).get(index).getAttribute("type");
				switch(locator)
				{							
				case name: 
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(driver.findElements(By.name(element)).get(index).isSelected())
			            				{
			            					driver.findElements(By.name(element)).get(index).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(driver.findElements(By.name(element)).get(index).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "name", element);
			            		}
			            	}
			        
							driver.findElements(By.name(element)).get(index).click();
							break;
				case id:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(driver.findElements(By.id(element)).get(index).isSelected())
			            				{
			            					driver.findElements(By.id(element)).get(index).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(driver.findElements(By.id(element)).get(index).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "id", element);
			            		}
			            	}
							driver.findElements(By.id(element)).get(index).click();
							break;
				case xpath:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(driver.findElements(By.xpath(element)).get(index).isSelected())
			            				{
			            					driver.findElements(By.xpath(element)).get(index).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(driver.findElements(By.xpath(element)).get(index).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "xpath", element);
			            		}
			            	}
							driver.findElements(By.xpath(element)).get(index).click();
							break;
				case css:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(driver.findElements(By.cssSelector(element)).get(index).isSelected())
			            				{
			            					driver.findElements(By.cssSelector(element)).get(index).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(driver.findElements(By.cssSelector(element)).get(index).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "cssSelector", element);
			            		}
			            	}
							driver.findElements(By.cssSelector(element)).get(index).click();
							break;
				case classname:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(driver.findElements(By.className(element)).get(index).isSelected())
			            				{
			            					driver.findElements(By.className(element)).get(index).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(driver.findElements(By.className(element)).get(index).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "className", element);
			            		}
			            	}
							driver.findElements(By.className(element)).get(index).click();
							break;
				case link:
							driver.findElements(By.linkText(element)).get(index).click();
							break;
				case partiallink:
							driver.findElements(By.partialLinkText(element)).get(index).click();
							break;
				case tagname:
							if(type.equalsIgnoreCase("checkbox"))
			            	{		            				
			            				if(driver.findElements(By.tagName(element)).get(index).isSelected())
			            				{
			            					driver.findElements(By.tagName(element)).get(index).click();
			            				}
			            	}
			            	if(type.equalsIgnoreCase("radio"))
			            	{
			            		if(driver.findElements(By.tagName(element)).get(index).isSelected())
			            		{
			            			TableElementUtils.clearRadioElement(driver, "tagName", element);
			            		}
			            	}
							driver.findElements(By.tagName(element)).get(index).click();
							break;
				default:
					break;	
				}
				break;
		case type:
			switch(locator)
			{
				case name: 
				driver.findElements(By.name(element)).get(index).sendKeys(elementValue);
				break;
				case id:
				driver.findElements(By.id(element)).get(index).sendKeys(elementValue);
				break;
				case xpath:
				driver.findElements(By.xpath(element)).get(index).sendKeys(elementValue);
				break;
				case css:
				driver.findElements(By.cssSelector(element)).get(index).sendKeys(elementValue);
				break;
				case classname: 
				driver.findElements(By.className(element)).get(index).sendKeys(elementValue);
				break;
				case tagname:
				driver.findElements(By.tagName(element)).get(index).sendKeys(elementValue);
				break;
				default:
				break;
			}
			break;
		case clearAndType:
			switch(locator)
			{
				case name: 
				driver.findElements(By.name(element)).get(index).clear();
				driver.findElements(By.name(element)).get(index).sendKeys(elementValue);
				break;
				case id:
				driver.findElements(By.name(element)).get(index).clear();	
				driver.findElements(By.id(element)).get(index).sendKeys(elementValue);
				break;
				case xpath:
				driver.findElements(By.name(element)).get(index).clear();	
				driver.findElements(By.xpath(element)).get(index).sendKeys(elementValue);
				break;
				case css:
				driver.findElements(By.name(element)).get(index).clear();		
				driver.findElements(By.cssSelector(element)).get(index).sendKeys(elementValue);
				break;
				case classname: 
				driver.findElements(By.name(element)).get(index).clear();							
				driver.findElements(By.className(element)).get(index).sendKeys(elementValue);
				break;
				case tagname:
				driver.findElements(By.name(element)).get(index).clear();							
				driver.findElements(By.tagName(element)).get(index).sendKeys(elementValue);
				break;
				default:
				break;
			}
			break;
		case select:
			switch(locator)
			{
			case name:
				new Select(driver.findElement(By.name(element))).selectByVisibleText(elementValue);
				break;
			case id:
				new Select(driver.findElement(By.id(element))).selectByVisibleText(elementValue);
				break;
			case xpath:
				new Select(driver.findElement(By.xpath(element))).selectByVisibleText(elementValue);
				break;
			case css:
				new Select(driver.findElement(By.cssSelector(element))).selectByVisibleText(elementValue);
				break;
			case classname:
				new Select(driver.findElement(By.className(element))).selectByVisibleText(elementValue);
				break;
			case tagname:
				new Select(driver.findElement(By.tagName(element))).selectByVisibleText(elementValue);
				break;				
			default:
				break;
			}
			break;
		case clear:
			String attribute = driver.findElements(By.name(element)).get(index).getAttribute("type");
			switch(locator)
			{
			case name:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(driver.findElements(By.name(element)).get(index).isSelected())
            				{
            					driver.findElements(By.name(element)).get(index).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(driver.findElements(By.name(element)).get(index).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "name", element);
            		}
            	}else
            	{
            		driver.findElements(By.name(element)).clear();
            	}
				break;
			case id:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(driver.findElements(By.id(element)).get(index).isSelected())
            				{
            					driver.findElements(By.id(element)).get(index).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(driver.findElements(By.id(element)).get(index).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "id", element);
            		}
            	}else
            	{
            		driver.findElements(By.id(element)).clear();
            	}
				break;
			case xpath:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(driver.findElements(By.xpath(element)).get(index).isSelected())
            				{
            					driver.findElements(By.xpath(element)).get(index).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(driver.findElements(By.xpath(element)).get(index).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "xpath", element);
            		}
            	}else
            	{
            		driver.findElements(By.xpath(element)).clear();
            	}
				break;
			case classname:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(driver.findElements(By.className(element)).get(index).isSelected())
            				{
            					driver.findElements(By.className(element)).get(index).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(driver.findElements(By.className(element)).get(index).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "className", element);
            		}
            	}else
            	{
            		driver.findElements(By.className(element)).clear();
            	}
				break;
			case css:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(driver.findElements(By.cssSelector(element)).get(index).isSelected())
            				{
            					driver.findElements(By.cssSelector(element)).get(index).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(driver.findElements(By.cssSelector(element)).get(index).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "cssSelector", element);
            		}
            	}else
            	{
            		driver.findElements(By.cssSelector(element)).clear();
            	}
            	break;
			case tagname:
				if(attribute.equalsIgnoreCase("checkbox"))
            	{		            				
            				if(driver.findElements(By.tagName(element)).get(index).isSelected())
            				{
            					driver.findElements(By.tagName(element)).get(index).click();
            				}
            	}
            	if(attribute.equalsIgnoreCase("radio"))
            	{
            		if(driver.findElements(By.tagName(element)).get(index).isSelected())
            		{
            			TableElementUtils.clearRadioElement(driver, "tagName", element);
            		}
            	}else
            	{
            		driver.findElements(By.tagName(element)).get(index).clear();
            	}
				break;
			default:
				break;
			}
			break;
		default:
			break;
		
		}
		return;
	}
	
	public static WebDriver menuNavigation(WebDriver driver,By parentLink,By childLink)
	{
		Actions actions = new Actions(driver);
		PageUtil.checkPageLoad(driver);
		WebElement parentElement = driver.findElement(parentLink);
		new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(parentLink));
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		actions.moveToElement(parentElement).build().perform();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(childLink));		
		driver.findElement(childLink).click();
		PageUtil.checkPageLoad(driver);
		return driver;
	}
}
