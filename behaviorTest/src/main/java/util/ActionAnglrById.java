package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;


public class ActionAnglrById {

	/*********Update for framework upgrade by Nur and Venus**************/

	
	/* @param driver - Pass the webdriver instance
     * @param id - Pass the id identifier
     * @param timeoutInSecs - Pass the timeout to check page load, page load for angular and explicit wait for element to be visible
     */
    public static void waitForElementToBeVisible(WebDriver driver, String id, int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        
        ActionBy_TempFix.waitForElementToBeVisible(driver, By.id(id));
    }

    public static void waitForElementToBePresent(WebDriver driver, String id, int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        
        ActionBy_TempFix.waitForElementToBePresent(driver, By.id(id));
    }


    public static void waitForElementToBeClickable(WebDriver driver, String id, int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.id(id)));
        
        ActionBy_TempFix.waitForElementToBeClickable(driver, By.id(id));
    }

    public static boolean isDisplayed(WebDriver driver,String id,int timeoutInSecs)
    {

//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            waitForElementToBeVisible(driver,id,timeoutInSecs);
//            return driver.findElement(By.id(id)).isDisplayed();
//        }catch(Exception ex){
//            return false;
//        }
        
        return ActionBy_TempFix.isDisplayed(driver, By.id(id));
    }


    public static boolean isEnabled(WebDriver driver,String id,int timeoutInSecs)
    {

//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            waitForElementToBeVisible(driver,id,timeoutInSecs);
//            return driver.findElement(By.id(id)).isEnabled();
//        }catch(Exception ex){
//            return false;
//        }
        
      return  ActionBy_TempFix.isEnabled(driver, By.id(id));
    }

    public static boolean isSelected(WebDriver driver,String id,int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            waitForElementToBeVisible(driver,id,timeoutInSecs);
//            return driver.findElement(By.id(id)).isSelected();
//        }catch(Exception ex){
//            return false;
//        }
    	
    	return ActionBy_TempFix.isSelected(driver, By.id(id));
    }

    public static boolean isVisible(WebDriver driver,String id,int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
//            return true;
//        }catch (Exception ex)
//        {
//            return false;
//        }
    	
    	return ActionBy_TempFix.isVisible(driver, By.id(id));
    }

    public static boolean isClickable(WebDriver driver,String id,int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver,timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver,timeoutInSecs);
