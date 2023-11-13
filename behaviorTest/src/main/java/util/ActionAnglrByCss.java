package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;


public class ActionAnglrByCss {

	
	/*********Update for framework upgrade by Nur and Venus**************/

    /**
     *
     * @param driver - Pass the webdriver instance
     * @param cssSelector - Pass the cssSelector identifier
     * @param timeoutInSecs - Pass the timeout to check page load, page load for angular and explicit wait for element to be visible
     */
    public static void waitForElementToBeVisible(WebDriver driver, String cssSelector, int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
        
        ActionBy_TempFix.waitForElementToBeVisible(driver, By.cssSelector(cssSelector));
    }

    public static void waitForElementToBePresent(WebDriver driver, String cssSelector, int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
        
        ActionBy_TempFix.waitForElementToBePresent(driver, By.cssSelector(cssSelector));
    }


    public static void waitForElementToBeClickable(WebDriver driver, String cssSelector, int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
        
        ActionBy_TempFix.waitForElementToBeClickable(driver, By.cssSelector(cssSelector));
    }

    public static boolean isDisplayed(WebDriver driver,String cssSelector,int timeoutInSecs)
    {

//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//            return driver.findElement(By.cssSelector(cssSelector)).isDisplayed();
//        }catch(Exception ex){
//            return false;
//        }
        
        return ActionBy_TempFix.isDisplayed(driver, By.cssSelector(cssSelector));
    }


    public static boolean isEnabled(WebDriver driver,String cssSelector,int timeoutInSecs)
    {

//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//            return driver.findElement(By.cssSelector(cssSelector)).isEnabled();
//        }catch(Exception ex){
//            return false;
//        }
        
      return  ActionBy_TempFix.isEnabled(driver, By.cssSelector(cssSelector));
    }

    public static boolean isSelected(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//            return driver.findElement(By.cssSelector(cssSelector)).isSelected();
//        }catch(Exception ex){
//            return false;
//        }
    	
    	return ActionBy_TempFix.isSelected(driver, By.cssSelector(cssSelector));
    }

    public static boolean isVisible(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
//            return true;
//        }catch (Exception ex)
//        {
//            return false;
//        }
    	
    	return ActionBy_TempFix.isVisible(driver, By.cssSelector(cssSelector));
    }

    public static boolean isClickable(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
//        PageLoad.checkPageLoad(driver,timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver,timeoutInSecs);
//        try {
//            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
//            return true;
//        }catch (Exception ex)
//        {
//            return false;
//        }
    	
    	return ActionBy_TempFix.isClickable(driver, By.cssSelector(cssSelector));
    }
    public static void clear(WebDriver driver, String cssSelector, int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        driver.findElement(By.cssSelector(cssSelector)).clear();
    	
    	ActionBy_TempFix.clear(driver, By.cssSelector(cssSelector));
    }

    public static void clear(WebDriver driver, String cssSelector,int index, int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        driver.findElements(By.cssSelector(cssSelector)).get(index).clear();
    	
    	ActionBy_TempFix.clear(driver, By.cssSelector(cssSelector), index);
    }

    public static void type(WebDriver driver,String cssSelector,String input,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        driver.findElement(By.cssSelector(cssSelector)).sendKeys(input);
    	
    	ActionBy_TempFix.type(driver, By.cssSelector(cssSelector), input);
    }

    public static void type(WebDriver driver,String cssSelector,int index,String input,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        driver.findElements(By.cssSelector(cssSelector)).get(index).sendKeys(input);
    	
    	ActionBy_TempFix.type(driver, By.cssSelector(cssSelector), index, input);
    }

    public static void sendKeys(WebDriver driver,String cssSelector,String input,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        driver.findElement(By.cssSelector(cssSelector)).sendKeys(input);
    	
    	ActionBy_TempFix.sendKeys(driver, By.cssSelector(cssSelector), input);
    }

