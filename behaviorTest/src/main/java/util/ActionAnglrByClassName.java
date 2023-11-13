package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;

/**
 * Created by skumar on 8/19/2016.
 * For Angular Application Only
 * This class is to perform action for Angular Application using classname locator
 * input to method will be mainly your webdriver instance,classname identifier for HTML element and time out in secs which will check for different pageload strategies (explicit wait,pageload and angular page ready state)
 */
public class ActionAnglrByClassName {

	
	/*********Update for framework upgrade by Nur and Venus**************/
	
	/* @param driver - Pass the webdriver instance
     * @param className - Pass the classname identifier
     * @param timeoutInSecs - Pass the timeout to check page load, page load for angular and explicit wait for element to be visible
     */
    public static void waitForElementToBeVisible(WebDriver driver, String className, int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
        
        ActionBy_TempFix.waitForElementToBeVisible(driver, By.className(className));
    }

    public static void waitForElementToBePresent(WebDriver driver, String className, int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
        
        ActionBy_TempFix.waitForElementToBePresent(driver, By.className(className));
    }


    public static void waitForElementToBeClickable(WebDriver driver, String className, int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.className(className)));
        
        ActionBy_TempFix.waitForElementToBeClickable(driver, By.className(className));
    }

    public static boolean isDisplayed(WebDriver driver,String className,int timeoutInSecs)
    {

//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            waitForElementToBeVisible(driver,className,timeoutInSecs);
//            return driver.findElement(By.className(className)).isDisplayed();
//        }catch(Exception ex){
//            return false;
//        }
        
        return ActionBy_TempFix.isDisplayed(driver, By.className(className));
    }


    public static boolean isEnabled(WebDriver driver,String className,int timeoutInSecs)
    {

//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            waitForElementToBeVisible(driver,className,timeoutInSecs);
//            return driver.findElement(By.className(className)).isEnabled();
//        }catch(Exception ex){
//            return false;
//        }
        
      return  ActionBy_TempFix.isEnabled(driver, By.className(className));
    }

    public static boolean isSelected(WebDriver driver,String className,int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            waitForElementToBeVisible(driver,className,timeoutInSecs);
//            return driver.findElement(By.className(className)).isSelected();
//        }catch(Exception ex){
//            return false;
//        }
    	
    	return ActionBy_TempFix.isSelected(driver, By.className(className));
    }

    public static boolean isVisible(WebDriver driver,String className,int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
//            return true;
//        }catch (Exception ex)
//        {
//            return false;
//        }
    	
    	return ActionBy_TempFix.isVisible(driver, By.className(className));
    }

    public static boolean isClickable(WebDriver driver,String className,int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver,timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver,timeoutInSecs);
