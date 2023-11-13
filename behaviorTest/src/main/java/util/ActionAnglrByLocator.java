package util;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.PageLoad;

import java.util.List;

/**
 * Created by skumar on 8/19/2016.
 * For Angular Application Only
 * This class is to perform action for Angular Application using locator (E.g By)
 * input to method will be mainly your webdriver instance,locator and time out in secs which will check for different pageload strategies (explicit wait,pageload and angular page ready state)
 */
public class ActionAnglrByLocator {

    public static void waitForElementToBeVisible(WebDriver driver, By locator, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForElementToBePresent(WebDriver driver, By locator, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(locator));
    }


    public static void waitForElementToBeClickable(WebDriver driver, By locator, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean isDisplayed(WebDriver driver,By locator,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,locator,timeoutInSecs);
            return driver.findElement(locator).isDisplayed();
        }catch(Exception ex){
            return false;
        }
    }


    public static boolean isEnabled(WebDriver driver,By locator,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,locator,timeoutInSecs);
            return driver.findElement(locator).isEnabled();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isSelected(WebDriver driver,By locator,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,locator,timeoutInSecs);
            return driver.findElement(locator).isSelected();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isVisible(WebDriver driver,By locator,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean isClickable(WebDriver driver,By locator,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver,timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver,timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }
    public static void clear(WebDriver driver, By locator, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        driver.findElement(locator).clear();
    }

    public static void clear(WebDriver driver, By locator,int index, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        driver.findElements(locator).get(index).clear();
    }

    public static void type(WebDriver driver,By locator,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        driver.findElement(locator).sendKeys(input);
    }

    public static void type(WebDriver driver,By locator,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        driver.findElements(locator).get(index).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,By locator,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        driver.findElement(locator).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,By locator,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        driver.findElements(locator).get(index).sendKeys(input);
    }

    public static void selectByText(WebDriver driver,By locator,String text,int timeoutInSecs) {
        if (isSelected(driver, locator, timeoutInSecs)) {
            new Select(driver.findElement(locator)).selectByVisibleText(text);
        }
    }

    public static void selectByText(WebDriver driver,By locator,int index,String text,int timeoutInSecs) {
        if (isSelected(driver, locator, timeoutInSecs)) {
            new Select(driver.findElements(locator).get(index)).selectByVisibleText(text);
        }
    }

    public static void selectByValue(WebDriver driver,By locator,String value,int timeoutInSecs) {
        if (isSelected(driver, locator, timeoutInSecs)) {
            new Select(driver.findElement(locator)).selectByValue(value);
        }
    }

    public static void selectByValue(WebDriver driver,By locator,int index,String value,int timeoutInSecs) {
        if (isSelected(driver, locator, timeoutInSecs)) {
            new Select(driver.findElements(locator).get(index)).selectByValue(value);
        }
    }

    public static void selectByIndex(WebDriver driver,By locator,int index,int timeoutInSecs) {
        if (isSelected(driver, locator, timeoutInSecs)) {
            new Select(driver.findElement(locator)).selectByIndex(index);
        }
    }

    public static void selectByIndex(WebDriver driver,By locator,int index,int indexNo,int timeoutInSecs) {
        if (isSelected(driver, locator, timeoutInSecs)) {
            new Select(driver.findElements(locator).get(index)).selectByIndex(indexNo);
        }
    }

    public static void click(WebDriver driver,By locator,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,locator,timeoutInSecs);
        driver.findElement(locator).click();
    }

    public static void click(WebDriver driver,By locator,int index,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,locator,timeoutInSecs);
        driver.findElements(locator).get(index).click();
    }

    public static String getText(WebDriver driver,By locator,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        return driver.findElement(locator).getText();
    }

    public static String getText(WebDriver driver,By locator,int index,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        return driver.findElements(locator).get(index).getText();
    }

    public static boolean getTextContainsToVerify(WebDriver driver,By locator,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        return driver.findElement(locator).getText().contains(text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,By locator,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        return driver.findElements(locator).get(index).getText().contains(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,By locator,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        return driver.findElements(locator).get(index).getText().equals(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,By locator,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        return driver.findElement(locator).getText().equals(text);
    }


    public static WebElement getElement(WebDriver driver,By locator,int timeoutInSecs){
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        return driver.findElement(locator);
    }

    public static WebElement getElement(WebDriver driver,By locator,int index,int timeoutInSecs){
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        return driver.findElements(locator).get(index);
    }

    public static List<WebElement> getElements(WebDriver driver,By locator,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        List<WebElement> list = driver.findElements(locator);
        return list;
    }

    public static WebElement getElementInElement(WebDriver driver,WebElement element,By locator,int timeoutInSecs){
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        return element.findElement(locator);
    }

    public static WebElement getElementInElements(WebDriver driver,List<WebElement> elements,int index,By locator,int timeoutInSecs){
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        return elements.get(index).findElement(locator);
    }


    public static List<WebElement> getElementsInElement(WebDriver driver,WebElement element,By locator,int timeoutInSecs){
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        return element.findElements(locator);
    }

    public static void multiSelectOptions(WebDriver driver, By locator, List<String> text, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,locator,timeoutInSecs);
        List<WebElement> list = driver.findElements(locator);
        for(int i=0;i<list.size();i++)
        {
            for(int j=0;j<text.size();j++)
            {
                if(text.get(j).equalsIgnoreCase(list.get(i).getText()))
                {
                    list.get(i).click();
                }
            }
        }
    }

    public static void clickOption(WebDriver driver, By locator, String text, int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,locator,timeoutInSecs);
        List<WebElement> list = driver.findElements(locator);
        for(int i=0;i<list.size();i++)
        {
            if(text.equalsIgnoreCase(list.get(i).getText()))
            {
                list.get(i).click();
                break;
            }
        }
    }

    public static void assertVisible(WebDriver driver,By locator,int timeoutInSecs)
    {
        Assert.assertTrue(isVisible(driver,locator,timeoutInSecs));
    }

    public static void assertVisible(WebDriver driver,By locator,String msg,int timeoutInSecs)
    {
        Assert.assertTrue(msg,isVisible(driver,locator,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,By locator,int timeoutInSecs)
    {
        Assert.assertFalse(isVisible(driver,locator,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,By locator,String msg,int timeoutInSecs)
    {
        Assert.assertFalse(msg,isVisible(driver,locator,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,By locator,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,locator,text,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,By locator,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,locator,index,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,By locator,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,locator,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,By locator,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,locator,index,text,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,By locator,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,locator,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,By locator,int index,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,locator,index,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,By locator,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,locator,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,By locator,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,locator,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,By locator,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,locator,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,By locator,int index,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,locator,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,By locator,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,locator,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,By locator,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,locator,index,timeoutInSecs));
    }
}