    public static void sendKeys(WebDriver driver,String cssSelector,int index,String input,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        driver.findElements(By.cssSelector(cssSelector)).get(index).sendKeys(input);
    	
    	ActionBy_TempFix.sendKeys(driver, By.cssSelector(cssSelector), index, input);
    }

    public static void selectByText(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
//        if(isSelected(driver,cssSelector,timeoutInSecs)) {
//            new Select(driver.findElement(By.cssSelector(cssSelector))).selectByVisibleText(text);
//        }
    	
    	ActionBy_TempFix.selectByText(driver, By.cssSelector(cssSelector), text);
    }

    public static void selectByText(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs) {
//        if (isSelected(driver, cssSelector, timeoutInSecs)) {
//            new Select(driver.findElements(By.cssSelector(cssSelector)).get(index)).selectByVisibleText(text);
//        }
    	
    	ActionBy_TempFix.selectByText(driver, By.cssSelector(cssSelector), index, text);
    }

    public static void selectByValue(WebDriver driver,String cssSelector,String value,int timeoutInSecs) {
//        if (isSelected(driver, cssSelector, timeoutInSecs)) {
//            new Select(driver.findElement(By.cssSelector(cssSelector))).selectByValue(value);
//        }
    	
    	ActionBy_TempFix.selectByValue(driver, By.cssSelector(cssSelector), value);
    }

    public static void selectByValue(WebDriver driver,String cssSelector,int index,String value,int timeoutInSecs) {
//        if (isSelected(driver, cssSelector, timeoutInSecs)) {
//            new Select(driver.findElements(By.cssSelector(cssSelector)).get(index)).selectByValue(value);
//        }
    	
    	ActionBy_TempFix.selectByValue(driver, By.cssSelector(cssSelector), index, value);
    }
    public static void selectByIndex(WebDriver driver,String cssSelector,int index,int timeoutInSecs) {
//        if (isSelected(driver, cssSelector, timeoutInSecs)) {
//            new Select(driver.findElement(By.cssSelector(cssSelector))).selectByIndex(index);
//        }
    	
    	ActionBy_TempFix.selectByIndex(driver, By.cssSelector(cssSelector), index);
    }

    public static void selectByIndex(WebDriver driver,String cssSelector,int index,int indexNo,int timeoutInSecs) {
//        if (isSelected(driver, cssSelector, timeoutInSecs)) {
//            new Select(driver.findElements(By.cssSelector(cssSelector)).get(index)).selectByIndex(indexNo);
//        }
    	
    	ActionBy_TempFix.selectByIndex(driver, By.cssSelector(cssSelector), index, indexNo);
    }

    public static void click(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
//        waitForElementToBeClickable(driver,cssSelector,timeoutInSecs);
//        driver.findElement(By.cssSelector(cssSelector)).click();
    	
    	ActionBy_TempFix.click(driver, By.cssSelector(cssSelector));
    }

    public static void click(WebDriver driver,String cssSelector,int index,int timeoutInSecs)
    {
//        waitForElementToBeClickable(driver,cssSelector,timeoutInSecs);
//        driver.findElements(By.cssSelector(cssSelector)).get(index).click();
    	
    	ActionBy_TempFix.click(driver, By.cssSelector(cssSelector), index);
    }

    public static String getText(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        return driver.findElement(By.cssSelector(cssSelector)).getText();
    	
    	return ActionBy_TempFix.getText(driver, By.cssSelector(cssSelector));
    }