//        try {
//            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.className(className)));
//            return true;
//        }catch (Exception ex)
//        {
//            return false;
//        }
    	
    	return ActionBy_TempFix.isClickable(driver, By.className(className));
    }
    public static void clear(WebDriver driver, String className, int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        driver.findElement(By.className(className)).clear();
    	
    	ActionBy_TempFix.clear(driver, By.className(className));
    }

    public static void clear(WebDriver driver, String className,int index, int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        driver.findElements(By.className(className)).get(index).clear();
    	
    	ActionBy_TempFix.clear(driver, By.className(className), index);
    }

    public static void type(WebDriver driver,String className,String input,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        driver.findElement(By.className(className)).sendKeys(input);
    	
    	ActionBy_TempFix.type(driver, By.className(className), input);
    }

    public static void type(WebDriver driver,String className,int index,String input,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        driver.findElements(By.className(className)).get(index).sendKeys(input);
    	
    	ActionBy_TempFix.type(driver, By.className(className), index, input);
    }

    public static void sendKeys(WebDriver driver,String className,String input,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        driver.findElement(By.className(className)).sendKeys(input);
    	
    	ActionBy_TempFix.sendKeys(driver, By.className(className), input);
    }

    public static void sendKeys(WebDriver driver,String className,int index,String input,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        driver.findElements(By.className(className)).get(index).sendKeys(input);
    	
    	ActionBy_TempFix.sendKeys(driver, By.className(className), index, input);
    }

    public static void selectByText(WebDriver driver,String className,String text,int timeoutInSecs)
    {
//        if(isSelected(driver,className,timeoutInSecs)) {
//            new Select(driver.findElement(By.className(className))).selectByVisibleText(text);
//        }
    	
    	ActionBy_TempFix.selectByText(driver, By.className(className), text);
    }

    public static void selectByText(WebDriver driver,String className,int index,String text,int timeoutInSecs) {
//        if (isSelected(driver, className, timeoutInSecs)) {
//            new Select(driver.findElements(By.className(className)).get(index)).selectByVisibleText(text);
//        }
    	
    	ActionBy_TempFix.selectByText(driver, By.className(className), index, text);
    }

    public static void selectByValue(WebDriver driver,String className,String value,int timeoutInSecs) {
//        if (isSelected(driver, className, timeoutInSecs)) {
//            new Select(driver.findElement(By.className(className))).selectByValue(value);
//        }
    	
    	ActionBy_TempFix.selectByValue(driver, By.className(className), value);
    }

    public static void selectByValue(WebDriver driver,String className,int index,String value,int timeoutInSecs) {
//        if (isSelected(driver, className, timeoutInSecs)) {
//            new Select(driver.findElements(By.className(className)).get(index)).selectByValue(value);
//        }
    	
    	ActionBy_TempFix.selectByValue(driver, By.className(className), index, value);
    }
    public static void selectByIndex(WebDriver driver,String className,int index,int timeoutInSecs) {
//        if (isSelected(driver, className, timeoutInSecs)) {
//            new Select(driver.findElement(By.className(className))).selectByIndex(index);
//        }
    	
    	ActionBy_TempFix.selectByIndex(driver, By.className(className), index);
    }

    public static void selectByIndex(WebDriver driver,String className,int index,int indexNo,int timeoutInSecs) {
//        if (isSelected(driver, className, timeoutInSecs)) {
//            new Select(driver.findElements(By.className(className)).get(index)).selectByIndex(indexNo);
//        }
    	
    	ActionBy_TempFix.selectByIndex(driver, By.className(className), index, indexNo);
    }

    public static void click(WebDriver driver,String className,int timeoutInSecs)
    {
//        waitForElementToBeClickable(driver,className,timeoutInSecs);
//        driver.findElement(By.className(className)).click();
    	
    	ActionBy_TempFix.click(driver, By.className(className));
    }

    public static void click(WebDriver driver,String className,int index,int timeoutInSecs)
    {
//        waitForElementToBeClickable(driver,className,timeoutInSecs);
//        driver.findElements(By.className(className)).get(index).click();
    	
    	ActionBy_TempFix.click(driver, By.className(className), index);
    }

    public static String getText(WebDriver driver,String className,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        return driver.findElement(By.className(className)).getText();
    	
    	return ActionBy_TempFix.getText(driver, By.className(className));
    }

    public static String getText(WebDriver driver,String className,int index,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        return driver.findElements(By.className(className)).get(index).getText();
    	
    	return ActionBy_TempFix.getText(driver, By.className(className), index);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String className,String text,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        return driver.findElement(By.className(className)).getText().contains(text);
    	
    	return ActionBy_TempFix.getTextContainsToVerify(driver, By.className(className), text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String className,int index,String text,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        return driver.findElements(By.className(className)).get(index).getText().contains(text);
    	
    	return ActionBy_TempFix.getTextContainsToVerify(driver, By.className(className), index, text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String className,int index,String text,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        return driver.findElements(By.className(className)).get(index).getText().equals(text);
    	
    	return ActionBy_TempFix.getTextContainsToVerify(driver, By.className(className), index, text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String className,String text,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        return driver.findElement(By.className(className)).getText().equals(text);
    	
    	return ActionBy_TempFix.getTextEqualToVerify(driver, By.className(className), text);
    }

    public static WebElement getElement(WebDriver driver,String className,int timeoutInSecs){
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        return driver.findElement(By.className(className));
    	
    	return ActionBy_TempFix.getElement(driver, By.className(className));
    }

    public static WebElement getElement(WebDriver driver,String className,int index,int timeoutInSecs){
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        return driver.findElements(By.className(className)).get(index);
    	
    	return ActionBy_TempFix.getElement(driver, By.className(className), index);
    }

    public static WebElement getElementInElement(WebDriver driver,WebElement element,String className,int timeoutInSecs){
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        return element.findElement(By.className(className));
    	
    	return ActionBy_TempFix.getElementInElement(driver, element, By.className(className));
    }

    public static WebElement getElementInElements(WebDriver driver,List<WebElement> elements,int index,String className,int timeoutInSecs){
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        return elements.get(index).findElement(By.className(className));
    	
    	return ActionBy_TempFix.getElementInElements(driver, elements, index, By.className(className));
    }


    public static List<WebElement> getElementsInElement(WebDriver driver,WebElement element,String className,int timeoutInSecs){
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        return element.findElements(By.className(className));
    	
    	return ActionBy_TempFix.getElementsInElement(driver, element, By.className(className));
    }

    public static List<WebElement> getElements(WebDriver driver,String className,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        List<WebElement> list = driver.findElements(By.className(className));
//        return list;
    	
    	return ActionBy_TempFix.getElements(driver, By.className(className));
    }

    public static void multiSelectOptions(WebDriver driver, String className, List<String> text, int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,className,timeoutInSecs);
//        List<WebElement> list = driver.findElements(By.className(className));
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
    	ActionBy_TempFix.multiSelectOptions(driver, By.className(className), text);
    	
    }

    public static void clickOption(WebDriver driver, String className, String text, int timeoutInSecs)
    {
//        waitForElementToBeClickable(driver,className,timeoutInSecs);
//        List<WebElement> list = driver.findElements(By.className(className));
//        for(int i=0;i<list.size();i++)
//        {
//            if(text.equalsIgnoreCase(list.get(i).getText()))
//            {
//                list.get(i).click();
//                break;
//            }
//        }
    	ActionBy_TempFix.clickOption(driver, By.className(className), text);
    }

    public static void assertVisible(WebDriver driver,String className,int timeoutInSecs)
    {
//        Assert.assertTrue(isVisible(driver,className,timeoutInSecs));
    	ActionBy_TempFix.assertVisible(driver, By.className(className));
    }

    public static void assertVisible(WebDriver driver,String className,String msg,int timeoutInSecs)
    {
//        Assert.assertTrue(msg,isVisible(driver,className,timeoutInSecs));
    	ActionBy_TempFix.assertVisible(driver, By.className(className), msg);
    }

    public static void assertNotVisible(WebDriver driver,String className,int timeoutInSecs)
    {
//        Assert.assertFalse(isVisible(driver,className,timeoutInSecs));
    	ActionBy_TempFix.assertNotVisible(driver, By.className(className));
    }

    public static void assertNotVisible(WebDriver driver,String className,String msg,int timeoutInSecs)
    {
//        Assert.assertFalse(msg,isVisible(driver,className,timeoutInSecs));
    	ActionBy_TempFix.assertNotVisible(driver, By.className(className), msg);
    }

    public static void assertTextEqualsTrue(WebDriver driver,String className,String text,int timeoutInSecs)
    {
//        Assert.assertTrue(getTextEqualToVerify(driver,className,text,timeoutInSecs));
    	ActionBy_TempFix.assertTextEqualsTrue(driver, By.className(className), text);
    }

    public static void assertTextEqualsTrue(WebDriver driver,String className,int index,String text,int timeoutInSecs)
    {
//        Assert.assertTrue(getTextEqualToVerify(driver,className,index,text,timeoutInSecs));
    	ActionBy_TempFix.assertTextEqualsTrue(driver, By.className(className), index, text);
    }

    public static void assertTextContainsTrue(WebDriver driver,String className,String text,int timeoutInSecs)
    {
//        Assert.assertTrue(getTextContainsToVerify(driver,className,text,timeoutInSecs));
    	ActionBy_TempFix.assertTextContainsTrue(driver, By.className(className), text);
    }

    public static void assertTextContainsTrue(WebDriver driver,String className,int index,String text,int timeoutInSecs)
    {
//        Assert.assertTrue(getTextContainsToVerify(driver,className,index,text,timeoutInSecs));
    	ActionBy_TempFix.assertTextContainsTrue(driver, By.className(className), index, text);
    }

    public static void assertEqualsText(WebDriver driver,String className,String text,int timeoutInSecs)
    {
//        Assert.assertEquals(text,getText(driver,className,timeoutInSecs));
    	ActionBy_TempFix.assertEqualsText(driver, By.className(className), text);
    }

    public static void assertEqualsText(WebDriver driver,String className,int index,String text,int timeoutInSecs)
    {
//        Assert.assertEquals(text,getText(driver,className,index,timeoutInSecs));
    	ActionBy_TempFix.assertEqualsText(driver, By.className(className), index, text);
    }

    public static void assertEqualsText(WebDriver driver,String className,String text,String msg,int timeoutInSecs)
    {
//        Assert.assertEquals(msg,text,getText(driver,className,timeoutInSecs));
    	ActionBy_TempFix.assertEqualsText(driver, By.className(className), text);
    }

    public static void assertEqualsText(WebDriver driver,String className,int index,String text,String msg,int timeoutInSecs)
    {
//        Assert.assertEquals(msg,text,getText(driver,className,index,timeoutInSecs));
    	ActionBy_TempFix.assertEqualsText(driver, By.className(className), index, text);
    }

    public static void assertNotEqualsText(WebDriver driver,String className,String text,int timeoutInSecs)
    {
//        Assert.assertNotEquals(text,getText(driver,className,timeoutInSecs));
    	ActionBy_TempFix.assertNotEqualsText(driver, By.className(className), text);
    }

    public static void assertNotEqualsText(WebDriver driver,String className,int index,String text,int timeoutInSecs)
    {
//        Assert.assertNotEquals(text,getText(driver,className,index,timeoutInSecs));
    	ActionBy_TempFix.assertNotEqualsText(driver, By.className(className), index, text);
    }

    public static void assertNotEqualsText(WebDriver driver,String className,String text,String msg,int timeoutInSecs)
    {
//        Assert.assertNotEquals(msg,text,getText(driver,className,timeoutInSecs));
    	ActionBy_TempFix.assertNotEqualsText(driver, By.className(className), text, msg);
    }

    public static void assertNotEqualsText(WebDriver driver,String className,int index,String text,String msg,int timeoutInSecs)
    {
//        Assert.assertNotEquals(msg,text,getText(driver,className,index,timeoutInSecs));
    	ActionBy_TempFix.assertNotEqualsText(driver, By.className(className), index, text, msg);
    }

}