//        try {
//            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.id(id)));
//            return true;
//        }catch (Exception ex)
//        {
//            return false;
//        }
    	
    	return ActionBy_TempFix.isClickable(driver, By.id(id));
    }
    public static void clear(WebDriver driver, String id, int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        driver.findElement(By.id(id)).clear();
    	
    	ActionBy_TempFix.clear(driver, By.id(id));
    }

    public static void clear(WebDriver driver, String id,int index, int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        driver.findElements(By.id(id)).get(index).clear();
    	
    	ActionBy_TempFix.clear(driver, By.id(id), index);
    }

    public static void type(WebDriver driver,String id,String input,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        driver.findElement(By.id(id)).sendKeys(input);
    	
    	ActionBy_TempFix.type(driver, By.id(id), input);
    }

    public static void type(WebDriver driver,String id,int index,String input,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        driver.findElements(By.id(id)).get(index).sendKeys(input);
    	
    	ActionBy_TempFix.type(driver, By.id(id), index, input);
    }

    public static void sendKeys(WebDriver driver,String id,String input,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        driver.findElement(By.id(id)).sendKeys(input);
    	
    	ActionBy_TempFix.sendKeys(driver, By.id(id), input);
    }

    public static void sendKeys(WebDriver driver,String id,int index,String input,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        driver.findElements(By.id(id)).get(index).sendKeys(input);
    	
    	ActionBy_TempFix.sendKeys(driver, By.id(id), index, input);
    }

    public static void selectByText(WebDriver driver,String id,String text,int timeoutInSecs)
    {
//        if(isSelected(driver,id,timeoutInSecs)) {
//            new Select(driver.findElement(By.id(id))).selectByVisibleText(text);
//        }
    	
    	ActionBy_TempFix.selectByText(driver, By.id(id), text);
    }

    public static void selectByText(WebDriver driver,String id,int index,String text,int timeoutInSecs) {
//        if (isSelected(driver, id, timeoutInSecs)) {
//            new Select(driver.findElements(By.id(id)).get(index)).selectByVisibleText(text);
//        }
    	
    	ActionBy_TempFix.selectByText(driver, By.id(id), index, text);
    }

    public static void selectByValue(WebDriver driver,String id,String value,int timeoutInSecs) {
//        if (isSelected(driver, id, timeoutInSecs)) {
//            new Select(driver.findElement(By.id(id))).selectByValue(value);
//        }
    	
    	ActionBy_TempFix.selectByValue(driver, By.id(id), value);
    }

    public static void selectByValue(WebDriver driver,String id,int index,String value,int timeoutInSecs) {
//        if (isSelected(driver, id, timeoutInSecs)) {
//            new Select(driver.findElements(By.id(id)).get(index)).selectByValue(value);
//        }
    	
    	ActionBy_TempFix.selectByValue(driver, By.id(id), index, value);
    }
    public static void selectByIndex(WebDriver driver,String id,int index,int timeoutInSecs) {
//        if (isSelected(driver, id, timeoutInSecs)) {
//            new Select(driver.findElement(By.id(id))).selectByIndex(index);
//        }
    	
    	ActionBy_TempFix.selectByIndex(driver, By.id(id), index);
    }

    public static void selectByIndex(WebDriver driver,String id,int index,int indexNo,int timeoutInSecs) {
//        if (isSelected(driver, id, timeoutInSecs)) {
//            new Select(driver.findElements(By.id(id)).get(index)).selectByIndex(indexNo);
//        }
    	
    	ActionBy_TempFix.selectByIndex(driver, By.id(id), index, indexNo);
    }

    public static void click(WebDriver driver,String id,int timeoutInSecs)
    {
//        waitForElementToBeClickable(driver,id,timeoutInSecs);
//        driver.findElement(By.id(id)).click();
    	
    	ActionBy_TempFix.click(driver, By.id(id));
    }

    public static void click(WebDriver driver,String id,int index,int timeoutInSecs)
    {
//        waitForElementToBeClickable(driver,id,timeoutInSecs);
//        driver.findElements(By.id(id)).get(index).click();
    	
    	ActionBy_TempFix.click(driver, By.id(id), index);
    }

    public static String getText(WebDriver driver,String id,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        return driver.findElement(By.id(id)).getText();
    	
    	return ActionBy_TempFix.getText(driver, By.id(id));
    }

    public static String getText(WebDriver driver,String id,int index,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        return driver.findElements(By.id(id)).get(index).getText();
    	
    	return ActionBy_TempFix.getText(driver, By.id(id), index);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String id,String text,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        return driver.findElement(By.id(id)).getText().contains(text);
    	
    	return ActionBy_TempFix.getTextContainsToVerify(driver, By.id(id), text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String id,int index,String text,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        return driver.findElements(By.id(id)).get(index).getText().contains(text);
    	
    	return ActionBy_TempFix.getTextContainsToVerify(driver, By.id(id), index, text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String id,int index,String text,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        return driver.findElements(By.id(id)).get(index).getText().equals(text);
    	
    	return ActionBy_TempFix.getTextContainsToVerify(driver, By.id(id), index, text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String id,String text,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        return driver.findElement(By.id(id)).getText().equals(text);
    	
    	return ActionBy_TempFix.getTextEqualToVerify(driver, By.id(id), text);
    }

    public static WebElement getElement(WebDriver driver,String id,int timeoutInSecs){
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        return driver.findElement(By.id(id));
    	
    	return ActionBy_TempFix.getElement(driver, By.id(id));
    }

    public static WebElement getElement(WebDriver driver,String id,int index,int timeoutInSecs){
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        return driver.findElements(By.id(id)).get(index);
    	
    	return ActionBy_TempFix.getElement(driver, By.id(id), index);
    }

    public static WebElement getElementInElement(WebDriver driver,WebElement element,String id,int timeoutInSecs){
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        return element.findElement(By.id(id));
    	
    	return ActionBy_TempFix.getElementInElement(driver, element, By.id(id));
    }

    public static WebElement getElementInElements(WebDriver driver,List<WebElement> elements,int index,String id,int timeoutInSecs){
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        return elements.get(index).findElement(By.id(id));
    	
    	return ActionBy_TempFix.getElementInElements(driver, elements, index, By.id(id));
    }


    public static List<WebElement> getElementsInElement(WebDriver driver,WebElement element,String id,int timeoutInSecs){
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        return element.findElements(By.id(id));
    	
    	return ActionBy_TempFix.getElementsInElement(driver, element, By.id(id));
    }

    public static List<WebElement> getElements(WebDriver driver,String id,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        List<WebElement> list = driver.findElements(By.id(id));
//        return list;
    	
    	return ActionBy_TempFix.getElements(driver, By.id(id));
    }

    public static void multiSelectOptions(WebDriver driver, String id, List<String> text, int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,id,timeoutInSecs);
//        List<WebElement> list = driver.findElements(By.id(id));
//        for(int i=0;i<list.size();i++)
//        {
//            for(int j=0;j<text.size();j++)
//            {
//                if(text.get(j).equalsIgnoreCase(list.get(i).getText()))
//                {
//                    list.get(i).click();
//                }
//            }
//        }
    	ActionBy_TempFix.multiSelectOptions(driver, By.id(id), text);
    	
    }

    public static void clickOption(WebDriver driver, String id, String text, int timeoutInSecs)
    {
//        waitForElementToBeClickable(driver,id,timeoutInSecs);
//        List<WebElement> list = driver.findElements(By.id(id));
//        for(int i=0;i<list.size();i++)
//        {
//            if(text.equalsIgnoreCase(list.get(i).getText()))
//            {
//                list.get(i).click();
//                break;
//            }
//        }
    	ActionBy_TempFix.clickOption(driver, By.id(id), text);
    }

    public static void assertVisible(WebDriver driver,String id,int timeoutInSecs)
    {
//        Assert.assertTrue(isVisible(driver,id,timeoutInSecs));
    	ActionBy_TempFix.assertVisible(driver, By.id(id));
    }

    public static void assertVisible(WebDriver driver,String id,String msg,int timeoutInSecs)
    {
//        Assert.assertTrue(msg,isVisible(driver,id,timeoutInSecs));
    	ActionBy_TempFix.assertVisible(driver, By.id(id), msg);
    }

    public static void assertNotVisible(WebDriver driver,String id,int timeoutInSecs)
    {
//        Assert.assertFalse(isVisible(driver,id,timeoutInSecs));
    	ActionBy_TempFix.assertNotVisible(driver, By.id(id));
    }

    public static void assertNotVisible(WebDriver driver,String id,String msg,int timeoutInSecs)
    {
//        Assert.assertFalse(msg,isVisible(driver,id,timeoutInSecs));
    	ActionBy_TempFix.assertNotVisible(driver, By.id(id), msg);
    }

    public static void assertTextEqualsTrue(WebDriver driver,String id,String text,int timeoutInSecs)
    {
//        Assert.assertTrue(getTextEqualToVerify(driver,id,text,timeoutInSecs));
    	ActionBy_TempFix.assertTextEqualsTrue(driver, By.id(id), text);
    }

    public static void assertTextEqualsTrue(WebDriver driver,String id,int index,String text,int timeoutInSecs)
    {
//        Assert.assertTrue(getTextEqualToVerify(driver,id,index,text,timeoutInSecs));
    	ActionBy_TempFix.assertTextEqualsTrue(driver, By.id(id), index, text);
    }

    public static void assertTextContainsTrue(WebDriver driver,String id,String text,int timeoutInSecs)
    {
//        Assert.assertTrue(getTextContainsToVerify(driver,id,text,timeoutInSecs));
    	ActionBy_TempFix.assertTextContainsTrue(driver, By.id(id), text);
    }

    public static void assertTextContainsTrue(WebDriver driver,String id,int index,String text,int timeoutInSecs)
    {
//        Assert.assertTrue(getTextContainsToVerify(driver,id,index,text,timeoutInSecs));
    	ActionBy_TempFix.assertTextContainsTrue(driver, By.id(id), index, text);
    }

    public static void assertEqualsText(WebDriver driver,String id,String text,int timeoutInSecs)
    {
//        Assert.assertEquals(text,getText(driver,id,timeoutInSecs));
    	ActionBy_TempFix.assertEqualsText(driver, By.id(id), text);
    }

    public static void assertEqualsText(WebDriver driver,String id,int index,String text,int timeoutInSecs)
    {
//        Assert.assertEquals(text,getText(driver,id,index,timeoutInSecs));
    	ActionBy_TempFix.assertEqualsText(driver, By.id(id), index, text);
    }

    public static void assertEqualsText(WebDriver driver,String id,String text,String msg,int timeoutInSecs)
    {
//        Assert.assertEquals(msg,text,getText(driver,id,timeoutInSecs));
    	ActionBy_TempFix.assertEqualsText(driver, By.id(id), text);
    }

    public static void assertEqualsText(WebDriver driver,String id,int index,String text,String msg,int timeoutInSecs)
    {
//        Assert.assertEquals(msg,text,getText(driver,id,index,timeoutInSecs));
    	ActionBy_TempFix.assertEqualsText(driver, By.id(id), index, text);
    }

    public static void assertNotEqualsText(WebDriver driver,String id,String text,int timeoutInSecs)
    {
//        Assert.assertNotEquals(text,getText(driver,id,timeoutInSecs));
    	ActionBy_TempFix.assertNotEqualsText(driver, By.id(id), text);
    }

    public static void assertNotEqualsText(WebDriver driver,String id,int index,String text,int timeoutInSecs)
    {
//        Assert.assertNotEquals(text,getText(driver,id,index,timeoutInSecs));
    	ActionBy_TempFix.assertNotEqualsText(driver, By.id(id), index, text);
    }

    public static void assertNotEqualsText(WebDriver driver,String id,String text,String msg,int timeoutInSecs)
    {
//        Assert.assertNotEquals(msg,text,getText(driver,id,timeoutInSecs));
    	ActionBy_TempFix.assertNotEqualsText(driver, By.id(id), text, msg);
    }

    public static void assertNotEqualsText(WebDriver driver,String id,int index,String text,String msg,int timeoutInSecs)
    {
//        Assert.assertNotEquals(msg,text,getText(driver,id,index,timeoutInSecs));
    	ActionBy_TempFix.assertNotEqualsText(driver, By.id(id), index, text, msg);
    }

    
}