    public static String getText(WebDriver driver,String cssSelector,int index,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        return driver.findElements(By.cssSelector(cssSelector)).get(index).getText();
    	
    	return ActionBy_TempFix.getText(driver, By.cssSelector(cssSelector), index);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        return driver.findElement(By.cssSelector(cssSelector)).getText().contains(text);
    	
    	return ActionBy_TempFix.getTextContainsToVerify(driver, By.cssSelector(cssSelector), text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        return driver.findElements(By.cssSelector(cssSelector)).get(index).getText().contains(text);
    	
    	return ActionBy_TempFix.getTextContainsToVerify(driver, By.cssSelector(cssSelector), index, text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        return driver.findElements(By.cssSelector(cssSelector)).get(index).getText().equals(text);
    	
    	return ActionBy_TempFix.getTextContainsToVerify(driver, By.cssSelector(cssSelector), index, text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        return driver.findElement(By.cssSelector(cssSelector)).getText().equals(text);
    	
    	return ActionBy_TempFix.getTextEqualToVerify(driver, By.cssSelector(cssSelector), text);
    }

    public static WebElement getElement(WebDriver driver,String cssSelector,int timeoutInSecs){
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        return driver.findElement(By.cssSelector(cssSelector));
    	
    	return ActionBy_TempFix.getElement(driver, By.cssSelector(cssSelector));
    }

    public static WebElement getElement(WebDriver driver,String cssSelector,int index,int timeoutInSecs){
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        return driver.findElements(By.cssSelector(cssSelector)).get(index);
    	
    	return ActionBy_TempFix.getElement(driver, By.cssSelector(cssSelector), index);
    }

    public static WebElement getElementInElement(WebDriver driver,WebElement element,String cssSelector,int timeoutInSecs){
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        return element.findElement(By.cssSelector(cssSelector));
    	
    	return ActionBy_TempFix.getElementInElement(driver, element, By.cssSelector(cssSelector));
    }

    public static WebElement getElementInElements(WebDriver driver,List<WebElement> elements,int index,String cssSelector,int timeoutInSecs){
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        return elements.get(index).findElement(By.cssSelector(cssSelector));
    	
    	return ActionBy_TempFix.getElementInElements(driver, elements, index, By.cssSelector(cssSelector));
    }


    public static List<WebElement> getElementsInElement(WebDriver driver,WebElement element,String cssSelector,int timeoutInSecs){
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        return element.findElements(By.cssSelector(cssSelector));
    	
    	return ActionBy_TempFix.getElementsInElement(driver, element, By.cssSelector(cssSelector));
    }

    public static List<WebElement> getElements(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        List<WebElement> list = driver.findElements(By.cssSelector(cssSelector));
//        return list;
    	
    	return ActionBy_TempFix.getElements(driver, By.cssSelector(cssSelector));
    }

    public static void multiSelectOptions(WebDriver driver, String cssSelector, List<String> text, int timeoutInSecs)
    {
//        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
//        List<WebElement> list = driver.findElements(By.cssSelector(cssSelector));
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
    	ActionBy_TempFix.multiSelectOptions(driver, By.cssSelector(cssSelector), text);
    	
    }

    public static void clickOption(WebDriver driver, String cssSelector, String text, int timeoutInSecs)
    {
//        waitForElementToBeClickable(driver,cssSelector,timeoutInSecs);
//        List<WebElement> list = driver.findElements(By.cssSelector(cssSelector));
//        for(int i=0;i<list.size();i++)
//        {
//            if(text.equalsIgnoreCase(list.get(i).getText()))
//            {
//                list.get(i).click();
//                break;
//            }
//        }
    	ActionBy_TempFix.clickOption(driver, By.cssSelector(cssSelector), text);
    }

    public static void assertVisible(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
//        Assert.assertTrue(isVisible(driver,cssSelector,timeoutInSecs));
    	ActionBy_TempFix.assertVisible(driver, By.cssSelector(cssSelector));
    }

    public static void assertVisible(WebDriver driver,String cssSelector,String msg,int timeoutInSecs)
    {
//        Assert.assertTrue(msg,isVisible(driver,cssSelector,timeoutInSecs));
    	ActionBy_TempFix.assertVisible(driver, By.cssSelector(cssSelector), msg);
    }

    public static void assertNotVisible(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
//        Assert.assertFalse(isVisible(driver,cssSelector,timeoutInSecs));
    	ActionBy_TempFix.assertNotVisible(driver, By.cssSelector(cssSelector));
    }

    public static void assertNotVisible(WebDriver driver,String cssSelector,String msg,int timeoutInSecs)
    {
//        Assert.assertFalse(msg,isVisible(driver,cssSelector,timeoutInSecs));
    	ActionBy_TempFix.assertNotVisible(driver, By.cssSelector(cssSelector), msg);
    }

    public static void assertTextEqualsTrue(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
//        Assert.assertTrue(getTextEqualToVerify(driver,cssSelector,text,timeoutInSecs));
    	ActionBy_TempFix.assertTextEqualsTrue(driver, By.cssSelector(cssSelector), text);
    }

    public static void assertTextEqualsTrue(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs)
    {
//        Assert.assertTrue(getTextEqualToVerify(driver,cssSelector,index,text,timeoutInSecs));
    	ActionBy_TempFix.assertTextEqualsTrue(driver, By.cssSelector(cssSelector), index, text);
    }

    public static void assertTextContainsTrue(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
//        Assert.assertTrue(getTextContainsToVerify(driver,cssSelector,text,timeoutInSecs));
    	ActionBy_TempFix.assertTextContainsTrue(driver, By.cssSelector(cssSelector), text);
    }

    public static void assertTextContainsTrue(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs)
    {
//        Assert.assertTrue(getTextContainsToVerify(driver,cssSelector,index,text,timeoutInSecs));
    	ActionBy_TempFix.assertTextContainsTrue(driver, By.cssSelector(cssSelector), index, text);
    }

    public static void assertEqualsText(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
//        Assert.assertEquals(text,getText(driver,cssSelector,timeoutInSecs));
    	ActionBy_TempFix.assertEqualsText(driver, By.cssSelector(cssSelector), text);
    }

    public static void assertEqualsText(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs)
    {
//        Assert.assertEquals(text,getText(driver,cssSelector,index,timeoutInSecs));
    	ActionBy_TempFix.assertEqualsText(driver, By.cssSelector(cssSelector), index, text);
    }

    public static void assertEqualsText(WebDriver driver,String cssSelector,String text,String msg,int timeoutInSecs)
    {
//        Assert.assertEquals(msg,text,getText(driver,cssSelector,timeoutInSecs));
    	ActionBy_TempFix.assertEqualsText(driver, By.cssSelector(cssSelector), text);
    }

    public static void assertEqualsText(WebDriver driver,String cssSelector,int index,String text,String msg,int timeoutInSecs)
    {
//        Assert.assertEquals(msg,text,getText(driver,cssSelector,index,timeoutInSecs));
    	ActionBy_TempFix.assertEqualsText(driver, By.cssSelector(cssSelector), index, text);
    }

    public static void assertNotEqualsText(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
//        Assert.assertNotEquals(text,getText(driver,cssSelector,timeoutInSecs));
    	ActionBy_TempFix.assertNotEqualsText(driver, By.cssSelector(cssSelector), text);
    }

    public static void assertNotEqualsText(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs)
    {
//        Assert.assertNotEquals(text,getText(driver,cssSelector,index,timeoutInSecs));
    	ActionBy_TempFix.assertNotEqualsText(driver, By.cssSelector(cssSelector), index, text);
    }

    public static void assertNotEqualsText(WebDriver driver,String cssSelector,String text,String msg,int timeoutInSecs)
    {
//        Assert.assertNotEquals(msg,text,getText(driver,cssSelector,timeoutInSecs));
    	ActionBy_TempFix.assertNotEqualsText(driver, By.cssSelector(cssSelector), text, msg);
    }

    public static void assertNotEqualsText(WebDriver driver,String cssSelector,int index,String text,String msg,int timeoutInSecs)
    {
//        Assert.assertNotEquals(msg,text,getText(driver,cssSelector,index,timeoutInSecs));
    	ActionBy_TempFix.assertNotEqualsText(driver, By.cssSelector(cssSelector), index, text, msg);
    }

    
    
}
